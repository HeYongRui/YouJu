package com.heyongrui.main.mob.contract;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.heyongrui.base.base.BasePresenter;
import com.heyongrui.base.base.BaseView;
import com.heyongrui.main.adapter.HomeSectionAdapter;
import com.heyongrui.main.data.dto.DictionaryDto;
import com.heyongrui.main.data.dto.FlightDto;
import com.heyongrui.main.data.dto.IDCardDto;
import com.heyongrui.main.data.dto.IdiomDto;

import java.util.List;

public interface MobContract {

    interface View extends BaseView {
        void flightInfoQuerySuccess(List<FlightDto> flightDtoList);

        void idCardQuerySuccess(IDCardDto idCardDto);

        void dictionaryQuerySuccess(DictionaryDto dictionaryDto);

        void idiomQuerySuccess(IdiomDto idiomDto);

        void participleParseSuccess(List<String> stringList);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void setBorderBg(android.view.View... views);

        public abstract HomeSectionAdapter initRecyclerView(RecyclerView recyclerView, BaseQuickAdapter.OnItemClickListener listener);

        public abstract void flightInfoQuery(String start, String end, String flightNo);

        public abstract void idCardQuery(String word);

        public abstract void dictionaryQuery(String word);

        public abstract void idiomQuery(String idiomStr);

        public abstract void participleParse(String originStr);

    }
}
