package com.cml.second.app.baby.utils;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by cmlBeliever on 2016/2/25.
 */
public class GenerateGifUtils {
    private static final String TAG = GenerateGifUtils.class.getSimpleName();

    static {
        System.loadLibrary("gifflen");
    }

    /**
     * Init the gif file
     *
     * @param gifName    name
     * @param w          width
     * @param h          height
     * @param numColors  colors
     * @param quality
     * @param frameDelay times
     * @return
     */
    public native int Init(String gifName, int w, int h, int numColors, int quality,
                           int frameDelay);


    public native void Close();

    public native int AddFrame(int[] pixels);

    /**
     * encode the bitmaps to gif
     *
     * @param fileName
     * @param bitmaps
     * @param delay
     */
    public void Encode(String fileName, Bitmap[] bitmaps, int delay) {
        if (bitmaps == null || bitmaps.length == 0) {
            throw new NullPointerException("Bitmaps should have content!!!");

        }
        int width = Math.min(bitmaps[0].getWidth(), bitmaps[1].getWidth());
        int height = Math.min(bitmaps[0].getHeight(), bitmaps[1].getHeight());

        //生成gif图片
        if (Init(fileName, width, height, 256, 100, delay) != 0) {
            Log.e(TAG, "GifUtil init failed");
            return;
        }

        //将图片写入gif
        for (Bitmap bp : bitmaps) {

            int pixels[] = new int[width * height];

            bp.getPixels(pixels, 0, width, 0, 0, width, height);
            AddFrame(pixels);
        }

        Close();

    }
}
