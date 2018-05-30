package com.ivmai.kehu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivm.entity.TKehu;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.CountDownTimerUtils;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.utils.IP;

public class SFrenzhengActivity extends AppCompatActivity {

//    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    static  final int YANZHENGMAHUOQU_SUCCESS=300;
    static  final int YANZHENGMAHUOQU_FAILED=301;
    static  final int YANZHENG_SUCCESS=302;
    static  final int YANZHENGMA_GUOQI=303;
    static  final int YANZHENGMA_CUOWU=304;
    static  final int EXCEPTION=404;

    private Button get_code_but;
    private EditText input_code;
    private Button submit_but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfrenzheng);
        get_code_but=(Button)findViewById(R.id.identify_code_send);
        input_code=(EditText)findViewById(R.id.identify_code_input);
        submit_but=(Button)findViewById(R.id.identify_submit);

        final  CountDownTimerUtils mCountDownTimerUtils  = new CountDownTimerUtils(get_code_but, 60000, 1000);

//获取验证码
        get_code_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimerUtils.start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("zsw","inRun");
                        TKehu tKehu = IvmApplication.kehu;
                        String code=OkhttpUtils.post(tKehu, IP.SERVERURL+"m/Authcode");
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
            }
        });
 //提交验证
        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String   authcode=input_code.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("zsw","inRun");
                        String code= OkhttpUtils.get(IP.SERVERURL+"m/ConfirmAuthcode/"+ IvmApplication.kehu.kHID +"/"+authcode);
                        Message msg = new Message();
                        if(code.equals("\"200\"")){
                            msg.what=YANZHENG_SUCCESS;
                        }
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
            }
        });


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
                    finish();
                    break;
                case YANZHENGMA_GUOQI:
                    Toast.makeText(getApplicationContext(),"验证码过期",Toast.LENGTH_LONG).show();
                    break;
                case YANZHENGMA_CUOWU:
                    Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_LONG).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(SFrenzhengActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };



   /*
//获取验证码请求
*/
//    private boolean postKehu() {
//
//        //申明给服务端传递一个json串
//        //创建一个OkHttpClient对象
//        OkHttpClient okHttpClient = new OkHttpClient();
//        //创建tkehu对象，并将其转为json数据
//        TKehu tKehu = IvmApplication.kehu;
//
//        PojoMapper pojoMapper = new PojoMapper();
//        String json = null;
//        try {
//            json = pojoMapper.toJson(tKehu,true);
//
//            log.d("zsw",json+"json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//        //json为String类型的json数据
//        RequestBody requestBody= RequestBody.create(JSON,json );
//        //创建一个请求对象
//        Request request = new Request.Builder()
//                .url("http://192.168.1.137:8080/ivm/m/Authcode/"+IvmApplication.getAccount())
//                .post(requestBody)
//                .build();
//        //发送请求获取响应
//        try {
//            Response response=okHttpClient.newCall(request).execute();
//            //判断请求是否成功
//            if(response.isSuccessful()){
//                String responsedata = response.body().string();
//                log.d("返回结果",responsedata);
////   **********************************打印服务端返回结果*********************************//
//                ObjectMapper m =new ObjectMapper();
//                JsonNode jsonNode  = m.readTree(responsedata);
//                JsonNode jsonNode1 = jsonNode.path("Code");
//                String code = jsonNode1.toString();
//                log.d("code",code);
//                if(code.equals("\"200\"")){
//                    return  true;
//                }else{
//                    return false;
//                }
////   **********************************打印服务端返回结果*********************************
//            }
//        } catch (IOException e) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),"服务器异常！", Toast.LENGTH_SHORT).show();
//                }
//            });
//            e.printStackTrace();
//        }
//        return false;
//    }

    /*
//验证请求
*/

//    private int Yanzheng() {
//
//
//        //创建一个OkHttpClient对象
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//
//
//        Request request = new Request.Builder()
//                .url("http://192.168.1.137:8080/ivm/m/ConfirmAuthcode/"+IvmApplication.kehu.kHID+"/"+code+"/"+IvmApplication.getAccount())
//                .build();
//        //发送请求获取响应
//        try {
//            Response response=okHttpClient.newCall(request).execute();
//            //判断请求是否成功
//            if(response.isSuccessful()){
//                String responsedata = response.body().string();
//                log.d("返回结果",responsedata);
//                ObjectMapper m =new ObjectMapper();
//                JsonNode jsonNode  = m.readTree(responsedata);
//                JsonNode jsonNode1 = jsonNode.path("Code");
//                String code = jsonNode1.toString();
//                log.d("code",code);
//                if(code.equals("\"200\"")){
//                    return  200;
//                }
//                if(code.equals("\"201\"")){
//                    return  201;
//                }
//                if(code.equals("\"202\"")){
//                    return 202;
//                }
//            }
//        } catch (IOException e) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),"服务器异常！", Toast.LENGTH_SHORT).show();
//                }
//            });
//            e.printStackTrace();
//        }
//        return 0;
//    }
}

