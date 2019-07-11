package com.heyongrui.base.widget.ninegridimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heyongrui.base.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


/**
 * Created by lambert on 2018/11/19.
 */

public class NineGridLayout<T> extends ViewGroup {

    private static final float DEFUALT_SPACING = 3f;

    protected Context mContext;
    private float mSpacing = DEFUALT_SPACING;
    private int MAX_COUNT = 9;
    private int mColumns;
    private int mRows;
    private int mTotalWidth;
    private int mSingleWidth;

    private boolean mIsShowAll = false;
    private NineGridViewHolder<T> mNineGridViewHolder;
    private List<T> mDataList = new ArrayList<>();
    private SparseArray<ImageView> mImageViewList = new SparseArray<>();

    public NineGridLayout(Context context) {
        super(context);
        init(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout);

        mSpacing = typedArray.getDimension(R.styleable.NineGridLayout_sapcing, DEFUALT_SPACING);
        MAX_COUNT = typedArray.getInt(R.styleable.NineGridLayout_maxCount, 9);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if (getListSize(mDataList) == 0) {
            setVisibility(GONE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mTotalWidth = right - left;
        mSingleWidth = (int) ((mTotalWidth - mSpacing * (3 - 1)) / 3);
    }

    private void refresh() {
        mImageViewList.clear();
        removeAllViews();
        int size = getListSize(mDataList);
        if (size > 0) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }

        if (size == 1) {
            T t = mDataList.get(0);
            RatioImageView imageView = createImageView(0, t);

            //避免在ListView中一张图未加载成功时，布局高度受其他item影响
            LayoutParams params = getLayoutParams();
            params.height = mSingleWidth;
            setLayoutParams(params);
            imageView.layout(0, 0, mSingleWidth, mSingleWidth);

            if (mNineGridViewHolder != null) {
                boolean isCustomParam = mNineGridViewHolder.isCustomSingleParam(mContext, imageView, t, mTotalWidth);
                if (isCustomParam) {
                    addView(imageView);
                } else {
                    layoutImageView(imageView, 0, t, false);
                }
            }
            return;
        }

        generateChildrenLayout(size);
        layoutParams();

        for (int i = 0; i < size; i++) {
            T t = mDataList.get(i);
            RatioImageView imageView;
            if (!mIsShowAll) {
                if (i < MAX_COUNT - 1) {
                    imageView = createImageView(i, t);
                    layoutImageView(imageView, i, t, false);
                } else { //第9张时
                    if (size <= MAX_COUNT) {//刚好第9张
                        imageView = createImageView(i, t);
                        layoutImageView(imageView, i, t, false);
                    } else {//超过9张
                        imageView = createImageView(i, t);
                        layoutImageView(imageView, i, t, true);
                        break;
                    }
                }
            } else {
                imageView = createImageView(i, t);
                layoutImageView(imageView, i, t, false);
            }
        }
    }

    private void layoutParams() {
        int singleHeight = mSingleWidth;

        //根据子view数量确定高度
        LayoutParams params = getLayoutParams();
        params.height = (int) (singleHeight * mRows + mSpacing * (mRows - 1));
        setLayoutParams(params);
    }

    private RatioImageView createImageView(final int i, final T t) {
        RatioImageView imageView = new RatioImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnClickListener(v -> {
            if (mNineGridViewHolder != null) {
                mNineGridViewHolder.onItemClick(mContext, i, imageView, t);
            }
        });
        mImageViewList.put(i, imageView);
        return imageView;
    }

    /**
     * @param imageView
     * @param t
     * @param showNumFlag 是否在最大值的图片上显示还有未显示的图片张数
     */
    private void layoutImageView(RatioImageView imageView, int i, T t, boolean showNumFlag) {
        final int singleWidth = (int) ((mTotalWidth - mSpacing * (3 - 1)) / 3);
        int singleHeight = singleWidth;

        int[] position = findPosition(i);
        int left = (int) ((singleWidth + mSpacing) * position[1]);
        int top = (int) ((singleHeight + mSpacing) * position[0]);
        int right = left + singleWidth;
        int bottom = top + singleHeight;

        imageView.layout(left, top, right, bottom);

        addView(imageView);
        if (showNumFlag) {//添加超过最大显示数量的文本
            int overCount = getListSize(mDataList) - MAX_COUNT;
            if (overCount > 0) {
                float textSize = 30;
                final TextView textView = new TextView(mContext);
                textView.setText("+" + String.valueOf(overCount));
                textView.setTextColor(Color.WHITE);
                textView.setPadding(0, singleHeight / 2 - getFontHeight(textSize), 0, 0);
                textView.setTextSize(textSize);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.BLACK);
                textView.getBackground().setAlpha(120);

                textView.layout(left, top, right, bottom);
                addView(textView);
            }
        }
        if (mNineGridViewHolder != null) {
            mNineGridViewHolder.onBind(mContext, i, imageView, t);
        }
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mColumns; j++) {
                if ((i * mColumns + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    /**
     * 根据图片个数确定行列数量
     *
     * @param length
     */
    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            mRows = 1;
            mColumns = length;
        } else if (length <= 6) {
            mRows = 2;
            mColumns = 3;
            if (length == 4) {
                mColumns = 2;
            }
        } else {
            mColumns = 3;
            if (mIsShowAll) {
                mRows = length / 3;
                int b = length % 3;
                if (b > 0) {
                    mRows++;
                }
            } else {
                mRows = 3;
            }
        }

    }

    protected void setOneImageLayoutParams(RatioImageView imageView, int width, int height) {
        imageView.setLayoutParams(new LayoutParams(width, height));
        imageView.layout(0, 0, width, height);

        LayoutParams params = getLayoutParams();
//        params.width = width;
        params.height = height;
        setLayoutParams(params);
    }

    private int getListSize(List<T> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    private int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    public void setData(List<T> dataList, NineGridViewHolder<T> itemImageViewClickListener) {
        this.mNineGridViewHolder = itemImageViewClickListener;
        if (getListSize(dataList) == 0) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 设置间隔
     *
     * @param spacing
     */
    public void setSpacing(float spacing) {
        mSpacing = spacing;
    }

    /**
     * 设置是否显示所有图片（超过最大数时）
     *
     * @param isShowAll
     */
    public void setIsShowAll(boolean isShowAll) {
        mIsShowAll = isShowAll;
    }

    public void notifyDataSetChanged() {
        post(new TimerTask() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    public SparseArray<ImageView> getmImageViewList() {
        return mImageViewList;
    }
}
