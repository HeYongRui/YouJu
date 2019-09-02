package com.heyongrui.module.textword.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.utils.DrawableUtil;
import com.heyongrui.module.R;
import com.heyongrui.module.dagger.DaggerModuleComponent;
import com.heyongrui.module.dagger.ModuleModule;
import com.heyongrui.module.data.service.TextService;
import com.heyongrui.network.configure.ResponseDisposable;

import javax.inject.Inject;

@Route(path = ConfigConstants.PATH_SMART_ROBOT)
public class SmartRobotActivity extends BaseActivity implements View.OnClickListener {

    private EditText etInput;
    private TextView tvContent;
    private ImageView ivClear;

    @Inject
    TextService mTextService;

    RxManager mRxManager;

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerModuleComponent
                .builder()
                .appComponent(getAppComponent())
                .moduleModule(new ModuleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_clear) {
            etInput.setText("");
        } else if (id == R.id.btn_chat) {
            KeyboardUtils.hideSoftInput(etInput);
            String conversationContent = etInput.getText().toString();
            if (!TextUtils.isEmpty(conversationContent)) {
                chatWithSmartRobot(conversationContent);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart_robot;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mRxManager = new RxManager();

        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(DrawableUtil.tintDrawable(this, R.drawable.ic_back, Color.WHITE));
        etInput = findViewById(R.id.et_input);
        ivClear = findViewById(R.id.iv_clear);
        Button btnChat = findViewById(R.id.btn_chat);
        tvContent = findViewById(R.id.tv_content);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        addOnClickListeners(this, ivBack, ivClear, btnChat);

        initEdit(etInput);
    }

    private void initEdit(EditText editText) {
        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardUtils.hideSoftInput(editText);
                String conversationContent = textView.getText().toString();
                if (!TextUtils.isEmpty(conversationContent)) {
                    chatWithSmartRobot(conversationContent);
                }
                return true;
            }
            return false;
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
                String string = editable.toString();
                if (TextUtils.isEmpty(string)) {
                    ivClear.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void chatWithSmartRobot(String conversationContent) {
        mRxManager.add(mTextService.smartRobot(conversationContent)
                .subscribeWith(new ResponseDisposable<Object>(this, true) {
                    @Override
                    protected void onSuccess(Object object) {
                        tvContent.setText("");
                        String resultString = new Gson().toJson(object);
                        if (!TextUtils.isEmpty(resultString)) {
                            JsonObject jsonObject = new JsonParser().parse(resultString).getAsJsonObject();
                            if (jsonObject != null) {
                                boolean isHasContent = jsonObject.has("content");
                                if (isHasContent) {
                                    try {
                                        String content = jsonObject.get("content").getAsString();
                                        content = TextUtils.isEmpty(content) ? "" : content;
                                        content = content.replace("{br}", "\n");
                                        tvContent.setText(content);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    protected void onFailure(int errorCode, String errorMsg) {
                        ToastUtils.showShort(errorMsg);
                        tvContent.setText("");
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        if (null != mRxManager) {
            mRxManager.clear();
        }
        super.onDestroy();
    }
}
