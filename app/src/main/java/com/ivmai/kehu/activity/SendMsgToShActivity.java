package com.ivmai.kehu.activity;

import android.content.Intent;
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

import com.ivm.entity.TMsg;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

public class SendMsgToShActivity extends AppCompatActivity {

    static  final int PINGJIA_SUCCESS=300;
    static  final int PINGJIA_FAILED=301;
    static  final int EXCEPTION=404;

    private String msg;
    private Long shid;

    //    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    TextView textView;
    private EditText msgcontent;
    private Button  send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_msg_to_sh);

        //更改标题栏显示内容
        textView=(TextView)findViewById(R.id.title_name);
        textView.setText("给商家留言");

        msgcontent=(EditText)findViewById(R.id.msg_sh_content);
        send=(Button)findViewById(R.id.send_to_sh);

        Intent intent=getIntent();
        shid=Long.valueOf(intent.getStringExtra("shid"));


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(SendMsgToShActivity.this)) {
                    send.setEnabled(false);
                    msg=msgcontent.getText().toString();
                    Log.d("msg:======== ",msg);
                    if(!msg.equals("")){

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("zsw","inRun");

                                TMsg tMsg = new TMsg();
                                tMsg.setMsgContent(msg);
                                tMsg.setReceiverID(shid);
                                tMsg.setSenderID(IvmApplication.kehu.kHID);
                                tMsg.setMsgLeixing(15);
                                tMsg.setReceiverFlag(2);
                                tMsg.setSenderFlag(1);

                                String code= OkhttpUtils.post(tMsg, IP.SERVERURL+"m/MsgAdd");


//                            boolean islegal = postJson();
//                            log.d("islegal",""+islegal);
                                Message msg = new Message();
                                switch (code){
                                    case "\"200\"":
                                        msg.what = PINGJIA_SUCCESS;
                                        break;
                                    case "EXCEPTION":
                                        msg.what=EXCEPTION;
                                        break;
                                    default:
                                        msg.what=PINGJIA_FAILED;
                                        break;
                                }

                                myhandle.sendMessage(msg);
                            }
                        }).start();
                    }
                    else {
                        Toast.makeText(SendMsgToShActivity.this,"留言内容不能为空",Toast.LENGTH_SHORT).show();
                        send.setEnabled(true);
                    }
                }else {
                    ToastUtil.showToast(SendMsgToShActivity.this,"当前无网络");
                }
            }

        });

    }


    Handler myhandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PINGJIA_SUCCESS:
                    Toast.makeText(getApplicationContext(),"留言成功",Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case PINGJIA_FAILED:
                    Toast.makeText(getApplicationContext(),"留言失败",Toast.LENGTH_LONG).show();
                    send.setEnabled(true);
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(SendMsgToShActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };

  /*
//发送评价请求
*/

//    private boolean postJson() {
//
//        //申明给服务端传递一个json串
//        //创建一个OkHttpClient对象
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        //创建tMsg对象，并将其转为json数据
//        TMsg tMsg = new TMsg();
//        tMsg.setMsgContent(msg);
//        tMsg.setReceiverID(shid);
//        tMsg.setSenderID(IvmApplication.kehu.kHID);
//        tMsg.setMsgLeixing(15);
//        tMsg.setReceiverFlag(2);
//        tMsg.setSenderFlag(1);
//
//
//
//        PojoMapper pojoMapper = new PojoMapper();
//        String json = null;
//        try {
//            json = pojoMapper.toJson(tMsg,true);
//
//            log.d("lklklklk",json+"json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//        //json为String类型的json数据
//        RequestBody requestBody= RequestBody.create(JSON,json );
//        //创建一个请求对象
//        Request request = new Request.Builder()
//                .url(IP.SERVERURL+"m/MsgAdd/11111")
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
}
