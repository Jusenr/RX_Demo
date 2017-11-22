package com.jusenr.eg.demo.base;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.jaeger.library.StatusBarUtil;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.TotalApplication;
import com.jusenr.eg.demo.base.loading.LoadView;
import com.jusenr.eg.demo.base.loading.LoadingView;
import com.jusenr.eg.demo.theme.ColorUiUtil;
import com.jusenr.eg.demo.theme.Theme;
import com.jusenr.eg.demo.utils.PreUtils;
import com.jusenr.eg.demo.utils.ThemeUtils;
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
public abstract class BaseActivity extends AppCompatActivity implements LoadView, ColorChooserDialog.ColorCallback {
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
    protected ColorChooserDialog mColorChooserDialog;
    private ActivityManager mActivityManager;
    private Toolbar mToolbar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

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
        if (mColorChooserDialog == null) {
            mColorChooserDialog = new ColorChooserDialog.Builder(this, R.string.theme)
                    .customColors(R.array.colors, null)
                    .doneButton(R.string.done)
                    .cancelButton(R.string.cancel)
                    .allowUserColorInput(false)
                    .allowUserColorInputAlpha(false)
                    .build();

        }
        onViewCreated(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusBar();
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

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getCurrentThemeColor());
        if (mToolbar != null) mToolbar.setBackgroundColor(getCurrentThemeColor());
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected int getCurrentThemeColor() {
        return PreUtils.getCurrentThemeColor(this);
    }

    protected boolean showActionBar() {
        return true;
    }

    protected String getLoadingMessage() {
        return null;
    }

    protected abstract int getLayoutId();

    protected abstract void onViewCreated(@Nullable Bundle savedInstanceState);

    protected void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Brown:
                setTheme(R.style.BrownTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Purple:
                setTheme(R.style.PurpleTheme);
                break;
            case Teal:
                setTheme(R.style.TealTheme);
                break;
            case Pink:
                setTheme(R.style.PinkTheme);
                break;
            case DeepPurple:
                setTheme(R.style.DeepPurpleTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Indigo:
                setTheme(R.style.IndigoTheme);
                break;
            case LightGreen:
                setTheme(R.style.LightGreenTheme);
                break;
            case Lime:
                setTheme(R.style.LimeTheme);
                break;
            case DeepOrange:
                setTheme(R.style.DeepOrangeTheme);
                break;
            case Cyan:
                setTheme(R.style.CyanTheme);
                break;
            case BlueGrey:
                setTheme(R.style.BlueGreyTheme);
                break;
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
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        if (selectedColor == ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
            return;
        EventBusUtils.post(getLocalClassName(), getLocalClassName());
        PreUtils.setCurrentThemeColor(this, selectedColor);

        if (selectedColor == getResources().getColor(R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            PreUtils.setCurrentTheme(this, Theme.Blue);

        } else if (selectedColor == getResources().getColor(R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            PreUtils.setCurrentTheme(this, Theme.Red);

        } else if (selectedColor == getResources().getColor(R.color.colorBrownPrimary)) {
            setTheme(R.style.BrownTheme);
            PreUtils.setCurrentTheme(this, Theme.Brown);

        } else if (selectedColor == getResources().getColor(R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);
            PreUtils.setCurrentTheme(this, Theme.Green);

        } else if (selectedColor == getResources().getColor(R.color.colorPurplePrimary)) {
            setTheme(R.style.PurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.Purple);

        } else if (selectedColor == getResources().getColor(R.color.colorTealPrimary)) {
            setTheme(R.style.TealTheme);
            PreUtils.setCurrentTheme(this, Theme.Teal);

        } else if (selectedColor == getResources().getColor(R.color.colorPinkPrimary)) {
            setTheme(R.style.PinkTheme);
            PreUtils.setCurrentTheme(this, Theme.Pink);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepPurplePrimary)) {
            setTheme(R.style.DeepPurpleTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepPurple);

        } else if (selectedColor == getResources().getColor(R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.Orange);

        } else if (selectedColor == getResources().getColor(R.color.colorIndigoPrimary)) {
            setTheme(R.style.IndigoTheme);
            PreUtils.setCurrentTheme(this, Theme.Indigo);

        } else if (selectedColor == getResources().getColor(R.color.colorLightGreenPrimary)) {
            setTheme(R.style.LightGreenTheme);
            PreUtils.setCurrentTheme(this, Theme.LightGreen);

        } else if (selectedColor == getResources().getColor(R.color.colorDeepOrangePrimary)) {
            setTheme(R.style.DeepOrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.DeepOrange);

        } else if (selectedColor == getResources().getColor(R.color.colorLimePrimary)) {
            setTheme(R.style.LimeTheme);
            PreUtils.setCurrentTheme(this, Theme.Lime);

        } else if (selectedColor == getResources().getColor(R.color.colorBlueGreyPrimary)) {
            setTheme(R.style.BlueGreyTheme);
            PreUtils.setCurrentTheme(this, Theme.BlueGrey);

        } else if (selectedColor == getResources().getColor(R.color.colorCyanPrimary)) {
            setTheme(R.style.CyanTheme);
            PreUtils.setCurrentTheme(this, Theme.Cyan);

        }
        final View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }
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
