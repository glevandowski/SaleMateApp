package smartdash.salemate.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import smartdash.salemate.R;
import smartdash.salemate.repository.UsuarioRepository;

public class SplashScreenActivity extends Activity {

    //region PROPRIEDADES
    private SplashScreenActivity splashScreen;
    private UsuarioRepository usuarioRepository;
    private TextView splashImageViewLogo;
    private TextView splashSubText;
    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setupAnimations();
    }
    //endregion

    //region Setup Animations
    private void setupAnimations(){
        usuarioRepository = new UsuarioRepository(this);

        //Iniciar Splash
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        splashScreen = this;
        splashImageViewLogo = findViewById(R.id.app_splashscreen_logo);
        splashSubText = findViewById(R.id.app_splashscreen_logo_subtext);

        final Animation anime = AnimationUtils.loadAnimation(splashScreen, R.anim.appear);
        splashImageViewLogo.startAnimation(anime);
        splashSubText.startAnimation(anime);

        anime.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(final Animation animation) {

                final int value = (int) (1 + Math.random() * 6);
                int animationType = 0;

                switch (value) {
                    case 1:
                        animationType = R.anim.fade_out;
                        break;
                    case 2:
                        animationType = R.anim.push_down_out;
                        break;
                    case 3:
                        animationType = R.anim.disappear;
                        break;
                    case 4:
                        animationType = R.anim.slide_out_right;
                        break;
                    case 5:
                        animationType = R.anim.push_down_out;
                        break;
                    default:
                        animationType = R.anim.slide_out_right;
                        break;
                }

                final Animation slideToRightOut = AnimationUtils.loadAnimation(SplashScreenActivity.this, animationType);
                splashSubText.startAnimation(slideToRightOut);
                splashSubText.setVisibility(View.INVISIBLE);
                splashImageViewLogo.startAnimation(slideToRightOut);
                splashImageViewLogo.setVisibility(View.INVISIBLE);

                slideToRightOut.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationEnd(final Animation animation) {
                        finish();
                        openFirstActivity();
                    }

                    @Override
                    public void onAnimationRepeat(final Animation animation) { }

                    @Override
                    public void onAnimationStart(final Animation animation) { }
                });
            }

            @Override
            public void onAnimationRepeat(final Animation arg0) { }

            @Override
            public void onAnimationStart(final Animation arg0) { }
        });
    }
    //endregion

    //region Validação e intent de login
    public boolean isAuthenticated() {
        return usuarioRepository.isEmpty();
    }

    public void openFirstActivity() {
        final Intent intent = new Intent();
        Class<?> activityClass = MainActivity.class;

        if (!isAuthenticated())
            activityClass = LoginActivity.class;

        intent.setClass(splashScreen, activityClass);
        startActivity(intent);
    }
    //endregion
}

