package smartdash.salemate.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import smartdash.salemate.R;
import smartdash.salemate.repository.UsuarioRepository;

public class ProfileFragment extends Fragment {

    private NavigationView navigationViewProfile;
    private UsuarioRepository usuarioRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        this.init();
        this.setupNavigations(view);
        this.setProfileInfos();
        return view;
    }
    private void init(){
        this.usuarioRepository = new UsuarioRepository(getActivity());
    }

    private void setupNavigations(View view){
        navigationViewProfile = view.findViewById(R.id.nav_view_profile);
        navigationViewProfile.setNavigationItemSelectedListener(navigationProfileListener);
    }

    private  NavigationView.OnNavigationItemSelectedListener navigationProfileListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_sair:
                    ((MainActivity) getActivity()).logout(getActivity());
                    return true;
            }
            return false;
        }
    };

    private void setProfileInfos() {
        View header = navigationViewProfile.getHeaderView(0);

        TextView name =  header.findViewById(R.id.text_user_nav_header);
        name.setText(usuarioRepository.getUsuario().Nome);
    }
}
