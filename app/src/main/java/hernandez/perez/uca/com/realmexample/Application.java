package hernandez.perez.uca.com.realmexample;

import com.tumblr.remember.Remember;

import io.realm.Realm;

/**
 * Created by LENOVO on 3/5/2018.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        Remember.init(getApplicationContext(), "hernandez.perez.uca.com.realmexample");
    }

}
