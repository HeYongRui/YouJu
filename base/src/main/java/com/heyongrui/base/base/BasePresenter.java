/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.heyongrui.base.base;

import android.content.Context;

import com.heyongrui.base.assist.RxManager;

public abstract class BasePresenter<T extends BaseView> {

    protected Context mContext;
    protected RxManager mRxManager;
    protected T mView;

    public void attchView(Context context, T view) {
        mContext = context;
        mRxManager = new RxManager();
        this.mView = view;
    }

    public void detachView() {
        mRxManager.clear();
    }
}
