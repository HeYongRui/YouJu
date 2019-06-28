package com.heyongrui.module.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.module.R;
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
        }
    }
}
