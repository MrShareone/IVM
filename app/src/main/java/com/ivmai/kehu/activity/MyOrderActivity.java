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

import com.fasterxml.jackson.databind.JsonNode;
import com.ivm.entity.TMyDingdan;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.PojoMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ivmai.kehu.R;
import com.ivmai.kehu.adapter.HistoryOrderAdapter;
import com.ivmai.utils.IP;


public class MyOrderActivity extends AppCompatActivity {

    static  final int HUOQULISHIDINGDANLIEBIAO_SUCCESS=300;
    static  final int JIAZAILISHIDINGDANLIEBIAO_SUCCESS=301;
    static  final int EXCEPTION=404;

    private List<TMyDingdan> tDingdanList = new ArrayList<TMyDingdan>();
    private RecyclerView recyclerView;

    private HistoryOrderAdapter adapter;

    private ProgressBar my_order_loading;

    int lastVisibleItem;

    public static int start=0;

    private Handler mHand = new Handler()
    {
        public void handleMessage(Message msg)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    my_order_loading.setVisibility(View.GONE);
                }
            });
            switch (msg.what)
            {
                case HUOQULISHIDINGDANLIEBIAO_SUCCESS:
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                    recyclerView.invalidate();//强制控件刷新
                    my_order_loading.setVisibility(View.GONE);
                    break;
                case JIAZAILISHIDINGDANLIEBIAO_SUCCESS:
//                    recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
                    break;
                case EXCEPTION:
            ExceptionAlert.cancelalert(MyOrderActivity.this);//异常弹窗
                    break;
    default:break;
}
        }
                };

    //初始化订单
    public  void initDingdanList(){
        tDingdanList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                DoGetDingdanList();
                String code= OkhttpUtils.getResult(IP.SERVERURL+"d/KHAll/"+ IvmApplication.kehu.kHID+"/"+start);
                JsonNode jn=OkhttpUtils.getJsonnode();
                if(jn.isArray()) {
                    Iterator<JsonNode> iterator = jn.elements();
                    PojoMapper pj = new PojoMapper();
                    while (iterator.hasNext()) {
                        JsonNode nd = iterator.next();
                        TMyDingdan tDingdan = new TMyDingdan();
                        try {
                            tDingdan = (TMyDingdan) pj.fromJson(nd.toString(), TMyDingdan.class);
                            tDingdanList.add(tDingdan);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Message msg = new Message();
                switch (code){
                    case "\"200\"":
                        msg.what = HUOQULISHIDINGDANLIEBIAO_SUCCESS;
                        msg.obj =tDingdanList;
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
        setContentView(R.layout.my_order);

        start=0;

        my_order_loading=(ProgressBar)findViewById(R.id.my_order_loading);

        adapter = new HistoryOrderAdapter(tDingdanList,MyOrderActivity.this);
        Log.d( "start1111:=========== ",""+start);
        //更改标题栏显示内容
        TextView textView=(TextView)findViewById(R.id.title_name);
        textView.setText("历史订单");
        initDingdanList();
        recyclerView=(RecyclerView)findViewById(R.id.my_order_recyler);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==adapter.getItemCount()) {
                            start++;
                            Log.d( "index:=========== ",""+start);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
//                                    DoGetDingdanList();
                                    String code= OkhttpUtils.getResult(IP.SERVERURL+"d/KHAll/"+ IvmApplication.kehu.kHID+"/"+start);
                                    JsonNode jn=OkhttpUtils.getJsonnode();
                                    if(jn.isArray()) {
                                        Iterator<JsonNode> iterator = jn.elements();
                                        PojoMapper pj = new PojoMapper();
                                        while (iterator.hasNext()) {
                                            JsonNode nd = iterator.next();
                                            TMyDingdan tDingdan = new TMyDingdan();
                                            try {
                                                tDingdan = (TMyDingdan) pj.fromJson(nd.toString(), TMyDingdan.class);
                                                tDingdanList.add(tDingdan);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    Message msg = new Message();
                                    switch (code){
                                        case "\"200\"":
                                            msg.what = JIAZAILISHIDINGDANLIEBIAO_SUCCESS;
                                            msg.obj =tDingdanList;
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
