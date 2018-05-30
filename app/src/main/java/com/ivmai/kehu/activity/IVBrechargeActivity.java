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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alipay.sdk.app.PayTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ivm.entity.TDingdan;

import com.ivm.entity.alipay.AlipayRequestMsg;
import com.ivm.entity.alipay.AlipayResponseMsg;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.IVBrecharge;
import com.ivmai.kehu.R;
import com.ivmai.kehu.adapter.IVBrechargeAdapter;
import com.ivmai.kehu.alipay.PayResult;
import com.ivmai.kehu.tools.PojoMapper;
import com.ivmai.kehu.tools.RandomString;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.IP;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IVBrechargeActivity extends AppCompatActivity {
    private List<IVBrecharge> mivBrecharges = new ArrayList<>();
    private String a = "5,10,20,50,100,200,300,500";
    private String[] b = a.split(",");
    TDingdan tDingdan = new TDingdan();
    public String from = "";
    int checked=0;
    String ddid="0";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    AlipayResponseMsg alipayResponseMsg;
    EditText recharge_input;
    TextView chongzhiid;
    private String chargeFunds;
    Button confirmrechargebutton;
    RadioGroup radioGroup;
    static final int OK = 101;

    private IVBrechargeAdapter ivBrechargeAdapter;

    public void iniIVBrecharge() {
        int i;
        for (i = 0; i < 8; i++) {
            IVBrecharge a = new IVBrecharge(b[i]);
            mivBrecharges.add(a);
        }
    }

    TextView recharge_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
//        tDingdan = (TDingdan) intent.getSerializableExtra("tDingdanBack");
        ddid = intent.getStringExtra("ddid");
        setContentView(R.layout.ivb_recharge);

        //更改标题栏显示内容
        TextView textView = (TextView) findViewById(R.id.title_name);
        textView.setText("爱微币充值");
        //更改充值账号中的内容为当前用户的手机号码
        chongzhiid = (TextView) findViewById(R.id.ivb_recharge_khid);
        chongzhiid.setText(IvmApplication.kehu.khTel);
        //充值键
        confirmrechargebutton = (Button) findViewById(R.id.confirmrecharge_button);
        //
        recharge_show = (TextView) findViewById(R.id.ivb_recharge_money);
        recharge_input = (EditText) findViewById(R.id.ivb_recharge_input);
        confirmrechargebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup = (RadioGroup) findViewById(R.id.pay_method);
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.r1:
                        Toast.makeText(getApplicationContext(), "微信支付暂不开通", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.r2:
                        if (Integer.parseInt(recharge_show.getText().toString()) == 0) {
                            Toast.makeText(getApplicationContext(), "充值金额不能为0", Toast.LENGTH_LONG).show();
                            return;
                        } else if (recharge_input.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "请输入充值金额", Toast.LENGTH_LONG).show();
                            return;
                        }
                        new Thread(alipayrunable).start();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "请选择充值方式", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

//         自定义金额输入框输入事件监听
        recharge_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null || !charSequence.equals("")) {
                    try {
                        int str = Integer.parseInt(String.valueOf(charSequence));
                        if (str > 5000) {
                            Toast.makeText(IVBrechargeActivity.this, "输入金额不得大于5000元", Toast.LENGTH_SHORT).show();
                            recharge_input.setText("5000");
                            recharge_input.setSelection(recharge_input.getText().length());
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
                }
            }
        });
        iniIVBrecharge();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ivb_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        ivBrechargeAdapter = new IVBrechargeAdapter(this, mivBrecharges);
        recyclerView.setAdapter(ivBrechargeAdapter);
        //调用方法,传入一个接口回调
        ivBrechargeAdapter.setItemClickListener(new IVBrechargeAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                IVBrecharge ivBrecharge = mivBrecharges.get(position);
                recharge_input.setText(ivBrecharge.getIvb());
            }
        });

    }

    final Runnable alipayrunable = new Runnable() {
        @Override
        public void run() {
            try {
                AlipayRequestMsg alipayRequestMsg = new AlipayRequestMsg();
                alipayRequestMsg.setChongZhiflag("1");//充值对象，规定1为公司，2为商户
                alipayRequestMsg.setGSID("3");//设置gongsiid,目前公司id只为3
                alipayRequestMsg.setKHID(IvmApplication.kehu.kHID.toString());//充值kehuid
                alipayRequestMsg.setJinE(recharge_show.getText().toString());//充值金额
                alipayRequestMsg.setONCE_STR(RandomString.getRandomString(6));//设置一个唯一码，来防止重复提交
                PojoMapper pojoMapper = new PojoMapper();
                String json = pojoMapper.toJson(alipayRequestMsg, true);
                System.out.println("json" + json);

                RequestBody requestBody = RequestBody.create(JSON, json);

//                log.d("ssss", IP.SERVERURL);
//                log.d("ssss", IvmApplication.getAccount());

                Request request = new Request.Builder().url(IP.SERVERURL + "alipay/ChongZhiGS/" + TokenBuilder.buildToken()).post(requestBody).build();

//                log.d("ssss", IP.SERVERURL + "alipay/ChongZhiGS/" + IvmApplication.getAccount());

                OkHttpClient okHttpClient = new OkHttpClient();

                Response response = okHttpClient.newCall(request).execute();

                String responsedata = responsedata = response.body().string();

                System.out.println("支付宝返回结果" + responsedata);

                alipayResponseMsg = (AlipayResponseMsg) pojoMapper.fromJson(responsedata, AlipayResponseMsg.class);


                System.out.println("支付宝返回结果2" + alipayResponseMsg.getorderInfo());
                PayTask alipay = new PayTask(IVBrechargeActivity.this);
                Map<String, String> result = alipay.payV2(alipayResponseMsg.getorderInfo(), true);
                Message message = new Message();
                message.what = OK;//访问成功
                message.obj = result;
                myhander.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    final Handler myhander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OK:
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
//                    if (true) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(IP.SERVERURL+ "alipay/CheckCharge" + "/"+alipayResponseMsg.getdJID().toString()+ "/"+TokenBuilder.buildToken())
                                        .build();
                                try {
                                    Response response = okHttpClient.newCall(request).execute();
                                    String responsedata = response.body().string();
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    JsonNode rootNode = objectMapper.readTree(responsedata);
                                    JsonNode code = rootNode.path("Code");
                                    String c = code.toString();
                                    c =c.replace("\"","");
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
                        Toast.makeText(IVBrechargeActivity.this, "支付失败2", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(IVBrechargeActivity.this, IVBdetailsActivity.class));
                    }
                    break;
                case 0:
                    Log.d("OrderInfo", "服务器没有返回orderinfo");
                    break;
                default:
                    break;
            }
        }
    };

    private int repostJson(){
        OkHttpClient okHttpClient = new OkHttpClient();
        PojoMapper pojoMapper = new PojoMapper();
        int codeint = 0;
        try {
            String json = pojoMapper.toJson(tDingdan, true);
            Log.d("hhhhh", json);
            RequestBody requestBody = RequestBody.create(JSON, json);
//            log.d("hhhh", "d/KHDaiGou/");
//            log.d("hhh", IP.SERVERURL + "d/KHDaiGou/" + IvmApplication.getAccount());

            Request request = new Request.Builder()
                    .url(IP.SERVERURL+ "d/KHDaiGou/"+ddid+"/"+ TokenBuilder.buildToken())
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return codeint;
    }
    Handler checkhander = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 200://成功
                            checked = 1;
                            Toast.makeText(IVBrechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(IVBrechargeActivity.this, IVBdetailsActivity.class));
                    break;
                case 201:
                    checked = 0;
                    Toast.makeText(IVBrechargeActivity.this, "支付失败1", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(IVBrechargeActivity.this, IVBdetailsActivity.class));
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
                    final Intent intent = new Intent(IVBrechargeActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "下单成功", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "连接不上服务器", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
