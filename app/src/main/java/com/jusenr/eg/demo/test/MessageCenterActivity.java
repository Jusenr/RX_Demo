package com.jusenr.eg.demo.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jusenr.eg.demo.DataJson;
import com.jusenr.eg.demo.R;
import com.jusenr.eg.demo.base.BaseActivity;
import com.jusenr.eg.demo.model.MessageTypeModel;
import com.jusenr.eg.demo.test.adapter.MessageTypeListAdapter;
import com.jusenr.eg.demo.widgets.recyclerView.BaseRecyclerView;
import com.jusenr.toolslibrary.utils.JsonUtils;
import com.jusenr.toolslibrary.utils.ListUtils;

import java.util.List;

import butterknife.BindView;

public class MessageCenterActivity extends BaseActivity {

    @BindView(R.id.brv_message_type_list)
    BaseRecyclerView mBrvMessageTypeList;

    private MessageTypeListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        setTitle("消息中心");

        initViews();
        initDatas();
    }

    private void initDatas() {
        MessageTypeModel model = JsonUtils.parseData(DataJson.messageTypeList, MessageTypeModel.class);
        if (model != null) {
            List<MessageTypeModel.DataBean> beanList = model.getData();
            if (!ListUtils.isEmpty(beanList))
                mAdapter.setNewData(beanList);
        }
    }

    private void initViews() {
        mAdapter = new MessageTypeListAdapter(null);
        mAdapter.setOnItemClickListener(mItemtClickListener);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mBrvMessageTypeList.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnItemClickListener mItemtClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            MessageTypeModel.DataBean item = mAdapter.getItem(position);
            if (item != null) {
                Intent intent = new Intent(mActivity, MessageDetailActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        }
    };
}
