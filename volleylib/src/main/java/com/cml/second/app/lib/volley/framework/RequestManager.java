package com.cml.second.app.lib.volley.framework;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Manager for the queue <br>
 * private void initVolley() {
 * RequestManager.init(this); <br>
 * ImageCacheManager.getInstance().init(this, this.getPackageCodePath(), <br>
 * ImageCaches.Disk.Size, ImageCaches.Disk.Format, ImageCaches.Disk.Quality, <br>
 * ImageCacheManager.CacheType.DISK); <br>
 * }
 *
 * @author Trey Robinson
 */
public class RequestManager {

    /**
     * the queue :-)
     */
    private static RequestQueue mRequestQueue;

    /**
     * Nothing to see here.
     */
    private RequestManager() {
        // no instances
    }

    /**
     * @param context application context
     */
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * @return
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }
}
