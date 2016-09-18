package mobi.foodzen.foodzen.transport;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yegia on 17.09.2016.
 */

public class RestRequester {

    private static RestRequester ourInstance;
    private Context mCtx;
    private RequestQueue mRequestQueue;

    private RestRequester(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RestRequester getInstance(Context context) {
        if (ourInstance != null) {
            return ourInstance;
        } else return new RestRequester(context);
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

    public List<String> getInstagramPhotosByPlaceId(String id) {
        JsonRequest jsonRequest =
        return new ArrayList<>();
    }


}
