package f1vote.news.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


public class MyApplication extends Application{
	private static Context context;
	private static Handler mainHandler;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

		mainHandler = new Handler();
	}
	

	public static Context getContext(){
		return context;
	}
	
	public static Handler getMainHandler(){
		return mainHandler;
	}

}
