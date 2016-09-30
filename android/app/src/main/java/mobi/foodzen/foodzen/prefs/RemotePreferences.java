package mobi.foodzen.foodzen.prefs;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import mobi.foodzen.foodzen.R;

/**
 * Created by artur.egiazarov on 19.09.2016.
 */
public class RemotePreferences {

    public static final String INSTAGRAM_ACCESS_TOKEN = "instagram_access_token";

    private static RemotePreferences ourInstance;
    private boolean mInitialized;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private RemotePreferences() {
        initializeRemotePreferences();
    }

    public static RemotePreferences getInstance() {
        if (ourInstance == null) {
            synchronized (RemotePreferences.class) {
                if (ourInstance == null) {
                    ourInstance = new RemotePreferences();
                }
            }
        }
        return ourInstance;
    }

    public void forceGetRemoteConfig() {
        mInitialized = false;
    }

    public void initializeRemotePreferences() {
        if (!mInitialized) {
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            mFirebaseRemoteConfig.setDefaults(R.xml.remote_preferences);
            mFirebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mFirebaseRemoteConfig.activateFetched();
                        mInitialized = true;
                    } else {
                        initializeRemotePreferences();
                    }
                }
            });
        }
    }

    public String getInstagramAccessToken() {
        return mFirebaseRemoteConfig.getString(INSTAGRAM_ACCESS_TOKEN);
    }
}
