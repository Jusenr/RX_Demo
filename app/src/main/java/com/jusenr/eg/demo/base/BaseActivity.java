package com.jusenr.eg.demo.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.TotalApplication;
import com.jusenr.eg.demo.base.loading.LoadView;
import com.jusenr.eg.demo.base.loading.LoadingView;
import com.jusenr.eg.demo.theme.Theme;
import com.jusenr.eg.demo.utils.PreUtils;
import com.jusenr.toolslibrary.utils.EventBusUtils;
import com.jusenr.toolslibrary.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
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
    protected CompositeDisposable disposable;

    protected boolean isResume;
    protected boolean isShowActionBar = showActionBar();
    protected LoadingView mLoadingView;
    private ActivityManager mActivityManager;
    private Toolbar mToolbar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        onPreCreate();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }

        if (isShowActionBar) {
            View view_main = View.inflate(this, getLayoutId(), null);
            View view_root = View.inflate(this, R.layout.layout_root_view, null);
            LinearLayout rootView = (LinearLayout) view_root.findViewById(R.id.ll_root_view);
            mToolbar = (Toolbar) view_root.findViewById(R.id.toolbar);
            rootView.addView(view_main, 1);
            setContentView(view_root);
            // 设置toolbar
            setSupportActionBar(mToolbar);
            getSupportActionBar();
        } else {
            setContentView(getLayoutId());
        }

        mActivity = this;
        mApplication = (TotalApplication) getApplication();
        mActivityManager = mApplication.getActivityManager();
        mActivityManager.addActivity(this);
        mBundle = getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        unbinder = ButterKnife.bind(this);
        mLoadingView = new LoadingView(this, getLoadingMessage());
        subscriptions = new CompositeSubscription();
        disposable = new CompositeDisposable();
        if (useEventBus()) {
            EventBusUtils.register(this);
        }
        onViewCreated(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        ActionBar actionBar = super.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return actionBar;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected boolean showActionBar() {
        return true;
    }

    protected String getLoadingMessage() {
        return null;
    }

    protected abstract int getLayoutId();

    protected abstract void onViewCreated(@Nullable Bundle savedInstanceState);

    private void onPreCreate() {
        PreUtils.setCurrentTheme(this, Theme.Blue);
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
//            case Red:
//                setTheme(R.style.RedTheme);
//                break;
//            case Brown:
//                setTheme(R.style.BrownTheme);
//                break;
//            case Green:
//                setTheme(R.style.GreenTheme);
//                break;
//            case Purple:
//                setTheme(R.style.PurpleTheme);
//                break;
//            case Teal:
//                setTheme(R.style.TealTheme);
//                break;
//            case Pink:
//                setTheme(R.style.PinkTheme);
//                break;
//            case DeepPurple:
//                setTheme(R.style.DeepPurpleTheme);
//                break;
//            case Orange:
//                setTheme(R.style.OrangeTheme);
//                break;
//            case Indigo:
//                setTheme(R.style.IndigoTheme);
//                break;
//            case LightGreen:
//                setTheme(R.style.LightGreenTheme);
//                break;
//            case Lime:
//                setTheme(R.style.LimeTheme);
//                break;
//            case DeepOrange:
//                setTheme(R.style.DeepOrangeTheme);
//                break;
//            case Cyan:
//                setTheme(R.style.CyanTheme);
//                break;
//            case BlueGrey:
//                setTheme(R.style.BlueGreyTheme);
//                break;
        }

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityManager.setCurrentActivity(this);
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(getLocalClassName());
        isResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mActivityManager.getCurrentActivity() == this) {
            mActivityManager.setCurrentActivity(null);
        }
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(getLocalClassName());
        isResume = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLoadingView != null) mLoadingView.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mActivityManager != null) mActivityManager.removeActivity(this);
        if (unbinder != null) unbinder.unbind();
        if (subscriptions != null) subscriptions.clear();
        if (disposable != null) disposable.clear();
        if (useEventBus()) EventBusUtils.unregister(this);
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
