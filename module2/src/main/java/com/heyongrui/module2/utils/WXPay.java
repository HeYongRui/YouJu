package com.heyongrui.module2.utils;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.heyongrui.module2.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class WXPay {

    public static void startWeZhi(final Context c, final View view) {
        File dir = c.getExternalFilesDir("pay_img");
        if (dir == null || dir.exists() || dir.mkdirs()) {
            File[] f = dir.listFiles();
            File[] var4 = f;
            int var5 = f.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                File file = var4[var6];
                file.delete();
            }

            String fileName = "weixin_qa.png";
            final File file = new File(dir, fileName);
            (new AsyncTask<Context, String, String>() {
                Context context;

                protected void onPreExecute() {
                    super.onPreExecute();
                }

                protected String doInBackground(Context... cx) {
                    this.context = cx[0];
                    snapShot(this.context, file, view);
                    return null;
                }

                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    startWechat(this.context);
                }
            }).execute(new Context[]{c});
        }
    }

    private static void snapShot(Context context, @NonNull File file, @NonNull View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        view.draw(canvas);
        FileOutputStream fos = null;
        boolean isSuccess = false;

        try {
            fos = new FileOutputStream(file);
            isSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
            fos.flush();
        } catch (Exception var22) {
            var22.printStackTrace();
        } finally {
            closeIO(fos);
        }

        if (isSuccess) {
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues values = new ContentValues(4);
            values.put("datetaken", System.currentTimeMillis());
            values.put("mime_type", "image/png");
            values.put("orientation", 0);
            values.put("title", "捐赠");
            values.put("description", "捐赠二维码");
            values.put("_data", file.getAbsolutePath());
            values.put("date_modified", System.currentTimeMillis() / 1000L);
            Uri url = null;

            try {
                if (Build.VERSION.SDK_INT >= 24) {
                    context.grantUriPermission(context.getPackageName(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                url = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                OutputStream imageOut = contentResolver.openOutputStream(url);

                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOut);
                } finally {
                    closeIO(imageOut);
                }

                long id = ContentUris.parseId(url);
                MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id, 1, (BitmapFactory.Options) null);
            } catch (Exception var24) {
                if (url != null) {
                    contentResolver.delete(url, (String) null, (String[]) null);
                }
            }
        }
    }

    private static void startWechat(Context c) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
        intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
        intent.setFlags(335544320);
        intent.setAction("android.intent.action.VIEW");
        if (isActivityAvailable(c, intent)) {
            c.startActivity(intent);
        } else {
            ToastUtils.showShort(R.string.wx_install_tip);
        }
    }

    private static void closeIO(Closeable target) {
        try {
            if (target != null)
                target.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isActivityAvailable(Context cxt, Intent intent) {
        PackageManager pm = cxt.getPackageManager();
        if (pm == null) {
            return false;
        }
        List<ResolveInfo> list = pm.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list != null && list.size() > 0;
    }
}
