package com.heyongrui.module2.adapter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heyongrui.base.glide.GlideApp;
import com.heyongrui.base.utils.TimeUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.module2.R;
import com.heyongrui.module2.data.dto.GankDto;

import java.util.List;


/**
 * lambert
 * 2019/6/25 18:17
 */
public class Module2SectionAdapter extends BaseSectionMultiItemQuickAdapter<Module2SectionEntity, BaseViewHolder> {

    public Module2SectionAdapter(List<Module2SectionEntity> data) {
        this(0, data);
    }

    public Module2SectionAdapter(int sectionHeadResId, List<Module2SectionEntity> data) {
        super(sectionHeadResId, data);
        addItemType(Module2SectionEntity.WELFARE, R.layout.recycle_item_welfare);
        addItemType(Module2SectionEntity.GANK, R.layout.recycle_item_android);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, Module2SectionEntity item) {
//        TextView tvHeadSection = helper.getView(R.id.tv_head_section);
//        tvHeadSection.setText(item.header);
//        tvHeadSection.setVisibility(item.isShow() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void convert(BaseViewHolder helper, Module2SectionEntity item) {
        switch (helper.getItemViewType()) {
            case Module2SectionEntity.WELFARE: {
                ConstraintLayout content = helper.getView(R.id.content);
                ImageView coverIv = helper.getView(R.id.iv);
                GankDto.GankBean gankBean = item.getGankBean();
                if (gankBean == null) return;
                String url = gankBean.getUrl();
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder_fail)
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                GlideApp.with(mContext).asBitmap().load(url).apply(options).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        float ratio = (float) resource.getWidth() / resource.getHeight();
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(content);
                        constraintSet.setDimensionRatio(coverIv.getId(), ratio + "");
                        constraintSet.applyTo(content);
                        coverIv.setImageBitmap(resource);
                    }
                });
            }
            break;
            case Module2SectionEntity.GANK: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                TextView tvTitle = helper.getView(R.id.tv_title);
                TextView tvType = helper.getView(R.id.tv_type);
                TextView tvDate = helper.getView(R.id.tv_date);

                String title = "", type = "", date = "";
                GankDto.GankBean gankBean = item.getGankBean();
                if (null != gankBean) {
                    title = gankBean.getDesc();
                    type = gankBean.getType();
                    String publishedAt = gankBean.getPublishedAt();
                    date = TimeUtil.getDateString(publishedAt, TimeUtil.ISO8601, TimeUtil.DAY_ONE);
                }
                tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
                tvType.setText(TextUtils.isEmpty(type) ? "" : type);
                tvDate.setText(TextUtils.isEmpty(date) ? "" : date);
            }
            break;
        }
    }
}
