package com.example.kk.buildpart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.kk.buildpart.R;
import com.example.kk.buildpart.base.BaseActivity;
import com.example.kk.buildpart.tools.IntentUtils;
import com.example.kk.buildpart.tools.WelcomeAnimatorListener;


public class WelcomeActivity extends BaseActivity {

    private ImageView loadingItem;
    private Animation welcomeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_welcome);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
//        if (!PreferManager.getUserId().isEmpty())
//        {
//            PreferManager.saveTimeStart(System.currentTimeMillis()+"");
//        }
//        if(helper ==null){
//            helper = new SQLHelper(this);
//        }
    }

    @Override
    protected void findWidgets() {
        loadingItem = findView(R.id.ivImg);
    }

    @Override
    protected void initComponent() {
//        welcomeAnimation = new AlphaAnimation(0.1f, 1.0f);
//        welcomeAnimation.setDuration(500);
//       // welcomeAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_loading);
       //welcomeAnimation.setAnimationListener(new WelcomeAnimatorListener(this));

        setHideAnimation(loadingItem,1000);

    }
    /**
     * View渐隐动画效果
     */
    public  void setHideAnimation(final View view, int duration)
    {
        if (null == view || duration < 0)
        {
            return;
        }

        if (null != welcomeAnimation)
        {
            welcomeAnimation.cancel();
        }
        // 监听动画结束的操作
        welcomeAnimation = new AlphaAnimation(1.0f, 1.0f);
        welcomeAnimation.setDuration(duration);
        welcomeAnimation.setFillAfter(true);
        welcomeAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation arg0)
            {

            }

            @Override
            public void onAnimationRepeat(Animation arg0)
            {

            }

            @Override
            public void onAnimationEnd(Animation arg0)
            {
                view.setVisibility(View.GONE);
                IntentUtils.startAty(WelcomeActivity.this, MainActivity.class);
                finish();
            }
        });
        view.startAnimation(welcomeAnimation);
    }


    @Override
    protected void excuteOther() {
        loadingItem.startAnimation(welcomeAnimation);
    }
}
