package com.pers.libs.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.base.base.lib.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


/**
 * @author : wuyq
 * Time : 2020/9/24 10:13
 * Description :
 */
public abstract class BaseBottomDialog extends BottomSheetDialog {

    protected AppCompatActivity mActivity;

    public BaseBottomDialog(@NonNull AppCompatActivity context) {
        super(context, R.style.baseBottomDialogStyle);
        this.mActivity = context;
        initView();
    }

    public BaseBottomDialog(@NonNull AppCompatActivity context, int theme) {
        super(context, theme);
        this.mActivity = context;
        initView();
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback;

    private void initView() {
        View view = View.inflate(getContext(), getContentView(getContext()), null);
        setContentView(view);
        //关闭时：向下滑动关闭
        setDismissWithAnimation(true);
        try {
            //去除默认白色背景
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView(view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isStarted = true;
        if (mExpandHeight > 0) {
            setExpandHeight(mExpandHeight);
        }

        mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (mBottomDialogCallbacks != null) {
                    for (int i = 0, z = mBottomDialogCallbacks.size(); i < z; i++) {
                        OnBottomDialogCallback listener = mBottomDialogCallbacks.get(i);
                        if (listener != null) {
                            listener.onStateChanged(bottomSheet, newState);
                        }
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (mBottomDialogCallbacks != null) {
                    for (int i = 0, z = mBottomDialogCallbacks.size(); i < z; i++) {
                        OnBottomDialogCallback listener = mBottomDialogCallbacks.get(i);
                        if (listener != null) {
                            listener.onSlide(bottomSheet, slideOffset);
                        }
                    }
                }
            }
        };
        getBehavior().addBottomSheetCallback(mBottomSheetCallback);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        removeAllCallbacks();
    }


    protected abstract int getContentView(Context context);

    protected abstract void initView(View view);

    public abstract static class OnBottomDialogCallback {
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
        }

        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }


    /**
     * 添加一个定位监听，每个监听只会回调一次
     */
    private ArrayList<OnBottomDialogCallback> mBottomDialogCallbacks;

    public void addBottomDialogCallback(OnBottomDialogCallback callback) {
        if (mBottomDialogCallbacks == null) {
            mBottomDialogCallbacks = new ArrayList<>();
        }
        if (!mBottomDialogCallbacks.contains(callback)) {
            mBottomDialogCallbacks.add(callback);
        }
    }

    public void removeBottomDialogCallback(OnBottomDialogCallback listener) {
        if (mBottomDialogCallbacks != null) {
            mBottomDialogCallbacks.remove(listener);
        }
        if (mBottomSheetCallback != null) {
            getBehavior().removeBottomSheetCallback(mBottomSheetCallback);
        }
    }

    public void removeAllCallbacks() {
        if (mBottomDialogCallbacks != null) {
            mBottomDialogCallbacks.clear();
        }
        if (mBottomSheetCallback != null) {
            getBehavior().removeBottomSheetCallback(mBottomSheetCallback);
        }
    }

    private boolean isStarted;
    private int mExpandHeight = -1;

    /**
     * 设置展开高度
     */
    public void setExpandHeight(int expandHeight) {
        if (!isStarted) {
            //如果还未执行 onStart()。记录下高度，待 onStart() 之后再设置高度
            this.mExpandHeight = expandHeight;
            return;
        }

        View bottomSheet = findViewById(com.google.android.material.R.id.design_bottom_sheet);
        assert bottomSheet != null;
        assert bottomSheet.getLayoutParams() != null;
        //修改的是我们传入的跟布局高度。必须在onStart之后设置，否则会崩溃
        bottomSheet.getLayoutParams().height = expandHeight;
    }

    /**
     * BottomSheetBehavior 是否可用
     */
    public void setPeekHeight(int peekHeight) {
        BottomSheetBehavior<?> behavior = getBehavior();
        //设置折叠高度
        behavior.setPeekHeight(peekHeight, true);
    }

    /**
     * 当隐藏时，是否跳过折叠状态，直接全部隐藏
     */
    public void setSkipCollapsed(boolean skipCollapsed) {
        BottomSheetBehavior<?> behavior = getBehavior();
        behavior.setSkipCollapsed(skipCollapsed);
    }


    /**
     * 是否禁止拖动来显示/隐藏，当设置为false，需要实现自定义方式来展开/折叠
     */
    public void setDraggable(boolean draggable) {
        BottomSheetBehavior<?> behavior = getBehavior();
        behavior.setDraggable(draggable);
    }


    /**
     * 设置 BottomSheetDialog 状态
     * <p>
     * BottomSheetBehavior.STATE_COLLAPSED：折叠（默认）
     * BottomSheetBehavior.STATE_EXPANDED：展开
     * BottomSheetBehavior.STATE_HIDDEN：隐藏，布局区域全部不可见，半透明背景可见
     * BottomSheetBehavior.STATE_HALF_EXPANDED：折叠布局半关闭，fitToContents=false时有效。
     * 这时 BottomSheetDialogFragment 有3个可见状态：展开、折叠、半折叠
     */
    public void setState(int state) {
        BottomSheetBehavior<?> behavior = getBehavior();
        if (state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
            behavior.setFitToContents(false);
        }
        behavior.setState(state);
    }


    /**
     * 设置是否可以滑动
     *
     * @param hideAble
     */
    public void setHideAble(boolean hideAble) {
        //此处设置表示禁止BottomSheetBehavior的执行
        BottomSheetBehavior behavior = getBehavior();
        //hideAble = false 表示禁止BottomSheetBehavior的执行,那么滑动功能也被禁止
        behavior.setHideable(hideAble);
    }
}
