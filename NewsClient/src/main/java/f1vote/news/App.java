package f1vote.news;

import android.app.Application;
import android.content.Context;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by markopolo on 07/07/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = null;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helvetica_CE_Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
