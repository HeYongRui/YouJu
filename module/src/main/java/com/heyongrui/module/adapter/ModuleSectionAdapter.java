package com.heyongrui.module.adapter;

import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heyongrui.module.R;

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
        addItemType(ModuleSectionEntity.KAIYAN, R.layout.recycle_item_kaiyan);
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
            case ModuleSectionEntity.KAIYAN: {

            }
            break;
        }
    }
}
