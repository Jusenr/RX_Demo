package com.jusenr.eg.demo.dagger2Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.TotalApplication;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.dagger2Test.component.DaggerSecondComponent;
import com.jusenr.eg.demo.dagger2Test.model.ClothHandler;
import com.jusenr.eg.demo.dagger2Test.model.ClothModel;
import com.jusenr.eg.demo.dagger2Test.module.SecondModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SecondActivity extends BaseActivity {

    @BindView(R.id.button10)
    Button mButton10;
    @BindView(R.id.button11)
    Button mButton11;
    @BindView(R.id.textView9)
    TextView mTextView9;
    @BindView(R.id.textView10)
    TextView mTextView10;
    @BindView(R.id.textView11)
    TextView mTextView11;
    @BindView(R.id.textView12)
    TextView mTextView12;
    @BindView(R.id.textView13)
    TextView mTextView13;
    @BindView(R.id.textView14)
    TextView mTextView14;

    @Inject
    ClothModel greenCloth;
    @Inject
    ClothHandler clothHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        setTitle("Dagger2Test2");

        DaggerSecondComponent.builder()
                .baseComponent(((TotalApplication) getApplication()).getBaseComponent())
                .secondModule(new SecondModule())
                .build()
                .inject(this);

        mTextView9.setText("绿布料加工后变成了" + clothHandler.handle(greenCloth) + "\nclothHandler地址:" + clothHandler);
        mTextView10.setText("");
        mTextView11.setText("");
        mTextView12.setText("");
        mTextView13.setText("");
        mTextView14.setText("");
    }

    @OnClick({R.id.button10, R.id.button11})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button10:
                break;
            case R.id.button11:
                break;
        }
    }
}
