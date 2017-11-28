package com.jusenr.eg.demo.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        setTitle("RoundImage");
    }
}
