package com.ivmai.kehu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.ivm.entity.TMyDanju;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.adapter.MyBillAdapter;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.PojoMapper;
import com.ivmai.utils.IP;

public class MyBillActivity extends AppCompatActivity {

    static  final int HUOQUZHANGDANLIEBIAO_SUCCESS=300;
    static  final int JIAZAIZHANGDANLIEBIAO_SUCCESS=301;
    static  final int EXCEPTION=404;


private List<TMyDanju> tMyDanjuList=new ArrayList<>();

   private RecyclerView recyclerView;

   private MyBillAdapter myBillAdapter;

   private int lastVisibleItem;

    private ProgressBar bill_loading;

    public static int start=0;


    private Handler mHand = new Handler()
    {
        public void handleMessage(Message msg)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bill_loading.setVisibility(View.GONE);
                }
            });
            switch (msg.what)
            {
                case HUOQUZHANGDANLIEBIAO_SUCCESS:
                    recyclerView.setAdapter(myBillAdapter);
                    myBillAdapter.notifyDataSetChanged();
//                    recyclerView.invalidate();//强制控件刷新
                    bill_loading.setVisibility(View.GONE);
                    break;
                case JIAZAIZHANGDANLIEBIAO_SUCCESS:
//                    recyclerView.setAdapter(adapter);
                    myBillAdapter.notifyDataSetChanged();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(MyBillActivity.this);//异常弹窗
                    break;
                default:break;
            }
        }
    };
    //初始化账单
    public  void initDanjuList(){
        tMyDanjuList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                DoGetDanJuList();
                String code= OkhttpUtils.getResult(IP.SERVERURL+"alipay/KHGetDj/"+ IvmApplication.kehu.kHID+"/"+start);
                JsonNode jn=OkhttpUtils.getJsonnode();
                if(jn!=null && jn.isArray()) {
                    Iterator<JsonNode> iterator = jn.elements();
                    PojoMapper pj = new PojoMapper();
                    while (iterator.hasNext()) {
                        JsonNode nd = iterator.next();
                        TMyDanju tMyDanju = new TMyDanju();
                        try {
                            tMyDanju = (TMyDanju) pj.fromJson(nd.toString(), TMyDanju.class);
                            tMyDanjuList.add(tMyDanju);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Message msg = new Message();
                switch (code){
                    case "\"200\"":
                        msg.what = HUOQUZHANGDANLIEBIAO_SUCCESS;
                        msg.obj =tMyDanjuList;
                        break;
                    case "EXCEPTION":
                        msg.what = EXCEPTION;
                        break;
                    default:
                        break;
                }
                mHand.sendMessage(msg);
            }
        }).start();//
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bill);
        start=0;

        myBillAdapter=new MyBillAdapter(tMyDanjuList,MyBillActivity.this);
        Log.d( "start1111:=========== ",""+start);
        //更改标题栏显示内容
        TextView textView=(TextView)findViewById(R.id.title_name);
        textView.setText("我的账单");

        bill_loading=(ProgressBar)findViewById(R.id.bill_loading);

        initDanjuList();
        recyclerView=(RecyclerView)findViewById(R.id.bill_recyclerview);

        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==myBillAdapter.getItemCount()) {
                    start++;
                    Log.d( "index:=========== ",""+start);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            DoGetDanJuList();
                            String code= OkhttpUtils.getResult(IP.SERVERURL+"alipay/KHGetDj/"+ IvmApplication.kehu.kHID+"/"+start);
                            JsonNode jn=OkhttpUtils.getJsonnode();

                            if(jn.isArray()) {
                                Iterator<JsonNode> iterator = jn.elements();
                                PojoMapper pj = new PojoMapper();
                                while (iterator.hasNext()) {
                                    JsonNode nd = iterator.next();
                                    TMyDanju tMyDanju = new TMyDanju();
                                    try {
                                        tMyDanju = (TMyDanju) pj.fromJson(nd.toString(), TMyDanju.class);
                                        tMyDanjuList.add(tMyDanju);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            Message msg = new Message();
                            switch (code){
                                case "\"200\"":
                                    msg.what = JIAZAIZHANGDANLIEBIAO_SUCCESS;
                                    msg.obj =tMyDanjuList;
                                    break;
                                case "EXCEPTION":
                                    msg.what = EXCEPTION;
                                    break;
                                default:
                                    break;
                            }
                            mHand.sendMessage(msg);
                        }
                    }).start();
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
}
