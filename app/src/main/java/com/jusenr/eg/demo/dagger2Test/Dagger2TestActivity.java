package com.jusenr.eg.demo.dagger2Test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.TotalApplication;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.dagger2Test.component.DaggerMainComponent;
import com.jusenr.eg.demo.dagger2Test.model.ClothHandler;
import com.jusenr.eg.demo.dagger2Test.model.ClothModel;
import com.jusenr.eg.demo.dagger2Test.model.ClothesModel;
import com.jusenr.eg.demo.dagger2Test.model.ShoeModel;
import com.jusenr.eg.demo.dagger2Test.module.MainModule;

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

    }

    @OnClick({R.id.textView2, R.id.button8, R.id.button9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textView2:
                break;
            case R.id.button8:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.button9:
                break;
        }
    }
}
