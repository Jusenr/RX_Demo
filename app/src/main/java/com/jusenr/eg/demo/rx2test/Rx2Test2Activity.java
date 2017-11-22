package com.jusenr.eg.demo.rx2test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Rx2Test2Activity extends BaseActivity {
    public static final String TAG = Rx2Test2Activity.class.getSimpleName();


    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.button5)
    Button mButton5;
    @BindView(R.id.button6)
    Button mButton6;

    private Subscription mSubscription;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx2_test2;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {

        setTitle("Flowable");
        setStatusBar();
    }

    private void flowableTest() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 128; i++) {
                    Log.d(TAG, "emit " + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)//增加了一个参数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
//                        s.request(Long.MAX_VALUE);  //注意这句代码
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);

                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void flowableTest2() {
        Flowable.interval(1, TimeUnit.MICROSECONDS)
                .onBackpressureDrop()  //加上背压策略
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void practice1() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                try {
                    InputStreamReader reader = new InputStreamReader(getResources().getAssets().open("text.txt"));
                    BufferedReader br = new BufferedReader(reader);

                    String str;

                    while ((str = br.readLine()) != null && !emitter.isCancelled()) {
                        while (emitter.requested() == 0) {
                            if (emitter.isCancelled()) {
                                break;
                            }
                        }
                        emitter.onNext(str);
                    }

                    br.close();
                    reader.close();

                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String string) {
                        System.out.println(string);
                        try {
                            Thread.sleep(2000);
                            mSubscription.request(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @OnClick({R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button3:
                if (mSubscription != null) mSubscription.request(10);
                break;
            case R.id.button4:
                flowableTest();
                break;
            case R.id.button5:
                flowableTest2();
                break;
            case R.id.button6:
                practice1();
                break;
        }
    }
}
