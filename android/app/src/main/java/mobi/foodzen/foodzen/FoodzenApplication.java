package mobi.foodzen.foodzen;

import android.app.Application;
import android.content.Context;

/**
 * Created by artur.egiazarov on 27.09.2016.
 */

public class FoodzenApplication extends Application {

    private static Context sApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplicationContext = getApplicationContext();
    }

    public static Context getCurrentApplicationContext() {
        return sApplicationContext;
    }
}
