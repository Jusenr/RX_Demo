package com.jusenr.eg.demo.widgets.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riven_chris on 2017/4/2.
 */

public abstract class BaseAdapter<Item, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected List<Item> mItems;

    public BaseAdapter(Context context, List<Item> items) {
        this.context = context;
        this.mItems = items != null ? items : new ArrayList<Item>();
    }

    /**
     * 获得全部对象
     *
     * @return
     */
    public List<Item> getItems() {
        return mItems;
    }

    /**
     * 设置item布局id
     *
     * @param viewType view类型
     * @return item布局id
     */
    public abstract int getLayoutId(int viewType);

    /**
     * 设置ViewHolder
     *
     * @param itemView item布局
     * @param viewType view类型
     * @return ViewHolder
     */
    public abstract VH getViewHolder(View itemView, int viewType);

    /**
     * 向itemView上绑定数据
     *
     * @param holder ViewHolder
     * @param item   item数据
     */
    public abstract void onBindItem(VH holder, Item item, int position) throws ParseException;

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * 获得item
     *
     * @param position 标号
     * @return item
     */
    public Item getItem(int position) {
        return mItems.get(position);
    }

    protected VH createViewViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), null);
        return getViewHolder(view, viewType);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(context).inflate(getLayoutId(viewType), parent, false);
        return getViewHolder(mItemView, viewType);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        final Item item = getItem(position);
        final View itemView = holder.itemView;
        itemView.setTag(position);
        try {
            onBindItem(holder, item, position);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void add(Item item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void add(int position, Item item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void addAll(List<Item> items) {
        int indexStart = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(indexStart, items.size());
    }

    public void addAll(int position, List<Item> items) {
        int indexStart = mItems.size();
        mItems.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public void replace(Item oldItem, Item newItem) {
        int index = mItems.indexOf(oldItem);
        replace(index, newItem);
    }

    public void replace(int index, Item item) {
        mItems.set(index, item);
        notifyItemChanged(index);
    }

    public void replaceAll(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void delete(Item item) {
        int index = mItems.indexOf(item);
        delete(index);
    }

    public void delete(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public boolean contains(Item item) {
        return mItems.contains(item);
    }
}
