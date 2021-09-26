package com.lorenzomiro.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //register parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("i3m7hFLDo4En7OAqeSB1eQ9IKPY5wiViYW3jud7m")
                .clientKey("Yplt4hRkSQSsJPYGsMWRnrcI1HPs04CJkkZMw5jd")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
