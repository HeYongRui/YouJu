package com.heyongrui.module.kaiyan.view;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.KaiYanDto;
import com.heyongrui.module.data.dto.KaiYanItemBean;
import com.heyongrui.module.kaiyan.contract.KaiYanContract;
import com.heyongrui.module.kaiyan.presenter.KaiYanPresenter;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = ConfigConstants.PATH_KAIYAN_LIST)
public class KaiYanListActivity extends BaseActivity<KaiYanContract.Presenter> implements KaiYanContract.View {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ModuleSectionAdapter mModuleAdapter;

    @Autowired(name = "type")
    int mType;//0-发现 1-推荐 2-日报 3-相关推荐

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kaiyan_list;
    }

    @Override
    protected KaiYanContract.Presenter setPresenter() {
        return new KaiYanPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);
        StoreHouseHeader storeHouseHeader = findViewById(R.id.storeHouseHeader);
        initSwipeRefresh(refreshLayout, storeHouseHeader);
        refreshLayout.autoRefresh();

        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            }
        }, R.id.iv_back);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        mModuleAdapter = new ModuleSectionAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mModuleAdapter.bindToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(this));
        mModuleAdapter.setOnItemClickListener((adapter, view, position) -> {
            KaiYanItemBean kaiYanItemBean = mModuleAdapter.getData().get(position).getKaiYanItemBean();
            ARouter.getInstance().build(ConfigConstants.PATH_KAIYAN_DETAIL).withParcelable("parcel", kaiYanItemBean).navigation();
        });
    }

    private void initSwipeRefresh(SmartRefreshLayout smartRefreshLayout, StoreHouseHeader storeHouseHeader) {
        storeHouseHeader.setTextColor(ContextCompat.getColor(this, R.color.white));
//        storeHouseHeader.setPrimaryColors(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        smartRefreshLayout.setPrimaryColors(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        smartRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        smartRefreshLayout.setEnableNestedScroll(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            requestData();
        });
    }

    private void requestData() {
        if (mPresenter == null) return;
        switch (mType) {
            case 0://发现
                mPresenter.getDiscovery();
                break;
            case 1://推荐
                mPresenter.getRecommend();
                break;
            case 2://日报
                mPresenter.getDaily();
                break;
            case 3://相关推荐
//                mPresenter.getRelatedRecommend(162833);
                break;
            default:
                break;
        }
    }

    private void refreshFinish() {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getKaiYanSuccess(KaiYanDto kaiYanDto) {
        refreshFinish();
        updateData(kaiYanDto);
    }

    @Override
    public void requestFail(int errorCode, String errorMsg) {
        refreshFinish();
        ToastUtils.showShort(errorMsg);
    }

    private void updateData(KaiYanDto kaiYanDto) {
        List<ModuleSectionEntity> newDataList = new ArrayList<>();
        if (kaiYanDto != null) {
            List<KaiYanItemBean> itemList = kaiYanDto.getItemList();
            if (itemList != null) {
                for (KaiYanItemBean kaiYanItemBean : itemList) {
                    String type = kaiYanItemBean.getType();
                    if (TextUtils.equals("videoSmallCard", type)) {
                        newDataList.add(new ModuleSectionEntity(ModuleSectionEntity.KAIYAN_ONE, kaiYanItemBean));
                    } else if (TextUtils.equals("followCard", type)) {
                        newDataList.add(new ModuleSectionEntity(ModuleSectionEntity.KAIYAN_TWO, kaiYanItemBean));
                    }
                }
            }
        }
        mModuleAdapter.replaceData(newDataList);
        recyclerView.scrollToPosition(0);
    }
}
