package com.dizuo.xapp;

import com.dizuo.xapp.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Tab�ؼ�
 * 
 * @author aaronguo
 */
public class TabGroup extends FrameLayout implements OnClickListener {

    private final static int ANIM_DURATION = 250;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    private int mCheckedId = -1;

    /**
     * tab item�ı���
     */
    private ImageView mTabBg = null;

    /**
     * tab������
     */
    private LinearLayout mTabsContainer = null;

    /**
     * ��ǰ����item indx
     */
    private int mFocusedItem = 0;

    /**
     * Ĭ�ϵ�foucused index
     */
    private int mDefaultFocused = -1;

    /**
     * Tab�л�ʱ�Ƿ���Ҫ���Ŷ���
     */
    private boolean mIsAnimationOn = true;

    public TabGroup(Context context) {
        this(context, null);
    }

    public TabGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        // mTabBg = (ImageView)findViewById(R.id.tab_bg);
        mTabsContainer = (LinearLayout)findViewById(R.id.tabs_container);

        final int childCount = mTabsContainer.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            mTabsContainer.getChildAt(i).setOnClickListener(this);
        }
    }

    /**
     * <p>
     * Register a callback to be invoked when the checked tab changes in this
     * group.
     * </p>
     * 
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void onClick(View v) {
        setCheckedTabId(v.getId(), true);
    }

    /**
     * <p>
     * Sets the selection to the tab whose identifier is passed in parameter.
     * Using -1 as the selection identifier clears the selection.
     * </p>
     * 
     * @param id the unique id of the tab to select in this group
     * @see #getCheckedTabId()
     */
    public void check(int id) {
        setCheckedTabId(id, false);
    }

    /**
     * <p>
     * Returns the identifier of the selected tab in this group. Upon empty
     * selection, the returned value is -1.
     * </p>
     * 
     * @return the unique id of the selected radio button in this group
     * @see #check(int)
     */
    public int getCheckedTabId() {
        return mCheckedId;
    }

    /**
     * Returns the index of the selected tab in this group.
     */
    public int getCheckedTabIndex() {
        return getChildIndex(mCheckedId);
    }

    /**
     * ����Tabѡ�ж�������
     * 
     * @param on {@code true}�򿪣�{@code false}�ر�
     */
    public void setFocusAnimation(boolean on) {
        mIsAnimationOn = on;
    }

    /**
     * ����tab��ѡ��ҳ��Id
     * 
     * @param id
     * @param isClicked
     */
    private void setCheckedTabId(int id, boolean isClicked) {
        if (id != -1 && id == mCheckedId || null == mTabsContainer) {
            return;
        }

        final int childCount = mTabsContainer.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = mTabsContainer.getChildAt(i);
            int childId = child.getId();
            if (id == childId) {
                // ѡ��ָ��child
                if (id != mCheckedId) {
                    child.setSelected(true);
                    if (isClicked && mIsAnimationOn) {
                        // �û������Ҫ���ж���
                        startSwitchAnim(getChildIndex(id));
                    } else {
                        // ����ʱ�����ж���
                        final int childWidth = getChildWidth();
                        if (0 != childWidth) {
                            mFocusedItem = getChildIndex(id);
                            changeBgPos(mFocusedItem, childWidth);
                        }
                    }

                    mCheckedId = id;
                    if (mOnCheckedChangeListener != null) {
                        mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
                    }
                }

                if (-1 == mDefaultFocused) {
                    mDefaultFocused = i;
                    mFocusedItem = i;
                }
            } else {
                // ȡ������child��ѡ��̬
                child.setSelected(false);
            }
        }

        // �ص�ȡ��ѡ��
        if (id == -1 && mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, id);
        }
    }

    /**
     * �����л�����
     */
    private void startSwitchAnim(final int foucusIndex) {
        if (null == mTabsContainer) {
            return;
        }

        final int childWidth = getChildWidth();
        if (0 == childWidth) {
            return;
        }

        if (mTabBg != null) {
            final int endDeltaX = (foucusIndex - mFocusedItem) * childWidth;

            TranslateAnimation anim = new TranslateAnimation(0, endDeltaX, 0, 0);
            anim.setDuration(ANIM_DURATION);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setAnimationListener(new SimpleAnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mTabBg.clearAnimation();
                    changeBgPos(foucusIndex, childWidth);
                }
            });

            mTabBg.startAnimation(anim);
        }

        mFocusedItem = foucusIndex;
    }

    /**
     * �ı�tab bg��λ��
     */
    private void changeBgPos(int focusIndex, int childWidth) {
        if (null != mTabBg) {
            LayoutParams lp = (LayoutParams)mTabBg.getLayoutParams();
            /*if (MobileIssueSettings.isOldLayoutParamsWrong) {
                // Add by kapalai:SONY LT26I�����޷����Ѿ����ڵĶ�����и�ֵ�������´���һ������
            	KapalaiAdapterUtil.getKAUInstance().changeBgPos(mTabBg,lp,focusIndex, childWidth);
            } else {*/
                lp.leftMargin = childWidth * focusIndex;
                lp.gravity = Gravity.CENTER_VERTICAL;
                mTabBg.setLayoutParams(lp);
            // }
        }
    }

    /**
     * ��ȡָ��child��index
     * 
     * @param child
     * @return
     */
    private int getChildIndex(int id) {
        final int childCount = mTabsContainer.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            if (id == mTabsContainer.getChildAt(i).getId()) {
                return i;
            }
        }

        return 0;
    }

    /**
     * ��ȡchild�Ŀ��
     * 
     * @return
     */
    private int getChildWidth() {
        final int childCount = mTabsContainer.getChildCount();
        if (childCount > 0) {
            int totalWidth = mTabsContainer.getWidth();
            int childWidth = mTabsContainer.getChildAt(0).getWidth();
            if (0 == totalWidth) {
                mTabsContainer.measure(0, 0);
                totalWidth = mTabsContainer.getMeasuredWidth();
                childWidth = mTabsContainer.getChildAt(0).getMeasuredWidth();
            }
            final int totalGap = totalWidth - childWidth * childCount;
            final int eachGap = (childCount - 1 > 0) ? totalGap / (childCount - 1) : totalGap;

            return childWidth + eachGap;
        }

        return 0;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (null == mTabsContainer) {
            return;
        }
        
        final int childCount = mTabsContainer.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = mTabsContainer.getChildAt(i);
            if (null != child) {
                child.setEnabled(enabled);
            }
        }
    }
    
    /**
     * <p>
     * Interface definition for a callback to be invoked when the checked tab
     * changed in this group.
     * </p>
     */
    public interface OnCheckedChangeListener {
        /**
         * <p>
         * Called when the checked tab has changed. When the selection is
         * cleared, checkedId is -1.
         * </p>
         * 
         * @param group the group in which the checked radio button has changed
         * @param checkedId the unique identifier of the newly checked radio
         *            button
         */
        public void onCheckedChanged(TabGroup group, int checkedId);
    }

}
