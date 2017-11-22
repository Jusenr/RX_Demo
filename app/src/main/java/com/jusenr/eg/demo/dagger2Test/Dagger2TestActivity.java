package com.jusenr.eg.demo.dagger2Test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jaeger.library.StatusBarUtil;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.TotalApplication;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.dagger2Test.component.DaggerMainComponent;
import com.jusenr.eg.demo.dagger2Test.model.ClothHandler;
import com.jusenr.eg.demo.dagger2Test.model.ClothModel;
import com.jusenr.eg.demo.dagger2Test.model.ClothesModel;
import com.jusenr.eg.demo.dagger2Test.model.ShoeModel;
import com.jusenr.eg.demo.dagger2Test.module.MainModule;
import com.jusenr.eg.demo.model.MaterialBenefitsModel;
import com.jusenr.eg.demo.retrofit.RxRetrofitComposer;
import com.jusenr.eg.demo.retrofit.api.GankApi;
import com.jusenr.eg.demo.retrofit.subscriber.Subscriber0;
import com.jusenr.toolslibrary.log.logger.Logger;
import com.jusenr.toolslibrary.utils.ToastUtils;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class Dagger2TestActivity extends BaseActivity {


    @BindView(R.id.button8)
    Button mButton8;
    @BindView(R.id.button9)
    Button mButton9;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.textView4)
    TextView mTextView4;
    @BindView(R.id.textView5)
    TextView mTextView5;
    @BindView(R.id.textView6)
    TextView mTextView6;
    @BindView(R.id.textView7)
    TextView mTextView7;
    @BindView(R.id.textView8)
    TextView mTextView8;
    @BindView(R.id.button13)
    Button mButton13;
    @BindView(R.id.tv_status_alpha)
    TextView mTvStatusAlpha;
    @BindView(R.id.sb_change_alpha)
    SeekBar mSbChangeAlpha;

    @Inject
    ClothModel mClothModel;
    @Inject
    @Named("blue")
    ClothModel mBlueCloth;
    @Inject
    ShoeModel mShoeModel;
    @Inject
    ClothesModel mClothesModel;
    @Inject
    ClothHandler clothHandler;
    @Inject
    GankApi mGankApi;

    private int mColor;
    private int mAlpha;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dagger2_test;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        setTitle("Dagger2Test");

        DaggerMainComponent.builder()
                .baseComponent(((TotalApplication) getApplication()).getBaseComponent())
                .mainModule(new MainModule())
                .build()
                .inject(this);

        mTextView2.setText("");
        mTextView3.setText("我现在有" + mClothModel);
        mTextView4.setText("给你网购的" + mShoeModel);
        mTextView5.setText("这是" + mClothesModel);
        mTextView6.setText("什么是" + mBlueCloth);
        mTextView7.setText("redCloth=clothes中的cloth吗?:" + (mClothModel == mClothesModel.getModel()));
        mTextView8.setText("红布料加工后变成了" + clothHandler.handle(mClothModel) + "\nclothHandler地址:" + clothHandler);

        mColor = getCurrentThemeColor();
        mAlpha = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA;
        mSbChangeAlpha.setMax(255);
        mSbChangeAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAlpha = progress;
                StatusBarUtil.setColor(mActivity, mColor, mAlpha);
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

    @OnClick({R.id.textView2, R.id.button8, R.id.button9, R.id.button13})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView2:
                break;
            case R.id.button8:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.button9:
                initData();
                break;
            case R.id.button13:
                // 改变颜色
                Random random = new Random();
                mColor = 0xff000000 | random.nextInt(0xffffff);
                if (getToolbar() != null) getToolbar().setBackgroundColor(mColor);
                StatusBarUtil.setColor(this, mColor, mAlpha);
                break;
        }
    }

    public void initData() {
        mGankApi.materialBenefits(GankApi.TYPE_MATERIALBENEFITS, 10, 1)
                .compose(RxRetrofitComposer.<JSONObject>applySchedulers())
                .subscribe(new Subscriber0<JSONObject>() {
                    @Override
                    public void onNext(String msg, JSONObject object) {
                        MaterialBenefitsModel model = JSON.toJavaObject(object, MaterialBenefitsModel.class);
                        if (model != null) {
                            List<MaterialBenefitsModel.ResultsBean> results = model.getResults();
                            if (results != null) {
                                Logger.d(results.toString());
                                ToastUtils.show(mActivity, Integer.toString(results.size()));
                            }
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.show(mActivity, msg);
                    }
                });
    }
}
