package com.ivmai.kehu.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.ivm.entity.TShanghu;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.adapter.AddHYAdapter;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.PojoMapper;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddHYActivity extends AppCompatActivity {

    static  final int HUOQUSHANGHU_SUCCESS=300;
    static  final int JIAZAICHENGGONG_SUCCESS=301;
    static  final int TIANJIAHUIYUAN_SUCCESS=302;
    static  final int YISHIHUYI=303;
    static  final int WUJIEGUO=304;
    static  final int SOUSUO_SUCCESS=305;
    static  final int EXCEPTION=404;


    private List<TShanghu> tShanghuList=new ArrayList<TShanghu>();

    private EditText hy_add_edit;
    private Button   hy_add_search;
    private ImageButton hy_add_back;

    private ProgressBar addhy_loading;
    private  ImageButton network_refresh;
    private LinearLayout fragment_connect_no;

    private RecyclerView  recyclerView;

//    private SwipeRefreshLayout swipeRefresh;

    private AddHYAdapter adapter;

    private int lastVisibleItem;

    public  static int start=0;

    protected  void  binview(){
        hy_add_edit=(EditText)findViewById(R.id.hy_add_edit);
        hy_add_search=(Button)findViewById(R.id.hy_add_search);
        hy_add_back=(ImageButton)findViewById(R.id.hy_add_back);
        recyclerView=(RecyclerView)findViewById(R.id.hy_add_recyler);
//        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.hy_add_refresh);
        fragment_connect_no=(LinearLayout)findViewById(R.id.fragment_connect_no);
        addhy_loading=(ProgressBar)findViewById(R.id.addhy_loading);
        network_refresh=(ImageButton)findViewById(R.id.network_refresh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_huiyuan);



        start=0;

        binview();

        if (NetWork.isNetwork(AddHYActivity.this)) {
            fragment_connect_no.setVisibility(View.GONE);
            iniShanghu();
        }else {
            addhy_loading.setVisibility(View.GONE);
            fragment_connect_no.setVisibility(View.VISIBLE);
        }

        network_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(AddHYActivity.this)) {
                    fragment_connect_no.setVisibility(View.GONE);
                    addhy_loading.setVisibility(View.VISIBLE);
                    iniShanghu();
                }else {
                    addhy_loading.setVisibility(View.GONE);
                    fragment_connect_no.setVisibility(View.VISIBLE);
                    ToastUtil.showToast(AddHYActivity.this,"当前无网络");
                }
            }
        });



        adapter=new AddHYAdapter(AddHYActivity.this,tShanghuList);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setItemClickListener(new AddHYAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(AddHYActivity.this);
                dialog.setTitle("提示");
//                dialog.setMessage("确认要成为该商家会员?");
                dialog.setMessage("确认要成为"+tShanghuList.get(position).getShName()+"的会员?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (NetWork.isNetwork(AddHYActivity.this)) {
                            final TShanghu tShanghu=tShanghuList.get(position);
                            final Long shid=tShanghu.getsHID();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
//                            int islegle=AddHY(tShanghu.getsHID());
                                    String code=OkhttpUtils.get(IP.SERVERURL+"h/Tobehy/"+ IvmApplication.kehu.kHID +"/"+shid);
                                    Message msg = new Message();
                                    switch (code){
                                        case "\"200\"":
                                            msg.what = TIANJIAHUIYUAN_SUCCESS;
                                            break;
                                        case "\"201\"":
                                            msg.what = YISHIHUYI;
                                            break;
                                        case "EXCEPTION":
                                            msg.what = EXCEPTION;
                                            break;
                                        default:
                                            break;
                                    }
                                    mhand.sendMessage(msg);
                                }
                            }).start();

                        }else {
                            ToastUtil.showToast(AddHYActivity.this,"当前无网络");
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();


            }
        });


        hy_add_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(AddHYActivity.this)) {
                final String text=hy_add_edit.getText().toString();
                if (!text.equals("")) {
                    tShanghuList.clear();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            int islegal = GetShanghuSearch(text);
                            String code=OkhttpUtils.getResult(IP.SERVERURL+"sh/SearchSH/"+text);
                            JsonNode jn=OkhttpUtils.getJsonnode();
                            if(jn.toString().equals("\"\"")||jn.toString()==null){
                                code="404";
                            }
                            if (jn.isArray()) {
                                Iterator<JsonNode> iterator = jn.elements();
                                PojoMapper pj = new PojoMapper();
                                while (iterator.hasNext()) {
                                    JsonNode nd = iterator.next();
                                    TShanghu tShanghu = null;
                                    try {
                                        tShanghu = (TShanghu) pj.fromJson(nd.toString(), TShanghu.class);
                                        tShanghuList.add(tShanghu);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            Message msg = new Message();
                            switch (code){
                                case "\"200\"":
                                    msg.what = SOUSUO_SUCCESS;
                                    msg.obj = tShanghuList;
                                    break;
                                case "EXCEPTION":
                                    msg.what=EXCEPTION;
                                    break;
                                case "404":
                                    msg.what=WUJIEGUO;
                                    break;
                                default:
                                    break;
                            }
                            mhand.sendMessage(msg);
                        }
                    }).start();
                }else {
                    Toast.makeText(AddHYActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
                }else {
                    ToastUtil.showToast(AddHYActivity.this,"当前无网络");
                }
            }
        });

        hy_add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==adapter.getItemCount()) {
                    if (NetWork.isNetwork(AddHYActivity.this)) {
                    start++;
                    Log.d( "index:=========== ",""+start);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            DoGetShanghuList();
                            String code= OkhttpUtils.getResult(IP.SERVERURL+"sh/GetAll/"+start);
                            JsonNode jn=OkhttpUtils.getJsonnode();
                            if (jn.isArray()) {
                                Iterator<JsonNode> iterator = jn.elements();
                                PojoMapper pj = new PojoMapper();
                                while (iterator.hasNext()) {
                                    JsonNode nd = iterator.next();
                                    TShanghu tShanghu;
                                    try {
                                        tShanghu = (TShanghu) pj.fromJson(nd.toString(), TShanghu.class);
                                        tShanghuList.add(tShanghu);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            Message msg = new Message();
                            switch (code){
                                case "\"200\"":
                                    msg.what = JIAZAICHENGGONG_SUCCESS;
                                    msg.obj =tShanghuList;
                                    break;
                                case "EXCEPTION":
                                    msg.what = EXCEPTION;
                                    break;
                                default:
                                    break;
                            }
                            mhand.sendMessage(msg);
                        }
                    }).start();
                }else {
                        ToastUtil.showToast(AddHYActivity.this,"当前无网络");
                    }
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    private Handler mhand = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HUOQUSHANGHU_SUCCESS:
                    Log.d("zsw" ,"in");
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.invalidate();//强制控件刷新
                    addhy_loading.setVisibility(View.GONE);
//                    swipeRefresh.setRefreshing(false);
                    break;
                case JIAZAICHENGGONG_SUCCESS:
                    adapter.notifyDataSetChanged();
                    break;
                case TIANJIAHUIYUAN_SUCCESS:
                    Toast.makeText(AddHYActivity.this,"添加会员成功,请刷新",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case YISHIHUYI:
                    Toast.makeText(AddHYActivity.this,"您已是该商户会员",Toast.LENGTH_SHORT).show();
                    break;
                case WUJIEGUO:
                    Toast.makeText(AddHYActivity.this,"无此商户,请检查关键词内容是否有误",Toast.LENGTH_SHORT).show();
                    break;
                case SOUSUO_SUCCESS:
                    adapter.notifyDataSetChanged();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(AddHYActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };

    private void iniShanghu(){
        tShanghuList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                DoGetShanghuList();
                String code= OkhttpUtils.getResult(IP.SERVERURL+"sh/GetAll/"+start);
                JsonNode jn=OkhttpUtils.getJsonnode();
                if (jn.isArray()) {
                    Iterator<JsonNode> iterator = jn.elements();
                    PojoMapper pj = new PojoMapper();
                    while (iterator.hasNext()) {
                        JsonNode nd = iterator.next();
                        TShanghu tShanghu;
                        try {
                            tShanghu = (TShanghu) pj.fromJson(nd.toString(), TShanghu.class);
                            tShanghuList.add(tShanghu);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Message msg = new Message();
                switch (code){
                    case "\"200\"":
                        msg.what = HUOQUSHANGHU_SUCCESS;
                        msg.obj=tShanghuList;
                        break;
                    case "EXCEPTION":
                        msg.what = EXCEPTION;
                        break;
                    default:
                        break;
                }
                mhand.sendMessage(msg);
            }
        }).start();

    }

}
