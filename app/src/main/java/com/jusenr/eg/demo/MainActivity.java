package com.jusenr.eg.demo;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
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
import com.jusenr.eg.demo.theme.ColorRelativeLayout;
import com.jusenr.eg.demo.theme.ColorUiUtil;
import com.jusenr.eg.demo.theme.Theme;
import com.jusenr.eg.demo.utils.PreUtils;
import com.jusenr.eg.demo.utils.ThemeUtils;
import com.jusenr.eg.demo.widgets.ResideLayout;
import com.jusenr.toolslibrary.utils.DensityUtils;
import com.jusenr.toolslibrary.utils.EventBusUtils;
import com.jusenr.toolslibrary.utils.StringUtils;
import com.jusenr.toolslibrary.utils.ToastUtils;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.desc)
    TextView mDesc;
    @BindView(R.id.all)
    TextView mAll;
    @BindView(R.id.fuli)
    TextView mFuli;
    @BindView(R.id.android)
    TextView mAndroid;
    @BindView(R.id.video)
    TextView mVideo;
    @BindView(R.id.resource)
    TextView mResource;
    @BindView(R.id.more)
    TextView mMore;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.about)
    TextView mAbout;
    @BindView(R.id.theme)
    TextView mTheme;
    @BindView(R.id.menu)
    ColorRelativeLayout mMenu;
    @BindView(R.id.resideLayout)
    ResideLayout mResideLayout;

    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
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
    private boolean isTransparent = true;
    private int mAlpha;
    private ColorChooserDialog mColorChooserDialog;

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
        mEditText.setFocusable(false);
//        getDataTest();

//        int statusBarHeight = DensityUtils.getStatusBarHeight(this);
//        Log.i("Activity", "onViewCreated: statusBarHeight=" + statusBarHeight);

        if (!isTransparent) {
            mSbChangeAlpha.setVisibility(View.VISIBLE);
            setSeekBar();
        } else {
            mSbChangeAlpha.setVisibility(View.GONE);
        }

        setIconDrawable(mAll, MaterialDesignIconic.Icon.gmi_view_comfy);
        setIconDrawable(mFuli, MaterialDesignIconic.Icon.gmi_mood);
        setIconDrawable(mAndroid, MaterialDesignIconic.Icon.gmi_android);
        setIconDrawable(mVideo, MaterialDesignIconic.Icon.gmi_collection_video);
        setIconDrawable(mResource, FontAwesome.Icon.faw_location_arrow);
        setIconDrawable(mMore, MaterialDesignIconic.Icon.gmi_more);
        setIconDrawable(mAbout, MaterialDesignIconic.Icon.gmi_account);
        setIconDrawable(mTheme, MaterialDesignIconic.Icon.gmi_palette);

        boolean isFirst = PreUtils.getBoolean(this, "isFirst", true);
        if (isFirst) {
            mResideLayout.openPane();
            PreUtils.putBoolean(this, "isFirst", false);
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

    @Override
    protected boolean useEventBus() {
        return true;
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

    @OnClick({R.id.tv_text, R.id.btn_gank, R.id.button2, R.id.button, R.id.button7, R.id.button12, R.id.button14, R.id.theme})
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
                    mRlMain.setBackgroundDrawable(getResources().getDrawable(R.mipmap.photo_002));
                } else {
                    mRlMain.setBackgroundDrawable(getResources().getDrawable(R.mipmap.photo_003));
                }
                break;
            case R.id.theme:
                if (mColorChooserDialog == null) {
                    mColorChooserDialog = new ColorChooserDialog.Builder(this, R.string.theme)
                            .customColors(R.array.colors, null)
                            .doneButton(R.string.done)
                            .cancelButton(R.string.cancel)
                            .allowUserColorInput(false)
                            .allowUserColorInputAlpha(false)
                            .build();

                }
                mColorChooserDialog.show(this);
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

    @Override
    public void onBackPressed() {
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else {
            super.onBackPressed();
        }
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

    private void setIconDrawable(TextView view, IIcon icon) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(16),
                null, null, null);
        view.setCompoundDrawablePadding(DensityUtils.dp2px(this, 10));
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

}
