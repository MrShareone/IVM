package com.ivmai.kehu.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.List;

public class LocalService {
        private LocationManager lm;//地址管理器，从他这里可以获取location，并可以对其进行监听并且实时修改数据
        private String provider="";//位置服务提供者，比如gps或者是network
        private Context context;//这个是必备的，因为功能类里面是不存在上下文的，而好多功能的实现需要上下文作为参数，所以这个类里面也要添加一个上下文
        LocationListener locationListener; //这个是地址的监听器
        Location location = null;
        private int bestProvider =1;//i有1和2两种选择，1代表网络优先，2代表gps优先

        //设置提供者 i有1和2两种选择，1代表网络优先，2代表gps优先
        public void setProvider(int i){
            this.bestProvider = i==1 ? 1:2;
        }
        //构造函数
        public LocalService(Context context){
            this.context = context;
            lm = (LocationManager)context.getSystemService(this.context.LOCATION_SERVICE); // 先初始化地址管理者
            List<String> list = lm.getProviders(true);   //查看可用的provider
                    if (list.contains(LocationManager.GPS_PROVIDER)) {
                        //是否为GPS位置控制器
                        provider = LocationManager.GPS_PROVIDER;
                    }
                    else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
                        //是否为网络位置控制器
                        provider = LocationManager.NETWORK_PROVIDER;
                    }
//                    else {

//                        System.out.println("打开gps显示周边商品");

                        //********可以考虑在此处引导用户到设置页面，代码如下********//
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        context.startActivity(intent);
//                        Looper.prepare();
//                        Toast.makeText(context, "打开gps显示周边商品", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
        }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    //返回当前的location
        public Location getCurrentLocation(){
            System.out.print(provider);
            if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                location = lm.getLastKnownLocation(provider);
                return location;
            }
            return location;
        }

        //设置监听,对位置进行监听，第一个参数是重载的监听器，第二个是时间，第三个是距离
        public void setLocationListener(LocationListener locationListener,int times,int length){
            this.locationListener = locationListener;//把参数赋值到监听器中
            if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(provider,2000,2,this.locationListener);//监听，两秒一次
            }
        }

        @Override
        //这个对象在被销毁的时要取消这个监听
        protected void finalize() throws Throwable {
            super.finalize();
            if (lm != null&&locationListener!=null) {
                lm.removeUpdates(locationListener);
            }
        }
    };