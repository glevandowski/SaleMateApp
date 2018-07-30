package smartdash.salemate.views;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import smartdash.salemate.R;
import smartdash.salemate.repository.UsuarioRepository;
import smartdash.salemate.util.ActivityNavigator;
import smartdash.salemate.util.AppDialogs;
import smartdash.salemate.util.SharedPreference;

public class MainActivity extends AppCompatActivity{

    private UsuarioRepository usuarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        this.setupNavigations();
        this.init();

        if (!isAuthenticated())
            ActivityNavigator.openActivity(this, LoginActivity.class, true);

        this.openFragment(new HomeFragment());
    }


    private void init(){
        this.usuarioRepository = new UsuarioRepository(this);
    }


    private void setupNavigations(){
        BottomNavigationView navigationBottomView = this.findViewById(R.id.navigation);
        navigationBottomView.setOnNavigationItemSelectedListener(navigationBottomListener);

    }

    private void openFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationBottomListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    openFragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    openFragment(new ProfileFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean isAuthenticated() {
        return usuarioRepository.isEmpty();
    }

    public void logout(final Activity activity) {
        final AppDialogs appDialogs = new AppDialogs(activity);
        appDialogs.showProgress(activity.getString(R.string.dialog_main_progress_logout));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appDialogs.closeProgress();
                SharedPreference.getInstance().clear(activity);
                ActivityNavigator.openActivity(activity,SplashScreenActivity.class, true);
                finish();
            }
        }, 1500);
    }
}
