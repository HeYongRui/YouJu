package com.heyongrui.module.kaiyan.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.KaiYanDataBean;
import com.heyongrui.module.data.dto.KaiYanDto;
import com.heyongrui.module.data.dto.KaiYanHeaderBean;
import com.heyongrui.module.data.dto.KaiYanItemBean;
import com.heyongrui.module.kaiyan.contract.KaiYanContract;
import com.heyongrui.module.kaiyan.presenter.KaiYanPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

@Route(path = ConfigConstants.PATH_KAIYAN_DETAIL)
public class KaiYanDetailActivity extends BaseActivity<KaiYanContract.Presenter> implements KaiYanContract.View {

    private TextView tvTitle;
    private JzvdStd jzVideo;
    private RecyclerView recyclerView;

    @Autowired(name = "parcel")
    KaiYanItemBean kaiYanItemBean;

    String title;
    int id;
    private ModuleSectionAdapter mModuleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kaiyan_detail;
    }

    @Override
    protected KaiYanContract.Presenter setPresenter() {
        return new KaiYanPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle = findViewById(R.id.tv_title);
        jzVideo = findViewById(R.id.jz_video);
        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                onBackPressedSupport();
            }
        }, R.id.iv_back);
        setVideoInfo();
        if (mPresenter != null) {
            mPresenter.getRelatedRecommend(id);
        }
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

    private void setVideoInfo() {
        String coverUrl = null;
        if (kaiYanItemBean != null) {
            String type = kaiYanItemBean.getType();
            if (TextUtils.equals("videoSmallCard", type)) {
                KaiYanDataBean dataBean = kaiYanItemBean.getData();
                if (dataBean != null) {
                    id = dataBean.getId();
                    title = dataBean.getTitle();
                    KaiYanDataBean.CoverBean coverBean = dataBean.getCover();
                    if (coverBean != null) {
                        coverUrl = coverBean.getFeed();
                    }
                }
            } else if (TextUtils.equals("followCard", type)) {
                KaiYanDataBean dataBean = kaiYanItemBean.getData();
                if (dataBean != null) {
                    KaiYanHeaderBean headerBean = dataBean.getHeader();
                    if (headerBean != null) {
                        id = headerBean.getId();
                        title = headerBean.getTitle();
                    }
                    KaiYanItemBean contentBean = dataBean.getContent();
                    if (contentBean != null) {
                        KaiYanDataBean contentDataBean = contentBean.getData();
                        if (contentDataBean != null) {
                            KaiYanDataBean.CoverBean contentDataCoverBean = contentDataBean.getCover();
                            if (contentDataCoverBean != null) {
                                coverUrl = contentDataCoverBean.getFeed();
                            }
                        }
                    }
                }
            }
        }
        tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        jzVideo.setUp("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=" + id + "&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss&udid=91b8e110aadc42e3a1b7f5bbc81161a9a65e7b57&vc=492&vn=5.4.1&size=1080X1920&deviceModel=MI%204LTE&first_channel=eyepetizer_web&last_channel=eyepetizer_web&system_version_code=23&token=dddfab1908264e55"
                , TextUtils.isEmpty(title) ? "" : title, Jzvd.SCREEN_NORMAL);
        GlideUtil.loadImage(this, coverUrl, jzVideo.thumbImageView, null);
        jzVideo.startVideo();
    }

    @Override
    public void getKaiYanSuccess(KaiYanDto kaiYanDto) {
        updateData(kaiYanDto);
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

    @Override
    public void requestFail(int errorCode, String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.resetAllVideos();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Jzvd.backPress()) {
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressedSupport() {
        if (Jzvd.backPress()) return;
        super.onBackPressedSupport();
    }
}
