package com.ivmai.kehu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivm.entity.TKehu;
import com.ivmai.kehu.R;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.CountDownTimerUtils;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

public class YanZhengActivity extends AppCompatActivity {

//    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    static  final int YANZHENGMAHUOQU_SUCCESS=300;
    static  final int YANZHENGMAHUOQU_FAILED=301;
    static  final int YANZHENG_SUCCESS=302;
    static  final int YANZHENGMA_GUOQI=303;
    static  final int YANZHENGMA_CUOWU=304;
    static  final int EXCEPTION=404;

    private boolean isExit=false;

    private Button get_code_but;
    private EditText input_code;
    private Button submit_but;
    private Button ignore_but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanzheng);
        get_code_but=(Button)findViewById(R.id.yanzheng_code_send);
        input_code=(EditText)findViewById(R.id.yanzheng_code_input);
        submit_but=(Button)findViewById(R.id.yanzheng_submit);
        ignore_but=(Button)findViewById(R.id.yanzheng_ignore);

        final CountDownTimerUtils mCountDownTimerUtils  = new CountDownTimerUtils(get_code_but, 60000, 1000);


        get_code_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(YanZhengActivity.this)) {
                mCountDownTimerUtils.start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("zsw","inRun");
                        TKehu tKehu = IvmApplication.kehu;
                        String code= OkhttpUtils.post(tKehu, IP.SERVERURL+"m/Authcode");
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
                    ToastUtil.showToast(YanZhengActivity.this,"当前无网络");
                }
            }
        });

        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(YanZhengActivity.this)) {
             final String  authcode=input_code.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("zsw","inRun");
                        String code=OkhttpUtils.get(IP.SERVERURL+"m/ConfirmAuthcode/"+ IvmApplication.kehu.kHID +"/"+authcode);
//                        int islegal= Yanzheng();
//                        log.d("islegal",""+islegal);
                        Message msg = new Message();
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
                    ToastUtil.showToast(YanZhengActivity.this,"当前无网络");
                }
            }
        });
        ignore_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ignorealert();
            }
        });
    }

    public void ignorealert(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(YanZhengActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("跳过后您的账号将处于未验证状态，存在着安全风险。确认要跳过手机号验证？ ");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定跳过", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(YanZhengActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    //捕获用户按下back键，执行exit()方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            myhandle.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
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
                    Intent intent = new Intent(YanZhengActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case YANZHENGMA_GUOQI:
                    Toast.makeText(getApplicationContext(),"验证码过期",Toast.LENGTH_LONG).show();
                    break;
                case YANZHENGMA_CUOWU:
                    Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_LONG).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(YanZhengActivity.this);//异常弹窗
                    break;
                case 0:
                    isExit=false;
                    break;
                default:
                    break;
            }
        }
    };
}
