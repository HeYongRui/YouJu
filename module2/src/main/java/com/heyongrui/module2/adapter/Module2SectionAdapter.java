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
import com.heyongrui.module2.data.dto.LeisureReadDto;

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
        addItemType(Module2SectionEntity.LEISURE_READ, R.layout.recycle_item_leisure_read);
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
                GankDto gankDto = item.getGankDto();
                if (gankDto == null) return;
                String url = gankDto.getUrl();
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
                GankDto gankDto = item.getGankDto();
                if (null != gankDto) {
                    title = gankDto.getDesc();
                    type = gankDto.getType();
                    String publishedAt = gankDto.getPublishedAt();
                    date = TimeUtil.getDateString(publishedAt, TimeUtil.ISO8601, TimeUtil.DAY_ONE);
                }
                tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
                tvType.setText(TextUtils.isEmpty(type) ? "" : type);
                tvDate.setText(TextUtils.isEmpty(date) ? "" : date);
            }
            break;
            case Module2SectionEntity.LEISURE_READ: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                ConstraintLayout content = helper.getView(R.id.content);
                ImageView ivCover = helper.getView(R.id.iv_cover);
                TextView tvTitle = helper.getView(R.id.tv_title);

                String cover = "", title = "";
                LeisureReadDto leisureReadDto = item.getLeisureReadDto();
                if (null != leisureReadDto) {
                    cover = leisureReadDto.getCover();
                    title = leisureReadDto.getTitle();
                }

                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder_fail)
                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                GlideApp.with(mContext).asBitmap().load(cover).apply(options).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        float ratio = (float) resource.getWidth() / resource.getHeight();
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(content);
                        constraintSet.setDimensionRatio(ivCover.getId(), ratio + "");
                        constraintSet.applyTo(content);
                        ivCover.setImageBitmap(resource);
                    }
                });
                tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
            }
            break;
        }
    }
}
