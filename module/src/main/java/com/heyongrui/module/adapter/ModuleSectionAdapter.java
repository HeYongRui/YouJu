package com.heyongrui.module.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heyongrui.base.glide.transformations.RoundedCornersTransformation;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.module.R;
import com.heyongrui.module.data.dto.GarbageCardBean;
import com.heyongrui.module.data.dto.KaiYanDataBean;
import com.heyongrui.module.data.dto.KaiYanHeaderBean;
import com.heyongrui.module.data.dto.KaiYanItemBean;

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
        }
    }
}
