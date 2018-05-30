package com.ivmai.kehu.fragment;

import android.content.Intent;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.ivm.newentity.VipListResponse;
import com.ivmai.base.BaseFragment;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;

import com.ivmai.kehu.activity.AddHYActivity;
import com.ivmai.kehu.activity.LoginActivity;
import com.ivmai.kehu.adapter.HuyuanAdapter;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.DisplayUtil;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.IP;
import com.ivmai.utils.LogUtil;
import com.ivmai.utils.NetWorkUtils;
import com.ivmai.vidget.RecycleViewDivider;

import butterknife.BindView;

/**
 * Created by lk on 2017/7/26.
 * 会员card页面
 */

public class MyVipCardFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.hy_recylerview)
    RecyclerView recyclerView;
    HuyuanAdapter adapter;
    private List<VipListResponse.VipMsg> huiyuanList = new ArrayList<>();
    @BindView(R.id.hy_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.hy_add)
    ImageView hy_add;

    @Override
    public int getViewId() {
        return R.layout.vip_page;
    }

    @Override
    public void prepareWork() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(context, RecyclerView.HORIZONTAL, DisplayUtil.dip2px(context, 10), getResources().getColor(R.color.light_primary_color)));
        adapter = new HuyuanAdapter(getActivity(), huiyuanList);
        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshvipList();
            }
        });
    }

    /**
     * 刷新会员列表
     */
    public void refreshvipList() {
        if (!IvmApplication.islogin) {
            startActivity(new Intent(context, LoginActivity.class));
        } else {
            swipeRefresh.setRefreshing(true);
            NetWorkUtils.startPost(IP.SERVERURL + "vk/KHhy/" + IvmApplication.kehu.kHID + "/" + TokenBuilder.buildToken(), context, handler, 1);  //获取客户未完成订单
        }
    }

    @Override
    public void bindView() {
        hy_add.setOnClickListener(this);
    }

    @Override
    public void initData() {
        refreshvipList();
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
                    VipListResponse response = (VipListResponse) GsonUtil.toObject(json, VipListResponse.class);
                    if (response != null && response.result != null && response.result.size() > 0) {
                        adapter.setVipMsgList(response.result);
                    }
                    LogUtil.log(TAG, msg.obj.toString());
                }
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hy_add:
                Intent intent = new Intent(getActivity(), AddHYActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}


