package com.jusenr.eg.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.gank.MMActivity;
import com.jusenr.eg.demo.model.UserModel;
import com.jusenr.eg.demo.retrofit.RetrofitManager;
import com.jusenr.eg.demo.retrofit.RxRetrofitComposer;
import com.jusenr.eg.demo.retrofit.model.Model1;
import com.jusenr.eg.demo.retrofit.subscriber.ApiSubscriber1;
import com.jusenr.eg.demo.retrofit.subscriber.ApiSubscriber2;
import com.jusenr.eg.demo.rx2test.Rx2Test2Activity;
import com.jusenr.eg.demo.rx2test.Rx2TestActivity;
import com.jusenr.toolslibrary.utils.StringUtils;
import com.jusenr.toolslibrary.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.btn_gank)
    Button mBtnGank;
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.button7)
    Button mButton7;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        NativeLib nativeLib = new NativeLib();
        mTvText.setText(nativeLib.stringFromJNI());
//        getDataTest();
    }

    private void getDataTest() {
        subscriptions.add(RetrofitManager.getGankApi()
                .login(new HashMap<String, String>())
                .compose(RxRetrofitComposer.<JSONObject>applySchedulers())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(mActivity, e.getMessage());
                    }

                    @Override
                    public void onNext(JSONObject object) {

                    }
                }));

        subscriptions.add(RetrofitManager.getGankApi()
                .logout(new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber1<UserModel>() {
                    @Override
                    public void onNext(String msg, Model1<UserModel> userModelModel1) {

                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.show(mActivity, msg);
                    }
                }));

        subscriptions.add(RetrofitManager.getPassportApi()
                .getNickname(new HashMap<String, String>())
                .compose(RxRetrofitComposer.<UserModel>applySchedulers())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoading();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        dismissLoading();
                    }
                })
                .subscribe(new ApiSubscriber2<UserModel>() {
                    @Override
                    public void onNext(String msg, UserModel userModel) {

                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.show(mActivity, msg);
                    }
                }));

    }

    @OnClick({R.id.tv_text, R.id.btn_gank, R.id.button2, R.id.button, R.id.button7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_text:
                startActivity(new Intent(this, Rx2Test2Activity.class));
                break;
            case R.id.btn_gank:
                startActivity(new Intent(this, MMActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, Rx2TestActivity.class));
                break;
            case R.id.button:
//                boolean focusable = mEditText.isFocusable();
//                Log.i("MainActivity", "onViewClicked: " + focusable);
//                mEditText.setFocusable(!focusable);
//                mEditText.requestFocus();
                String password = mEditText.getText().toString().trim();
                mTextView.setText(password);
                String regex = "^[\\da-zA-Z~!@#$%^&*_+?><.]{6,18}$";
                boolean b = StringUtils.checkRegex(password, regex);
                ToastUtils.show(this, Boolean.toString(b));
                break;
            case R.id.button7:
                setTheme(R.style.BlueTheme);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            return exit(2000);
        }
        return super.onKeyDown(keyCode, event);
    }
}
