package com.jusenr.eg.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jaeger.library.StatusBarUtil;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.dagger2Test.Dagger2TestActivity;
import com.jusenr.eg.demo.gank.MMActivity;
import com.jusenr.eg.demo.jsouptest.HtmlActivity;
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

    @BindView(R.id.cl_main)
    ConstraintLayout mCLMain;
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
    @BindView(R.id.button12)
    Button mButton12;
    @BindView(R.id.button14)
    Button mButton14;
    @BindView(R.id.tv_status_alpha)
    TextView mTvStatusAlpha;
    @BindView(R.id.sb_change_alpha)
    SeekBar mSbChangeAlpha;

    private boolean isBgChanged;
    private boolean isTransparent;
    private int mAlpha;

    @Override
    protected boolean showActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        NativeLib nativeLib = new NativeLib();
        mTvText.setText(nativeLib.stringFromJNI());
//        getDataTest();

        if (!isTransparent) {
            mSbChangeAlpha.setVisibility(View.VISIBLE);
            setSeekBar();
        } else {
            mSbChangeAlpha.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setStatusBar() {
        if (isTransparent) {
            StatusBarUtil.setTransparent(this);
        } else {
            StatusBarUtil.setTranslucent(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        }
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

    @OnClick({R.id.tv_text, R.id.btn_gank, R.id.button2, R.id.button, R.id.button7, R.id.button12, R.id.button14})
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
                startActivity(new Intent(this, Dagger2TestActivity.class));
                break;
            case R.id.button12:
                startActivity(new Intent(this, HtmlActivity.class));
                break;
            case R.id.button14:
                isBgChanged = !isBgChanged;
                if (isBgChanged) {
                    mCLMain.setBackgroundDrawable(getResources().getDrawable(R.mipmap.photo_001));
                } else {
                    mCLMain.setBackgroundDrawable(getResources().getDrawable(R.mipmap.photo_002));
                }
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

    private void setSeekBar() {
        mSbChangeAlpha.setMax(255);
        mSbChangeAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAlpha = progress;
                StatusBarUtil.setTranslucent(mActivity, mAlpha);
                mTvStatusAlpha.setText(String.valueOf(mAlpha));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSbChangeAlpha.setProgress(StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }
}
