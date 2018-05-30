package com.ivmai.kehu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ivmai.kehu.R;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.CountDownTimerUtils;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.HBshouji;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePhonubrActivity extends AppCompatActivity {


    static  final int YANZHENGMAHUOQU_SUCCESS=300;
    static  final int YANZHENGMAHUOQU_FAILED=301;
    static  final int YANZHENG_SUCCESS=302;
    static  final int YANZHENGMA_GUOQI=303;
    static  final int YANZHENGMA_CUOWU=304;
    static  final int SHOUJIHAO_NO_USE=305;
    static  final int EXCEPTION=404;
    private  EditText input_phone_numb;
    private  Button get_code_but;
    private  EditText input_code;
    private  Button submit_but;
    private  TextView title_text;

    private  String authcode;
    private  String newtel;

    protected  void binview(){
        input_phone_numb=(EditText)findViewById(R.id.phone_numb_input);
        input_code=(EditText)findViewById(R.id.code_input);
        get_code_but=(Button)findViewById(R.id.get_code_but);
        submit_but=(Button)findViewById(R.id.phone_submit_but);
        title_text=(TextView)findViewById(R.id.title_name);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_phonubr);
        binview();
        title_text.setText("换绑手机");


        final CountDownTimerUtils mCountDownTimerUtils  = new CountDownTimerUtils(get_code_but, 60000, 1000);


        get_code_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(ChangePhonubrActivity.this)) {
                    newtel=input_phone_numb.getText().toString();
                    if(isMobileNum(newtel)){
                        mCountDownTimerUtils.start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("zsw","inRun");
                                String code=OkhttpUtils.get(IP.SERVERURL+"k/GetAuthCode/"+ IvmApplication.kehu.kHID +"/"+newtel);
                                Message msg = new Message();

                                switch (code){
                                    case "\"200\"":
                                        msg.what = YANZHENGMAHUOQU_SUCCESS;
                                        break;
                                    case "EXCEPTION":
                                        msg.what=EXCEPTION;
                                        break;
                                    default:
                                        msg.what=YANZHENGMAHUOQU_FAILED;
                                        break;
                                }
                                myhandle.sendMessage(msg);
                            }
                        }).start();
                    }else {
                        Toast.makeText(ChangePhonubrActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ToastUtil.showToast(ChangePhonubrActivity.this,"当前无网络");
                }

            }
        });

        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(ChangePhonubrActivity.this)) {
                    authcode=input_code.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("zsw","inRun");
                            HBshouji hBshouji=new HBshouji();
                            hBshouji.setAuthcode(authcode);
                            hBshouji.setNewtel(newtel);

                            String code=OkhttpUtils.post(hBshouji, IP.SERVERURL+"k/ConfirmAuthcode/"+ IvmApplication.kehu.kHID);

//                        int islegal= postHBshouji();
//                        log.d("islegal",""+islegal);
                            Message msg = new Message();
//                        msg.what=islegal;
                            switch (code){
                                case "\"200\"":
                                    msg.what = YANZHENG_SUCCESS;
                                    break;
                                case "\"201\"":
                                    msg.what = YANZHENGMA_GUOQI;
                                    break;
                                case "\"204\"":
                                    msg.what = YANZHENGMA_CUOWU;
                                    break;
                                case "\"203\"":
                                    msg.what = SHOUJIHAO_NO_USE;
                                    break;
                                case "EXCEPTION":
                                    msg.what = EXCEPTION;
                                    break;
                                default:
                                    break;
                            }
                            myhandle.sendMessage(msg);

                        }
                    }).start();
                }else {
                    ToastUtil.showToast(ChangePhonubrActivity.this,"当前无网络");
                }

            }
        });
    }

    public static boolean isMobileNum(String telNum){
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    Handler myhandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case YANZHENGMAHUOQU_SUCCESS:
                    Toast.makeText(getApplicationContext(),"请求成功",Toast.LENGTH_LONG).show();
                    break;
                case YANZHENGMAHUOQU_FAILED:
                    Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_LONG).show();
                    break;
                case YANZHENG_SUCCESS:
                    Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_LONG).show();
                    break;
                case YANZHENGMA_GUOQI:
                    Toast.makeText(getApplicationContext(),"验证码过期",Toast.LENGTH_LONG).show();
                    break;
                case YANZHENGMA_CUOWU:
                    Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_LONG).show();
                    break;
                case SHOUJIHAO_NO_USE:
                    Toast.makeText(getApplicationContext(),"该号码已被注册",Toast.LENGTH_LONG).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(ChangePhonubrActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };

}
