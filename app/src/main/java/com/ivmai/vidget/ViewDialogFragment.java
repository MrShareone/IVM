package com.ivmai.vidget;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ivm.newentity.OrderResponse;
import com.ivmai.bdpush.CommonEvent;
import com.ivmai.kehu.R;

import org.greenrobot.eventbus.EventBus;


public class ViewDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "ViewDialogFragment";
    private Callback callback;
    private OrderResponse.DingDan dingDan;
    Context context;

    TextView cancelOrder, checkOrderFinish, rush;  //取消订单，确认订单，催单

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cal_order:
                EventBus.getDefault().post(new CommonEvent(101,dingDan));
                this.dismiss();
                onDestroy();
                break;
            case R.id.check_order_finish:
                EventBus.getDefault().post(new CommonEvent(102,dingDan));
                this.dismiss();
                break;
            case R.id.russ:
               EventBus.getDefault().post(new CommonEvent(103,dingDan));
               this.dismiss();
                break;
            default:
                break;
        }
    }

    public interface Callback {
        public void fun();
    }

    public void setDingDan(OrderResponse.DingDan dingDan) {
        this.dingDan = dingDan;
    }

//    public void show(FragmentManager fragmentManager) {
//        this.show(fragmentManager,"justtag");
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pickupcode, null);
        Dialog dialog = new Dialog(getActivity(), R.style.ThemeOverlay_AppCompat_Dialog);
        cancelOrder = (TextView) view.findViewById(R.id.cal_order);
        checkOrderFinish = (TextView) view.findViewById(R.id.check_order_finish);
        rush = (TextView) view.findViewById(R.id.russ);
        checkOrderFinish.setOnClickListener(this);
        rush.setOnClickListener(this);
        cancelOrder.setOnClickListener(this);

        // 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}