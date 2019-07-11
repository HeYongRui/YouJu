package com.heyongrui.module.mono.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.utils.TimeUtil;
import com.heyongrui.base.widget.imagewatcher.ImageWatcherHelper;
import com.heyongrui.base.widget.itemdecoration.RecycleViewItemDecoration;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.data.dto.MonoTeaDto;
import com.heyongrui.module.mono.contract.MonoTeaContract;
import com.heyongrui.module.mono.presenter.MonoTeaPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(path = ConfigConstants.PATH_MONO_TEA)
public class MonoTeaActivity extends BaseActivity<MonoTeaContract.Presenter> implements MonoTeaContract.View {

    ConstraintLayout toolBar;
    ImageView calendarIv;
    ImageView bgIv;
    TextView headTv;
    TextView summaryTv;

    private ModuleSectionAdapter mMonoAdapter;
    public ImageWatcherHelper mIwHelper;
    private int mScrollY;

    @Autowired(name = "source_type")
    int mSourceType;
    @Autowired(name = "id")
    int id;
    @Autowired(name = "kind")
    int mKind;
    @Autowired(name = "release_date")
    String mReleaseDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mono_tea;
    }

    @Override
    protected MonoTeaContract.Presenter setPresenter() {
        return new MonoTeaPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        mIwHelper = mPresenter.getImageWatcher();
        initData();
    }

    private void initView() {
        toolBar = findViewById(R.id.tool_bar);
        ImageView backIv = findViewById(R.id.iv_back);
        backIv.setImageDrawable(DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background)));
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);
        calendarIv = findViewById(R.id.iv_calendar);
        GradientDrawable normalDrawable = new DrawableUtil.DrawableBuilder(this).setColor(Color.parseColor("#44000000")).setGradientShapeType(GradientDrawable.OVAL).createGradientDrawable();
        GradientDrawable pressDrawable = new DrawableUtil.DrawableBuilder(this).setColor(Color.parseColor("#9c000000")).setGradientShapeType(GradientDrawable.OVAL).createGradientDrawable();
        StateListDrawable stateListDrawable = new DrawableUtil.DrawableBuilder(this).setStateListNormalDrawable(normalDrawable).setStateListPressedDrawable(pressDrawable).createStateListDrawable();
        ViewCompat.setBackground(calendarIv, stateListDrawable);
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_calendar) {
                ARouter.getInstance().build(ConfigConstants.PATH_MONO_TEA_HISTORY_DATE).navigation();
            } else if (id == R.id.iv_back) {
                onBackPressedSupport();
            }
        }, R.id.iv_back, R.id.iv_calendar);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        mMonoAdapter = new ModuleSectionAdapter(data);
        View headView = getLayoutInflater().inflate(R.layout.header_monotea, null);
        bgIv = headView.findViewById(R.id.bg_iv);
        headTv = headView.findViewById(R.id.head_tv);
        summaryTv = headView.findViewById(R.id.summary_tv);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/hanyizhuzi.ttf");
        headTv.setTypeface(typeFace);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TimeUtil.isPM() ? getString(R.string.afternoon_tea) : getString(R.string.morning_tea));
        stringBuffer.append(TimeUtil.getDateString(new Date(), TimeUtil.DAY_TWO));
        headTv.setText(stringBuffer);
        mMonoAdapter.setHeaderView(headView);
        mMonoAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mMonoAdapter.bindToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecycleViewItemDecoration(this, ConvertUtils.dp2px(1)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;
                float alpha = 0;
                if (mScrollY <= 0) {//设置标题栏和状态栏的背景颜色全透明
                    alpha = 0;
                } else if (mScrollY > 0 && mScrollY <= bgIv.getHeight()) {//根据滑动距离动态设置渐变色
                    float scale = (float) mScrollY / bgIv.getHeight();
                    alpha = (255 * scale);
                } else {
                    alpha = 255;
                }
                toolBar.getBackground().mutate().setAlpha((int) alpha);
            }
        });
        mMonoAdapter.setOnItemClickListener((adapter, view, position) -> {
            MonoTeaDto.EntityListBean entityListBean = ((ModuleSectionAdapter) adapter).getItem(position).getEntityListBean();
            if (entityListBean == null) return;
            MonoTeaDto.EntityListBean.MeowBean meow = entityListBean.getMeow();
            if (meow == null) return;
            String rec_url = meow.getRec_url();
            if (TextUtils.isEmpty(rec_url)) return;
            ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", rec_url).navigation();
        });
        mMonoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.category_tv) {
                MonoTeaDto.EntityListBean entityListBean = mMonoAdapter.getData().get(position).getEntityListBean();
                if (entityListBean == null) return;
                MonoTeaDto.EntityListBean.MeowBean meow = entityListBean.getMeow();
                if (meow == null) return;
                MonoTeaDto.EntityListBean.MeowBean.GroupBean group = meow.getGroup();
                if (group == null) return;
                ARouter.getInstance().build(ConfigConstants.PATH_MONO_CATEGORY)
                        .withInt("category_id", group.getCategory_id())
                        .withString("category_name", group.getCategory())
                        .navigation();
            }
        });
    }

    private void initData() {
        if (mSourceType == 0) {//外层进入，查看今日数据
            calendarIv.setVisibility(View.VISIBLE);
            mPresenter.getTea();
        } else if (mSourceType == 1) {//日历进入，查看历史数据
            calendarIv.setVisibility(View.GONE);
            mPresenter.getHistoryTea(id);
        }
    }

    @Override
    public void getTeaSuccess(Object object) {
        if (object == null) return;
        if (object instanceof MonoTeaDto) {
            MonoTeaDto.TeaBean morningTea = ((MonoTeaDto) object).getMorning_tea();
            MonoTeaDto.TeaBean afternoonTea = ((MonoTeaDto) object).getAfternoon_tea();
            //根据时间状态和内容更新头布局
            StringBuffer stringBuffer = new StringBuffer();
            if (TimeUtil.isPM()) {
                if (afternoonTea == null) {
                    afternoonTea = morningTea;
                    stringBuffer.append(getString(R.string.morning_tea));
                } else {
                    stringBuffer.append(getString(R.string.afternoon_tea));
                }
            } else {
                stringBuffer.append(getString(R.string.morning_tea));
            }
            stringBuffer.append(TimeUtil.getDateString(new Date(), TimeUtil.DAY_TWO));
            headTv.setText(stringBuffer);
            //添加内容列表
            addData(TimeUtil.isPM() ? afternoonTea : morningTea, new Date());
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(mKind == 0 ? getString(R.string.morning_tea) : getString(R.string.afternoon_tea));
            stringBuffer.append(TimeUtil.getDateString(TimeUtil.getDate(mReleaseDate, TimeUtil.DAY_ONE), TimeUtil.DAY_TWO));
            headTv.setText(stringBuffer);
            try {
                Gson gson = new Gson();
                String resultString = gson.toJson(object);
                JsonObject jsonObject = new JsonParser().parse(resultString).getAsJsonObject();
                boolean isHaveTea = jsonObject.has("tea");
                if (isHaveTea) {
                    JsonElement tea = jsonObject.get("tea");
                    MonoTeaDto.TeaBean teaBean = gson.fromJson(tea, MonoTeaDto.TeaBean.class);
                    addData(teaBean, TimeUtil.getDate(mReleaseDate, TimeUtil.DAY_ONE));
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void addData(MonoTeaDto.TeaBean teaBean, Date teaDate) {
        if (teaBean == null) return;
        GlideUtil.loadImage(MonoTeaActivity.this, teaBean.getBg_img_url(), bgIv, null);
        List<MonoTeaDto.EntityListBean> entityListBeans = teaBean.getEntity_list();
        if (entityListBeans == null || entityListBeans.isEmpty()) return;
        //设置头部概要信息
        StringBuffer stringBuffer = new StringBuffer();
        String[] stringArray = getResources().getStringArray(R.array.weekdays);
        stringBuffer.append(stringArray[TimeUtil.getWeek(teaDate)]);
        stringBuffer.append(getString(R.string.few_content, entityListBeans.size()));
        stringBuffer.append(teaBean.getRead_time());
        summaryTv.setText(stringBuffer);
        //添加列表数据
        mMonoAdapter.setNewData(null);
        for (MonoTeaDto.EntityListBean entityListBean : entityListBeans) {
            MonoTeaDto.EntityListBean.MeowBean meow = entityListBean.getMeow();
            if (meow == null) continue;
            List<MonoTeaDto.EntityListBean.MeowBean.ThumbBean> pics = meow.getPics();
            if (pics == null || pics.isEmpty()) {
                mMonoAdapter.addData(new ModuleSectionEntity(ModuleSectionEntity.TEA_NORMAL, entityListBean));
            } else {
                mMonoAdapter.addData(new ModuleSectionEntity(ModuleSectionEntity.TEA_NINE_GRID, entityListBean));
            }
        }
    }

    @Override
    public void getTeaFail(int errorCode, String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void onBackPressedSupport() {
        if (mIwHelper == null) {
            super.onBackPressedSupport();
        } else {
            if (!mIwHelper.handleBackPressed()) {
                super.onBackPressedSupport();
            }
        }
    }
}
