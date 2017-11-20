package com.jusenr.eg.demo.jsouptest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;

import butterknife.BindView;

public class HtmlActivity extends BaseActivity {

    @BindView(R.id.wv_view)
    WebView wv_view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_html;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        wv_view.loadUrl("http://ac.qq.com/ComicView/index/id/524019/cid/1");
    }
}
