package com.ivmai.base;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivmai.kehu.R;
import com.ivmai.vidget.MyProgressDialog;

import java.util.HashMap;

import butterknife.ButterKnife;
import okhttp3.MediaType;

/**
 * Created by MR-SHAREONE on 2018/1/30.
 */

public abstract class BaseFragment extends Fragment implements Handler.Callback{

    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected String TAG;
    protected Handler handler;
    protected Context context;
    public Typeface typeFace;
    public HashMap<String, Object> hashMap = new HashMap<>();

    public MyProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view= View.inflate(context,getViewId(),null);
        ButterKnife.bind(this,view);
        handler = new Handler(this);
        TAG = this.getClass().getName();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        prepareWork();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    /**
     * 隐藏进度条
     */
    protected void dissmissDialog() {
        if (progressDialog == null)
            return;
        if (progressDialog.isShowing()) {
            try {

                progressDialog.dismiss();

            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                progressDialog = null;
            }
        }
    }


    /**
     * @param context 显示进度条
     * @param s       显示的消息
     */
    protected void showDialog(Context context, String s) {
        if (progressDialog == null) {
            progressDialog = new MyProgressDialog(context, R.style.ThemeOverlay_AppCompat_Dialog);
            if (!TextUtils.isEmpty(s)) {
                progressDialog.setMessage(s);
            } else {
                progressDialog.setMessage("正在加载…");
            }
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * @param context 显示进度条
     */
    protected void showDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new MyProgressDialog(context, R.style.ThemeOverlay_AppCompat_Dialog);

            progressDialog.setMessage("正在加载…");

            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public abstract int getViewId();

    public abstract void prepareWork();

    public abstract void bindView();

    public abstract void initData();

    @Override
    public boolean handleMessage(Message message) {

        dissmissDialog();
        return false;
    }
}
