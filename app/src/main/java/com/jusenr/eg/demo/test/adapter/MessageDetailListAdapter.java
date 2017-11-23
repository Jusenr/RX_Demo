package com.jusenr.eg.demo.test.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.model.MessageDetailModel;

import java.util.List;

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Email      : jusenr@163.com
 * Author     : Jusenr
 * Date       : 2017/11/23
 * Time       : 9:25
 * Project    ：RX_Demo.
 */
public class MessageDetailListAdapter extends BaseQuickAdapter<MessageDetailModel.DataBean, BaseViewHolder> {

    public MessageDetailListAdapter(List<MessageDetailModel.DataBean> data) {
        super(R.layout.layout_item_message_type_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageDetailModel.DataBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_desc, item.getDesc());
    }
}
