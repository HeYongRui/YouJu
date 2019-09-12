package com.heyongrui.main.mob.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.main.R;
import com.heyongrui.main.adapter.HomeSectionAdapter;
import com.heyongrui.main.adapter.HomeSectionEntity;
import com.heyongrui.main.data.dto.DictionaryDto;
import com.heyongrui.main.data.dto.FlightDto;
import com.heyongrui.main.data.dto.IDCardDto;
import com.heyongrui.main.data.dto.IdiomDto;
import com.heyongrui.main.mob.contract.MobContract;
import com.heyongrui.main.mob.presenter.MobPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = ConfigConstants.PATH_MOB)
public class MobActivity extends BaseActivity<MobContract.Presenter> implements MobContract.View
        , BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private TextView tvTitle;
    private EditText etStart;
    private ImageView ivSwitch;
    private EditText etEnd;
    private EditText etInput;
    private TextView tvContent;
    private RecyclerView rlvMob;

    @Autowired(name = "mobType")
    int mMobType;

    private HomeSectionAdapter mMobAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mob;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_switch) {
            String startStr = etStart.getText().toString();
            String endStr = etEnd.getText().toString();
            etStart.setText(endStr);
            etEnd.setText(startStr);
        } else if (id == R.id.btn_query) {
            query();
        }
    }

    @Override
    protected MobContract.Presenter setPresenter() {
        return new MobPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //初始化View
        ImageView ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        etStart = findViewById(R.id.et_start);
        ivSwitch = findViewById(R.id.iv_switch);
        etEnd = findViewById(R.id.et_end);
        etInput = findViewById(R.id.et_input);
        Button btnQuery = findViewById(R.id.btn_query);
        ClickUtils.applyScale(btnQuery);
        tvContent = findViewById(R.id.tv_content);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        rlvMob = findViewById(R.id.rlv_mob);

        //图标着色
        Drawable drawable = DrawableUtil.tintDrawable(this,
                R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);

        ivSwitch.setImageDrawable(DrawableUtil.tintDrawable(ivSwitch.getDrawable(),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)));

        mPresenter.setBorderBg(etStart, etEnd, etInput);

        //设置点击监听器
        addOnClickListeners(this, ivBack, ivSwitch, btnQuery);

        initViewStatus();

        //初始化适配器
        mMobAdapter = mPresenter.initRecyclerView(rlvMob, this);
    }

    private void initViewStatus() {
        //初始化标题
        String titleStr = "", startTip = "", endTip = "", inputTip = "";
        if (mMobType == 1) {//分词解析
            titleStr = getString(R.string.participle_parse);
            inputTip = getString(R.string.participle_parse_input_tip);
            etStart.setVisibility(View.GONE);
            ivSwitch.setVisibility(View.GONE);
            etEnd.setVisibility(View.GONE);
            etInput.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            rlvMob.setVisibility(View.GONE);
        } else if (mMobType == 2) {//航班查询
            titleStr = getString(R.string.flight_query);
            startTip = getString(R.string.flight_start_input_tip);
            endTip = getString(R.string.flight_end_input_tip);
            inputTip = getString(R.string.flight_number_input_tip);
            etStart.setVisibility(View.VISIBLE);
            ivSwitch.setVisibility(View.VISIBLE);
            etEnd.setVisibility(View.VISIBLE);
            etInput.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.GONE);
            rlvMob.setVisibility(View.VISIBLE);
        } else if (mMobType == 3) {//身份证查询
            titleStr = getString(R.string.id_card_query);
            inputTip = getString(R.string.id_card_input_tip);
            etStart.setVisibility(View.GONE);
            ivSwitch.setVisibility(View.GONE);
            etEnd.setVisibility(View.GONE);
            etInput.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            rlvMob.setVisibility(View.GONE);
        } else if (mMobType == 5) {//新华字典查询
            titleStr = getString(R.string.dictionary_query);
            inputTip = getString(R.string.dictionary_input_tip);
            etStart.setVisibility(View.GONE);
            ivSwitch.setVisibility(View.GONE);
            etEnd.setVisibility(View.GONE);
            etInput.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            rlvMob.setVisibility(View.GONE);
        } else if (mMobType == 6) {//成语查询
            titleStr = getString(R.string.idiom_query);
            inputTip = getString(R.string.idiom_input_tip);
            etStart.setVisibility(View.GONE);
            ivSwitch.setVisibility(View.GONE);
            etEnd.setVisibility(View.GONE);
            etInput.setVisibility(View.VISIBLE);
            tvContent.setVisibility(View.VISIBLE);
            rlvMob.setVisibility(View.GONE);
        }
        tvTitle.setText(TextUtils.isEmpty(titleStr) ? "" : titleStr);
        etStart.setHint(startTip);
        etEnd.setHint(endTip);
        etInput.setHint(inputTip);
    }

    private void query() {
        tvContent.setText("");
        mMobAdapter.replaceData(new ArrayList<>());
        if (null == mPresenter) return;
        if (mMobType == 1) {//分词解析
            String originStr = etInput.getText().toString();
            mPresenter.participleParse(originStr);
        } else if (mMobType == 2) {//航班查询
            String flightStart = etStart.getText().toString();
            String flightEnd = etEnd.getText().toString();
            String flightNumber = etInput.getText().toString();
            mPresenter.flightInfoQuery(flightStart, flightEnd, flightNumber);
        } else if (mMobType == 3) {//身份证查询
            String idCardStr = etInput.getText().toString();
            mPresenter.idCardQuery(idCardStr);
        } else if (mMobType == 5) {//新华字典查询
            String word = etInput.getText().toString();
            mPresenter.dictionaryQuery(word);
        } else if (mMobType == 6) {//成语查询
            String idiom = etInput.getText().toString();
            mPresenter.idiomQuery(idiom);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void flightInfoQuerySuccess(List<FlightDto> flightDtoList) {
        List<HomeSectionEntity> newDataList = new ArrayList<>();
        if (null != flightDtoList) {
            for (FlightDto flightDto : flightDtoList) {
                newDataList.add(new HomeSectionEntity(HomeSectionEntity.FLIGHT, flightDto));
            }
        }
        mMobAdapter.replaceData(newDataList);
    }

    @Override
    public void idCardQuerySuccess(IDCardDto idCardDto) {
        SpanUtils spanUtils = new SpanUtils();
        if (null != idCardDto) {
            spanUtils.append(getString(R.string.idcard_address, idCardDto.getArea() + "\n\n"));
            spanUtils.append(getString(R.string.idcard_birthday, idCardDto.getBirthday() + "\n\n"));
            spanUtils.append(getString(R.string.idcard_sex, idCardDto.getSex()));
        }
        SpannableStringBuilder spannableStringBuilder = spanUtils.create();
        tvContent.setText(TextUtils.isEmpty(spannableStringBuilder) ? "" : spannableStringBuilder);
    }

    @Override
    public void dictionaryQuerySuccess(DictionaryDto dictionaryDto) {
        SpanUtils spanUtils = new SpanUtils();
        if (null != dictionaryDto) {
            spanUtils.append(getString(R.string.dictionary_word, dictionaryDto.getName() + "\n\n"));
            spanUtils.append(getString(R.string.dictionary_wubi, dictionaryDto.getWubi() + "\n\n"));
            spanUtils.append(getString(R.string.dictionary_bushou, dictionaryDto.getBushou() + "\n\n"));
            spanUtils.append(getString(R.string.dictionary_bihua_without_bushou, dictionaryDto.getBihuaWithBushou() + "\n\n"));
            spanUtils.append(getString(R.string.pinyin, dictionaryDto.getPinyin() + "\n\n"));
            spanUtils.append(getString(R.string.dictionary_desc, dictionaryDto.getBrief() + "\n\n"));
            spanUtils.append(getString(R.string.dictionary_detail, dictionaryDto.getDetail()));
        }
        SpannableStringBuilder spannableStringBuilder = spanUtils.create();
        tvContent.setText(TextUtils.isEmpty(spannableStringBuilder) ? "" : spannableStringBuilder);
    }

    @Override
    public void idiomQuerySuccess(IdiomDto idiomDto) {
        SpanUtils spanUtils = new SpanUtils();
        if (null != idiomDto) {
            spanUtils.append(getString(R.string.idiom_name, idiomDto.getName() + "\n\n"));
            spanUtils.append(getString(R.string.pinyin, idiomDto.getPinyin() + "\n\n"));
            spanUtils.append(getString(R.string.idiom_pretation, idiomDto.getPretation() + "\n\n"));
            spanUtils.append(getString(R.string.idiom_from, idiomDto.getSource() + "\n\n"));
            spanUtils.append(getString(R.string.idiom_sample, idiomDto.getSample() + "\n\n"));
            spanUtils.append(getString(R.string.idiom_sample_from, idiomDto.getSampleFrom()));
        }
        SpannableStringBuilder spannableStringBuilder = spanUtils.create();
        tvContent.setText(TextUtils.isEmpty(spannableStringBuilder) ? "" : spannableStringBuilder);
    }

    @Override
    public void participleParseSuccess(List<String> stringList) {
        SpanUtils spanUtils = new SpanUtils();
        if (null != stringList) {
            for (String participle : stringList) {
                spanUtils.append(participle + "\t\t");
            }
        }
        SpannableStringBuilder spannableStringBuilder = spanUtils.create();
        tvContent.setText(TextUtils.isEmpty(spannableStringBuilder) ? "" : spannableStringBuilder);
    }
}
