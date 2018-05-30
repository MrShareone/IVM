package com.ivmai.kehu.activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivm.entity.TDingdan;
import com.ivm.entity.TMyVhuiyuanSH;
import com.ivm.entity.VSpview;
import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.adapter.BiaoqianAdapter;
import com.ivmai.kehu.tools.Biaoqian;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.FlowRadioGroup;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.PojoMapper;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.IP;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderActivity extends BaseActvity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static  final int TUISONGCHENGGONG = 200;
    public static  final int TUISONGSHIBAI = 201;
    public static  final int EXCEPTION = 999;

    public  Biaoqian jiagebiaoqian;//存放当前商品的价格标签
    public static String port = "d/KHCreate/";
    public static String huiyuanport = "d/KHCreate/";
    public static String daigouport = "d/KHDaiGou/";
    String ddid="";//订单id
    int count = 1;
    String Count;//计数
    double totalPrice = 0;
    String TotalPrice;//总价
    double price = 0;//根据商品标签来变化的单价


    String realurl;//访问服务器的url
    int dizhifuwubiaoqian = 1;//地址服务标签，默认为1，储物柜取
    ArrayList<Biaoqian> biaoqianlist = new ArrayList<Biaoqian>();//存放Biaoqian类的list

    VSpview vSpview = new VSpview();//当前商品
    TDingdan tDingdanTemp = new TDingdan();//本次结账的订单对象
    TDingdan tDingdanBack = new TDingdan();//返回的订单

    //定义组件
    TextView title_textView;//标题

    TextView spname;//商品名称
    TextView shname;//商户名称
    TextView spdetail;//商户名称
    ImageView sptupian;//图片

    RecyclerView biaoqianListView;//可选标签recyclerview
    FlowRadioGroup jiageradiogrop;//价格标签

    TextView count_text;//数量显示框
    TextView price_text; //价格显示框
    ImageButton add;//增加按钮
    ImageButton reduce;//减少按钮

    FlowRadioGroup dizhifuwubiaoqiangrop;//地址服务标签组
    RadioButton ziqu_radio;//自取按钮
    RadioButton songhuo_radio;//外卖按钮
    RadioButton chuancai_radio;//传菜按钮
    RadioButton tangshi_radio;//堂食按钮
    RadioButton chuwugui_radio;//橱柜自取按钮
    EditText chuancaiaddress;//传菜地址输入框
    EditText songhuoaddress;//外卖地址输入框
    Button xiadanButton;//下单按钮


    AlertDialog alertdialog;

    TMyVhuiyuanSH vhuiyuanSH;



    //初始化组件
    public void findView() {
        title_textView = (TextView) findViewById(R.id.title_name);
        title_textView.setText("用户下单");

        spname = (TextView) findViewById(R.id.spname);//获取商品名称框
        shname = (TextView) findViewById(R.id.shname);//获取商户名称框
        spdetail = (TextView) findViewById(R.id.spdetail);//获取商品描述框
        sptupian = (ImageView) findViewById(R.id.sptupian);//商品图片

        biaoqianListView = (RecyclerView) findViewById(R.id.biaoqian_listview);
        jiageradiogrop = (FlowRadioGroup) findViewById(R.id.jiageradiogrop);

        count_text = (TextView) findViewById(R.id.count);//获取数量显示框对象
        price_text = (TextView) findViewById(R.id.total_price);//获取价格显示框对象
        add = (ImageButton) findViewById(R.id.count_add);//获取增加按钮对象
        reduce = (ImageButton) findViewById(R.id.count_reduce);//获取减少按钮对象

        dizhifuwubiaoqiangrop = (FlowRadioGroup) findViewById(R.id.dizhifuwubiaoqiangrop);
        ziqu_radio = (RadioButton) findViewById(R.id.ziqu);//获取自取按钮对象
        songhuo_radio = (RadioButton) findViewById(R.id.songhuo);//获取外卖按钮对象
        songhuoaddress = (EditText) findViewById(R.id.input_waimaiaddress);//获取地址输入框对象设置字体
        chuancai_radio = (RadioButton) findViewById(R.id.chuancai);//获取传菜按钮对象
        tangshi_radio = (RadioButton) findViewById(R.id.tangshi);
        chuwugui_radio = (RadioButton) findViewById(R.id.chuwugui);
        chuancaiaddress = (EditText) findViewById(R.id.input_chuancaiaddress);//获取地址输入框对象设置字体
        xiadanButton = (Button) findViewById(R.id.xiadan_button);
    }

    //初始化listview的数据arraylist
    public void initList() {
        jiagebiaoqian = new Biaoqian(vSpview.getSpPricebiaoqian());//初始化当前商品的价格标签对象
        biaoqianlist = initBiaoQianList(vSpview);//初始化当前商品的可选标签对象
    }

    public void refreshprice() {
        count_text.setText(String.valueOf(count));//显示当前数量
        totalPrice = price * count;

        price_text.setText(String.format("¥%.2f" , totalPrice));//显示当前总价
    }

    //自定义hander类
    private Handler getShangpingHander = new Handler() {
        public void handleMessage(Message msg) {
//            xiadanButton.setEnabled(true);
            switch (msg.what) {
                case 2232:
                    initList();
                    price = jiagebiaoqian.getPrices()[0];
                    refreshprice();
                    //添加价格标签控件
                    jiageradiogrop.removeAllViews();
                    Log.d("jiagebiaoqian changdu :",""+jiagebiaoqian.getStrings().length);
                    for (int i = 0; i < jiagebiaoqian.getStrings().length; i++) {
                        final RadioButton tempbutton = (RadioButton) LayoutInflater.from(getApplicationContext()).inflate(R.layout.radio_button, null);
                        tempbutton.setText(jiagebiaoqian.getStrings()[i]);
                        jiageradiogrop.addView(tempbutton);
                        if (i == 0) {
                            jiageradiogrop.check(tempbutton.getId());
                        }
                    }
                    jiageradiogrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int isel) {

            /***********************2017年11月19日18:05:45*********************************/
                            String stringg=vSpview.getSpPricebiaoqian();
                            String[] strings = stringg.split("\\|");
             /***********************2017年11月19日18:05:45*********************************/
                            RadioButton radioButton = (RadioButton) radioGroup.findViewById(isel);
                            String string = radioButton.getText().toString();
                            String string1 = string.substring(string.indexOf("￥") + 1, string.indexOf(")"));
                            {
                               //2017年9月23日 13:35:56，修改标签和上平价格读取和提取错误的bug
                               String bq = string.replace("\n(￥", "=");
                                bq = bq.replace(")","");
//                               bq = bq.substring(0, bq.indexOf(")"));
                               jiagebiaoqian.setCheckedbiqoain(bq);
                                /***********************2017年11月19日18:05:45*********************************/
//                                log.d("i===============", ""+isel);
//                                jiagebiaoqian.setCheckedbiqoain(strings[isel-1]);
                                /***********************2017年11月19日18:05:45*********************************/
                            }
                            price = Double.parseDouble(string1);
                            refreshprice();
                        }
                    });
                    BiaoqianAdapter biaoqianAdapter = new BiaoqianAdapter(biaoqianlist, getApplicationContext());//用参数biaoqianlist构造适配器;.............
                    biaoqianAdapter.setItemClickListener(new BiaoqianAdapter.MyItemClickListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                            ;
                        }

                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i, int t) {
                            RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                            biaoqianlist.get(t).setCheckedbiqoain(radioButton.getText().toString());
                        }
                    });
                    biaoqianListView.setAdapter(biaoqianAdapter);
                    biaoqianAdapter.notifyDataSetChanged();
                    biaoqianListView.invalidate();
                    spname.setText(vSpview.getSpName());//商品名称

                {
                    int temp = 0;
                    dizhifuwubiaoqian = Integer.parseInt(vSpview.getSpDizhifuwubiaoqian());
                    if (dizhifuwubiaoqian >= 10000) {
                        ziqu_radio.setVisibility(View.VISIBLE);
                        temp++;
                        if (temp == 1) {
                            ziqu_radio.setChecked(true);
                        }
                        dizhifuwubiaoqian = dizhifuwubiaoqian % 10000;
                    }
                    if (dizhifuwubiaoqian >= 1000) {
                        tangshi_radio.setVisibility(View.VISIBLE);
                        temp++;
                        if (temp == 1)
                            tangshi_radio.setChecked(true);
                        dizhifuwubiaoqian = dizhifuwubiaoqian % 1000;
                    }
                    if (dizhifuwubiaoqian >= 100) {
                        chuancai_radio.setVisibility(View.VISIBLE);
                        temp++;
                        if (temp == 1) {
                            chuancai_radio.setChecked(true);
                            chuancaiaddress.setVisibility(View.VISIBLE);
                        }
                        dizhifuwubiaoqian = dizhifuwubiaoqian % 100;
                    }
                    if (dizhifuwubiaoqian >= 10) {
                        songhuo_radio.setVisibility(View.VISIBLE);
                        temp++;
                        if (temp == 1) {
                            songhuo_radio.setChecked(true);
                            songhuoaddress.setVisibility(View.VISIBLE);
                        }
                        dizhifuwubiaoqian = dizhifuwubiaoqian % 10;
                    }
                    if (dizhifuwubiaoqian >= 1) {
                        chuwugui_radio.setVisibility(View.VISIBLE);
                        temp++;
                        if (temp == 1)
                            chuwugui_radio.setChecked(true);
                    }
                }
                shname.setText(vSpview.getShName());//商户名称
                spdetail.setText(vSpview.getSpJianjie());//商品说明书
                sptupian.setImageBitmap(vSpview.getBitmap());
                break;
                default:
                    break;
            }
        }
    };

    public void DoGetShangpingn(String url) {

        try {
            OkHttpClient mCli = new OkHttpClient();
            Request mReq = new Request.Builder()
                    .url(url)
                    .build();
            Response mResp = mCli.newCall(mReq).execute();
            String responsedata = mResp.body().string();//得到服务器返回的具体内容`
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(responsedata);
            JsonNode jn = rootNode.path("result");
            PojoMapper pj = new PojoMapper();
            vSpview = (VSpview) pj.fromJson(jn.toString(), VSpview.class);
            Bitmap bmp = null;
            try {
                if(!vSpview.getSpTupian().equals("")&&vSpview.getSpTupian()!=null){
                    bmp = DoGetImage(vSpview.getSpTupian());
                }

            } catch (Exception e) {
                e.printStackTrace();
                bmp = null;
            }
            if (bmp == null) {
                bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
            }
            vSpview.setBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载图片
    public Bitmap DoGetImage(String imgurl)
            throws java.io.IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(IP.SERVERURL + imgurl)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            Log.d("onResponse: ", "xxxxxxxxx");
            byte[] Picture_bt = response.body().bytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length);
            return bitmap;
        }
        return null;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent.getDataString() != null) {
            String appurl = intent.getDataString();//获得返回过来的uri地址
            realurl = appurl.replace(IP.SERVERURI+"s", IP.SERVERURL+"sp");//uri转成可用的url地址，向后台获取商品信息
//            realurl = realurl.replace("s","sp");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);
        findView();//初始化组件

        //从sharepreferce中读取记录额优先购买方式，来改变port。
        SharedPreferences morengoumaifangshi = this.getSharedPreferences("morendaigoufangshi", Context.MODE_APPEND);
        int s = morengoumaifangshi.getInt("morendaigoufanghi",1);
        if(s == 1) {//默认代购
            port = daigouport;
        }else if(s == 2){
            port = port;
        }

        biaoqianListView.setLayoutManager(new LinearLayoutManager(this));

        new Thread(new Runnable() {//获取商品信息
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        xiadanButton.setEnabled(false);
                    }
                });

                DoGetShangpingn(realurl+"/"+TokenBuilder.buildToken());
                Message msg = new Message();
                msg.what = 2232;
                getShangpingHander.sendMessage(msg);
            }
        }).start();

