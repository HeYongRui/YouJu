package com.heyongrui.iflytek.provider;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.UiThread;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.heyongrui.base.assist.ConfigConstants;
import com.heyongrui.base.provider.IIflytekProvider;
import com.heyongrui.iflytek.R;
import com.heyongrui.iflytek.speech.setting.IatSettings;
import com.heyongrui.iflytek.speech.util.FucUtil;
import com.heyongrui.iflytek.speech.util.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.cloud.util.ResourceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


@Route(path = ConfigConstants.PATH_VOICE_DICATION_PROVIDER)
public class IVoiceDictationProvider implements IIflytekProvider {

    private Context mContext;
    private static String TAG = "IVoiceDictationProvider";
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private SharedPreferences mSharedPreferences;
    private Toast mToast;
    private boolean mTranslateEnable = false;
    private String mEngineType = "cloud";
    private VoiceDictationListener mVoiceDictationListener;
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void init(Context context) {
        //此方法为IProvider默认初始化回调，context为application，可能造成内存泄漏，不建议使用
    }

    @Override
    public void initialize(Context context) {
        mContext = context;
        //初始化语音听写使用云端还是本地资源
        mEngineType = SpeechConstant.TYPE_CLOUD;
//        mEngineType = SpeechConstant.TYPE_LOCAL;
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(mContext, mInitListener);
        //初始化Toast
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        mSharedPreferences = mContext.getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.i(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip(mContext.getString(R.string.init_object_failed_tip, code));
            }
        }
    };

    /**
     * 上传联系人/词表监听器。
     */
    private LexiconListener mLexiconListener = new LexiconListener() {
        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
                showTip(error.toString());
            } else {
                showTip(mContext.getString(R.string.text_upload_success));
            }
        }
    };

    /**
     * 听写无UI监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip(mContext.getString(R.string.start_talking));
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            if (mTranslateEnable && error.getErrorCode() == 14002) {
                showTip(error.getPlainDescription(true) + "\n" + mContext.getString(R.string.translate_tip));
            } else {
                showTip(error.getPlainDescription(true));
            }
            if (null != mVoiceDictationListener) {
                mVoiceDictationListener.onResult("", false);
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip(mContext.getString(R.string.end_talking));
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if (mTranslateEnable) {
                printTransResult(results);
            } else {
                String text = JsonParser.parseIatResult(results.getResultString());
                stringBuilder.append(text);
                if (isLast) {
                    if (null != mVoiceDictationListener) {
                        mVoiceDictationListener.onResult(stringBuilder.toString(), true);
                    }
                    Log.i(TAG, "听写无UI监听器结果: " + stringBuilder.toString());
                }
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip(mContext.getString(R.string.talking_voice_tip, volume));
            Log.i(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.i(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.i(TAG, "recognizer result：" + results.getResultString());
            if (mTranslateEnable) {
                printTransResult(results);
            } else {
                String text = JsonParser.parseIatResult(results.getResultString());
                stringBuilder.append(text);
                if (isLast) {
                    if (null != mVoiceDictationListener) {
                        mVoiceDictationListener.onResult(stringBuilder.toString(), true);
                    }
                    Log.i(TAG, "听写UI监听器结果: " + stringBuilder.toString());
                }
            }
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            if (mTranslateEnable && error.getErrorCode() == 14002) {
                showTip(error.getPlainDescription(true) + "\n" + mContext.getString(R.string.translate_tip));
            } else {
                showTip(error.getPlainDescription(true));
            }
            if (null != mVoiceDictationListener) {
                mVoiceDictationListener.onResult("", false);
            }
        }
    };

    /**
     * 获取联系人监听器。
     */
    private ContactManager.ContactListener mContactListener = new ContactManager.ContactListener() {

        @Override
        public void onContactQueryFinish(final String contactInfos, boolean changeFlag) {
            // 注：实际应用中除第一次上传之外，之后应该通过changeFlag判断是否需要上传，否则会造成不必要的流量.
            // 每当联系人发生变化，该接口都将会被回调，可通过ContactManager.destroy()销毁对象，解除回调。
            // if(changeFlag) {
            // 指定引擎类型
            Toast.makeText(mContext, mContext.getString(R.string.contact_info) + contactInfos, Toast.LENGTH_SHORT).show();

            mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
            int ret = mIat.updateLexicon("contact", contactInfos, mLexiconListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip(mContext.getString(R.string.text_upload_contacts_fail) + "：" + ret);
            }
        }
    };

    @UiThread
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
        // 设置引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        this.mTranslateEnable = mSharedPreferences.getBoolean(mContext.getString(R.string.pref_key_translate), false);
        if (mEngineType.equals(SpeechConstant.TYPE_LOCAL)) {
            // 设置本地识别资源
            mIat.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
        }
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable) {
            Log.i(TAG, "translate enable");
            mIat.setParameter(SpeechConstant.ASR_SCH, "1");
            mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
            mIat.setParameter(SpeechConstant.TRS_SRC, "its");
        }
        //设置语言，目前离线听写仅支持中文
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);


            if (mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "en");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "cn");
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if (mEngineType.equals(SpeechConstant.TYPE_CLOUD) && mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "cn");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "en");
            }
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    private String getResourcePath() {
        StringBuffer tempBuffer = new StringBuffer();
        //识别通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "iat/common.jet"));
        tempBuffer.append(";");
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "iat/sms_16k.jet"));
        //识别8k资源-使用8k的时候请解开注释
        return tempBuffer.toString();
    }

    private void printTransResult(RecognizerResult results) {
        String trans = JsonParser.parseTransResult(results.getResultString(), "dst");
        String oris = JsonParser.parseTransResult(results.getResultString(), "src");

        if (TextUtils.isEmpty(trans) || TextUtils.isEmpty(oris)) {
            showTip(mContext.getString(R.string.parsing_fail_translate_tip));
        } else {
            Log.i(TAG, "原始语言:\n" + oris + "\n目标语言:\n" + trans);
            if (null != mVoiceDictationListener) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("oris", oris);
                    jsonObject.put("trans", trans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mVoiceDictationListener.onResult(jsonObject.toString(), true);
            }
        }
    }

    @Override
    public void startDictation(VoiceDictationListener voiceDictationListener) {//开始听写
        mVoiceDictationListener = voiceDictationListener;
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip(mContext.getString(R.string.create_object_failed_tip));
            return;
        }
        stringBuilder.delete(0, stringBuilder.length());
        mIatResults.clear();
        // 设置参数
        setParam();
        boolean isShowDialog = mSharedPreferences.getBoolean(mContext.getString(R.string.pref_key_iat_show), true);
        if (isShowDialog) {
            showTip(mContext.getString(R.string.text_begin));
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            //设置mIatDialog隐藏下方的官方版权文字
            View textlink = mIatDialog.getWindow().getDecorView().findViewWithTag("textlink");
            if (null != textlink && textlink instanceof TextView) {
                ((TextView) textlink).setText("");
            }
        } else {
            // 不显示听写对话框
            int ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip(mContext.getString(R.string.dictation_object_failed_tip, ret));
            } else {
                showTip(mContext.getString(R.string.text_begin));
            }
        }
    }

    @Override
    public void stopDictation() {//停止听写
        mIat.stopListening();
    }

    @Override
    public void cancelDictation() {//取消听写
        mIat.cancel();
    }

    @Override
    public void uploadContact() {//上传用户联系人
        ContactManager mgr = ContactManager.createManager(mContext, mContactListener);
        mgr.asyncQueryAllContactsName();
    }

    @Override
    public void uploadUserWords() {//上传用户词表
        String contents = FucUtil.readFile(mContext, "userwords", "utf-8");
        // 指定引擎类型
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 置编码类型
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        int ret = mIat.updateLexicon("userword", contents, mLexiconListener);
        if (ret != ErrorCode.SUCCESS)
            showTip(mContext.getString(R.string.upload_hot_failed_tip, ret));
    }

    @Override
    public void release() {
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
        ContactManager.destroy();
    }
}
