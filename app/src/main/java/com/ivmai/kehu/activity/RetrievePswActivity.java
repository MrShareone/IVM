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
import android.widget.ImageView;
import android.widget.Toast;

import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.RetrievePsw;
import com.ivmai.kehu.tools.CountDownTimerUtils;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetrievePswActivity extends AppCompatActivity {

//    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    static  final int YANZHENGMAHUOQU_SUCCESS=300;
    static  final int YANZHENGMAHUOQU_FAILED=301;
    static  final int CAOZUO_SUCCESS=302;
    static  final int YANZHENGMA_GUOQI=303;
    static  final int YANZHENGMA_CUOWU=304;
    static  final int EXCEPTION=404;

    private EditText  retrieve_phnumber_input;
    private Button   retrieve_get_code;
    private EditText  retrieve_code_input;
    private EditText retrieve_psw_input;
    private EditText retrieve_repsw_input;
    private Button  retrieve_submit;
    private ImageView back;
    private Long  khid;


    protected  void binview(){
        retrieve_phnumber_input=(EditText)findViewById(R.id.retrieve_phnumber_input);
        retrieve_get_code=(Button)findViewById(R.id.retrieve_get_code);
        retrieve_code_input=(EditText)findViewById(R.id.retrieve_code_input);
        retrieve_psw_input=(EditText)findViewById(R.id.retrieve_psw_input);
        retrieve_repsw_input=(EditText)findViewById(R.id.retrieve_repsw_input);
        retrieve_submit=(Button)findViewById(R.id.retrieve_submit);
        back=(ImageView)findViewById(R.id.retrieve_back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_psw);

        binview();

        TransformationMethod method = PasswordTransformationMethod.getInstance();//密码暗文显示
        retrieve_psw_input.setTransformationMethod(method);
        retrieve_repsw_input.setTransformationMethod(method);

        final CountDownTimerUtils mCountDownTimerUtils  = new CountDownTimerUtils(retrieve_get_code, 60000, 1000);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        retrieve_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(RetrievePswActivity.this)) {
                    final String  newtel=retrieve_phnumber_input.getText().toString();
                    if(isMobileNum(newtel)){
                        mCountDownTimerUtils.start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("zsw","inRun");
                                String code= OkhttpUtils.getResult(IP.SERVERURL+"k/GetPasswordAuthCode/"+newtel);
                                try{
                                    khid=OkhttpUtils.getJsonnode().asLong();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    code="EXCEPTION";
                                }



//                            boolean islegal = getAuthCode(newtel);
//                            log.d("islegal",""+islegal);
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
                        Toast.makeText(RetrievePswActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ToastUtil.showToast(RetrievePswActivity.this,"当前无网络");
                }
            }
        });

        retrieve_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final   String   pswd=retrieve_psw_input.getText().toString();
                String    repswd=retrieve_repsw_input.getText().toString();
                if (NetWork.isNetwork(RetrievePswActivity.this)) {
                    if (!pswd.equals("") && !repswd.equals("") && pswd.equals(repswd)) {
                        final String authcode = retrieve_code_input.getText().toString();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("zsw", "inRun");
                                RetrievePsw re=new RetrievePsw();
                                re.setAuthcode(authcode);
                                re.setPassword(pswd);
                                String code=OkhttpUtils.post(re, IP.SERVERURL+"k/ChangePassword/"+khid);

//                                int islegal = postRetrievePsw(authcode, pswd);
                                Log.d("islegal", "" + code);
                                Message msg = new Message();
                                switch (code){
                                    case "\"200\"":
                                        msg.what = CAOZUO_SUCCESS;
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

                    } else {
                        Toast.makeText(getApplicationContext(), "重复密码输入不正确", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ToastUtil.showToast(RetrievePswActivity.this,"当前无网络");
                }
            }
        });
    }


    public static boolean isMobileNum(String telNum){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    Handler myhandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case YANZHENGMAHUOQU_SUCCESS:
                    Toast.makeText(getApplicationContext(),"请求成功",Toast.LENGTH_SHORT).show();
                    break;
                case YANZHENGMAHUOQU_FAILED:
                    Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                    break;
                case CAOZUO_SUCCESS:
                    Toast.makeText(getApplicationContext(),"操作成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case YANZHENGMA_GUOQI:
                    Toast.makeText(getApplicationContext(),"验证码过期",Toast.LENGTH_SHORT).show();
                    break;
                case YANZHENGMA_CUOWU:
                    Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_SHORT).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(RetrievePswActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };
}
