package vinay.gaba.snappy;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by vgaba on 2/24/2015.
 */
public class SnappyApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "key", "key");



    }

}
