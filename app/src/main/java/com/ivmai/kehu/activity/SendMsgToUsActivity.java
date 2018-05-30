package com.ivmai.kehu.activity;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ivm.entity.TMsg;
import com.ivm.newentity.BaseBean;
import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.IP;
import com.ivmai.utils.LogUtil;
import com.ivmai.utils.NetWorkUtils;

public class SendMsgToUsActivity extends BaseActvity implements View.OnClickListener {
    String msg;
    TextView textView;
    EditText msgcontent;
    TextView send;

    @Override
    public int getViewId() {
        return R.layout.send_msg_to_us;
    }

    @Override
    public void prepareWork() {
        textView.setText("评价我们");
    }

    @Override
    public void bindView() {
        msgcontent = (EditText) findViewById(R.id.msg_gs_content);
        send = (TextView) findViewById(R.id.send_to_gs);
        send.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.title_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_to_gs:
                send();
                break;
            default:
                break;
        }
    }


    public void send() {
        String msg = msgcontent.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            ToastUtil.showToast(context, "您还没有填意见哦");
            return;
        }

        TMsg tMsg = new TMsg();
        tMsg.setMsgContent(msg);
        tMsg.setReceiverID(Long.valueOf(3));
        tMsg.setSenderID(IvmApplication.kehu.kHID);
        tMsg.setMsgLeixing(16);
        tMsg.setReceiverFlag(3);
        tMsg.setSenderFlag(1);

        hashMap.clear();
        hashMap.put("msg", tMsg);

        NetWorkUtils.startPost(IP.SERVERURL + "m/MsgAdd/" + "/" + TokenBuilder.buildToken(), hashMap, context, handler, 1);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                if (msg.obj != null) {
                    BaseBean bean = (BaseBean) GsonUtil.toObject(msg.obj.toString(), BaseBean.class);
                    switch (bean.Code) {
                        case "200":
                            ToastUtil.showToast(context, "评价成功，您的提议是我们宝贵的财富！");
                            msgcontent.setText("");
                            break;
                        default:
                            LogUtil.log(TAG,"评价失败！");
                            ToastUtil.showToast(context, "评价成功，您的提议是我们宝贵的财富！");
                            break;
                    }
//                    LogUtil.log(TAG,msg.obj.toString());
                }
                break;
            default:
                break;
        }
        return true;
    }
}
