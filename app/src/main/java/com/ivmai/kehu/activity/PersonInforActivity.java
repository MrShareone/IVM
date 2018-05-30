package com.ivmai.kehu.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.adapter.PersonInforAdapter;
import com.ivmai.kehu.tools.NetWork;
import com.ivmai.kehu.tools.PersonInfor;
import com.ivmai.kehu.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class PersonInforActivity extends AppCompatActivity {


    private List<PersonInfor> personInforList=new ArrayList<>();

    public SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_information);

        personInforList.add(new PersonInfor("我的头像"));
        personInforList.add(new PersonInfor("修改密码"));
        personInforList.add(new PersonInfor("换绑手机"));
        personInforList.add(new PersonInfor("修改地址"));
        personInforList.add(new PersonInfor("下单设置"));

        if(IvmApplication.kehu !=null && IvmApplication.islogin && IvmApplication.kehu.khStatus==1)
        {
            personInforList.add(new PersonInfor("身份验证"));
        }
        personInforList.add(new PersonInfor("退出账号"));



        //更改标题栏显示内容
        TextView textView=(TextView)findViewById(R.id.title_name);
        textView.setText("个人设置");
        sp = this.getSharedPreferences("userInfo", Context.MODE_APPEND);//新建一个sharedpreferences，存放记下的密码账号
        PersonInforAdapter adapter=new PersonInforAdapter(PersonInforActivity.this,R.layout.person_infor_item,personInforList);
        ListView listView=(ListView)findViewById(R.id.person_infor_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PersonInfor personInfor=personInforList.get(position);
                switch (personInfor.getName()){
                    case "我的头像":
                        Intent intent=new Intent(PersonInforActivity.this,ChangeAvatarActivity.class);
                        startActivity(intent);
                        break;
                    case "修改密码":
                        Intent intent2=new Intent(PersonInforActivity.this,ChangePasswordActivity.class);
                        startActivity(intent2);
                        break;
                    case "换绑手机":
                        Intent intent3=new Intent(PersonInforActivity.this,ChangePhonubrActivity.class);
                        startActivity(intent3);
                        break;
                    case "修改地址":
//                        Intent intent4=new Intent(PersonInforActivity.this,IVBdetailsActivity.class);
//                        startActivity(intent4);
                        ToastUtil.showToast(PersonInforActivity.this,"开发中...");
                        break;
                    case "身份验证":
                        Intent intent5=new Intent(PersonInforActivity.this,SFrenzhengActivity.class);
                        startActivity(intent5);
                        break;
                    case "下单设置":
                        Intent intent6 = new Intent(PersonInforActivity.this,DefaultOrderSetting.class);
                        startActivity(intent6);
                        break;
                    case "退出账号":
                        zhuxiaoalert();
                        break;
                    default:
                        break;
                }

            }
        });

    }

//注销对话框
    public void zhuxiaoalert(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(PersonInforActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("确认要登出该账号?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (NetWork.isNetwork(PersonInforActivity.this)) {
                    //退出登录，删除此帐号有关信息
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("ivmkeystore", getApplicationContext().MODE_APPEND);
                    SharedPreferences sp3 = getApplicationContext().getSharedPreferences("ivmkhidstore", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = sp.edit();
                    SharedPreferences.Editor editor3 = sp3.edit();
                    editor.putString("ivmkey","");
                    editor.apply();
                    editor3.putString("ivmkhid","");
                    editor3.apply();
                    IvmApplication.kehu.kHID = 0L;
                    IvmApplication.islogin = false;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            TextView textView = (TextView)findViewById(R.id.ivm_id);
//                            textView.setText("未登录...");
//                        }
//                    }).index();

//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            TextView textView = (TextView)findViewById(R.id.ivm_id);
//                            textView.setText("未登录...");
//                        }
//                    });
                    Intent intent6=new Intent(PersonInforActivity.this,LoginActivity.class);
                    intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent6);
                    ToastUtil.showToast(PersonInforActivity.this,"注销成功");
                }else{
                    ToastUtil.showToast(PersonInforActivity.this,"当前无网络");
                }

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                ToastUtil.showToast(PersonInforActivity.this,"取消注销");
                ;
            }
        });
        dialog.show();
    }
}
