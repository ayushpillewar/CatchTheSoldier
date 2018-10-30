package com.gamoflaskcatchthesoldier.game;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gamoflaskcatchthesoldier.game.CatchTheSoldier;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication implements AddInterface {
	InterstitialAd interstitialAd;
	//private String DUMMY_ADD_ID = "ca-app-pub-3940256099942544/1033173712";
	private String ADD_ID = "ca-app-pub-9763401578858179/6641191209";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CatchTheSoldier(this), config);
		MobileAds.initialize(this,"ca-app-pub-9763401578858179~8168652027");
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(ADD_ID);

		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.build();
		interstitialAd.loadAd(ad);

		interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

		if(interstitialAd.isLoaded()){
			interstitialAd.show();
		}else{
            Log.d("ADS","still loading");
		}
	}
	@Override
    protected  void onPause(){
	    super.onPause();
	    if(interstitialAd.isLoaded()){
	        interstitialAd.show();
        }
    }
    public void showAdd(){
	    if(interstitialAd.isLoaded()){
	        interstitialAd.show();
        }
    }

}
