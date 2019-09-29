package com.heyongrui.module;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.AppData;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.dagger.DaggerModuleComponent;
import com.heyongrui.module.dagger.ModuleModule;
import com.heyongrui.module.data.dto.MenuCardDto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ModuleFragment extends BaseFragment {

    @Inject
    AppData mAppData;

    public static ModuleFragment getInstance() {
        ModuleFragment fragment = new ModuleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.ic_news);
        bundle.putInt("tabTitleId", R.string.news);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module;
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerModuleComponent
                .builder()
                .appComponent(getAppComponent())
                .moduleModule(new ModuleModule(this))
                .build()
                .inject(this);
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
                case 3://诗词
                    ARouter.getInstance().build(ConfigConstants.PATH_POETRY).navigation();
                    break;
                case 4://开眼
                    ARouter.getInstance().build(ConfigConstants.PATH_KAIYAN_LIST).withInt("type", 1).navigation();
                    break;
                case 5://电影
                    ARouter.getInstance().build(ConfigConstants.PATH_DOUBAN).navigation();
                    break;
                case 6://知乎日报
                    ARouter.getInstance().build(ConfigConstants.PATH_ZHIHU_DAILY_NEWS).navigation();
                    break;
                case 7://好奇心日报
                    ARouter.getInstance().build(ConfigConstants.PATH_Q_DAILY).navigation();
                    break;
            }
        });
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.tea), R.drawable.ic_tea, 1)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.hitokoto_title), R.drawable.ic_yiyan, 2)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.poetry), R.drawable.ic_poetry, 3)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.kaiyan), R.drawable.ic_kaiyan, 4)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.movie), R.drawable.ic_movie, 5)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.zhihu_daily), R.drawable.ic_zhihu, 6)));
        dataList.add(new ModuleSectionEntity(ModuleSectionEntity.MENU_CARD, new MenuCardDto(getString(R.string.qdaily), R.drawable.ic_qdaily, 7)));
        monoAdapter.replaceData(dataList);
    }
}
