package com.heyongrui.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ConvertUtils;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.base.BaseFragment;
import com.heyongrui.base.widget.MarqueeTextView;
import com.heyongrui.base.widget.numberruntextview.NumberRunningTextView;
import com.heyongrui.base.widget.planetball.adapter.PlanetAdapter;
import com.heyongrui.base.widget.planetball.view.PlanetBallView;
import com.heyongrui.base.widget.tickerview.TickerUtils;
import com.heyongrui.base.widget.tickerview.TickerView;
import com.heyongrui.main.data.dto.FloatingDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private TickerView tickerView;
    private NumberRunningTextView numberRunTv;

    private int tickerCount;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tabIconId", R.drawable.ic_tool);
        bundle.putInt("tabTitleId", R.string.tool);
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
        if (id == R.id.ticker_view) {
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
        PlanetBallView planetBall = mView.findViewById(R.id.planet_ball);
        initPlanetBallView(planetBall, getFloatListData());

        tickerView = mView.findViewById(R.id.ticker_view);
        numberRunTv = mView.findViewById(R.id.number_run_tv);
        addOnClickListeners(this, tickerView, numberRunTv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private void initPlanetBallView(PlanetBallView planetBallView, List<FloatingDto> floatingDtoList) {
        int dp70 = ConvertUtils.dp2px(70);
        planetBallView.setAdapter(new PlanetAdapter() {
            @Override
            public int getCount() {
                if (null == floatingDtoList || floatingDtoList.isEmpty()) {
                    return 0;
                } else {
                    return floatingDtoList.size();
                }
            }

            @Override
            public View getView(Context context, int position, ViewGroup parent) {
                String name = "";
                int iconRes = R.drawable.ic_launcher;
                Object item = getItem(position);
                if (null != item && item instanceof FloatingDto) {
                    name = ((FloatingDto) item).getName();
                    iconRes = ((FloatingDto) item).getIcon_res();
                }
                MarqueeTextView marqueeTextView = new MarqueeTextView(context);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(dp70, dp70);
                marqueeTextView.setLayoutParams(layoutParams);
                marqueeTextView.setBackgroundResource(R.drawable.bg_circle_shadow);
                marqueeTextView.setGravity(Gravity.CENTER);
                marqueeTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                marqueeTextView.setMarqueeRepeatLimit(-1);
                marqueeTextView.setSingleLine(true);
                marqueeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                marqueeTextView.setTextColor(Color.WHITE);
                marqueeTextView.setText(TextUtils.isEmpty(name) ? "" : name);
                Drawable drawable = ContextCompat.getDrawable(context, iconRes);
                drawable.setBounds(0, 0, 80, 80 * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth());
                marqueeTextView.setCompoundDrawables(null, drawable, null, null);
                marqueeTextView.setFocusable(true);
                marqueeTextView.post(() -> marqueeTextView.onWindowFocusChanged(true));
                marqueeTextView.setOnClickListener(view -> {
                    if (null != item && item instanceof FloatingDto) {
                        int type = ((FloatingDto) item).getType();
                        switch (type) {
                            case 1://分词解析
                                break;
                            case 2://航班查询
                                break;
                            case 3://身份证查询
                                break;
                            case 4://天气预报
                                break;
                            case 5://新华字典查询
                                break;
                            case 6://成语查询
                                break;
                            case 7://动作布局动画
                                ARouter.getInstance().build(ConfigConstants.PATH_KOTLIN).navigation();
                                break;
                            case 8://星球
                                ARouter.getInstance().build(ConfigConstants.PATH_PLANET_BALL).navigation();
                                break;
                            case 9://垃圾分类
                                ARouter.getInstance().build(ConfigConstants.PATH_GARBAGE_CLASSIFY).navigation();
                                break;
                            case 10://智能机器人对话
                                ARouter.getInstance().build(ConfigConstants.PATH_SMART_ROBOT).navigation();
                                break;
                            case 11://音乐搜索
                                ARouter.getInstance().build(ConfigConstants.PATH_H5).withString("h5Url", "https://ys.juan8014.cn/yin/").navigation();
                                break;
                        }
                    }
                });
                return marqueeTextView;
            }

            @Override
            public Object getItem(int position) {
                if (null == floatingDtoList || floatingDtoList.isEmpty()) {
                    return null;
                } else {
                    return floatingDtoList.get(position);
                }
            }

            @Override
            public int getPopularity(int position) {
                return position % 10;
            }

            @Override
            public void onThemeColorChanged(View view, int themeColor) {

            }
        });
    }

    private List<FloatingDto> getFloatListData() {
        List<FloatingDto> floatingDtoList = new ArrayList<>();
        floatingDtoList.add(creatFloatingDto(getString(R.string.participle_parse), R.drawable.ic_participle, 1));
        floatingDtoList.add(creatFloatingDto(getString(R.string.flight_query), R.drawable.ic_flight, 2));
        floatingDtoList.add(creatFloatingDto(getString(R.string.id_card_query), R.drawable.ic_id_card, 3));
        floatingDtoList.add(creatFloatingDto(getString(R.string.weather_forecast), R.drawable.ic_weather, 4));
        floatingDtoList.add(creatFloatingDto(getString(R.string.dictionary_query), R.drawable.ic_dictionary, 5));
        floatingDtoList.add(creatFloatingDto(getString(R.string.idiom_query), R.drawable.ic_idiom, 6));
        floatingDtoList.add(creatFloatingDto(getString(R.string.motion_layout_anim), R.drawable.ic_anim, 7));
        floatingDtoList.add(creatFloatingDto(getString(R.string.planet), R.drawable.ic_planet, 8));
        floatingDtoList.add(creatFloatingDto(getString(R.string.garbage_classify_query), R.drawable.ic_ashcan, 9));
        floatingDtoList.add(creatFloatingDto(getString(R.string.artificial_intelligence), R.drawable.ic_robot, 10));
        floatingDtoList.add(creatFloatingDto(getString(R.string.music_search), R.drawable.ic_music_disc, 11));
        return floatingDtoList;
    }

    private FloatingDto creatFloatingDto(String name, @DrawableRes int drawableRes, int type) {
        FloatingDto floatingDto = new FloatingDto();
        floatingDto.setName(name);
        floatingDto.setIcon_res(drawableRes);
        floatingDto.setType(type);
        return floatingDto;
    }

    private String generateChars(Random random, String list, int numDigits) {
        final char[] result = new char[numDigits];
        for (int i = 0; i < numDigits; i++) {
            result[i] = list.charAt(random.nextInt(list.length()));
        }
        return new String(result);
    }

}
