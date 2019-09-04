package com.heyongrui.module.textword.view;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.assist.RxManager;
import com.heyongrui.base.base.BaseActivity;
import com.heyongrui.base.provider.IIflytekProvider;
import com.heyongrui.base.utils.DialogUtil;
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

    private RxManager mRxManager = new RxManager();
    private IIflytekProvider mIiflytekProvider;

    @Inject
    TextService mTextService;

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
        } else if (id == R.id.iv_voice) {
            performSpeechAnalysis();
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
        //防止进入页面自动弹起键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //初始化View
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(DrawableUtil.tintDrawable(this, R.drawable.ic_back, Color.WHITE));
        View bgInput = findViewById(R.id.bg_input);
        ShapeDrawable borderEnableBg = new DrawableUtil.DrawableBuilder(this).setRingOutRadius(22)
                .setRingInnerRadius(20).setRingSpaceOutAndInner(2)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)).createRingDrawable();
        ViewCompat.setBackground(bgInput, borderEnableBg);
        etInput = findViewById(R.id.et_input);
        initEdit(etInput);
        ImageView ivVoice = findViewById(R.id.iv_voice);
        ivVoice.setImageDrawable(DrawableUtil.tintDrawable(ivVoice.getDrawable(), ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        ivClear = findViewById(R.id.iv_clear);
        ivClear.setImageDrawable(DrawableUtil.tintDrawable(ivClear.getDrawable(), ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        Button btnChat = findViewById(R.id.btn_chat);
        tvContent = findViewById(R.id.tv_content);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        //初始化语音识别服务
        boolean isHasIflytekComponent = checkIsHasPath(ConfigConstants.PATH_VOICE_DICATION_PROVIDER);
        ivVoice.setVisibility(isHasIflytekComponent ? View.VISIBLE : View.GONE);
        if (isHasIflytekComponent) {
            Object navigation = ARouter.getInstance().build(ConfigConstants.PATH_VOICE_DICATION_PROVIDER).navigation();
            if (null != navigation && navigation instanceof IIflytekProvider) {
                mIiflytekProvider = (IIflytekProvider) navigation;
                mIiflytekProvider.initialize(this);
            }
        }

        //初始化点击监听器
        addOnClickListeners(this, ivBack, ivVoice, ivClear, btnChat);
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

    private void performSpeechAnalysis() {
        if (null != mIiflytekProvider) {
            PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE, PermissionConstants.CONTACTS, PermissionConstants.PHONE)
                    .rationale(shouldRequest -> shouldRequest.again(true))
                    .callback(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            mIiflytekProvider.startDictation((result, isSuccess) -> {
                                if (isSuccess) {//语音识别完毕，获取转换文本并执行请求
                                    if (!TextUtils.isEmpty(result)) {
                                        KeyboardUtils.hideSoftInput(etInput);
                                        etInput.setText(result);
                                        etInput.setSelection(result.length());
                                        chatWithSmartRobot(result);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onDenied() {
                            DialogUtil.showPermissionDialog(SmartRobotActivity.this, getString(R.string.related_permisson));
                        }
                    }).request();
        }
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

    private boolean checkIsHasPath(String path) {//检查是否集成了此路径的组件
        try {
            String group = path.substring(1, path.indexOf("/", 1));
            LogisticsCenter.completion(new Postcard(path, group));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (null != mRxManager) {
            mRxManager.clear();
        }
        if (null != mIiflytekProvider) {
            mIiflytekProvider.release();
        }
        super.onDestroy();
    }
}
