package com.heyongrui.base.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IIflytekProvider extends IProvider {

    void initialize(Context context);

    void startDictation(VoiceDictationListener voiceDictationListener);

    void stopDictation();

    void cancelDictation();

    void uploadContact();

    void uploadUserWords();

    void release();

    interface VoiceDictationListener {
        void onResult(String result, boolean isSuccess);
    }
}