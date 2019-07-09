package com.heyongrui.module.dailylife.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.module.R;
import com.heyongrui.module.adapter.ModuleSectionAdapter;
import com.heyongrui.module.adapter.ModuleSectionEntity;
import com.heyongrui.module.dailylife.contract.GarbageClassificationContract;
import com.heyongrui.module.dailylife.presenter.GarbageClassificationPresenter;
import com.heyongrui.module.data.dto.GarbageCardBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route(path = ConfigConstants.PATH_GARBAGE_CLASSIFY)
public class GarbageClassificationActivity extends BaseActivity<GarbageClassificationContract.Presenter> implements GarbageClassificationContract.View {

    private ModuleSectionAdapter mGarbageAdapter;
    private RecyclerView rlvGarbageCategory;
    private RecyclerView rlvGarbageQuery;
    private ImageView ivClear;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_garbage_classification;
    }

    @Override
    protected GarbageClassificationContract.Presenter setPresenter() {
        return new GarbageClassificationPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View bgInput = findViewById(R.id.bg_input);
        ShapeDrawable borderEnableBg = new DrawableUtil.DrawableBuilder(this).setRingOutRadius(22)
                .setRingInnerRadius(20).setRingSpaceOutAndInner(2)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)).createRingDrawable();
        ViewCompat.setBackground(bgInput, borderEnableBg);
        EditText etInput = findViewById(R.id.et_input);
        initEdit(etInput);
        ivClear = findViewById(R.id.iv_clear);
        Drawable drawable = DrawableUtil.tintDrawable(this, android.R.drawable.ic_menu_close_clear_cancel, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        ivClear.setImageDrawable(drawable);

        Button btnQuery = findViewById(R.id.btn_query);
        GradientDrawable normalDrawable = new DrawableUtil.DrawableBuilder(this).setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)).setGradientRoundRadius(24).createGradientDrawable();
        GradientDrawable pressDrawable = new DrawableUtil.DrawableBuilder(this).setColor(Color.parseColor("#FF2780F0")).setGradientRoundRadius(24).createGradientDrawable();
        StateListDrawable stateListDrawable = new DrawableUtil.DrawableBuilder(this).setStateListNormalDrawable(normalDrawable)
                .setStateListPressedDrawable(pressDrawable).createStateListDrawable();
        ViewCompat.setBackground(btnQuery, stateListDrawable);

        rlvGarbageCategory = findViewById(R.id.rlv_garbage_category);
        initRlvGarbageCategory(rlvGarbageCategory);

        rlvGarbageQuery = findViewById(R.id.rlv_garbage_query);
        initRlvGarbageQuery(rlvGarbageQuery);

        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            } else if (id == R.id.iv_clear) {
                etInput.setText("");
            } else if (id == R.id.btn_query) {
                if (mPresenter != null) {
                    mPresenter.queryGarbageCategory(etInput.getText().toString());
                }
            }
        }, R.id.iv_back, R.id.iv_clear, R.id.btn_query);
    }

    private void initEdit(EditText editText) {
        ViewCompat.setBackground(editText, null);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtils.hideSoftInput(editText);
                    if (mPresenter != null) {
                        mPresenter.queryGarbageCategory(textView.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String garbageStr = editable.toString();
                if (TextUtils.isEmpty(garbageStr)) {
                    rlvGarbageCategory.setVisibility(View.VISIBLE);
                    rlvGarbageQuery.setVisibility(View.GONE);
                    ivClear.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRlvGarbageCategory(RecyclerView rlvGarbageCategory) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        ModuleSectionAdapter garbageCategoryAdapter = new ModuleSectionAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rlvGarbageCategory.setLayoutManager(manager);
        rlvGarbageCategory.setNestedScrollingEnabled(false);
        garbageCategoryAdapter.bindToRecyclerView(rlvGarbageCategory);
        List<GarbageCardBean> gargabeCardListData = mPresenter.getGargabeCardListData();
        for (GarbageCardBean garbageCardBean : gargabeCardListData) {
            data.add(new ModuleSectionEntity(ModuleSectionEntity.GARBAGE_CARD, garbageCardBean));
        }
        garbageCategoryAdapter.replaceData(data);
    }

    private void initRlvGarbageQuery(RecyclerView rlvGarbageQuery) {
        List<ModuleSectionEntity> data = new ArrayList<>();
        mGarbageAdapter = new ModuleSectionAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rlvGarbageQuery.setLayoutManager(manager);
        rlvGarbageQuery.setNestedScrollingEnabled(false);
        mGarbageAdapter.bindToRecyclerView(rlvGarbageQuery);
    }

    @Override
    public void queryGarbageCategorySuccess(Object object) {
        rlvGarbageCategory.setVisibility(View.GONE);
        rlvGarbageQuery.setVisibility(View.VISIBLE);
        List<ModuleSectionEntity> newDataList = new ArrayList<>();
        //返回的json数据key为动态，故动态解析
        if (object != null) {
            String resultString = new Gson().toJson(object);
            if (!TextUtils.isEmpty(resultString)) {
                try {
                    JsonObject jsonObject = new JsonParser().parse(resultString).getAsJsonObject();
                    if (jsonObject != null) {
                        Set<String> keySet = jsonObject.keySet();
                        if (keySet != null) {
                            for (String key : keySet) {
                                JsonObject asJsonObject = jsonObject.getAsJsonObject(key);
                                boolean isHaveName = asJsonObject.has("name");
                                boolean isHaveType = asJsonObject.has("type");
                                StringBuilder builder = new StringBuilder("");
                                if (isHaveName) {
                                    String name = asJsonObject.get("name").getAsString();
                                    builder.append(name);
                                }
                                if (isHaveType) {
                                    String type = asJsonObject.get("type").getAsString();
                                    builder.append(getString(R.string.belong));
                                    builder.append(type);
                                }
                                ModuleSectionEntity entity = new ModuleSectionEntity(ModuleSectionEntity.GARBAGE, builder.toString());
                                newDataList.add(entity);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mGarbageAdapter.replaceData(newDataList);
                }
            }
        }
        mGarbageAdapter.replaceData(newDataList);
    }
}
