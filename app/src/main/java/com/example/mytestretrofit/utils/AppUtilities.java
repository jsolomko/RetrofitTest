package com.example.mytestretrofit.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.mytestretrofit.ui.MainActivity;

public class AppUtilities {
    public static void webLink(Activity activity) {
        updateLink(activity, "http://гб4сочи.рф/%D0%BF%D0%BB%D0%B0%D1%82%D0%BD%D1%8B%D0%B5-%D1%83%D1%81%D0%BB%D1%83%D0%B3%D0%B8/%D0%BE%D1%84%D1%82%D0%B0%D0%BB%D1%8C%D0%BC%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8%D1%8F0");
    }
    private static void updateLink(Activity activity, String text){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager packageManager = activity.getPackageManager();
        if (packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            activity.startActivity(intent);
        }
    }

}
