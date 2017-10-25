package com.jusenr.eg.demo.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.gank.adapter.MMListAdapter;
import com.jusenr.eg.demo.model.MaterialBenefitsModel;
import com.jusenr.eg.demo.retrofit.RetrofitManager;
import com.jusenr.eg.demo.retrofit.RxRetrofitComposer;
import com.jusenr.eg.demo.retrofit.api.GankApi;
import com.jusenr.eg.demo.retrofit.subscriber.Subscriber0;
import com.jusenr.eg.demo.widgets.recyclerView.BaseRecyclerView;
import com.jusenr.toolslibrary.utils.ListUtils;
import com.jusenr.toolslibrary.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
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


    private void initDatas(int page) {
        subscriptions.add(RetrofitManager.getGankApi()
                .materialBenefits(GankApi.TYPE_MATERIALBENEFITS, 20, page)
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
                            if (!ListUtils.isEmpty(results)) {
                                mAdapter.setNewData(results);
                            }
                            return;
                        }
                        mBrvMmList.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.show(mActivity, msg);
                    }
                }));
    }

    private void initViews() {
        mAdapter = new MMListAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemtClickListener);
        mBrvMmList.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(mRefreshListener);
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemtClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
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
            if (mPage > 55) {
                mPage = 1;
            }
        }
    };
}
