package com.ivmai.kehu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ivm.entity.TKehu;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.MD5;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

public class ChangePasswordActivity extends AppCompatActivity {

//    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    static  final int XIUGAIMIMA_SUCCESS=300;
    static  final int XIUGAIMIMA_FAILED=301;
    static  final int EXCEPTION=404;

    private TextView title_text;
    private EditText current_pswd;
    private EditText pswd;
    private EditText repswd;
    private Button   button;

    public void bindView() {
        title_text=(TextView)findViewById(R.id.title_name);
        current_pswd=(EditText)findViewById(R.id.change_current_pswd);//当前密码输入框
        pswd=(EditText)findViewById(R.id.change_pswd);//新密码输入框
        repswd=(EditText)findViewById(R.id.change_repswd);//重复新密码输入框
        button=(Button)findViewById(R.id.change_password_button);//确认修改按钮
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        bindView();
        title_text.setText("修改密码");
        TransformationMethod method = PasswordTransformationMethod.getInstance();//密码暗文显示
        current_pswd.setTransformationMethod(method);
        pswd.setTransformationMethod(method);
        repswd.setTransformationMethod(method);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(ChangePasswordActivity.this)) {
                String   currentpswd=current_pswd.getText().toString() ;
                final   String   changepswd=pswd.getText().toString();
                String    changerepswd=repswd.getText().toString();
                //判断当前密码输入正确且非空

                    String encrypt = "";
                    try {
                        encrypt = MD5.md5Encode(currentpswd);
                    } catch (Exception e) {
                        e.printStackTrace();
                        encrypt="";
                    }

                    if(!currentpswd.equals("")&&encrypt.equals(IvmApplication.kehu.khMima)){
                    //判断密码相同且非空
                    if(!changepswd.equals("")&&!changerepswd.equals("")&&changepswd.equals(changerepswd)){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("zsw","inRun");
                                TKehu tKehu = new TKehu();
                                tKehu.khMima = changepswd;
                                tKehu.kHID = IvmApplication.kehu.kHID;
                                String code= OkhttpUtils.post(tKehu, IP.SERVERURL+"k/KHUpdate");
//                                boolean islegal = postJson(changepswd);
//                                log.d("islegal",""+islegal);
                                Message msg = new Message();
                                switch (code){
                                    case "\"200\"":
                                        msg.what = XIUGAIMIMA_SUCCESS;
                                        break;
                                    case "EXCEPTION":
                                        msg.what=EXCEPTION;
                                        break;
                                    default:
                                        msg.what = XIUGAIMIMA_FAILED;
                                        break;
                                }
//                                if(code.equals("\"200\"")){
//                                    msg.what = XIUGAIMIMA_SUCCESS;
//                                }else{
//                                    msg.what = XIUGAIMIMA_FAILED;
//                                }
//                                if(islegal){
//                                    msg.what = XIUGAIMIMA_SUCCESS;
//                                }else{
//                                    msg.what = XIUGAIMIMA_FAILED;
//                                }
                                myhandle.sendMessage(msg);
                            }
                        }).start();

                    }else {
                        Toast.makeText(getApplicationContext(),"重复密码输入不正确",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"当前密码输入不正确",Toast.LENGTH_SHORT).show();
                }
                }else {
                    ToastUtil.showToast(ChangePasswordActivity.this,"当前无网络");
                }
            }

        });
    }


    Handler myhandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case XIUGAIMIMA_SUCCESS:
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case XIUGAIMIMA_FAILED:
                    Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_LONG).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(ChangePasswordActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };
}

