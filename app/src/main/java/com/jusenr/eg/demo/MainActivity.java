package com.jusenr.eg.demo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.dagger2Test.Dagger2TestActivity;
import com.jusenr.eg.demo.gank.MMActivity;
import com.jusenr.eg.demo.jsouptest.HtmlActivity;
import com.jusenr.eg.demo.model.GifSizeFilter;
import com.jusenr.eg.demo.model.UserModel;
import com.jusenr.eg.demo.retrofit.RetrofitManager;
import com.jusenr.eg.demo.retrofit.RxRetrofitComposer;
import com.jusenr.eg.demo.retrofit.model.Model1;
import com.jusenr.eg.demo.retrofit.subscriber.ApiSubscriber1;
import com.jusenr.eg.demo.retrofit.subscriber.ApiSubscriber2;
import com.jusenr.eg.demo.rx2test.Rx2Test2Activity;
import com.jusenr.eg.demo.rx2test.Rx2TestActivity;
import com.jusenr.eg.demo.test.MessageCenterActivity;
import com.jusenr.eg.demo.test.TestActivity;
import com.jusenr.eg.demo.theme.ColorRelativeLayout;
import com.jusenr.eg.demo.utils.PreUtils;
import com.jusenr.eg.demo.widgets.ResideLayout;
import com.jusenr.toolslibrary.utils.AppUtils;
import com.jusenr.toolslibrary.utils.DensityUtils;
import com.jusenr.toolslibrary.utils.StringUtils;
import com.jusenr.toolslibrary.utils.ToastUtils;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    public static final int REQUEST_CODE_CHOOSE = 100;

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
    @BindView(R.id.version)
    TextView mVersion;

    private boolean isBgChanged = false;
    boolean isPermissionsPass;

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
        applyPermissions();

        NativeLib nativeLib = new NativeLib();
        mTvText.setText(nativeLib.stringFromJNI());
        mVersion.setText("version：" + AppUtils.getVersionName(this));
        mEditText.setFocusable(false);
//        getDataTest();

//        int statusBarHeight = DensityUtils.getStatusBarHeight(this);
//        Log.i("Activity", "onViewCreated: statusBarHeight=" + statusBarHeight);

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

        mButton.setOnClickListener(v -> {

        });
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @OnClick({R.id.tv_text, R.id.btn_gank, R.id.button2, R.id.button, R.id.button7, R.id.button12, R.id.button14,
            R.id.all, R.id.fuli, R.id.android, R.id.video, R.id.resource, R.id.more, R.id.about, R.id.theme})
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
            case R.id.all:
                startActivity(new Intent(this, MessageCenterActivity.class));
                mResideLayout.closePane();
                break;
            case R.id.fuli:
                startActivity(new Intent(this, TestActivity.class));
                mResideLayout.closePane();
                break;
            case R.id.android:
                if (isPermissionsPass) {
                    matisse();
                }
                mResideLayout.closePane();
                break;
            case R.id.video:
                mResideLayout.closePane();
                break;
            case R.id.resource:
                mResideLayout.closePane();
                break;
            case R.id.more:
                mResideLayout.closePane();
                break;
            case R.id.about:
                mResideLayout.closePane();
                break;
            case R.id.theme:
                mColorChooserDialog.show(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            Log.i("Matisse", "mSelected: " + mSelected);
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

    @PermissionYes(101)
    public void permissionsSucceed(List<String> deniedList) {
        isPermissionsPass = true;
//        if (false) {
//            Matisse.from(this)
//                    .choose(MimeType.ofAll(), false)
//                    .theme(R.style.Matisse_Zhihu)
//                    .countable(true)
//                    .capture(true)
//                    .captureStrategy(new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
//                    .maxSelectable(9)
//                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                    .thumbnailScale(0.85f)
//                    .imageEngine(new GlideEngine())
//                    .forResult(REQUEST_CODE_CHOOSE);
//        } else {
//        Matisse.from(this)
//                .choose(MimeType.ofImage())
//                .theme(R.style.Matisse_Dracula)
//                .countable(false)
//                .maxSelectable(9)
//                .imageEngine(new PicassoEngine())
//                .forResult(REQUEST_CODE_CHOOSE);
//        }
    }

    @PermissionNo(101)
    public void PermissionsFailed(List<String> deniedList) {
        isPermissionsPass = false;
        ToastUtils.show(this, getString(R.string.permission_request_denied));
    }

    private void matisse() {
        Matisse.from(this)
                .choose(MimeType.ofAll(), false)
                .theme(R.style.Matisse_Zhihu)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void applyPermissions() {
        AndPermission.with(this)
                .callback(this)
                .permission(Permission.STORAGE)
                .requestCode(101)
                .start();
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
                .doOnSubscribe(() -> showLoading())
                .doOnTerminate(() -> dismissLoading())
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
