package com.example.quizmillionaire.config;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Collections;
import java.util.List;

public class AdMobConfiguration {
    private static final String DEVICE_ID = "B33C81ED35A25990C8EC1268B686C1F0";

    public static void configureAdMob(Context context, AdView adView) {
        MobileAds.initialize(context, initializationStatus -> {});
        List<String> testDeviceIds = Collections.singletonList(DEVICE_ID);
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
    }
}
