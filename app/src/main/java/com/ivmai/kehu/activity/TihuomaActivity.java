//package com.ivmai.kehu.activity;
//
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//
//import com.ivmai.base.IvmApplication;
//import com.ivmai.kehu.R;
//import com.ivmai.kehu.fragment.ScanOrderFragment;
//import com.ivmai.kehu.tools.NetWork;
//import com.ivmai.kehu.tools.OkhttpUtils;
//import com.ivmai.kehu.tools.TiHuoMa;
//import com.ivmai.kehu.tools.ToastUtil;
//import com.ivmai.utils.IP;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class TihuomaActivity extends AppCompatActivity {
//
//
//
//    static  final int WANCHENG_SUCCESS=302;
//    static  final int CAOZUO_FIALED=303;
//    static  final int EXCEPTION=404;
//
//    private List<TiHuoMa> tiHuoMaList=new ArrayList<>();
//
//    TextView shname;
//    TextView tihuoma;
//    TextView tihuoshijian;
//    TextView spxiangqing;
//    Button button;
//
//    ProgressDialog progress_dialog;
//
//    String dDID;
////    TDingdan tDingdan;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tihuoma);
//        shname=(TextView)findViewById(R.id.dp_name_show);
//        tihuoma=(TextView)findViewById(R.id.tihuoma_show);
//        tihuoshijian=(TextView)findViewById(R.id.tiqu_time);
//        spxiangqing=(TextView)findViewById(R.id.sp_show);
//        button=(Button)findViewById(R.id.tihuoma_button);//确认提货按钮
//        ImageView imageView=(ImageView)findViewById(R.id.tihuoma_back); //返回按钮
////        tihuoma.setTypeface(typeFace); //设置字体
//
////        Button yemianback=(Button)findViewById(R.id.yemianback);
//
////        yemianback.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                finish();
////            }
////        });
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finishalert();
//            }
//        });
//
//        Intent intent=getIntent();
//        String ShName=intent.getStringExtra("SHNAME");
//        String TiHuoMa=intent.getStringExtra("TIHUOHAO");
//        String SpName=intent.getStringExtra("SPNAME");
//        String SpShuLiang=intent.getStringExtra("SPSHULIANG");
//        String SpKeXuanBiaoQiAN=intent.getStringExtra("DDKEXUANBIAOQIANZHI");
//        String DdJiaGe=intent.getStringExtra("DDJIAGE");
//        Boolean iszhifu=intent.getBooleanExtra("isZHIFU",false);
//         dDID=intent.getStringExtra("DDID");
//
//
//
//
////        tDingdan=(TDingdan)intent.getSerializableExtra("tDingdan_data");
//
//        shname.setText(ShName);
//        tihuoma.setText(TiHuoMa);
//        spxiangqing.setText("(商品名: "+SpName+"×"+SpShuLiang+"  总价: "+DdJiaGe+"  商品标签: "+SpKeXuanBiaoQiAN+"  留言: "+")");
//        if(iszhifu){
//            button.setEnabled(true);
//        }else {
//            button.setEnabled(false);
//        }
//
////        for (String a:IvmApplication.list){
////            tiHuoMaList.add(new TiHuoMa(a));
////        }
////        TiHuoMaAdapter adapter=new TiHuoMaAdapter(TihuomaActivity.this,R.layout.tihuoma_item,tiHuoMaList);
////        ListView listView=(ListView)findViewById(R.id.tihuoma_list);
////        listView.setDivider(null);//去掉listview分隔线
////        listView.setAdapter(adapter);
//    }
//
//
//
////    private Handler mHand = new Handler() {
////        public void handleMessage(Message msg) {
////
////            switch (msg.what) {
////                case WANCHENG_SUCCESS:
////                    if (progress_dialog!=null)
////                    {
////                        progress_dialog.dismiss();
////                    }
////                    tihuoma.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
////                    tihuoma.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );//加删除线
////                    SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
////                    Date date=new Date();
////                    tihuoshijian.setText("提货时间:"+df.format(date));
////                    ToastUtil.showToast(TihuomaActivity.this,"操作成功");
////                    ScanOrderFragment.ch=true;
////                    button.setEnabled(false);
////                    break;
////                case CAOZUO_FIALED:
////                    if (progress_dialog!=null)
////                    {
////                        progress_dialog.dismiss();
////                    }
////
////                    ToastUtil.showToast(TihuomaActivity.this,"操作失败,请检查网络");
////                    break;
////                case EXCEPTION:
////                    if (progress_dialog!=null)
////                    {
////                        progress_dialog.dismiss();
////                    }
////                    ToastUtil.showToast(TihuomaActivity.this,"请检查网络");
////                    break;
////                default:
////                    break;
////            }
////        }
////    };
//
//    public void finishalert(){
//        AlertDialog.Builder dialog=new AlertDialog.Builder(TihuomaActivity.this);
//        dialog.setTitle("提示");
//        dialog.setMessage("确认要完成该订单?");
//        dialog.setCancelable(false);
//        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (NetWork.isNetwork(TihuomaActivity.this)) {
//                    ProgressDialog("订单完成中！");
////                    tDingdanList.clear();
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String code= OkhttpUtils.get(IP.SERVERURL+"d/KHConfirm/"+ IvmApplication.kehu.kHID +"/"+dDID);
//
//                            Message msg = new Message();
//                            switch (code){
//                                case "\"200\"":
//
//                                    msg.what = WANCHENG_SUCCESS;
////                                    msg.obj = tDingdanList;
//                                    break;
//                                case "EXCEPTION":
//                                    msg.what = EXCEPTION;
//                                    break;
//                                default:
//                                    msg.what=CAOZUO_FIALED;
//                                    break;
//                            }
//                            mHand.sendMessage(msg);
//                        }
//                    }).start();
//                }else {
//                    ToastUtil.showToast(TihuomaActivity.this,"当前无网络");
//                }
//            }
//        });
//        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                Toast.makeText(TihuomaActivity.this,"取消完成",Toast.LENGTH_SHORT).show();
//            }
//        });
//        dialog.show();
//    }
//
//    public void ProgressDialog(String tip){
//        progress_dialog=new ProgressDialog(TihuomaActivity.this);
//        progress_dialog.setTitle(tip);
//        progress_dialog.setMessage("loading...");
//        progress_dialog.setCancelable(false);
//        progress_dialog.show();
//    }
//}
