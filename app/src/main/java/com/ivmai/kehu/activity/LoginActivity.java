package com.ivmai.kehu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ivm.entity.TKehu;
import com.ivm.newentity.ClientInfo;
import com.ivm.newentity.KehuNoteBean;
import com.ivm.newentity.LoginResponse;
import com.ivm.newentity.LoginResponseSuccess;
import com.ivm.newentity.RegisterResponse;
import com.ivm.newentity.RegisterResponseSuccess;
import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;

import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.kehu.tools.TokenBuilder;
import com.ivmai.utils.Config;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.IP;
import com.ivmai.utils.LogUtil;
import com.ivmai.utils.NetWorkUtils;
import com.ivmai.utils.StatusBarUtil;

import butterknife.BindView;

public class LoginActivity extends BaseActvity implements View.OnClickListener {
    static final int LOGIN_MODE = 0;
    static final int RIGESTER_MODE = 1;

    String userTel;
    String password;
    TKehu tKehu;
    ClientInfo clientInfo;
    Config config;
    String firstpas = "";
    String secondepas = "";
    int pasflag = 0;

    int MODE = LOGIN_MODE;

    //界面组件
    @BindView(R.id.login_button)
    TextView loginbutton;
    @BindView(R.id.userpassword_edittext)
    EditText password_editText;
    @BindView(R.id.username_edittext)
    EditText name_editText;
    @BindView(R.id.login_bg_img)
    ImageView loginBg;
    @BindView(R.id.welcome)
    RelativeLayout welcom;
    @BindView(R.id.welocme_login)
    TextView welcomLogin;
    @BindView(R.id.welocme_register)
    TextView welcomRegister;
    @BindView(R.id.about_us_us)
    TextView aboutUs;
    @BindView(R.id.look_first)
    TextView lookFirst;
    @BindView(R.id.forgetpassword)
    TextView forgetPassword;
    @BindView(R.id.secrect)
    View secrect;
    @BindView(R.id.login_holder)
    RelativeLayout loginHolder;
    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindView(R.id.go_rigister)
    TextView goRigister;
    @BindView(R.id.checkbox)
    CheckBox checkBox;
    @BindView(R.id.welcom_bottom_pic)
    ImageView welcomBottomPic;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 准备工作，判断是不是第一次进入应用，获取缓存的信息
     */
    public void prepareWork() {
        StatusBarUtil.StatusBarLightMode(this);

        config = IvmApplication.config;

        String string = config.getuserinfoJsonString("");
        if (TextUtils.isEmpty(string)) {
            tKehu = (TKehu) GsonUtil.toObject(string, TKehu.class);
            if (tKehu != null) {
                name_editText.setText(tKehu.khTel.toString());
            }
        }

        String string2 = config.getClientMsgJsonString("");
        if (TextUtils.isEmpty(string2)) {
            clientInfo = (ClientInfo) GsonUtil.toObject(string2, ClientInfo.class);
            if (clientInfo == null) {
                LogUtil.log(TAG, "新用户");
                welcom.setVisibility(View.VISIBLE);
                clientInfo = new ClientInfo();
                clientInfo.setFirst(false);
                config.setClientMsgJsonString(GsonUtil.toJson(clientInfo));
            } else {
                welcom.setVisibility(View.GONE);
            }
        }

        loginbutton.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        welcomLogin.setOnClickListener(this);
        welcomRegister.setOnClickListener(this);
        lookFirst.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        secrect.setOnClickListener(this);
        goRigister.setOnClickListener(this);

        StatusBarUtil.StatusBarLightMode(this);
        StatusBarUtil.setStatusBarColor(this, R.color.light_primary_color);
    }

    @Override
    public void bindView() {

    }

    @Override
    public int getViewId() {
        return R.layout.login;
    }

