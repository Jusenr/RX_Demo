package com.jusenr.eg.demo.test.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
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
public class MessageDetailListAdapter extends BaseMultiItemQuickAdapter<MultipleItem<MessageDetailModel.DataBean>, BaseViewHolder> {

    public MessageDetailListAdapter(List<MultipleItem<MessageDetailModel.DataBean>> data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.layout_item_message_type_1);
        addItemType(MultipleItem.IMG, R.layout.layout_item_message_type_2);
        addItemType(MultipleItem.IMG_TEXT, R.layout.layout_item_message_type_3);
        addItemType(MultipleItem.IMG_TEXT_21, R.layout.layout_item_message_type_4);
        addItemType(MultipleItem.TEXT_TIPS, R.layout.layout_item_message_type_4);
        addItemType(MultipleItem.TEXT_TIPS_LINK, R.layout.layout_item_message_type_4);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem<MessageDetailModel.DataBean> item) {
        MessageDetailModel.DataBean content = item.getContent();
        if (content != null) {
            switch (helper.getItemViewType()) {
                case MultipleItem.TEXT:
                    helper.setText(R.id.tv_time, "2017-11-23");
                    helper.setText(R.id.tv_desc, "淘淘连续使用平板超过1小时了，为了孩子的健康，请及时设置定时休息。");
                    break;
                case MultipleItem.IMG:
                    helper.setText(R.id.tv_time, "星期五");
                    helper.setText(R.id.tv_title, "21天逻辑思维训练计划");
                    helper.setImageResource(R.id.iv_pic, R.mipmap.photo_004);
                    break;
                case MultipleItem.IMG_TEXT:
                    helper.setText(R.id.tv_time, "2017年11月23日 15：04");
                    helper.setText(R.id.tv_title, content.getTitle());
                    helper.setText(R.id.tv_length_of_use, "5小时20分");
                    helper.setText(R.id.tv_defeat, "89%");
                    break;
                case MultipleItem.IMG_TEXT_21:
//                    helper.setVisible(R.id.tv_finish, true);
                    helper.setText(R.id.tv_time, "20：04");
                    helper.setText(R.id.tv_title, content.getTitle());
                    helper.setText(R.id.tv_day_number, "6");
                    break;
                case MultipleItem.TEXT_TIPS:
                    helper.setVisible(R.id.tv_desc, true);
                    helper.setVisible(R.id.ll_day, false);
                    helper.setVisible(R.id.rl_detail_link, false);
                    helper.setText(R.id.tv_time, "20：04");
                    helper.setText(R.id.tv_title, "淘淘任务待完成提醒");
                    helper.setText(R.id.tv_desc, "淘淘今天还没完成21天逻辑思维训练（1阶）的今日任务哦，快去督促一下吧！");
                    break;
                case MultipleItem.TEXT_TIPS_LINK:
                    helper.setVisible(R.id.tv_desc, true);
                    helper.setVisible(R.id.ll_day, false);
                    helper.setText(R.id.tv_time, "20：04");
                    helper.setText(R.id.tv_title, "更换题卡提醒");
                    helper.setText(R.id.tv_desc, "淘淘在逻辑思维（1阶）学习有一段时间了，可以更换题卡挑战更高的难度了！");
                    break;
            }
        }
    }
}
