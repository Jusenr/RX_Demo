package com.jusenr.eg.demo.test.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MultipleItem<T> implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;
    public static final int IMG_TEXT = 3;
    public static final int IMG_TEXT_21 = 4;
    public static final int TEXT_TIPS = 5;
    public static final int TEXT_TIPS_LINK = 6;

    private int itemType;
    private T content;

    public MultipleItem(int itemType, T content) {
        this.itemType = itemType;
        this.content = content;
    }

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
