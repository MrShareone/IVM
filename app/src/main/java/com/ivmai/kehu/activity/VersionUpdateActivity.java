package com.ivmai.kehu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.ivm.newentity.BaseBean;
import com.ivm.newentity.VersionMsg;
import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.service.UpdateService;
import com.ivmai.vidget.TitleLayout;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.IP;
import com.ivmai.utils.NetWorkUtils;

import butterknife.BindView;

public class VersionUpdateActivity extends BaseActvity implements View.OnClickListener {

    @BindView(R.id.titlelayout)
    TitleLayout titleLayout;
    @BindView(R.id.latestversion)
    TextView lateestVersion;

    @Override
    public int getViewId() {
        return R.layout.version_update;
    }

    @Override
    public void prepareWork() {
        titleLayout.setTitle("更新");
        lateestVersion.setOnClickListener(this);
        lateestVersion.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
    }

    @Override
    public void bindView() {

    }

    /**
     * 更新版本
     */
    public void update() {
        ToastUtil.showToast(context, "开始更新");
        startService(new Intent(VersionUpdateActivity.this, UpdateService.class));//启动后台服务，进行下载
        onBackPressed();
    }

    /**
     * @return 检查版本
     */
    public boolean checkVersion() {

        NetWorkUtils.startPost(IP.SERVERURL + "Tool/getKHVersion/" + TokenBuilder.buildToken(), context, handler, 1);

        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                if (msg.obj != null) {
                    BaseBean bean = (BaseBean) GsonUtil.toObject(msg.obj.toString(), BaseBean.class);
                    if (bean != null) {
                        switch (bean.Code){
                            case "200":
                                VersionMsg versionMsg = new VersionMsg();
                                versionMsg.needUpdate = true;
                                IvmApplication.config.setVersionInfo(versionMsg);
                                break;
                            default:
                                break;
                        }
                    }
//                    LogUtil.log(TAG,msg.obj.toString());
                }
                break;
            default:
                break;
        }
        return true;
    }

    //获取当前版本号
    public static int getPackageVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        } else {
            return 1;
        }
    }

    //获取当前版本名称
    public static String getPackageVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return packageInfo.versionName;
        } else {
            return "";
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.latestversion:
                update();
//                checkVersion();
                break;
            default:
                break;
        }
    }
}
