package com.ivmai.kehu.fragment;

import android.content.Intent;
import android.os.Message;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import com.ivm.newentity.BaseBean;
import com.ivm.newentity.ProductsResponseSuccess;
import com.ivmai.base.BaseFragment;
import com.ivmai.kehu.R;

import com.ivmai.kehu.activity.SearchActivity;
import com.ivmai.kehu.adapter.VSpviewAdapter;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.IP;
import com.ivmai.utils.LogUtil;
import com.ivmai.utils.NetWorkUtils;

import butterknife.BindView;

/**
 * Created by lk on 2017/7/27.
 * editedby mrshareone
 * 下单界面，此界面展示附近的商家
 */

public class SearchOrderFragment extends BaseFragment implements View.OnClickListener {

//    30.492192 114.384887,暂时设置为固定值（学校附近），后期将会集成百度地图sdk
    private Double lat = 30.492192;
    private Double lon = 114.384887;

    public static int index = 1;   //分页请求下标,起始值为0

    private List<ProductsResponseSuccess.ResultBean> resultBeans;
    private VSpviewAdapter vSpviewAdapter;

    @BindView(R.id.search_recycler)
    public RecyclerView recyclerView;
    @BindView(R.id.search_edit_input)
    public TextView searchText;
    @BindView(R.id.discovery_swip)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.not_fond)
    RelativeLayout notFondLayout;

    //初始化附近的所有商品列表，200表示成功，其它就是失败
    private void refreshProductsList() {
//        showDialog(context, "正在加载商品...");
        swipeRefreshLayout.setRefreshing(true);
        NetWorkUtils.startPost(IP.SERVERURL + "sp/AllByGps/" + index + "/" + lat + "/" + lon + "/" + IvmApplication.kehu.kHID + "/" + TokenBuilder.buildToken(), context, handler, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public int getViewId() {
        return R.layout.discovery;
    }

    @Override
    public boolean handleMessage(Message msg) {
        dissmissDialog();
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        switch (msg.what) {
            case 1:
                if (msg.obj != null) {
                    BaseBean bean = (BaseBean) GsonUtil.toObject(msg.obj.toString(), BaseBean.class);
                    if (bean != null) {
                        switch (bean.Code) {
                            case "200":
                                ProductsResponseSuccess responseSuccess = (ProductsResponseSuccess) GsonUtil.toObject(msg.obj.toString(),ProductsResponseSuccess.class);
                                if(responseSuccess!= null && responseSuccess.result.size()> 0){
                                    vSpviewAdapter.setmVSpviewList(responseSuccess.result);
                                    notFondLayout.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }else{
                                    notFondLayout.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }
                                break;
                            case "201":
                                notFondLayout.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                break;
                            default:
                                notFondLayout.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                break;
                        }
                    }
                    LogUtil.log(TAG, msg.obj.toString());
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void prepareWork() {
        searchText.setOnClickListener(this);
        vSpviewAdapter = new VSpviewAdapter(resultBeans, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(vSpviewAdapter);
        refreshProductsList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshProductsList();
            }
        });

        
    }

    @Override
    public void bindView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_edit_input:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            default:
                break;
        }
    }
}