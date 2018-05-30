package com.ivmai.kehu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivm.entity.TMyVhuiyuanSH;
import com.ivm.entity.VhuiyuanSH;
import com.ivmai.kehu.R;


public class HYdetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hy_details);

        //更改标题栏显示内容
        TextView textView=(TextView)findViewById(R.id.title_name);
        textView.setText("会员详情");

        Button recharge=(Button)findViewById(R.id.recharge_button);
        Button sendmsg=(Button)findViewById(R.id.sendmsg_button);

        TextView shname=(TextView)findViewById(R.id.hy_detail_shname) ;
        TextView hyjibie=(TextView)findViewById(R.id.hy_detail_hyjibie);
        TextView hyshb=(TextView)findViewById(R.id.hy_detail_hyshb);


        Intent intent=getIntent();
        final String shName=intent.getStringExtra("shName");
        final String hyJibie=intent.getStringExtra("hyJibie");
        final String hyShb=intent.getStringExtra("hyShb");
        final String hyZhekou=intent.getStringExtra("hyZhekou");
        final String shId=intent.getStringExtra("shId");
        final int  hyJibieint= intent.getIntExtra("hyJibieint",0);
        final TMyVhuiyuanSH tMyVhuiyuanSH1 = (TMyVhuiyuanSH) intent.getSerializableExtra("vhuiyuan");//获取传过来的会员对象！！！
        //设置商户名称！！
        shname.setText(shName);
//        hyjibie.setText(hyJibie+"(本店享受全场"+hyZhekou+"折优惠)");
        //设置商户商户币余额
        hyshb.setText(hyShb);
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HYdetailsActivity.this,HYrechargeActivity.class);
                intent.putExtra("shName",shName);
                intent.putExtra("hyJibie",hyJibie);
                intent.putExtra("hyShb",hyShb);
                intent.putExtra("hyZhekou",hyZhekou);
                intent.putExtra("shId",shId);
                intent.putExtra("hyJibieint",hyJibieint);
//                VhuiyuanSH vhuiyuanSH = new VhuiyuanSH();
//                vhuiyuanSH = tMyVhuiyuanSH1;
                intent.putExtra("vhuiyuan",tMyVhuiyuanSH1);//向下一个页面传递vhuiyaun对象
                startActivity(intent);
            }
        });

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(HYdetailsActivity.this,SendMsgToShActivity.class);
                intent1.putExtra("shid",shId);
                startActivity(intent1);
            }
        });
    }
}
