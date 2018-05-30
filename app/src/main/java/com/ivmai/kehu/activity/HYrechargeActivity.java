package com.ivmai.kehu.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alipay.sdk.app.PayTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivm.entity.TDingdan;
import com.ivm.entity.TMyVhuiyuanSH;
import com.ivm.entity.alipay.AlipayResponseMsg;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.ExceptionAlert;
import com.ivmai.kehu.tools.HYrecharge;
import com.ivmai.kehu.R;
import com.ivmai.kehu.adapter.HYrechargeAdapter;
import com.ivmai.kehu.alipay.PayResult;
import com.ivmai.kehu.tools.OkhttpUtils;
import com.ivmai.kehu.tools.PojoMapper;
import com.ivmai.kehu.tools.RandomString;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.IP;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HYrechargeActivity extends AppCompatActivity {
    public static  final int TUISONGCHENGGONG = 200;
    public static  final int TUISONGSHIBAI = 201;
    public static  final int EXCEPTION = 999;
    static final int GET_ORDERINFO_SUCCESS = 101;
    static final int GET_ORDERINFO_FAILED = 103;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private List<HYrecharge> hYrechargeList = new ArrayList<>();
    private String ha = "5,20,50,100,200,500";
    private String[] b = ha.split(",");

    private String chargeFunds;

    private double a = 0.0;//返现比例，默认值为0.1
    private BigDecimal gift = new BigDecimal("0"); //定义一个大数,即赠送金额
    int checked = 0;

    //intent传递的信息
    public String shName = "";
    public String hyJibie = "";
    public String hyShb = "";
    public String hyZhekou = "";
    public String shId = "";
    public int hyJibieint = 0;    //


    public TMyVhuiyuanSH tMyVhuiyuanSH;
    TDingdan tDingdan = new TDingdan();
    String ddid = 0 + "";
    public String from = "";
    AlipayResponseMsg alipayResponseMsg;

    private HYrechargeAdapter hYrechargeAdapter;

    public void iniHYrecharge() {
        int i;
        for (i = 0; i < 6; i++) {
            HYrecharge a = new HYrecharge(b[i]);
            hYrechargeList.add(a);
        }
    }

    TextView recharge_show;//充值金额S
    TextView recharge_gift;//返现金额
    Button confirmrechargebutton;//充值按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.hy_recharge);
//         获得传入值得时候传过来的信息
        Intent intent = getIntent();
        shName = intent.getStringExtra("shName");
        hyJibie = intent.getStringExtra("hyJibie");
        hyShb = intent.getStringExtra("hyShb");
        hyZhekou = intent.getStringExtra("hyZhekou");
        shId = intent.getStringExtra("shId");
        hyJibieint = intent.getIntExtra("hyJibieint",0);
        from = intent.getStringExtra("from");
//        tDingdan = (TDingdan) intent.getSerializableExtra("tDingdanBack");
        ddid = intent.getStringExtra("ddid");

            tMyVhuiyuanSH = (TMyVhuiyuanSH)intent.getSerializableExtra("vhuiyuan");//接受到V会员商户SSSS



        //用来计算会员的配套比率应该是多少
        float f = 0;
        int jibie = tMyVhuiyuanSH.getHyJibie();
        switch (jibie){
            case 1://
                f = tMyVhuiyuanSH.getShPutongpeitaobilu();
                a = Double.parseDouble(String.valueOf(f));//普通
                break;
            case 2:
                f = tMyVhuiyuanSH.getShYinkapeitaobilu();
                a = Double.parseDouble(String.valueOf(f));//银卡
                break;
            case 3:
                f = tMyVhuiyuanSH.getShJinkapeitaobilu();
                a = Double.parseDouble(String.valueOf(f));//金卡
                break;
            case 4:
                f = tMyVhuiyuanSH.getShZuanshipeitaobilu();
                a = Double.parseDouble(String.valueOf(f));//钻石
                break;
            default:
                break;
        }
//        log.d("intent",shName+"#"+hyJibie+"#"+hyShb+"#"+hyZhekou+"#"+sshId+"#"+hyJibieint);

        //更改标题栏显示内容

        TextView textView = (TextView) findViewById(R.id.title_name);
        textView.setText("会员充值");

        recharge_show = (TextView) findViewById(R.id.hy_recharge_money);
        recharge_gift = (TextView) findViewById(R.id.hy_recharge_gift);
        final EditText recharge_input = (EditText) findViewById(R.id.hy_recharge_input);

        //充值键
        confirmrechargebutton = (Button) findViewById(R.id.hy_chongzhi_button);
        confirmrechargebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.hy_chongzhifangshi);
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.hywechat:
                        Toast.makeText(getApplicationContext(), "微信支付暂不开通", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.hyalipay:
                        if (Integer.parseInt(recharge_show.getText().toString()) == 0) {
                            Toast.makeText(getApplicationContext(), "充值金额不能为0", Toast.LENGTH_LONG).show();
                            return;
                        } else if (recharge_show.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "请输入充值金额", Toast.LENGTH_LONG).show();
                            return;
                        }
                        new Thread(alipayrunable).start();//支付宝充值商户
                        break;
