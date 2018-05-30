package com.ivmai.kehu.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.os.Message;

import com.ivm.entity.TMyVhuiyuanSH;
import com.ivm.newentity.CommonBean;
import com.ivm.newentity.OrderResponse;
import com.ivmai.base.BaseFragment;
import com.ivmai.base.IvmApplication;
import com.ivmai.bdpush.CommonEvent;
import com.ivmai.kehu.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import com.ivmai.kehu.R;

import com.ivmai.kehu.adapter.TdingdanAdapter;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.DisplayUtil;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.IP;
import com.ivmai.utils.LogUtil;
import com.ivmai.utils.NetWorkUtils;
import com.ivmai.vidget.RecycleViewDivider;
import com.ivmai.vidget.ViewDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


/**
 * 订单界面，我也忘了为什么当时取名为scanorder了，，，orz
 */
public class ScanOrderFragment extends BaseFragment {

    private List<OrderResponse.DingDan> tDingdanList = new ArrayList<OrderResponse.DingDan>();
    TdingdanAdapter adapter;//新建当前订单专用适配器(自定义类)
    ViewDialogFragment viewDialogFragment;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
//    int firstrefresh = 0;

    TMyVhuiyuanSH vhuiyuanSH = new TMyVhuiyuanSH();

    @BindView(R.id.order_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.order_swip)
    SwipeRefreshLayout swipeRefresh;

    @Override
    public int getViewId() {
        return R.layout.scan_fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void prepareWork() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TdingdanAdapter(context, tDingdanList);
        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshOrderList();
            }
        });
        recyclerView.addItemDecoration(new RecycleViewDivider(context, RecyclerView.HORIZONTAL, DisplayUtil.dip2px(context, 10), getResources().getColor(R.color.light_primary_color)));
        EventBus.getDefault().register(this);
    }

    @Override
    public void bindView() {

    }

    @Override
    public void initData() {
        refreshOrderList();

    }

    @Override
    public boolean handleMessage(Message msg) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
            if (msg.obj != null)
                ToastUtil.showToast(context, "更新数据");
            else
                ToastUtil.showToast(context, "请求失败");
        }
        switch (msg.what) {
            case 1:
                if (msg.obj != null) {
                    String json = (String) msg.obj;
                    OrderResponse response = (OrderResponse) GsonUtil.toObject(json, OrderResponse.class);
                    if (response != null && response.result != null && response.result.size() > 0) {
                        adapter.settDingdanList(response.result);
                    }
                    LogUtil.log(TAG, msg.obj.toString());
                }
                break;
            case 2:
                if(msg.obj != null){
                    CommonBean bean = (CommonBean) GsonUtil.toObject(msg.obj.toString(),CommonBean.class);
                    if(bean != null && bean.Code.equals("200")){
                        ToastUtil.showToast(context,"已取消，在 我的->历史订单中查看");
                    }else{
                        ToastUtil.showToast(context,"取消失败");
                    }
                    LogUtil.log(TAG, msg.obj.toString());
                    refreshOrderList();
                }else{
                    ToastUtil.showToast(context,"操作失败，全怪网络！");
                }
                break;
            case 3:
                if(msg.obj != null){
                    CommonBean bean = (CommonBean) GsonUtil.toObject(msg.obj.toString(),CommonBean.class);
                    if(bean != null && bean.Code.equals("200")){
                        ToastUtil.showToast(context,"已确认");
                    }else{
                        ToastUtil.showToast(context,"无法确认");
                    }
                    LogUtil.log(TAG, msg.obj.toString());
                    refreshOrderList();
                }else{
                    ToastUtil.showToast(context,"操作失败，全怪网络！");
                }
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    /**
     * 刷新订单
     */
    public void refreshOrderList() {
        if (!IvmApplication.islogin) {
            startActivity(new Intent(context, LoginActivity.class));
        } else {
            swipeRefresh.setRefreshing(true);
            NetWorkUtils.startPost(IP.SERVERURL + "d/KHUndone/" + IvmApplication.kehu.kHID + "/" + TokenBuilder.buildToken(), context, handler, 1);  //获取客户未完成订单
        }
    }


    // Called in Android UI's main thread
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(CommonEvent event) {
        if (event == null || event.getCode() < 0 || event.getData() == null) {
            return;
        }
        switch (event.getCode()) {
            case 100:
                OrderResponse.DingDan dingDan = (OrderResponse.DingDan) event.getData();
                if (viewDialogFragment == null)
                    viewDialogFragment = new ViewDialogFragment();

                viewDialogFragment.setDingDan(dingDan);
                viewDialogFragment.setContext(getContext());
                fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                viewDialogFragment.show(fragmentTransaction, "justtag");
                break;
            case 101:
                cancelOrder((OrderResponse.DingDan) event.getData());
                break;
            case 102:
                checkOrder((OrderResponse.DingDan) event.getData());
                break;
            case 103:
                rushOrder((OrderResponse.DingDan) event.getData());
                break;
            default:
                break;
        }
    }


    /**
     * @param dingDan 取消订单
     */
    public void cancelOrder(OrderResponse.DingDan dingDan) {
        showDialog(context, "撤销订单...");
        NetWorkUtils.startPost(IP.SERVERURL + "d/KHdelete/" + IvmApplication.kehu.kHID + "/" + dingDan.dDID + "/" + TokenBuilder.buildToken(), context, handler, 2);
    }


    /**
     * @param dingDan 确认订单
     */
    public void checkOrder(OrderResponse.DingDan dingDan) {
        showDialog(context, "确认订单...");
        NetWorkUtils.startPost(IP.SERVERURL+"d/KHConfirm/"+IvmApplication.kehu.kHID+"/"+dingDan.dDID+ "/" + TokenBuilder.buildToken(),context,handler,3);
    }

    /**
     * @param dingDan 催单功能
     */
    public void rushOrder(OrderResponse.DingDan dingDan) {
        ToastUtil.showToast(context, "催单成功");  //暂时不实现逻辑，欺骗一下用户16:20:07，后期补充催单功能
    }
}








