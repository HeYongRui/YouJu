package com.heyongrui.base.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.base.R;
import com.heyongrui.base.widget.catloadingview.CatLoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateCheckUtil {
    public static final int REQUEST_CODE_APP_INSTALL = 1000;

    private AppCompatActivity mActivity;
    private CompositeDisposable mCompositeDisposable;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private ProgressDialog progressDialog;

    public UpdateCheckUtil(AppCompatActivity activity) {
        mActivity = activity;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void checkUpdate(@NonNull String updateCheckUrl, @NonNull String authority, @NonNull String appName) {
        //初始化网络请求配置
        OkHttpClient client = new OkHttpClient().newBuilder()
                .hostnameVerifier((hostname, session) -> true)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        try {
            //显示加载弹窗
            CatLoadingDialog catLoadingDialog = new CatLoadingDialog(mActivity);
            catLoadingDialog.setOnCancelListener(dialogInterface -> cancelTask());
            catLoadingDialog.show();
            //构建网络请求
            mCompositeDisposable.add(Observable.just(updateCheckUrl).map(requestUrl -> {
                Request request = new Request.Builder().url(requestUrl).build();
                return client.newCall(request).execute();
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(response -> {
                //网络请求返回结果解析
                if (catLoadingDialog != null && catLoadingDialog.isShowing()) {
                    catLoadingDialog.dismiss();
                }
                if (response != null && response.code() == 200) {
                    String result = new String(response.body().string().getBytes(), "UTF-8");
                    Log.i("检查更新", result);
                    if (TextUtils.isEmpty(result)) {
                        ToastUtils.showShort(R.string.update_error);
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int update_ver_code = jsonObject.getInt("update_ver_code");
                        String update_ver_name = jsonObject.getString("update_ver_name");
                        String update_url = jsonObject.getString("update_url");
                        String update_content = jsonObject.getString("update_content");

                        int appVersionCode = AppUtils.getAppVersionCode();
                        if (appVersionCode < update_ver_code) {
                            //是否强制更新
                            boolean is_force_update = jsonObject.getBoolean("ignore_able");
                            showUpdateDialog(mActivity, "V " + update_ver_name + "\n" + update_content, update_url,
                                    is_force_update, authority, appName);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.showShort(R.string.update_error);
                    }
                } else {
                    ToastUtils.showShort(R.string.update_error);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(R.string.update_error);
        }
    }

    private void showUpdateDialog(Context context, String updateContent, String updateUrl, boolean isForceUpdate,
                                  @NonNull String authority, @NonNull String app_name) {
        //展示检查更新弹窗
        new DialogUtil.DialogBuilder(context).setTitle(context.getString(R.string.update_title))
                .setContent(updateContent).setConfirmText(context.getString(R.string.update)).setContentGravity(Gravity.LEFT)
                .setCancelable(!isForceUpdate)
                .setOutsideCancelable(false)
                .setDialogClickCallback(new DialogUtil.DialogClickCallback() {
                    @Override
                    public void onYes(Dialog dialog) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0加入了动态权限
                            PermissionUtils.permission(PermissionConstants.STORAGE)
                                    .rationale(shouldRequest -> shouldRequest.again(true))
                                    .callback(new PermissionUtils.FullCallback() {
                                        @Override
                                        public void onGranted(List<String> permissionsGranted) {
                                            startDownload(updateUrl, authority, app_name);
                                        }

                                        @Override
                                        public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                                            DialogUtil.showPermissionDialog(mActivity, mActivity.getString(R.string.read_write_permisson));
                                        }
                                    }).request();
                        } else {
                            startDownload(updateUrl, authority, app_name);
                        }
                    }

                    @Override
                    public void onNo(Dialog dialog) {
                        dialog.dismiss();
                    }
                }).showRemindDialog();
    }

    private void startDownload(@NonNull String downloadUrl, @NonNull String authority, @NonNull String appName) {
        initNotification();
        mCompositeDisposable.add(Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(downloadUrl).build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    inputStream = response.body().byteStream();
                    long length = response.body().contentLength();
                    //将文件下载到file路径下
                    outputStream = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/download/" + mActivity.getString(R.string.app_name) + ".apk");
                    byte data[] = new byte[1024];
                    emitter.onNext(0);
                    long total = 0;
                    int count;
                    int progress, progress2 = 0;
                    while ((count = inputStream.read(data)) != -1) {
                        total += count;
                        // 返回当前实时进度
                        progress = (int) (total * 100 / length);
                        if (progress != progress2) {
                            progress2 = progress;
//                            emitter.onNext(progress2);
                            builder.setProgress(100, progress2, false);
                            builder.setContentText(mActivity.getString(R.string.download_progress, progress2));
                            if (progress2 == 100) {
                                builder.setContentInfo(mActivity.getString(R.string.download_done))
                                        .setContentTitle(mActivity.getString(R.string.download_done));
                            }
                            notificationManager.notify(0x3, builder.build());
                            progressDialog.setProgress(progress2);
                            Log.v("startDownload: ", progress2 + "%");
                        }
                        outputStream.write(data, 0, count);
                    }
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                }
            } catch (IOException e) {
                //告诉订阅者错误信息
                emitter.onError(e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(progress -> {//onNext
            //更新通知栏的下载进度
        }, throwable -> {//onError
            cancelTask();
        }, () -> {//onComplete
            progressDialog.dismiss();
            installApp(mActivity, authority, appName);
        }));
    }

    private void initNotification() {
        //初始化通知栏
        String channelId = "channell_id1";
        String channelName = "下载通知";
        if (notificationManager == null) {
            notificationManager = (NotificationManager) mActivity.getSystemService(Activity.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT));
            }
        }
        if (builder == null) {
            builder = new NotificationCompat.Builder(mActivity, channelId);
        }
        //设置通知栏属性
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setOngoing(false)
                .setContentInfo(mActivity.getString(R.string.downloading))
                .setContentTitle(mActivity.getString(R.string.doing_download));
        //初始化下载进度条弹窗
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setTitle(R.string.downloading);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setOnCancelListener(dialogInterface -> cancelTask());
        progressDialog.show();
    }

    public void cancelTask() {
        //取消任务
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void installApp(AppCompatActivity activity, @NonNull String authority, @NonNull String app_name) {
        try {
            Intent[] intent = {new Intent(Intent.ACTION_VIEW)};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //先获取是否有安装未知来源应用的权限（适配8.0）
                boolean haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {//没有权限
                    ToastUtils.showShort(activity.getString(R.string.install_permisson));
                    startInstallPermissionSettingActivity(activity);
                    return;
                } else {//授过权，可以自动安装
                    intent[0] = buildInstallIntent(activity, intent[0], authority, app_name);
                }
            } else {//8.0以下
                intent[0] = buildInstallIntent(activity, intent[0], authority, app_name);
            }
            activity.startActivity(intent[0]);
        } catch (Exception e) {
            Log.e("UpdateCheckUtil", "installApp: 安装失败");
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null) return;
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }

    private Intent buildInstallIntent(Context context, Intent intent, @NonNull String authority, @NonNull String app_name) {
        //构建安装Intent
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/download/" + app_name + ".apk";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//适配7.0+
            File file = new File(filePath);
            Uri apkUri = FileProvider.getUriForFile(context, authority, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {//适配7.0-
            File file = new File(filePath);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }
}
