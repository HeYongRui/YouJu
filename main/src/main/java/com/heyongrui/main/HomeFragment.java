package com.heyongrui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.widget.numberruntextview.NumberRunningTextView;
import com.heyongrui.base.widget.tickerview.TickerUtils;
import com.heyongrui.base.widget.tickerview.TickerView;

import java.util.Random;

public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private TickerView tickerView;
    private NumberRunningTextView numberRunTv;

    private int tickerCount;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.icon_mono);
        bundle.putInt("tabTitleId", R.string.app_name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_home) {
            ARouter.getInstance().build(ConfigConstants.PATH_KOTLIN).navigation();
        } else if (id == R.id.ticker_view) {
            if (tickerCount < 5) {
                tickerView.setCharacterLists(TickerUtils.provideNumberList());
                tickerView.setText("￥" + new Random().nextInt(500) + "." + new Random().nextInt(100));
            } else if (tickerCount < 10 && tickerCount >= 5) {
                tickerView.setCharacterLists(TickerUtils.provideAlphabeticalList());
                int digits = new Random().nextInt(2) + 6;
                tickerView.setText(generateChars(new Random(), TickerUtils.provideAlphabeticalList(), digits));
            }
            if (tickerCount == 10) {
                tickerCount = 0;
            } else {
                tickerCount++;
            }
        } else if (id == R.id.number_run_tv) {
            numberRunTv.setContent(new Random().nextInt(500) + ".47");
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tickerView = mView.findViewById(R.id.ticker_view);
        numberRunTv = mView.findViewById(R.id.number_run_tv);
        TextView tvHome = mView.findViewById(R.id.tv_home);

        addOnClickListeners(this, tvHome);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private String generateChars(Random random, String list, int numDigits) {
        final char[] result = new char[numDigits];
        for (int i = 0; i < numDigits; i++) {
            result[i] = list.charAt(random.nextInt(list.length()));
        }
        return new String(result);
    }

}
