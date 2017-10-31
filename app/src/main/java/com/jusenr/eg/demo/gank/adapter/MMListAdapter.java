package com.jusenr.eg.demo.gank.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.model.MaterialBenefitsModel;
import com.jusenr.eg.demo.retrofit.api.BaseApi;
import com.jusenr.eg.demo.widgets.fresco.FrescoImageView;

import java.util.List;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/10/25
 * Time       : 10:40
 * Project    ：RX_Demo.
 */
public class MMListAdapter extends BaseQuickAdapter<MaterialBenefitsModel.ResultsBean, BaseViewHolder> {

    public MMListAdapter(List<MaterialBenefitsModel.ResultsBean> data) {
        super(R.layout.layout_item_mm, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MaterialBenefitsModel.ResultsBean bean) {
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getUrl())) {
                ((FrescoImageView) holder.getView(R.id.fiv_image)).setImageURL(bean.getUrl() + BaseApi.IMAGE_TYPE_300);
            }
            holder.setText(R.id.tv_desc, bean.getWho());
        }
    }
}
