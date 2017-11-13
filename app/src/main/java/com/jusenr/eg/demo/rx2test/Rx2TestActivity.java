package com.jusenr.eg.demo.rx2test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.gank.adapter.MMListAdapter;
import com.jusenr.eg.demo.model.MaterialBenefitsModel;
import com.jusenr.eg.demo.retrofit.RetrofitManager;
import com.jusenr.eg.demo.retrofit.api.GankApi;
import com.jusenr.eg.demo.widgets.recyclerView.BaseRecyclerView;
import com.jusenr.toolslibrary.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Rx2TestActivity extends BaseActivity {
    public static final String TAG = Rx2TestActivity.class.getSimpleName();


    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.brv_mm_list)
    BaseRecyclerView mBrvMmList;

    private MMListAdapter mAdapter;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx2_test;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {

        initViews();
        initDatas(mPage);

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.i(TAG, "emit 1");
                emitter.onNext(1);
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissLoading();
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "Observer thread is :" + Thread.currentThread().getName());
                        Log.i(TAG, "onNext: " + integer);
                    }
                });
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

    /**
     * subscribe和subscribe1结果一样
     *
     * @param page
     */
    private void initDatas(int page) {
        Disposable subscribe = RetrofitManager.getGank2Api()
                .materialBenefits1(GankApi.TYPE_MATERIALBENEFITS, 10, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<JSONObject, MaterialBenefitsModel>() {
                    @Override
                    public MaterialBenefitsModel apply(JSONObject object) throws Exception {
                        return JSON.toJavaObject(object, MaterialBenefitsModel.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissLoading();
                    }
                })
                .subscribe(new Consumer<MaterialBenefitsModel>() {
                    @Override
                    public void accept(MaterialBenefitsModel model) throws Exception {
                        if (model != null) {
                            List<MaterialBenefitsModel.ResultsBean> results = model.getResults();
                            if (results != null) {
                                mAdapter.addData(results);
                            }
                            return;
                        }
                        mBrvMmList.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.show(mActivity, throwable.getMessage());
                    }
                });

        Disposable subscribe1 = RetrofitManager.getGank2Api()
                .materialBenefits1(GankApi.TYPE_MATERIALBENEFITS, 10, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissLoading();
                    }
                })
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject object) throws Exception {
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
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.show(mActivity, throwable.getMessage());
                    }
                });
        disposable.add(subscribe);
    }

    private void initViews() {
        setTitle("RX2Test");
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
}
