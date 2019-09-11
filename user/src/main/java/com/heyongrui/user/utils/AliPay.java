package com.heyongrui.user.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.service.quicksettings.TileService;

import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.user.R;

import java.net.URISyntaxException;

public class AliPay {
    private static final String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";
    private static final String INTENT_URL_FORMAT = "intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s%3Dweb-other&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end";

    public static boolean startAlipayClient(Activity activity, String urlCode) {
        return startIntentUrl(activity, "intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s%3Dweb-other&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end".replace("{urlCode}", urlCode));
    }

    private static boolean startIntentUrl(Activity activity, String intentFullUrl) {
        try {
            Intent intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME);
            activity.startActivity(intent);
            return true;
        } catch (URISyntaxException var3) {
            var3.printStackTrace();
            return false;
        } catch (ActivityNotFoundException var4) {
            ToastUtils.showShort(R.string.ali_install_tip);
            return false;
        }
    }

    private static boolean hasInstalledAlipayClient(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.eg.android.AlipayGphone", 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static String getAlipayClientVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo("com.eg.android.AlipayGphone", 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static boolean openAlipayScan(Context context) {
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (context instanceof TileService) {
                    ((TileService) context).startActivityAndCollapse(intent);
                } else {
                    context.startActivity(intent);
                }
            } else {
                context.startActivity(intent);
            }
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static boolean openAlipayBarcode(Context context) {
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=20000056");
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (context instanceof TileService) {
                    ((TileService) context).startActivityAndCollapse(intent);
                } else {
                    context.startActivity(intent);
                }
            } else {
                context.startActivity(intent);
            }
            return true;
        } catch (Exception var3) {
            return false;
        }
    }
}
