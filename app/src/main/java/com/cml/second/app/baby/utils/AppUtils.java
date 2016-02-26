package com.cml.second.app.baby.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by cmlBeliever on 2016/2/26.
 */
public class AppUtils {
    private static final String PATH_IMG = "data_img/";

    public static File getPicturePath() {
        File path = new File(Environment.getExternalStorageDirectory() + "/" + PATH_IMG);
        if (!path.exists()) {
            path.mkdirs();
        }
        return path;
    }
}
