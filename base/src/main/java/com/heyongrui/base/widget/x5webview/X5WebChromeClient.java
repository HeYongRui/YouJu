package com.heyongrui.base.widget.x5webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;

/**
 * Created by lambert on 2018/5/29.
 * 自定义X5WebChromeClient
 */

public class X5WebChromeClient extends WebChromeClient {

    private Activity mActivity;
    private FileChooseCallBack mFileChooseCallBack;

    public X5WebChromeClient(Activity activity) {
        mActivity = activity;
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
        Log.i("X5WebChromeClient", "X5文件选择android3.0以上处理" + "\tacceptType:" + acceptType);
        if (null != mFileChooseCallBack) {
            mFileChooseCallBack.fileChoose(uploadFile, null);
        }
        openFileChooseProcess(mActivity);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadFile) {
        Log.i("X5WebChromeClient", "X5文件选择android3.0以下处理");
        if (null != mFileChooseCallBack) {
            mFileChooseCallBack.fileChoose(uploadFile, null);
        }
        openFileChooseProcess(mActivity);
    }

    // For Android  > 4.1.1
    @Override
    public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
        Log.i("X5WebChromeClient", "X5文件选择android4.1.1以上处理" + "\tacceptType:" + acceptType + "\tcapture:" + capture);
        if (null != mFileChooseCallBack) {
            mFileChooseCallBack.fileChoose(uploadFile, null);
        }
        openFileChooseProcess(mActivity);
    }

    // For Android  >= 5.0
    @Override
    public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                     ValueCallback<Uri[]> uploadFiles,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        Log.i("X5WebChromeClient", "X5文件选择android5.0以上处理" + "\t" + uploadFiles.toString());
        if (null != mFileChooseCallBack) {
            mFileChooseCallBack.fileChoose(null, uploadFiles);
        }
        openFileChooseProcess(mActivity);
        return true;
    }

    public void openFileChooseProcess(Activity activity) {//打开文件选择意图(所有类型)
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(i, ""), 0);
    }

    public void setFileChooseCallBack(FileChooseCallBack fileChooseCallBack) {
        this.mFileChooseCallBack = fileChooseCallBack;
    }

    public interface FileChooseCallBack {
        void fileChoose(ValueCallback<Uri> uploadFile, ValueCallback<Uri[]> uploadFiles);
    }
}
