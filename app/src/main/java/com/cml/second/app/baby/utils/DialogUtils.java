package com.cml.second.app.baby.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;

import com.cml.second.app.baby.R;

/**
 * Created by cmlBeliever on 2016/2/26.
 */
public class DialogUtils {

    public static final int INDEX_CAMERA = 0;
    public static final int INDEX_LOCAL_PICTURE = 1;
    public static final int INDEX_VIDEO = 2;
    public static final int INDEX_LOCAL_VIDEO = 3;

    public static void showPicker(Activity activity, String title, int contentRes, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle(title).setSingleChoiceItems(contentRes, -1, listener);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        dialog.show();
    }
}
