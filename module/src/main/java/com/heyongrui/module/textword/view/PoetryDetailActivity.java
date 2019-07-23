package com.heyongrui.module.textword.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.base.utils.GlideUtil;
import com.heyongrui.base.utils.UiUtil;
import com.heyongrui.module.R;
import com.heyongrui.module.data.dto.PoemDetailDto;
import com.heyongrui.module.data.service.TextService;
import com.heyongrui.network.configure.ResponseDisposable;

import java.util.Random;

@Route(path = ConfigConstants.PATH_POETRY_DETAIL)
public class PoetryDetailActivity extends BaseActivity {

    private TextView tvPoem;
    private TextService mTextService;
    private RxManager mRxManager;

    @Autowired(name = "id")
    int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poetry_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        addOnClickListeners(view -> {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();
            }
        }, R.id.iv_back);
        //随机设置背景
        ImageView ivBg = findViewById(R.id.iv_bg);
        int[] bgResIds = new int[]{R.drawable.bg_poem_1, R.drawable.bg_poem_2, R.drawable.bg_poem_3, R.drawable.bg_poem_4, R.drawable.bg_poem_5, R.drawable.bg_poem_6, R.drawable.bg_poem_7};
        GlideUtil.loadImage(this, bgResIds[new Random().nextInt(100) % 7], ivBg, null);
        //图标着色
        ImageView ivBack = findViewById(R.id.iv_back);
        Drawable drawable = DrawableUtil.tintDrawable(this, R.drawable.ic_back, ContextCompat.getColor(this, R.color.background));
        ivBack.setImageDrawable(drawable);
        //设置字体样式
        tvPoem = findViewById(R.id.tv_poem);
        UiUtil.setFontStyle(tvPoem, "fonts/font_hwzs.ttf");
        //初始化数据
        mTextService = new TextService();
        mRxManager = new RxManager();
        getPoemDetail();
    }

    public void getPoemDetail() {
        mRxManager.add(mTextService.getPoemDetail(id)
                .subscribeWith(new ResponseDisposable<PoemDetailDto>(this, true) {
                    @Override
                    protected void onSuccess(PoemDetailDto poemDetailDto) {
                        if (poemDetailDto != null) {
                            PoemDetailDto.DataBean dataBean = poemDetailDto.getData();
                            if (dataBean != null) {
                                SpannableStringBuilder stringBuilder = new SpanUtils().append(dataBean.getMingcheng())
                                        .setForegroundColor(ContextCompat.getColor(PoetryDetailActivity.this, R.color.text_color))
                                        .setFontSize(20, true)
                                        .append("\n\n[" + dataBean.getChaodai() + "]\t" + dataBean.getZuozhe())
                                        .setForegroundColor(ContextCompat.getColor(PoetryDetailActivity.this, R.color.text_color_hint))
                                        .setFontSize(18, true)
                                        .append("\n\n" + dataBean.getYuanwen())
                                        .setForegroundColor(ContextCompat.getColor(PoetryDetailActivity.this, R.color.text_color_hint))
                                        .setFontSize(18, true)
                                        .create();
                                tvPoem.setText(stringBuilder);
                            }
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        if (mRxManager != null) {
            mRxManager.clear();
        }
        mTextService = null;
        super.onDestroy();
    }
}
