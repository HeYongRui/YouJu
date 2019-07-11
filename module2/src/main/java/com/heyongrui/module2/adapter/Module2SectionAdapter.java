package com.heyongrui.module2.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heyongrui.base.glide.GlideApp;
import com.heyongrui.module2.R;
import com.heyongrui.module2.data.dto.WelfareDto;

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
                WelfareDto.WelfareBean welfareBean = item.getWelfareBean();
                if (welfareBean == null) return;
                String url = welfareBean.getUrl();
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
        }
    }
}
