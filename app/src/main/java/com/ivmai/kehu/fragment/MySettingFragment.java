package com.ivmai.kehu.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.ivmai.base.IvmApplication;
import com.ivmai.bdpush.ChatMsgEvet;
import com.ivmai.kehu.R;

import com.ivmai.kehu.activity.IVBdetailsActivity;
import com.ivmai.kehu.activity.LoginActivity;
import com.ivmai.kehu.activity.MyBillActivity;
import com.ivmai.kehu.activity.MyOrderActivity;
import com.ivmai.kehu.activity.PersonInforActivity;
import com.ivmai.kehu.activity.SendMsgToUsActivity;
import com.ivmai.kehu.activity.VersionUpdateActivity;
import com.ivmai.kehu.chat.activity.Contacts;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lk on 2017/7/26.
 * Edit by Mr-shareone
 *设置页面
 */

public class MySettingFragment extends Fragment {


    private ArrayList<String> data=new ArrayList<>();

    static Bitmap mBitmap;

    static CircleImageView  userimg;

    ImageView imageView;  //消息盒子

    View dot;  //红点

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("MySettingFragment","oncreateviewcalled");
        EventBus.getDefault().register(this);
        View view=inflater.inflate(R.layout.my_fragment4,container,false);
        data.add("我的爱微币");
        data.add("我的优惠券");
        data.add("我的订单");
        data.add("我的账单");
        data.add("评价我们");
        data.add("版本更新");
        data.add("个人设置");
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        ListView listView=(ListView)view.findViewById(R.id.person_list);
        TextView ivmid = (TextView)view.findViewById(R.id.ivm_id);
        imageView = (ImageView)view.findViewById(R.id.messagebox);
        dot = (View)view.findViewById(R.id.dot);
        if(IvmApplication.kehu.khTel!=null&& IvmApplication.kehu.khTel!="")
        {
            ivmid.setText(IvmApplication.kehu.khTel);
        }
        listView.setAdapter(adapter);


         userimg=(CircleImageView)view.findViewById(R.id.user_img);
         userimg.setImageResource(R.drawable.default_headpic);


        //给列表项设置监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (data.get(i)){
                    case "我的优惠券":
                        Toast.makeText(getActivity(),"开发中...",Toast.LENGTH_SHORT).show();
                        break;
                    case "我的爱微币":
                        if(IvmApplication.islogin) {
                            Intent intent = new Intent(getActivity(), IVBdetailsActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(),"请登录后查看",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "我的账单":

                        if(IvmApplication.islogin) {

                            Intent intent2=new Intent(getActivity(),MyBillActivity.class);
                            startActivity(intent2);
                        }else{
                            Toast.makeText(getActivity(),"请登录后查看",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "我的订单":
                        if(IvmApplication.islogin) {
                            Intent intent3=new Intent(getActivity(),MyOrderActivity.class);
                            startActivity(intent3);
                        }else{
                            Toast.makeText(getActivity(),"请登录后查看",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "评价我们":
                        Intent intent5=new Intent(getActivity(),SendMsgToUsActivity.class);
                        startActivity(intent5);
                        break;
                    case "版本更新":
                        Intent intent6=new Intent(getActivity(),VersionUpdateActivity.class);
                        startActivity(intent6);
                        break;
                    case "个人设置":
                        if(IvmApplication.islogin) {
                            Intent intent7=new Intent(getActivity(),PersonInforActivity.class);
                            startActivity(intent7);
                        }else{
                            startActivity(new Intent(getActivity(),LoginActivity.class));
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dot.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getActivity(), Contacts.class));
            }
        });

        return  view;
    }

    public static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  999:
                    String path= Environment.getExternalStorageDirectory().getPath();
                    //showToast(path);
                    mBitmap = BitmapFactory.decodeFile(path + "/user_head.jpg");// 从sdcard中获取本地图片,通过BitmapFactory解码,转成bitmap
                    if (mBitmap != null) {
                        userimg.setImageBitmap(mBitmap);

                    } else {
                        /** 从服务器取,同时保存在本地 ,后续的工作 */
                    }

            }
        }
    };

    //受到消息，显示红点
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatMsgEvet event) {
        if(event.a){
            dot.setVisibility(View.VISIBLE);
        }else{
            dot.setVisibility(View.INVISIBLE);
        }
    }
}
