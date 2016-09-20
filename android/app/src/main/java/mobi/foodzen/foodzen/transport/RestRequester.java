package mobi.foodzen.foodzen.transport;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mobi.foodzen.foodzen.prefs.RemotePreferences;

/**
 * Created by yegia on 17.09.2016.
 */

public class RestRequester {

    public static final String INSTAGRAM_PHOTOS_BY_PLACE_URL = "https://api.instagram.com/v1/locations/%s/media/recent?access_token=%s";

    private static RestRequester ourInstance;
    private Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private RestRequester(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static RestRequester getInstance(Context context) {
        if (ourInstance == null) {
            synchronized (RestRequester.class){
                if (ourInstance == null) {
                    ourInstance =  new RestRequester(context);
                }
            }
        }
        return ourInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
