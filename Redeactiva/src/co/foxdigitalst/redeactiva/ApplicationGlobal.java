package co.foxdigitalst.redeactiva;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class ApplicationGlobal extends Application {
	private static ApplicationGlobal instance = new ApplicationGlobal();

    public ApplicationGlobal() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, "zBwe2MB38Zsn3F2zRL29NYJiXtalKZ8rsB1IEd0j", "5YqqoUsMY96M9d8OTeuC9nY19LChMKqRgkI35IF0");
		
		PushService.setDefaultPushCallback(this, Splash.class);
        //PushService.subscribe(this, "EventisiteAndroid", Splash.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
	}
		
}
