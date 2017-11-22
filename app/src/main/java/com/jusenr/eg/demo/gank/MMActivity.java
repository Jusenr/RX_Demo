package com.jusenr.eg.demo.gank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.gank.adapter.MMListAdapter;
import com.jusenr.eg.demo.model.MaterialBenefitsModel;
import com.jusenr.eg.demo.retrofit.RetrofitManager;
import com.jusenr.eg.demo.retrofit.RxRetrofitComposer;
import com.jusenr.eg.demo.retrofit.api.GankApi;
import com.jusenr.eg.demo.retrofit.subscriber.Subscriber0;
import com.jusenr.eg.demo.widgets.recyclerView.BaseRecyclerView;
import com.jusenr.toolslibrary.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action0;

public class MMActivity extends BaseActivity {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.brv_mm_list)
    BaseRecyclerView mBrvMmList;


    private MMListAdapter mAdapter;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mm;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        initViews();
        initDatas(mPage);
    }

    @Override
    public void showLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void dismissLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                search();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDatas(int page) {
        Subscription subscribe = RetrofitManager.getGankApi()
                .materialBenefits(GankApi.TYPE_MATERIALBENEFITS, 10, page)
                .compose(RxRetrofitComposer.<JSONObject>applySchedulers())
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
                .subscribe(new Subscriber0<JSONObject>() {
                    @Override
                    public void onNext(String msg, JSONObject object) {
                        MaterialBenefitsModel model = JSON.toJavaObject(object, MaterialBenefitsModel.class);
                        if (model != null) {
                            List<MaterialBenefitsModel.ResultsBean> results = model.getResults();
                            if (results != null) {
                                mAdapter.addData(results);
                            }
                            return;
                        }
                        mBrvMmList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.show(mActivity, msg);
                    }
                });
        subscriptions.add(subscribe);
    }

    private void initViews() {
        setTitle("Gank");
        int color = getCurrentThemeColor();
        StatusBarUtil.setColor(this, color, 0);
        if (getToolbar() != null) getToolbar().setBackgroundColor(color);

        mAdapter = new MMListAdapter(null);
        mAdapter.setOnItemClickListener(mItemtClickListener);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mBrvMmList.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(mRefreshListener);
    }

    private BaseQuickAdapter.OnItemClickListener mItemtClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MaterialBenefitsModel.ResultsBean resultsBean = mAdapter.getItem(position);
            if (resultsBean != null) {
                ToastUtils.show(mActivity, resultsBean.getDesc());
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPage++;
            initDatas(mPage);
        }
    };

    private void search() {
        final EditText editText = new EditText(this);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        AlertDialog.Builder b = new AlertDialog.Builder(this)
                .setTitle("Input page number")
                .setIcon(android.R.drawable.ic_search_category_default)
                .setView(editText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String page = editText.getText().toString().trim();
                        initDatas(TextUtils.isEmpty(page) ? 1 : Integer.parseInt(page));
                    }
                });

        b.create().show();
    }

}
