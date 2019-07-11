package com.heyongrui.module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heyongrui.base.glide.GlideApp;
import com.heyongrui.base.glide.transformations.RoundedCornersTransformation;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.utils.TimeUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.base.widget.ninegridimageview.NineGridLayout;
import com.heyongrui.base.widget.ninegridimageview.NineGridViewHolder;
import com.heyongrui.base.widget.ninegridimageview.RatioImageView;
import com.heyongrui.module.R;
import com.heyongrui.module.data.dto.GarbageCardBean;
import com.heyongrui.module.data.dto.KaiYanDataBean;
import com.heyongrui.module.data.dto.KaiYanHeaderBean;
import com.heyongrui.module.data.dto.KaiYanItemBean;
import com.heyongrui.module.data.dto.MenuCardDto;
import com.heyongrui.module.data.dto.MonoCategoryDto;
import com.heyongrui.module.data.dto.MonoHistoryTeaDateDto;
import com.heyongrui.module.data.dto.MonoTeaDto;
import com.heyongrui.module.mono.view.MonoTeaActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * lambert
 * 2019/6/25 18:17
 */
public class ModuleSectionAdapter extends BaseSectionMultiItemQuickAdapter<ModuleSectionEntity, BaseViewHolder> {

    public ModuleSectionAdapter(List<ModuleSectionEntity> data) {
        this(0, data);
    }

    public ModuleSectionAdapter(int sectionHeadResId, List<ModuleSectionEntity> data) {
        super(sectionHeadResId, data);
        addItemType(ModuleSectionEntity.KAIYAN_ONE, R.layout.recycle_item_kaiyan_one);
        addItemType(ModuleSectionEntity.KAIYAN_TWO, R.layout.recycle_item_kaiyan_two);
        addItemType(ModuleSectionEntity.GARBAGE, android.R.layout.simple_list_item_1);
        addItemType(ModuleSectionEntity.GARBAGE_CARD, R.layout.recycle_item_garbage_category);
        addItemType(ModuleSectionEntity.MENU_CARD, R.layout.recycle_item_menu_card);
        addItemType(ModuleSectionEntity.TEA_NORMAL, R.layout.recycle_item_monotea_normal);
        addItemType(ModuleSectionEntity.TEA_NINE_GRID, R.layout.recycle_item_monotea_nine_grid);
        addItemType(ModuleSectionEntity.MONO_HISTORY_DATE, R.layout.recycle_item_history_date);
        addItemType(ModuleSectionEntity.MONO_CATEGORY, R.layout.recycle_item_monocategory);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ModuleSectionEntity item) {
//        TextView tvHeadSection = helper.getView(R.id.tv_head_section);
//        tvHeadSection.setText(item.header);
//        tvHeadSection.setVisibility(item.isShow() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void convert(BaseViewHolder helper, ModuleSectionEntity item) {
        switch (helper.getItemViewType()) {
            case ModuleSectionEntity.KAIYAN_ONE: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                ImageView ivCover = helper.getView(R.id.iv_cover);
                TextView tvDuration = helper.getView(R.id.tv_duration);
                ImageView ivAvatar = helper.getView(R.id.iv_avatar);
                TextView tvTitle = helper.getView(R.id.tv_title);
                TextView tvDesc = helper.getView(R.id.tv_desc);

                String coverUrl = "", duration = "", avatarUrl = "", title = "", desc = "";
                KaiYanItemBean kaiYanItemBean = item.getKaiYanItemBean();
                if (kaiYanItemBean != null) {
                    String type = kaiYanItemBean.getType();
                    KaiYanDataBean dataBean = kaiYanItemBean.getData();
                    if (TextUtils.equals("videoSmallCard", type)) {
                        if (dataBean != null) {
                            KaiYanDataBean.CoverBean coverBean = dataBean.getCover();
                            if (coverBean != null) {
                                coverUrl = coverBean.getFeed();
                            }
                            int seconds = dataBean.getDuration();
                            duration = String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60);
                            KaiYanDataBean.ProviderBean providerBean = dataBean.getProvider();
                            if (providerBean != null) {
                                avatarUrl = providerBean.getIcon();
                            }
                            if (TextUtils.isEmpty(avatarUrl)) {
                                KaiYanDataBean.AuthorBean authorBean = dataBean.getAuthor();
                                if (authorBean != null) {
                                    avatarUrl = authorBean.getIcon();
                                }
                            }
                            title = dataBean.getTitle();
                            desc = dataBean.getDescription();
                        }
                    }
                }
                GlideUtil.loadImage(mContext, coverUrl, ivCover, ContextCompat.getDrawable(mContext, R.drawable.placeholder));
                GlideUtil.loadCircle(mContext, avatarUrl, ivAvatar, ContextCompat.getDrawable(mContext, R.drawable.placeholder));
                tvDuration.setText(TextUtils.isEmpty(duration) ? "" : duration);
                tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
                tvDesc.setText(TextUtils.isEmpty(desc) ? "" : desc);
            }
            break;
            case ModuleSectionEntity.KAIYAN_TWO: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                ImageView ivCover = helper.getView(R.id.iv_cover);
                TextView tvDuration = helper.getView(R.id.tv_duration);
                TextView tvTitle = helper.getView(R.id.tv_title);
                TextView tvCategory = helper.getView(R.id.tv_category);