    @Override
    public boolean handleMessage(Message msg) {
        dissmissDialog();
        switch (msg.what) {
            case 1://登录
                if (msg.obj != null) {
                    LoginResponse loginResponse = (LoginResponse) GsonUtil.toObject((String) msg.obj, LoginResponse.class);
                    if (loginResponse != null) {
                        switch (loginResponse.Code) {
                            case "200":
                                //登录成功
                                IvmApplication.islogin = true;
                                LoginResponseSuccess loginResponseSuccess = (LoginResponseSuccess) GsonUtil.toObject((String) msg.obj, LoginResponseSuccess.class);
                                if (loginResponseSuccess != null && loginResponseSuccess.result != null) {
                                    IvmApplication.kehu = loginResponseSuccess.result;
                                    if (loginResponseSuccess.result.khNote != null) {
                                        KehuNoteBean kehuNoteBean = (KehuNoteBean) GsonUtil.toObject(loginResponseSuccess.result.khNote, KehuNoteBean.class);
                                        if (kehuNoteBean != null) {
                                            IvmApplication.kehuNoteBean = kehuNoteBean;
                                        }
                                    }
                                    startActivity(new Intent(context, MainActivity.class));
                                    finish();
                                } else {
                                    LogUtil.log(TAG, "请求成功，返回值异常");
                                }
                                break;
                            case "201":
                                IvmApplication.islogin = false;
                                IvmApplication.kehu = null;
                                ToastUtil.showToast(context, "用户名错误");
                                break;
                            case "202":
                                IvmApplication.islogin = false;
                                IvmApplication.kehu = null;
                                ToastUtil.showToast(context, "密码不正确");
                                break;
                            case "6000":
                                IvmApplication.islogin = false;
                                IvmApplication.kehu = null;
                                ToastUtil.showToast(context, "服务器忙，请稍候重试");     //实际为token失效，，你告诉用户用户也不懂，换个说法....
                                break;
                            default:

                        }
                    } else {
                        ToastUtil.showToast(context, "服务器忙，请稍候重试");
                    }

                } else {
                    ToastUtil.showToast(context, "服务器忙，请稍候重试!");   //注意：实际为响应超时，访问失败！！！！
                }
                startActivity(new Intent(context, MainActivity.class));
                break;
            case 2://注册
                if (msg.obj != null) {
                    RegisterResponse registerResponse = (RegisterResponse) GsonUtil.toObject((String) msg.obj, RegisterResponse.class);
                    if (registerResponse != null) {
                        switch (registerResponse.Code) {
                            case "200":
                                RegisterResponseSuccess success = (RegisterResponseSuccess) GsonUtil.toObject((String) msg.obj, RegisterResponseSuccess.class);
                                ToastUtil.showToast(context, "注册成功");
                                IvmApplication.kehu = success.result;
                                KehuNoteBean kehuNoteBean = (KehuNoteBean) GsonUtil.toObject(success.result.khNote, KehuNoteBean.class);
                                if (kehuNoteBean != null) {
                                    IvmApplication.kehuNoteBean = kehuNoteBean;
                                }
                                IvmApplication.islogin = true;
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                break;
                            case "201":
                                ToastUtil.showToast(context, "账号已被注册");
                                break;
                            default:
                                break;
                        }
                    } else {
                        ToastUtil.showToast(context, "服务器忙，请稍候重试!");
                    }

                } else {
                    ToastUtil.showToast(context, "服务器忙，请稍候重试!");   //注意：实际为响应超时，访问失败！！！！
                    LogUtil.log(TAG, "注册返回值为空");
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
            case R.id.login_button:
                /*登录*/
                userTel = name_editText.getText().toString();//获得用户名
                password = password_editText.getText().toString();//用户密码

                if (IvmApplication.isNetValuable){
                    if (TextUtils.isEmpty(userTel) || (TextUtils.isEmpty(password))) {
                        Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tKehu = new TKehu();
                    tKehu.khTel = userTel;
                    tKehu.khMima = password;

                    hashMap.clear();
                    hashMap.put("tkehu", tKehu);


                    if (MODE == LOGIN_MODE) {
                        NetWorkUtils.startPost(IP.SERVERURL + "k/KHLogin/" + TokenBuilder.buildToken(), hashMap, context, handler, 1);
                        showDialog(LoginActivity.this, "正在登录...");
                    } else {
                        if (checkBox.isChecked()) {
                            NetWorkUtils.startPost(IP.SERVERURL + "k/KHRegister/" + TokenBuilder.buildToken(), hashMap, context, handler, 2);
                            showDialog(LoginActivity.this, "正在注册...");
                        } else {
                            ToastUtil.showToast(context, "同意用户协议才能注册");
                        }
                    }

                } else {
                    ToastUtil.showToast(LoginActivity.this, "网络不可用");
                }
                startActivity(new Intent(context, MainActivity.class));
                break;
            case R.id.forgetpassword:
                /*忘记密码*/
                if (MODE == LOGIN_MODE) {
                    ToastUtil.showToast(context, "慢慢找....");
                } else {
                    Intent intent = new Intent(LoginActivity.this, CommonWebview.class);
                    intent.putExtra("from", "xieyi");
                    startActivity(intent);
                }
                break;
            case R.id.welocme_login:
                /*登录*/
                MODE = LOGIN_MODE;
                loginHolder.setVisibility(View.VISIBLE);
                welcom.setVisibility(View.GONE);
                loginTitle.setText("用户登录");
                loginbutton.setText("登录");
                goRigister.setText("注册账号");
                forgetPassword.setText("找回密码");
                checkBox.setVisibility(View.GONE);
                break;
            case R.id.welocme_register:
                /*注册*/
                MODE = RIGESTER_MODE;
//                startActivity(new Intent(context, RegisterActivity.class));
                welcom.setVisibility(View.GONE);
                loginHolder.setVisibility(View.VISIBLE);
                loginTitle.setText("用户注册");
                loginbutton.setText("注册");
                goRigister.setText("已有账号");
                forgetPassword.setText(Html.fromHtml("<u>同意爱微迈用户协议</u>"));
                checkBox.setVisibility(View.VISIBLE);
                break;

            case R.id.about_us_us:
                /*关于我们*/
                Intent intent = new Intent(LoginActivity.this, CommonWebview.class);
                intent.putExtra("from", "aboutus");
                startActivity(intent);
                break;
            case R.id.look_first:
                /*先去逛逛*/
                break;
            case R.id.secrect:
                welcom.setVisibility(View.VISIBLE);
                loginHolder.setVisibility(View.GONE);
                break;
            case R.id.go_rigister:
                if (MODE == LOGIN_MODE) {
                     /*注册*/
                    MODE = RIGESTER_MODE;
//                startActivity(new Intent(context, RegisterActivity.class));
                    welcom.setVisibility(View.GONE);
                    loginHolder.setVisibility(View.VISIBLE);
                    loginTitle.setText("用户注册");
                    loginbutton.setText("注册");
                    goRigister.setText("已有账号");
                    forgetPassword.setText(Html.fromHtml("<u>同意爱微迈用户协议</u>"));
                    checkBox.setVisibility(View.VISIBLE);
                } else {
                     /*登录*/
                    MODE = LOGIN_MODE;
                    loginHolder.setVisibility(View.VISIBLE);
                    welcom.setVisibility(View.GONE);
                    loginTitle.setText("用户登录");
                    loginbutton.setText("登录");
                    goRigister.setText("注册账号");
                    forgetPassword.setText("找回密码");
                    checkBox.setVisibility(View.GONE);
                }

                break;
            default:
                break;
        }
    }
}
