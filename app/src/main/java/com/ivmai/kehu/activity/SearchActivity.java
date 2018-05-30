package com.ivmai.kehu.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;

import com.ivm.entity.VSpview;
import com.ivm.newentity.ProductsResponseSuccess;
import com.ivmai.kehu.R;
import com.ivmai.kehu.adapter.VSpviewAdapter;
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

public class SearchActivity extends AppCompatActivity {
    private List<ProductsResponseSuccess.ResultBean> vSpviewList =new ArrayList<ProductsResponseSuccess.ResultBean>();

    public static final int JIEGUOWEIKONG = 300;
    public static final int HUOQUCHENGGONG = 301;
    static  final int EXCEPTION=404;

    private RecyclerView recyclerView;
    private TextView search_edt;
    private ImageView search_but;

    private  LinearLayout fragment_connect_no;
    private ProgressBar fragment2_loading;
    private  ImageView network_refresh;
    private VSpviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery);

        search_edt=(TextView)findViewById(R.id.search_edit_input);

//        search_but=(Button)findViewById(R.id.search_but);
        search_but = (ImageView)findViewById(R.id.search_icon_img);

        fragment_connect_no=(LinearLayout)findViewById(R.id.fragment_connect_no);
        network_refresh=(ImageButton)findViewById(R.id.network_refresh);

        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new VSpviewAdapter(vSpviewList,SearchActivity.this);

        Intent intent=getIntent();
        final String text=intent.getStringExtra("text");

        search_edt.setText(text);
//        search_edt.setSelection(search_edt.getText().length());//光标移到内容之后

        if (NetWork.isNetwork(SearchActivity.this)) {
            fragment_connect_no.setVisibility(View.GONE);
            ReSearchShangping(text);
        }else {
            fragment2_loading.setVisibility(View.GONE);
            fragment_connect_no.setVisibility(View.VISIBLE);
        }

        network_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(SearchActivity.this)) {
                    fragment_connect_no.setVisibility(View.GONE);
                    fragment2_loading.setVisibility(View.VISIBLE);
                    ReSearchShangping(text);
                }else {
                    fragment2_loading.setVisibility(View.GONE);
                    fragment_connect_no.setVisibility(View.VISIBLE);
                    ToastUtil.showToast(SearchActivity.this,"当前无网络");
                }
            }
        });


        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWork.isNetwork(SearchActivity.this)) {
                    fragment2_loading.setVisibility(View.VISIBLE);
                  String text2=search_edt.getText().toString();
                    ReSearchShangping(text2);
                }else {
                    ToastUtil.showToast(SearchActivity.this,"当前无网络");
                }
            }
        });

    }

    private Handler mhand = new Handler(){
        public void handleMessage(Message msg) {
            search_but.setEnabled(true);
            switch (msg.what) {
                case HUOQUCHENGGONG:
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//                    recyclerView.invalidate();//强制控件刷新
                    fragment2_loading.setVisibility(View.GONE);
                    break;
                case JIEGUOWEIKONG:
                    Toast.makeText(SearchActivity.this,"无此商品或店铺,请检查关键词内容是否有误",Toast.LENGTH_SHORT).show();
                    fragment2_loading.setVisibility(View.GONE);
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(SearchActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };
    private void ReSearchShangping(final String text){
        vSpviewList.clear();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        search_but.setEnabled(false);
//                    }
//                });
//                OkhttpUtils okhttpUtils = new OkhttpUtils();
//                String code = OkhttpUtils.getResult(IP.SERVERURL+"sp/search/"+text);//code
//                JsonNode jn = okhttpUtils.getJsonnode();//result
//                if(jn.toString().equals("\"\"")||jn.toString()==null){
//                    code="404";
//                }
//                if (jn.isArray()) {
//                    Iterator<JsonNode> iterator = jn.elements();
//                    PojoMapper pj = new PojoMapper();
//                    while (iterator.hasNext()) {
//                        JsonNode nd = iterator.next();
//                        VSpview  vSpview = null;
//                        try {
//                            vSpview = (VSpview) pj.fromJson(nd.toString(), VSpview.class);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            vSpview=null;
//                        }
//                        Bitmap bmp=null;
//                        try{
//                            if(!vSpview.getSpTupian().equals("")&&vSpview.getSpTupian()!=null){
//                                bmp = OkhttpUtils.getBitmap(IP.SERVERURL+vSpview.getSpTupian());
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                            bmp=null;
//                        }
//                        if(bmp==null){
//                            bmp= BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
//                        }
//                        vSpview.setBitmap(bmp);
//                        vSpviewList.add(vSpview);
//                    }
//                }
//                Message msg = new Message();
//                switch (code){
//                    case "\"200\"":
//                        msg.what = HUOQUCHENGGONG;
//                        msg.obj = vSpviewList;
//                        break;
//                    case "EXCEPTION":
//                        msg.what=EXCEPTION;
//                        break;
//                    case "404":
//                        msg.what=JIEGUOWEIKONG;
//                        break;
//                    default:
//                        break;
//                }
//                mhand.sendMessage(msg);
//            }
//        }).start();
    }
}

