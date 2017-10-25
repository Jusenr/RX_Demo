package com.jusenr.eg.demo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.TotalApplication;
import com.jusenr.eg.demo.base.loading.LoadView;
import com.jusenr.eg.demo.base.loading.LoadingView;
import com.jusenr.toolslibrary.utils.EventBusUtils;
import com.jusenr.toolslibrary.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/10/23
 * Time       : 15:27
 * Project    ：RX_Demo.
 */
public abstract class BaseActivity extends AppCompatActivity implements LoadView {
    private long exitTime = 0;
    private Unbinder unbinder;

    protected TotalApplication mApplication;
    protected Activity mActivity;
    protected Bundle mBundle;
    protected CompositeSubscription subscriptions;

    protected boolean isResume;
    protected LoadingView mLoadingView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mActivity = this;
        mApplication = (TotalApplication) getApplication();
        mBundle = getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        unbinder = ButterKnife.bind(this);
        mLoadingView = new LoadingView(this, getLoadingMessage());
        subscriptions = new CompositeSubscription();

        if (useEventBus()) {
            EventBusUtils.register(this);
        }
        onViewCreated(savedInstanceState);
    }

    public String getLoadingMessage() {
        return null;
    }

    protected abstract int getLayoutId();

    protected abstract void onViewCreated(@Nullable Bundle savedInstanceState);

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(getLocalClassName());
        isResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(getLocalClassName());
        isResume = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLoadingView != null)
            mLoadingView.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (useEventBus())
            EventBusUtils.unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingView.dismiss();
    }

    protected boolean useEventBus() {
        return false;
    }

    /**
     * 双击退出App
     *
     * @param exit 退出时间(毫秒数)
     */
    protected boolean exit(long exit) {
        if (System.currentTimeMillis() - exitTime > exit) {
            ToastUtils.show(this, getString(R.string.exit_again));
            exitTime = System.currentTimeMillis();
        } else {
            mApplication.getActivityManager().finishAll();
        }
        return true;
    }
}
