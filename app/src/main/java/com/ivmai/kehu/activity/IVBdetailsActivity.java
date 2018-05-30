package com.ivmai.kehu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivm.entity.TKehu;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.PojoMapper;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.IP;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IVBdetailsActivity extends AppCompatActivity {
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static  final int HUOQUKEHU_SUCCESS=300;
    static  final int HUOQUKEHU_FAILED=301;
    static  final int EXCEPTION=404;

    SwipeRefreshLayout swipeRefreshLayout;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ivb_details);
    //更改标题栏显示内容
        TextView textView=(TextView)findViewById(R.id.title_name);
        textView.setText("我的爱微币");
        textView2=(TextView)findViewById(R.id.ivbyue);
        textView2.setText(IvmApplication.kehu.KhIvb.toString());
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.ivbswip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetWork.isNetwork(IVBdetailsActivity.this)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();
                            PojoMapper pojoMapper = new PojoMapper();
                            Request request = new Request.Builder()
                                    .url(IP.SERVERURL + "k/PersonMsg/" + IvmApplication.kehu.kHID + "/" + TokenBuilder.buildToken())
                                    .build();
                            try {
                                Response response = okHttpClient.newCall(request).execute();
                                if (response.isSuccessful()) {
                                    String responsedata = response.body().string();
                                    ObjectMapper m = new ObjectMapper();
                                    JsonNode jsonNode = m.readTree(responsedata);
                                    JsonNode codenode = jsonNode.path("Code");
                                    if (codenode.toString().equals("\"200\"")) {
                                        JsonNode jsonNode2 = jsonNode.path("result");
                                        IvmApplication.kehu = (TKehu) pojoMapper.fromJson(jsonNode2.toString(), TKehu.class);
                                        Message message  = new Message();
                                        message.what = HUOQUKEHU_SUCCESS;
                                        mHand.sendMessage(message);
                                    }else if(codenode.toString().equals("\"60000\"")){
                                        //token失效处理
                                        Message message  = new Message();
                                        message.what = HUOQUKEHU_FAILED;
                                        mHand.sendMessage(message);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Message message  = new Message();
                                message.what = EXCEPTION;
                                mHand.sendMessage(message);

                            }
                        }
                    }).start();
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    ToastUtil.showToast(IVBdetailsActivity.this,"当前无网络");
                }
            }

        });

        Button button=(Button)findViewById(R.id.ivb_recharge);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IVBdetailsActivity.this,IVBrechargeActivity.class);
                startActivity(intent);
            }
        });
    }
    private Handler mHand = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HUOQUKEHU_SUCCESS:
                    swipeRefreshLayout.setRefreshing(false);
                    textView2.setText(IvmApplication.kehu.KhIvb.toString());
                    ToastUtil.showToast(IVBdetailsActivity.this,"刷新成功");
                    break;
                case HUOQUKEHU_FAILED:
                    Toast.makeText(getApplicationContext(),"刷新失败",Toast.LENGTH_LONG).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(IVBdetailsActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };

}
