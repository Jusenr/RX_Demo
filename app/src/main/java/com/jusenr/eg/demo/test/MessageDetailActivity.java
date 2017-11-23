package com.jusenr.eg.demo.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusenr.eg.demo.DataJson;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.model.MessageDetailModel;
import com.jusenr.eg.demo.test.adapter.MessageDetailListAdapter;
import com.jusenr.eg.demo.test.adapter.MultipleItem;
import com.jusenr.eg.demo.widgets.recyclerView.BaseRecyclerView;
import com.jusenr.toolslibrary.utils.JsonUtils;
import com.jusenr.toolslibrary.utils.ListUtils;
import com.jusenr.toolslibrary.utils.StringUtils;
import com.jusenr.toolslibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.brv_message_type_list)
    BaseRecyclerView mBrvMessageTypeList;

    private MessageDetailListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        int id = getIntent().getIntExtra("id", 1);
        setTitle(StringUtils.isEmpty(title) ? "消息中心" : title);

        initViews();
        initDatas(id);
    }

    private void initDatas(int id) {
        MessageDetailModel model;
        if (id == 1 || id == 3) {
            model = JsonUtils.parseData(DataJson.messageTypeList_1, MessageDetailModel.class);
        } else {
            model = JsonUtils.parseData(DataJson.messageTypeList_2, MessageDetailModel.class);
        }
        if (model != null) {
            List<MessageDetailModel.DataBean> beanList = model.getData();
            if (!ListUtils.isEmpty(beanList)) {
                List<MultipleItem<MessageDetailModel.DataBean>> list = new ArrayList<>();
                for (MessageDetailModel.DataBean dataBean : beanList) {
                    if (dataBean.getId() == 1) {
                        MultipleItem multipleItem = new MultipleItem(MultipleItem.TEXT);
                        multipleItem.setContent(dataBean);
                        list.add(multipleItem);
                    } else if (dataBean.getId() == 2) {
                        MultipleItem multipleItem = new MultipleItem(MultipleItem.IMG_TEXT);
                        multipleItem.setContent(dataBean);
                        list.add(multipleItem);
                    } else if (dataBean.getId() == 3) {
                        MultipleItem multipleItem = new MultipleItem(MultipleItem.IMG_TEXT_21);
                        multipleItem.setContent(dataBean);
                        list.add(multipleItem);
                    } else if (dataBean.getId() == 4) {
                        MultipleItem multipleItem = new MultipleItem(MultipleItem.TEXT_TIPS);
                        multipleItem.setContent(dataBean);
                        list.add(multipleItem);
                    } else {
                        MultipleItem multipleItem = new MultipleItem(MultipleItem.TEXT_TIPS_LINK);
                        multipleItem.setContent(dataBean);
                        list.add(multipleItem);
                    }
                }
                mAdapter.setNewData(list);
            }
        }
    }

    private void initViews() {
        mAdapter = new MessageDetailListAdapter(null);
        mAdapter.setOnItemClickListener(mItemtClickListener);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mBrvMessageTypeList.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnItemClickListener mItemtClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MultipleItem<MessageDetailModel.DataBean> item = mAdapter.getItem(position);
            if (item != null) {
                ToastUtils.show(mActivity, item.getContent().getDesc());
            }
        }
    };
}
