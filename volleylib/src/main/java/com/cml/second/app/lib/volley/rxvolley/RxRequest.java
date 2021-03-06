package com.cml.second.app.lib.volley.rxvolley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.RequestFuture;
import com.cml.second.app.lib.volley.framework.RequestManager;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2016/2/1.
 * <p>
 * 将Volley请求封装成RxJava结构返回，注意：所有的请求都在IO线程中处理
 */
public class RxRequest {

    /**
     * 发送post请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> post(String url, Class<?> target) {
        return request(url, target, Request.Method.POST, new DefaultRetryPolicy());
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> post(String url, Class<?> target, RetryPolicy retryPolicy) {
        return request(url, target, Request.Method.POST, retryPolicy);
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> get(String url, Class<?> target) {
        return request(url, target, Request.Method.GET, new DefaultRetryPolicy());
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> get(String url, Class<?> target, RetryPolicy retryPolicy) {
        return request(url, target, Request.Method.GET, retryPolicy);
    }

    public static <T> Observable<T> request(String url, Class<?> target, int method, RetryPolicy retryPolicy) {

        final RequestFuture<T> requestFuture = RequestFuture.newFuture();

        final GsonRequest<T> request = new GsonRequest<T>(target, method, url, null, requestFuture, requestFuture);
        request.setRetryPolicy(retryPolicy);
        request.setTag(url);

        requestFuture.setRequest(request);

        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    //只在被订阅后才进行网络请求处理
                    RequestManager.getRequestQueue().add(request);
                    if (!subscriber.isUnsubscribed() && !requestFuture.isCancelled()) {
                        subscriber.onNext(requestFuture.get());
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }

        }).subscribeOn(Schedulers.io());
    }

    /**
     * 取消请求
     *
     * @param url
     */
    public static void cancel(final String url) {
        RequestManager.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request.getTag().equals(url);
            }
        });
    }
}
