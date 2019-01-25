package com.example.kk.buildpart.tools;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.example.kk.buildpart.activity.MainActivity;
import com.example.kk.buildpart.activity.WelcomeActivity;


public class WelcomeAnimatorListener implements AnimationListener {
    private WelcomeActivity welcomeActivity;

    public WelcomeAnimatorListener(WelcomeActivity welcomeActivity) {
        this.welcomeActivity = welcomeActivity;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {

        IntentUtils.startAty(welcomeActivity, MainActivity.class);
        welcomeActivity.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
