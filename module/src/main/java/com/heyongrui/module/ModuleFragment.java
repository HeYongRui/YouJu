package com.heyongrui.module;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.MenuCardDto;

import java.util.ArrayList;
import java.util.List;

public class ModuleFragment extends BaseFragment {

    public static ModuleFragment getInstance() {
        ModuleFragment fragment = new ModuleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.ic_mono);
        bundle.putInt("tabTitleId", R.string.mono);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> dataList = new ArrayList<>();
        ModuleSectionAdapter monoAdapter = new ModuleSectionAdapter(dataList);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(manager);
        monoAdapter.bindToRecyclerView(recyclerView);
        monoAdapter.setOnItemClickListener((adapter, view, position) -> {
            MenuCardDto monoCardDto = monoAdapter.getData().get(position).getMenuCardDto();
            if (monoCardDto == null) return;
            switch (monoCardDto.getType()) {
                case 1://早午茶
                    ARouter.getInstance().build(ConfigConstants.PATH_MONO_TEA).navigation();
                    break;
                case 2://一言
                    ARouter.getInstance().build(ConfigConstants.PATH_HITOKOTO).navigation();
                    break;
                case 3://探索
                    break;
                case 4://音乐
                    break;
                case 5://开眼
                    ARouter.getInstance().build(ConfigConstants.PATH_KAIYAN_LIST).withInt("type", 1).navigation();
                    break;
                case 6://垃圾分类
                    ARouter.getInstance().build(ConfigConstants.PATH_GARBAGE_CLASSIFY).navigation();
                    break;
            }
        });
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.tea), R.drawable.ic_tea, 1)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.hitokoto_title), R.drawable.ic_yiyan, 2)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.explore), R.drawable.ic_explore, 3)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.music), R.drawable.ic_music_disc, 4)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.kaiyan), R.drawable.ic_kaiyan, 5)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.garbage_classification2), R.drawable.ic_ashcan, 6)));
        monoAdapter.replaceData(dataList);
    }
}