//        //加号点击事件实现数量加一
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                Count = String.valueOf(count);
                count_text.setText(Count);//显示当前数量
                totalPrice = price * count;
                TotalPrice = String.valueOf(totalPrice);
                price_text.setText("¥" + TotalPrice);//显示当前总价
                //添加数量不能大于存货量判断
                refreshprice();
            }
//
        });
        //减号点击事件实现数量减一
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                Count = String.valueOf(count);
                count_text.setText(Count);//显示当前数量

                totalPrice = price * count;
                TotalPrice = String.valueOf(totalPrice);
                price_text.setText("¥" + TotalPrice);//显示当前总价
                //数量不能小于1
                if (count < 1) {
                    count = 1;
                    Count = String.valueOf(count);
                    count_text.setText(Count);
                    totalPrice = price * count;
                    TotalPrice = String.valueOf(totalPrice);
                    price_text.setText("¥" + TotalPrice);
                    Toast.makeText(OrderActivity.this, "数量不能小于1！", Toast.LENGTH_SHORT).show();
                }
                refreshprice();
            }
        });

        xiadanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当选中送货和传菜标签时，如果地址为空则不允许提交



                if(dizhifuwubiaoqiangrop.getCheckedRadioButtonId() == R.id.songhuo){
                    if(songhuoaddress.getText().toString().equals("") || songhuoaddress.getText().toString() == null){
                        Toast.makeText(getApplicationContext(),"送货地址不能为空！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(dizhifuwubiaoqiangrop.getCheckedRadioButtonId() == R.id.chuancai){
                    if(chuancaiaddress.getText().toString().equals("") || chuancaiaddress.getText().toString() == null){
                        Toast.makeText(getApplicationContext(),"传菜地址不能为空！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                xiadanButton.setEnabled(false);
                            }
                        });
                        port = huiyuanport;
                        Message msg = new Message();
                        msg.what = postJson();
                        posthander.sendMessage(msg);
                    }
                }).start();
            }
        });
        ziqu_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuancaiaddress.setVisibility(View.GONE);//隐藏地址输入框
                songhuoaddress.setVisibility(View.GONE);//显示地址输入框
            }
        });
        songhuo_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songhuoaddress.setVisibility(View.VISIBLE);//显示地址输入框
                chuancaiaddress.setVisibility(View.GONE);//显示地址输入框
            }
        });
        chuancai_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuancaiaddress.setVisibility(View.VISIBLE);//显示地址输入框
                songhuoaddress.setVisibility(View.GONE);//隐藏地址输入框
            }
        });
        tangshi_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuancaiaddress.setVisibility(View.GONE);//显示地址输入框
                songhuoaddress.setVisibility(View.GONE);//隐藏地址输入框
            }
        });
        chuwugui_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuancaiaddress.setVisibility(View.GONE);//显示地址输入框
                songhuoaddress.setVisibility(View.GONE);//隐藏地址输入框
            }
        });
    }

    @Override
    public int getViewId() {
        return R.layout.order_page;
    }

    @Override
    public void prepareWork() {

    }

    @Override
    public void bindView() {

    }


    private  Handler tuiSongHander =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TUISONGCHENGGONG:
                    Toast.makeText(getApplicationContext(),"已通知商户",Toast.LENGTH_LONG).show();
                    break;
                case TUISONGSHIBAI:
                    Toast.makeText(getApplicationContext(),"通知商户失败",Toast.LENGTH_LONG).show();
                    break;
                case EXCEPTION:
                    ExceptionAlert.cancelalert(OrderActivity.this);//异常弹窗
                    break;
                default:
                    break;
            }
        }
    };

    private Handler posthander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            xiadanButton.setEnabled(true);
            switch (msg.what) {
                case 200://正常
                    final Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "下单成功", Toast.LENGTH_LONG).show();
                    //此线程用于在下单成功后访问推送接口
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String code= OkhttpUtils.get(IP.SERVERURL+"m/SendMsg/"+ddid);
                            Message msg = new Message();
                            switch (code){
                                case "\"200\"":
                                    msg.what = TUISONGCHENGGONG;//200
                                    break;
                                case "\"201\"":
                                    msg.what = TUISONGSHIBAI;//201
                                    break;
                                case "EXCEPTION":
                                    msg.what = EXCEPTION;//999
                                    break;
                                default:
                                    break;
                            }
                            tuiSongHander.sendMessage(msg);
                        }
                    }).start();
                    break;
                case 30104:
                    Toast.makeText(getApplicationContext(), "该公司不是本店会员，代购失败", Toast.LENGTH_LONG).show();
                    break;
                case 30101://非会员，需充值会员
                    Toast.makeText(getApplicationContext(), "非会员，请充值", Toast.LENGTH_LONG).show();
                    //跳转
                    break;
                case 30102://总余额不足，需充值爱微币
                    Toast.makeText(getApplicationContext(), "总余额不足，需充值爱微币", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage("总余额不足，需充值爱微币");     //添加MSG
                    LayoutInflater inflater = getLayoutInflater();
                    View views = inflater.inflate(R.layout.custom_view, null);
                    Button button1 = (Button) views.findViewById(R.id.A);
                    Button button2 = (Button) views.findViewById(R.id.B);
                    Button button3 = (Button) views.findViewById(R.id.C);
                    button1.setVisibility(View.GONE);
                    button2.setText("前往充值");
                    button3.setText("下次再说");
                    builder.setView(views);//添加自定义View
                    final AlertDialog alertdialog1 = (AlertDialog) builder.create();
                    final AlertDialog alertDialog1 = builder.show();
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent1 = new Intent(OrderActivity.this, IVBrechargeActivity.class);
                            intent1.putExtra("shId", vSpview.getShid().toString());
                            intent1.putExtra("from", "OrderActivity");
                            intent1.putExtra("tDingdanBack", tDingdanBack);
                            startActivity(intent1);
                            alertDialog1.dismiss();
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog1.dismiss();
                        }
                    });
                    //跳转
                    break;
    //2017年11月16日16:25:09
                case 30103://爱微币不足，代购失败。
                    Toast.makeText(getApplicationContext(),"爱微币不足，订单未支付成功",Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(OrderActivity.this,MainActivity.class);
                    startActivity(intent1);
                    break;

                case 30100://商户币和临时币不足，充值商户币或代购
                    final AlertDialog.Builder builder2 = new AlertDialog.Builder(OrderActivity.this);
                    builder2.setMessage("商户币和零时币不足以支付商品");     //添加MSG
                    LayoutInflater inflater1 = getLayoutInflater();
                    View views1 = inflater1.inflate(R.layout.custom_view, null);
                    builder2.setView(views1);//添加自定义View
                    builder2.create();
                    alertdialog = (AlertDialog) builder2.show();
                    Button button4 = (Button) views1.findViewById(R.id.A);
                    Button button5 = (Button) views1.findViewById(R.id.B);
                    Button button6 = (Button) views1.findViewById(R.id.C);
                    button4.setText("充值商户币");
                    button5.setText("爱微币代购");
                    button6.setVisibility(View.GONE);
                {
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            gethuiyuanSh(IP.SERVERURL+"vk/KHhy/"+ IvmApplication.kehu.kHID.toString()+"/"+vSpview.getShid().toString());

                        }
                    });
                    button5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Toast.makeText(getApplicationContext(), "代购", Toast.LENGTH_SHORT).show();
                            alertdialog.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    port = daigouport;
                                    Message msg = new Message();
                                    msg.what = repostJson();
                                    posthander.sendMessage(msg);
                                }
                            }).start();
                        }
                    });
                }
                //跳转
                break;
                case 44200:
                    Toast.makeText(getApplicationContext(), "库存不足", Toast.LENGTH_SHORT).show();
                    break;
                case 47000:
                    Toast.makeText(getApplicationContext(), "标签错误", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "访问出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    //获取下单页面的信息生成订单，提交服务器，根据返回值判断提交结果
    private int postJson(){
        tDingdanTemp.sPID = vSpview.getSpid();
        tDingdanTemp.kHID = IvmApplication.kehu.kHID;
        tDingdanTemp.sHID = vSpview.getShid();
        tDingdanTemp.ddImei = "**imei?**";
        tDingdanTemp.ddvalue = new BigDecimal(totalPrice);
        tDingdanTemp.ddSPNum = count;
        tDingdanTemp.ddJiageBiaoqianZhi = jiagebiaoqian.getCheckedbiqoain();
        StringBuilder stringBuilder = new StringBuilder();

        for (Biaoqian biaoqian : biaoqianlist) {
            if (biaoqian.getCheckedbiqoain() != "") {
                stringBuilder.append(biaoqian.getCheckedbiqoain());
                stringBuilder.append("、");
            }
        }
        tDingdanTemp.setDdKexuanBiaoqianZhi(stringBuilder.toString());
        if (ziqu_radio.isChecked()) {
            tDingdanTemp.setDdWeizhiFuwuBiaoqian("10000");
            tDingdanTemp.setDdTihuoDidian("自取带走");
        }else if(tangshi_radio.isChecked()){
            tDingdanTemp.setDdWeizhiFuwuBiaoqian("1000");
            tDingdanTemp.setDdTihuoDidian("堂食");
        }else if(chuwugui_radio.isChecked()){
            tDingdanTemp.setDdWeizhiFuwuBiaoqian("1");
            tDingdanTemp.setDdTihuoDidian("橱窗自取");
        }else if (songhuo_radio.isChecked()) {
            tDingdanTemp.setDdWeizhiFuwuBiaoqian("10");
            tDingdanTemp.setDdTihuoDidian(songhuoaddress.getText().toString());
        } else if (chuancai_radio.isChecked()) {
            tDingdanTemp.setDdWeizhiFuwuBiaoqian("100");
            tDingdanTemp.setDdTihuoDidian(chuancaiaddress.getText().toString());
        }


        OkHttpClient okHttpClient = new OkHttpClient();
        PojoMapper pojoMapper = new PojoMapper();
        int codeint = 0;
        try {
            String json = pojoMapper.toJson(tDingdanTemp, true);
            System.out.print(json);
            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(IP.SERVERURL + port + TokenBuilder.buildToken())
                    .post(requestBody)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responsedata = response.body().string();
                Log.d("responsedata", responsedata);
                ObjectMapper m = new ObjectMapper();
                JsonNode jsonNode = m.readTree(responsedata);
                JsonNode jsonNode1 = jsonNode.path("Code");
                JsonNode resultNode = jsonNode.path("result");
                ddid = resultNode.toString();//存储下来返回的订单id
                String code = jsonNode1.toString();
                code = code.replace("\"", "");
                codeint = Integer.valueOf(code);
                if(codeint != 200){
//                if (true) {
                    JsonNode jsonNode2 = jsonNode.path("result");
                    Log.d("lvhao", jsonNode2.toString());
                    tDingdanBack = (TDingdan) pojoMapper.fromJson(jsonNode2.toString(), TDingdan.class);
                    ddid = tDingdanBack.dDID.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return codeint;
    }
    private int repostJson() {
        OkHttpClient okHttpClient = new OkHttpClient();
        PojoMapper pojoMapper = new PojoMapper();
        int codeint = 0;
        try {
            String json = pojoMapper.toJson(tDingdanBack, true);
            RequestBody requestBody = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .url(IP.SERVERURL + port + tDingdanBack.dDID + "/" + TokenBuilder.buildToken())
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responsedata = response.body().string();
                ObjectMapper m = new ObjectMapper();
                JsonNode jsonNode = m.readTree(responsedata);
                JsonNode jsonNode1 = jsonNode.path("Code");
                String code = jsonNode1.toString();
                code = code.replace("\"", "");
                codeint = Integer.valueOf(code);
                if (codeint != 200) {
                    JsonNode jsonNode2 = jsonNode.path("result");
                    tDingdanBack = (TDingdan) pojoMapper.fromJson(jsonNode2.toString(), TDingdan.class);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return codeint;
    }


    private   void gethuiyuanSh(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String code="EXCEPTION";
                code=OkhttpUtils.getResult(url);
                JsonNode jn=OkhttpUtils.getJsonnode();
                if (jn.isArray()) {
                    Iterator<JsonNode> iterator = jn.elements();
                    PojoMapper pj = new PojoMapper();
                    while (iterator.hasNext()) {
                        JsonNode nd = iterator.next();
                        try {
                            vhuiyuanSH = (TMyVhuiyuanSH) pj.fromJson(nd.toString(), TMyVhuiyuanSH.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Message msg = new Message();
                switch (code){
                    case "\"200\"":
                        msg.what = 200;
                        msg.obj = vhuiyuanSH;
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


    private  Handler mHand = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    Toast.makeText(getApplicationContext(), "充值商户币", Toast.LENGTH_SHORT).show();
                    alertdialog.dismiss();
                    Intent intent1 = new Intent(OrderActivity.this, HYrechargeActivity.class);
                    intent1.putExtra("shId", vSpview.getShid().toString());
                    intent1.putExtra("from", "OrderActivity");
                    intent1.putExtra("ddid", tDingdanBack.dDID.toString());
                    intent1.putExtra("vhuiyuan",vhuiyuanSH);
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    };
    /*
    * in:spview
    * out:ArrayList<Biaoqian>
     */
    public ArrayList<Biaoqian> initBiaoQianList(VSpview vSpview){
        ArrayList<Biaoqian> a = new ArrayList<>();
        String kxbq = vSpview.getSpKexuanbiaoqian();
        String[] list = kxbq.split("#");
        for (int i = 0; i < list.length; i++) {
            Biaoqian tempBiaoqian = new Biaoqian(list[i]);
            tempBiaoqian.setCheckedbiqoain(tempBiaoqian.getStrings()[0]);
            a.add(tempBiaoqian);//添加到标签list中去
        }
        return a;
    }
}
