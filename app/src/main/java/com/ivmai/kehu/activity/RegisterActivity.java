package com.ivmai.kehu.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivm.entity.TKehu;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.OkhttpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

import okhttp3.MediaType;

public class RegisterActivity extends AppCompatActivity {
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    static  final int ZHUCE_SUCCESS=300;
    static  final int ZHUCE_FAILED=301;
    static  final int EXCEPTION=404;

    String registerPassword="";
    String registerTel="";
    String repeatPassword="";
    TKehu tKehu = new TKehu();

    private  Button registerButton;
    private TextView xieyi_content;
    private ImageView back;
    private CheckBox xieyi_checkbox;

    Handler myhander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ZHUCE_SUCCESS:
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("registerPassword",registerPassword);
                    intent.putExtra("registerTel",registerTel);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                    break;
                case ZHUCE_FAILED:
                    Toast.makeText(getApplicationContext(),"手机号已存在，注册失败",Toast.LENGTH_LONG).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(RegisterActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * @param telNum
     * @return 判断是否为合法手机号
     */
    public static boolean isMobileNum(String telNum){
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //依次获取布局的各个组件
        registerButton=(Button)findViewById(R.id.register_button);
        xieyi_content=(TextView)findViewById(R.id.xieyi_content);
        back=(ImageView)findViewById(R.id.register_back);
        xieyi_checkbox=(CheckBox)findViewById(R.id.xieyi_checkbox);
        final EditText register_name=(EditText)findViewById(R.id.register_name);
        final EditText register_password=(EditText)findViewById(R.id.register_password);
        final EditText repeat_password=(EditText)findViewById(R.id.repeat_password);


        xieyi_content.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
        xieyi_content.getPaint().setAntiAlias(true);//抗锯齿

          back.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  finish();
              }
          });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (NetWork.isNetwork(RegisterActivity.this)) {
                    if (i == R.id.register_button) {
                        registerPassword = register_password.getText().toString();
                        registerTel = register_name.getText().toString();
                        repeatPassword = repeat_password.getText().toString();
                        if (registerTel.equals("")) {
                            Toast.makeText(getApplicationContext(), "请您输入手机号！", Toast.LENGTH_SHORT).show();
//                            register_name.requestFocus();
                            return;
                        }
                        if (!isMobileNum(registerTel)) {
                            Toast.makeText(getApplicationContext(), "手机号格式不合法！", Toast.LENGTH_SHORT).show();
//                            register_name.requestFocus();
                            return;
                        }
                        if (registerPassword.equals("")) {
                            Toast.makeText(getApplicationContext(), "请您输入密码！", Toast.LENGTH_SHORT).show();
//                            register_password.requestFocus();
                            return;
                        }
                        if (repeatPassword.equals("")) {
                            Toast.makeText(getApplicationContext(), "请您确认密码！", Toast.LENGTH_SHORT).show();
//                            repeat_password.requestFocus();
                            return;
                        }
                        if(!xieyi_checkbox.isChecked()){
                            Toast.makeText(getApplicationContext(), "同意用户协议后注册！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!(registerPassword.equals(repeatPassword))) {
                            Toast.makeText(getApplicationContext(), "两次密码输入不一致，重新输入！", Toast.LENGTH_SHORT).show();
                            return;
                        } else {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {

//                                    boolean islegal = postJson();
                                    //创建tkehu对象，并将其转为json数据
                                    tKehu = new TKehu();
                                    tKehu.khTel = registerTel;
                                    tKehu.khMima = registerPassword;
                                    String code= OkhttpUtils.postResult(tKehu, IP.SERVERURL+"k/KHRegister");
                                    Message msg = new Message();
                                    switch (code){
                                        case "\"200\"":
                                            msg.what =  ZHUCE_SUCCESS;
                                            break;
                                        case "EXCEPTION":
                                            msg.what = EXCEPTION;
                                            break;
                                        default:
                                            msg.what=ZHUCE_FAILED;
                                            break;
                                    }
                                    myhander.sendMessage(msg);
                                }
                            }).start();
                        }
                    }
                }else {
                    ToastUtil.showToast(RegisterActivity.this,"当前无网络");
                }
            }

        });
    }
}
