package com.example.aotedan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by HAN on 2018/12/6 0006.
 */

public class MyScrollview extends ScrollView {

    /**
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
     */
    private int lastScrollY;

    public MyScrollview(Context context) {
        this(context, null);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollListener != null) {
            scrollListener.scroll(t);
        }
    }

    scrollListener scrollListener = null;

    public void setScroll(scrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public interface scrollListener {
        public void scroll(int yy);
    }
}