                String coverUrl = "", duration = "", title = "", desc = "";
                KaiYanItemBean kaiYanItemBean = item.getKaiYanItemBean();
                if (kaiYanItemBean != null) {
                    String type = kaiYanItemBean.getType();
                    if (TextUtils.equals("followCard", type)) {
                        KaiYanDataBean dataBean = kaiYanItemBean.getData();
                        if (dataBean != null) {
                            KaiYanItemBean contentBean = dataBean.getContent();
                            if (contentBean != null) {
                                KaiYanDataBean contentDataBean = contentBean.getData();
                                if (contentDataBean != null) {
                                    int seconds = contentDataBean.getDuration();
                                    duration = String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60);
                                    KaiYanDataBean.CoverBean contentDataCoverBean = contentDataBean.getCover();
                                    if (contentDataCoverBean != null) {
                                        coverUrl = contentDataCoverBean.getFeed();
                                    }
                                    KaiYanDataBean.AuthorBean contentDataAuthorBean = contentDataBean.getAuthor();
                                    if (contentDataAuthorBean != null) {
                                        contentDataAuthorBean.getIcon();
                                    }
                                }
                            }
                            KaiYanHeaderBean headerBean = dataBean.getHeader();
                            if (headerBean != null) {
                                title = headerBean.getTitle();
                                desc = headerBean.getDescription();
                            }
                        }
                    }
                }
                GlideUtil.loadImage(mContext, coverUrl, ivCover, ContextCompat.getDrawable(mContext, R.drawable.placeholder));
                tvDuration.setText(TextUtils.isEmpty(duration) ? "" : duration);
                tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
                tvCategory.setText(TextUtils.isEmpty(desc) ? "" : desc);
            }
            break;
            case ModuleSectionEntity.GARBAGE: {
                TextView tvGarbage = helper.getView(android.R.id.text1);
                tvGarbage.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                tvGarbage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tvGarbage.setGravity(Gravity.CENTER);
                Object object = item.getObject();
                int textColor = ContextCompat.getColor(mContext, R.color.text_color);
                if (object != null && object instanceof String) {
                    if (((String) object).contains("可回收")) {
                        textColor = Color.parseColor("#FF034884");
                    } else if (((String) object).contains("干垃圾")) {
                        textColor = Color.parseColor("#FF2E2D2B");
                    } else if (((String) object).contains("湿垃圾")) {
                        textColor = Color.parseColor("#FF664034");
                    } else if (((String) object).contains("有害垃圾")) {
                        textColor = Color.parseColor("#FFE53522");
                    }
                    tvGarbage.setText((String) object);
                    tvGarbage.setTextColor(textColor);
                }
            }
            break;
            case ModuleSectionEntity.GARBAGE_CARD: {
                ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                layoutParams.width = (int) (ScreenUtils.getScreenWidth() * 0.6);
                ImageView ivCover = helper.getView(R.id.iv_cover);
                TextView tvConcept = helper.getView(R.id.tv_concept);
                TextView tvConceptContent = helper.getView(R.id.tv_concept_content);
                TextView tvInclude = helper.getView(R.id.tv_include);
                TextView tvIncludeContent = helper.getView(R.id.tv_include_content);
                TextView tvStandard = helper.getView(R.id.tv_standard);
                TextView tvStandardContent = helper.getView(R.id.tv_standard_content);

                String iconUrl = "", concept = "", conceptContent = "", include = "", includeContent = "", standard = "", standardContent = "";
                int textColor = ContextCompat.getColor(mContext, R.color.text_color);
                GarbageCardBean garbageCardBean = item.getGarbageCardBean();
                if (garbageCardBean != null) {
                    iconUrl = garbageCardBean.getIconUrl();
                    concept = garbageCardBean.getConceptTitle();
                    conceptContent = garbageCardBean.getConceptContent();
                    include = garbageCardBean.getIncludeTitle();
                    includeContent = garbageCardBean.getIncludeContent();
                    standard = garbageCardBean.getStandardTitle();
                    standardContent = garbageCardBean.getStandardContent();
                    switch (garbageCardBean.getCategory()) {
                        case 1://可回收垃圾
                            textColor = Color.parseColor("#FF034884");
                            break;
                        case 2://干垃圾
                            textColor = Color.parseColor("#FF2E2D2B");
                            break;
                        case 3://湿垃圾
                            textColor = Color.parseColor("#FF664034");
                            break;
                        case 4://有害垃圾
                            textColor = Color.parseColor("#FFE53522");
                            break;
                    }
                }
                GlideUtil.loadRound(mContext, iconUrl, ivCover, 20, RoundedCornersTransformation.CornerType.ALL, R.drawable.placeholder);
                tvConcept.setText(concept);
                tvConceptContent.setText(conceptContent);
                tvInclude.setText(include);
                tvIncludeContent.setText(includeContent);
                tvStandard.setText(standard);
                tvStandardContent.setText(standardContent);

                tvConcept.setTextColor(textColor);
                tvConceptContent.setTextColor(textColor);
                tvInclude.setTextColor(textColor);
                tvIncludeContent.setTextColor(textColor);
                tvStandard.setTextColor(textColor);
                tvStandardContent.setTextColor(textColor);
            }
            break;
            case ModuleSectionEntity.MENU_CARD: {
                ImageView ivIcon = helper.getView(R.id.iv_icon);
                TextView tvName = helper.getView(R.id.tv_name);
                MenuCardDto menuCardDto = item.getMenuCardDto();
                if (menuCardDto == null) return;
                GlideUtil.loadImage(mContext, menuCardDto.getIcon_res(), ivIcon, null);
                tvName.setText(menuCardDto.getName());
            }
            break;
            case ModuleSectionEntity.TEA_NORMAL: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                ImageView oneAvatarIv = helper.getView(R.id.avatar_iv);
                TextView oneNameTv = helper.getView(R.id.name_tv);
                TextView oneCategoryTv = helper.getView(R.id.category_tv);
                helper.addOnClickListener(R.id.category_tv);
                ImageView oneThumbIv = helper.getView(R.id.thumb_iv);
                TextView oneTextTv = helper.getView(R.id.text_tv);
                TextView oneTitleTv = helper.getView(R.id.title_tv);
                TextView oneSubTitleTv = helper.getView(R.id.sub_title_tv);
                MonoTeaDto.EntityListBean oneEntityListBean = item.getEntityListBean();
                if (oneEntityListBean == null) return;
                MonoTeaDto.EntityListBean.MeowBean oneMeow = oneEntityListBean.getMeow();
                if (oneMeow == null) return;
                MonoTeaDto.EntityListBean.MeowBean.GroupBean oneGroup = oneMeow.getGroup();
                if (oneGroup != null) {
                    GlideUtil.loadImage(mContext, oneGroup.getLogo_url(), oneAvatarIv, null);
                    oneNameTv.setText(oneGroup.getName());
                    oneCategoryTv.setText(oneGroup.getCategory());
                    oneCategoryTv.setTextColor(UiUtil.createTextColorStateList(ContextCompat.getColor(mContext, R.color.colorAccent),
                            ContextCompat.getColor(mContext, R.color.colorPrimaryDark),
                            ContextCompat.getColor(mContext, R.color.colorAccent)));
                }
                MonoTeaDto.EntityListBean.MeowBean.ThumbBean thumb = oneMeow.getThumb();
                if (thumb != null) {
                    String raw = thumb.getRaw();
                    float aspectRatio = (float) thumb.getWidth() / thumb.getHeight();
                    ConstraintLayout itemRoot = (ConstraintLayout) helper.itemView;
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(itemRoot);
                    constraintSet.setDimensionRatio(oneThumbIv.getId(), aspectRatio + "");
                    constraintSet.applyTo(itemRoot);
                    if (raw.contains("gif")) {
                        GlideUtil.loadGif(mContext, raw, oneThumbIv, -1, null, null);
                    } else {
                        GlideUtil.loadImage(mContext, raw, oneThumbIv, null);
                    }
                }
                String oneText = oneMeow.getText();
                if (TextUtils.isEmpty(oneText)) {
                    oneTextTv.setVisibility(View.GONE);
                } else {
                    oneTextTv.setText("“" + oneText + "”");
                    oneTextTv.setVisibility(View.VISIBLE);
                }
                String oneTitle = oneMeow.getTitle();
                if (TextUtils.isEmpty(oneTitle)) {
                    oneTitleTv.setVisibility(View.GONE);
                } else {
                    oneTitleTv.setText(oneTitle);
                    oneTitleTv.setVisibility(View.VISIBLE);
                }
                String description = oneMeow.getDescription();
                if (TextUtils.isEmpty(description)) {
                    oneSubTitleTv.setVisibility(View.GONE);
                } else {
                    oneSubTitleTv.setText(description);
                    oneSubTitleTv.setVisibility(View.VISIBLE);
                }
            }
            break;
            case ModuleSectionEntity.TEA_NINE_GRID: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                ImageView twoAvatarIv = helper.getView(R.id.avatar_iv);
                TextView twoNameTv = helper.getView(R.id.name_tv);
                TextView twoCategoryTv = helper.getView(R.id.category_tv);
                helper.addOnClickListener(R.id.category_tv);
                TextView twoTitleTv = helper.getView(R.id.title_tv);
                TextView twoTextTv = helper.getView(R.id.text_tv);
                NineGridLayout twoNinegridview = helper.getView(R.id.ninegridview);

                MonoTeaDto.EntityListBean twoEntityListBean = item.getEntityListBean();
                if (twoEntityListBean == null) return;
                MonoTeaDto.EntityListBean.MeowBean twoMeow = twoEntityListBean.getMeow();
                if (twoMeow == null) return;
                MonoTeaDto.EntityListBean.MeowBean.GroupBean twoGroup = twoMeow.getGroup();
                String twoTitle = twoMeow.getTitle();
                if (TextUtils.isEmpty(twoTitle)) {
                    twoTitleTv.setVisibility(View.GONE);
                } else {
                    twoTitleTv.setText(twoTitle);
                    twoTitleTv.setVisibility(View.VISIBLE);
                }
                String twoText = twoMeow.getText();
                if (TextUtils.isEmpty(twoText)) {
                    twoTextTv.setVisibility(View.GONE);
                } else {
                    twoTextTv.setText(twoText);
                    twoTextTv.setVisibility(View.VISIBLE);
                }
                if (twoGroup != null) {
                    GlideUtil.loadImage(mContext, twoGroup.getLogo_url(), twoAvatarIv, null);
                    twoNameTv.setText(twoGroup.getName());
                    twoCategoryTv.setText(twoGroup.getCategory());
                    twoCategoryTv.setTextColor(UiUtil.createTextColorStateList(ContextCompat.getColor(mContext, R.color.colorAccent),
                            ContextCompat.getColor(mContext, R.color.colorPrimaryDark),
                            ContextCompat.getColor(mContext, R.color.colorAccent)));
                }
                List<MonoTeaDto.EntityListBean.MeowBean.ThumbBean> pics = twoMeow.getPics();
                if (pics != null && !pics.isEmpty()) {//设置九宫格
                    twoNinegridview.setData(pics, new NineGridViewHolder() {
                        @Override
                        public boolean isCustomSingleParam(Context context, RatioImageView ratioImageView, Object o, int parentWidth) {
                            if (o == null) return true;
                            if (o instanceof MonoTeaDto.EntityListBean.MeowBean.ThumbBean) {
                                String raw = ((MonoTeaDto.EntityListBean.MeowBean.ThumbBean) o).getRaw();
                                RequestOptions options = new RequestOptions()
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.placeholder_fail)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE);
                                GlideApp.with(mContext).asBitmap().load(raw).thumbnail(0.5f).apply(options).into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                        int oldW = bitmap.getWidth();
                                        int oldH = bitmap.getHeight();
                                        float old_hw_ratio = (float) oldH / oldW;
                                        int newW;
                                        int newH;
//                                        //最大宽高比限制单张图片
//                                        if (old_hw_ratio > 3f) {//h:w = 5:3
//                                            newW = parentWidth / 2;
//                                            newH = newW * 5 / 3;
//                                        } else if (oldH < oldW) {//h:w = 2:3
//                                            newW = parentWidth * 2 / 3;
//                                            newH = newW * 2 / 3;
//                                        } else {//newH:h = newW :w
//                                            newW = parentWidth / 2;
//                                            newH = oldH * newW / oldW;
//                                        }
                                        //自定义最大宽度的自适应单张图片
                                        int maxWidth = (int) (parentWidth * 0.7f);
                                        newW = oldW >= maxWidth ? maxWidth : oldW;
                                        newH = (int) (newW * old_hw_ratio);
                                        ratioImageView.setImageBitmap(bitmap);

                                        ratioImageView.setLayoutParams(new ViewGroup.LayoutParams(newW, newH));
                                        ratioImageView.layout(0, 0, newW, newH);

                                        ViewGroup.LayoutParams params = twoNinegridview.getLayoutParams();
//                                        params.width = width;
                                        params.height = newH;
                                        twoNinegridview.setLayoutParams(params);
                                    }
                                });
                            }
                            return true;
                        }

                        @Override
                        public void onBind(Context context, int position, RatioImageView ratioImageView, Object o) {
                            if (o == null) return;
                            if (o instanceof MonoTeaDto.EntityListBean.MeowBean.ThumbBean) {
                                String raw = ((MonoTeaDto.EntityListBean.MeowBean.ThumbBean) o).getRaw();
                                GlideUtil.loadThumbnail(mContext, raw, ratioImageView, 0.5f, null);
                            }
                        }

                        @Override
                        public void onItemClick(Context context, int position, RatioImageView ratioImageView, Object o) {
                            if (mContext instanceof MonoTeaActivity) {
                                ((MonoTeaActivity) mContext).mIwHelper.show(ratioImageView, twoNinegridview.getmImageViewList(), twoNinegridview.getDataList());
                            }
                        }
                    });
                }
            }
            break;
            case ModuleSectionEntity.MONO_HISTORY_DATE: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                TextView yearMonthTv = helper.getView(R.id.year_month_tv);
                TextView dayTv = helper.getView(R.id.day_tv);
                ImageView iconIv = helper.getView(R.id.icon_iv);
                TextView statusTv = helper.getView(R.id.status_tv);
                MonoHistoryTeaDateDto.RecentTeaBean recentTeaBean = item.getRecentTeaBean();
                if (recentTeaBean == null) return;
                String release_date = recentTeaBean.getRelease_date();
                Date date = TimeUtil.getDate(release_date, TimeUtil.DAY_ONE);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String[] stringArray = mContext.getResources().getStringArray(R.array.weekdays);
                yearMonthTv.setText(year + " " + String.format("%02d", month) + " " + stringArray[TimeUtil.getWeek(date)]);
                dayTv.setText(String.format("%02d", day));
                iconIv.setImageResource(recentTeaBean.getKind() == 0 ? R.drawable.sun : R.drawable.moon);
                statusTv.setText(recentTeaBean.getKind() == 0 ? R.string.morning_tea : R.string.afternoon_tea);
            }
            break;
            case ModuleSectionEntity.MONO_CATEGORY: {
                UiUtil.setOnclickFeedBack(mContext, ContextCompat.getColor(mContext, R.color.background), ContextCompat.getColor(mContext, R.color.gray), helper.itemView);
                ImageView categoryThumbIv = helper.getView(R.id.thumb_iv);
                TextView categoryMultipleTv = helper.getView(R.id.multiple_tv);
                TextView categoryTitleTv = helper.getView(R.id.title_tv);
                TextView categoryDesTv = helper.getView(R.id.des_tv);
                TextView categoryNameTv = helper.getView(R.id.name_tv);

                MonoCategoryDto.MeowsBean meowsBean = item.getMeowsBean();
                if (meowsBean != null) {
                    MonoCategoryDto.MeowBean meowBean = meowsBean.getMeow();
                    MonoCategoryDto.MeowBean.ThumbBean categoryThumb = meowBean.getThumb();
                    List<MonoCategoryDto.MeowBean.ThumbBean> images = meowBean.getImages();
                    if (categoryThumb != null) {
                        categoryMultipleTv.setVisibility(View.GONE);
                        String raw = categoryThumb.getRaw();
                        if (raw.contains("gif")) {
                            GlideUtil.loadGif(mContext, raw, categoryThumbIv, -1, null, null);
                        } else {
                            GlideUtil.loadImage(mContext, raw, categoryThumbIv, null);
                        }
                    } else {
                        if (images != null && !images.isEmpty()) {
                            MonoCategoryDto.MeowBean.ThumbBean thumbBean = images.get(0);
                            String raw = thumbBean.getRaw();
                            if (raw.contains("gif")) {
                                GlideUtil.loadGif(mContext, raw, categoryThumbIv, -1, null, null);
                            } else {
                                GlideUtil.loadImage(mContext, raw, categoryThumbIv, null);
                            }
                        }
                    }
                    if (images != null && !images.isEmpty()) {
                        categoryMultipleTv.setVisibility(View.VISIBLE);
                        categoryMultipleTv.setText(mContext.getString(R.string.pieces, images.size()));
                        Drawable drawable = DrawableUtil.tintDrawable(mContext, R.drawable.placeholder, ContextCompat.getColor(mContext, R.color.colorAccent));
                        drawable.setBounds(0, 0, 30, 30);
                        GradientDrawable gradientDrawable = new DrawableUtil.DrawableBuilder(mContext).setColor(Color.parseColor("#9c000000")).setGradientRoundRadius(30).createGradientDrawable();
                        ViewCompat.setBackground(categoryMultipleTv, gradientDrawable);
                        categoryMultipleTv.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        categoryMultipleTv.setVisibility(View.GONE);
                    }
                    categoryTitleTv.setText(meowBean.getTitle());
                    categoryDesTv.setText(meowBean.getDescription());
                    MonoCategoryDto.MeowBean.GroupBean group = meowBean.getGroup();
                    if (group != null) {
                        categoryNameTv.setText(group.getName());
                    }
                }
            }
            break;
        }
    }
}