//                    case R.id.hyivmai:
//                        if (Integer.parseInt(recharge_show.getText().toString()) == 0) {
//                            Toast.makeText(getApplicationContext(), "充值金额不能为0", Toast.LENGTH_LONG).show();
//                            return;
//                        } else if (recharge_show.getText().toString().equals("")) {
//                            Toast.makeText(getApplicationContext(), "请输入充值金额", Toast.LENGTH_LONG).show();
//                            return;
//                        }
//                        new Thread(ivbrunable).index();//iivb充值商户
//                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "请选择充值方式", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });


        //自定义金额输入框输入事件监听
        recharge_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null || !charSequence.equals("")) {
                    try {
                        Double str = Double.parseDouble(String.valueOf(charSequence));
                        {
                            BigDecimal a1 = new BigDecimal(Double.toString(str));
                            BigDecimal b1 = new BigDecimal(Double.toString(a));
                            gift = a1.multiply(b1);
                        }
                        if (str > 5000) {
                            Toast.makeText(HYrechargeActivity.this, "输入金额不得大于5000元", Toast.LENGTH_SHORT).show();
                            recharge_input.setText("5000");
                            recharge_input.setSelection(recharge_input.getText().length());
//                            gift = 5000 * a;
                            {
                                BigDecimal a1 = new BigDecimal(Double.toString(a));
                                BigDecimal b1 = new BigDecimal("5000");
                                gift = a1.multiply(b1);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    String inputText = recharge_input.getText().toString().trim();
                    if (Double.parseDouble(inputText) > 5000) {
                        chargeFunds = "5000";
                    } else {
                        chargeFunds = inputText;
                    }
                    recharge_show.setText(chargeFunds);
                    recharge_gift.setText(String.valueOf(gift));//赠送金额
                }
            }
        });
        iniHYrecharge();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.money_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        hYrechargeAdapter = new HYrechargeAdapter(this, hYrechargeList);
        recyclerView.setAdapter(hYrechargeAdapter);

        //调用方法,传入一个接口回调
        hYrechargeAdapter.setItemClickListener(new HYrechargeAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HYrecharge hYrecharge = hYrechargeList.get(position);
                recharge_input.setText(hYrecharge.getMoney());
//                gift = Double.valueOf(hYrecharge.getMoney()).intValue() * a;//赠送金额

                BigDecimal a1 = new BigDecimal(Double.toString(a));
                BigDecimal b1 = new BigDecimal(Double.toString(Double.valueOf(hYrecharge.getMoney())));
                 gift = a1.multiply(b1);

                recharge_gift.setText(String.valueOf( gift));
                recharge_show.setText(hYrecharge.getMoney());
            }
        });
    }

    final Runnable alipayrunable = new Runnable() {//用支付宝充值商户
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    confirmrechargebutton.setEnabled(false);
                }
            });
            try {
                HYRequest hyRequest = new HYRequest();
                hyRequest.chongZhiflag = "2";
                hyRequest.gsid = "5";
                hyRequest.jinE = recharge_show.getText().toString();
                hyRequest.khid = IvmApplication.kehu.kHID.toString();
                hyRequest.khivb = "0";
                hyRequest.shid = shId;
                hyRequest.once_STR = RandomString.getRandomString(6);

                PojoMapper pojoMapper = new PojoMapper();

                String json = pojoMapper.toJson(hyRequest, true);

                System.out.println("hyRequest" + json);

                RequestBody requestBody = RequestBody.create(JSON, json);


                Request request = new Request.Builder().url(IP.SERVERURL + "alipay/ChongZhiSH/" + TokenBuilder.buildToken()).post(requestBody).build();

                OkHttpClient okHttpClient = new OkHttpClient();

                Response response = okHttpClient.newCall(request).execute();

                String responsedata = response.body().string();

                System.out.println("支付宝返回结果" + responsedata);

                alipayResponseMsg = (AlipayResponseMsg) pojoMapper.fromJson(responsedata, AlipayResponseMsg.class);

                PayTask alipay = new PayTask(HYrechargeActivity.this);

                Map<String, String> result = alipay.payV2(alipayResponseMsg.getorderInfo(), true);

                Message message = new Message();

                message.what = GET_ORDERINFO_SUCCESS;//访问成功
                message.obj = result;
                myhander.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                Message message = new Message();
                message.what = GET_ORDERINFO_FAILED;//访问成功
                myhander.sendMessage(message);
            }
        }
    };
    final Handler myhander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            confirmrechargebutton.setEnabled(true);
            switch (msg.what) {
                case GET_ORDERINFO_SUCCESS:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(IP.SERVERURL + "alipay/CheckCharge" + "/" + alipayResponseMsg.getdJID().toString() + "/" + TokenBuilder.buildToken())
                                        .build();
                                try {
                                    Response response = okHttpClient.newCall(request).execute();
                                    String responsedata = response.body().string();
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    JsonNode rootNode = objectMapper.readTree(responsedata);
                                    JsonNode code = rootNode.path("Code");
                                    String c = code.toString();
                                    c = c.replace("\"", "");
                                    Message message = new Message();
                                    message.what = Integer.parseInt(c);
                                    checkhander.sendMessage(message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(HYrechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HYrechargeActivity.this, MainActivity.class));
                    }
                    break;
                case GET_ORDERINFO_FAILED:
                    Toast.makeText(getApplicationContext(), "网络超时", Toast.LENGTH_LONG);
                    startActivity(new Intent(HYrechargeActivity.this, MainActivity.class));
                    break;
                default:
                    break;
            }
        }
    };
    final Runnable ivbrunable = new Runnable() {
        @Override
        public void run() {
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   confirmrechargebutton.setEnabled(false);
               }
           });
            try {
                HYRequest hyRequest = new HYRequest();
                hyRequest.chongZhiflag = "2";
                hyRequest.gsid = "5";
                hyRequest.jinE = "0";
                hyRequest.khid = IvmApplication.kehu.kHID.toString();
                hyRequest.khivb = recharge_show.getText().toString();
                hyRequest.shid = shId;
                hyRequest.once_STR = RandomString.getRandomString(6);

                PojoMapper pojoMapper = new PojoMapper();

                String json = pojoMapper.toJson(hyRequest, true);

                Log.d("avimaiRequest", json);
                System.out.println("json" + json);

                RequestBody requestBody = RequestBody.create(JSON, json);

                Request request = new Request.Builder().url(IP.SERVERURL + "alipay/ChongZhiSH/" + TokenBuilder.buildToken()).post(requestBody).build();

                OkHttpClient okHttpClient = new OkHttpClient();

                Response response = okHttpClient.newCall(request).execute();

                String responsedata = response.body().string();

                System.out.println("后台返回结果A上" + responsedata);

                Message message = new Message();

                message.what = GET_ORDERINFO_SUCCESS;//访问成功
                ivbhander.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    Handler checkhander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200://成功
                    checked = 1;
                    Toast.makeText(HYrechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    if(from!=null && from.equals("OrderActivity")){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = repostJson();
                                posthander.sendMessage(msg);
                            }
                        }).start();
                    }
                    startActivity(new Intent(HYrechargeActivity.this, MainActivity.class));
                    break;
                case 201:
                    checked = 0;
                    Toast.makeText(HYrechargeActivity.this, "支付失败1", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HYrechargeActivity.this, MainActivity.class));
                    break;
                default:
                    break;
            }
        }
    };
    final Handler ivbhander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            confirmrechargebutton.setEnabled(true);
            switch (msg.what) {
                case GET_ORDERINFO_SUCCESS:
                    Toast.makeText(HYrechargeActivity.this, "爱微币转商户币支付成功", Toast.LENGTH_SHORT).show();
//                    RefreshTkehu.refreshTkehuByKhid();
                    if (from!=null && from.equals("OrderActivity")){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = repostJson();
                                posthander.sendMessage(msg);
                            }
                        }).start();
                    } else {
                        startActivity(new Intent(HYrechargeActivity.this, MainActivity.class));
            }
                    break;
                case GET_ORDERINFO_FAILED:
                    Toast.makeText(getApplicationContext(), "网络超时", Toast.LENGTH_LONG);
                    break;
                default:
                    break;
            }
        }
    };

    public class HYRequest {
        String chongZhiflag;
        String jinE;
        String khid;
        String once_STR;
        String shid;
        String khivb;
        String gsid;

        public String getchongZhiflag() {
            return chongZhiflag;
        }

        public void setchongZhiflag(String chongZhiflag) {
            this.chongZhiflag = chongZhiflag;
        }

        public String getjinE() {
            return jinE;
        }

        public void setjinE(String jinE) {
            this.jinE = jinE;
        }

        public String getkhid() {
            return khid;
        }

        public void setkhid(String khid) {
            this.khid = khid;
        }

        public String getonce_STR() {
            return once_STR;
        }

        public void setonce_STR(String once_STR) {
            this.once_STR = once_STR;
        }

        public String getshid() {
            return shid;
        }

        public void setshid(String shid) {
            this.shid = shid;
        }

        public String getkhivb() {
            return khivb;
        }

        public void setkhivb(String khivb) {
            this.khivb = khivb;
        }

        public String getgsid() {
            return gsid;
        }

        public void setgsid(String gsid) {
            this.gsid = gsid;
        }
    }

    private int repostJson() {
        OkHttpClient okHttpClient = new OkHttpClient();
        PojoMapper pojoMapper = new PojoMapper();
        int codeint = 0;
        try {
            String json = pojoMapper.toJson(tDingdan, true);

            RequestBody requestBody = RequestBody.create(JSON, json);


            Request request = new Request.Builder()
                    .url(IP.SERVERURL + "d/KHReCreate/" + ddid + "/" + TokenBuilder.buildToken())
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responsedata = response.body().string();
                Log.d("responsedata", responsedata);
                ObjectMapper m = new ObjectMapper();
                JsonNode jsonNode = m.readTree(responsedata);
                JsonNode jsonNode1 = jsonNode.path("Code");
                String code = jsonNode1.toString();
                code = code.replace("\"", "");
                codeint = Integer.valueOf(code);
                if (codeint != 200) {
                    JsonNode jsonNode2 = jsonNode.path("result");
                    tDingdan = (TDingdan) pojoMapper.fromJson(jsonNode2.toString(), TDingdan.class);
                    ddid = tDingdan.dDID.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return codeint;
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
                    ExceptionAlert.cancelalert(HYrechargeActivity.this);//异常弹窗
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
            switch (msg.what) {
                case 200://正常
                    final Intent intent = new Intent(HYrechargeActivity.this, MainActivity.class);
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
                    Toast.makeText(getApplicationContext(), "非会员，请前往充值会员", Toast.LENGTH_LONG).show();
                    //跳转
                    break;
                case 30102://总余额不足，需充值爱微币
                    Toast.makeText(getApplicationContext(), "总余额不足，需充值爱微币", Toast.LENGTH_SHORT).show();
                    //跳转
                    break;
                case 30100://商户币和临时币不足，充值商户币或代购
                    Toast.makeText(getApplicationContext(), "商户币和临时币不足，充值商户币或代购", Toast.LENGTH_LONG).show();
                    //跳转
                    break;
                case 47000:
                    Toast.makeText(getApplicationContext(), "标签错误", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "访问异常", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
