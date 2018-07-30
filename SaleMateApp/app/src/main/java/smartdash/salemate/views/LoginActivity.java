package smartdash.salemate.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smartdash.salemate.API.*;
import smartdash.salemate.API.parameters.LoginViewModel;
import smartdash.salemate.API.services.IAutenticacaoService;
import smartdash.salemate.R;
import smartdash.salemate.models.Usuario;
import smartdash.salemate.repository.UsuarioRepository;
import smartdash.salemate.util.ActivityNavigator;
import smartdash.salemate.util.AppDialogs;
import smartdash.salemate.util.ManageKeyboard;
import smartdash.salemate.util.ValidateForm;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText email, senha;
    TextInputLayout emailLayout,senhaLayout;

    private Button btnRealizaLogin;
    private Button btnRealizarCadastro;

//    private IAutenticacaoService AuthService;
    private AppDialogs appDialogs;
    private ValidateForm validateForm;
    private UsuarioRepository usuarioRepository;
    private ManageKeyboard manageKeyboard;

    //firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.init();
        this.findViews();
        this.createServiceFirebase();
        this.openButtons();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);//Firebase
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);//Firebase
        }
    }

    private void findViews(){
        email = findViewById(R.id.text_email_login);
        senha = findViewById(R.id.text_senha_login);

        emailLayout = findViewById(R.id.text_layout_email_login);
        senhaLayout = findViewById(R.id.text_layout_senha_login);
        btnRealizaLogin = findViewById(R.id.buttonLogin);
        btnRealizarCadastro = findViewById(R.id.buttonCadastro);
    }

    public void init(){
        //TODO Aguardando Backend
//        AuthService = APIClient.getAutenticacaoService(this);
        appDialogs = new AppDialogs(LoginActivity.this);
        validateForm = new ValidateForm(this);
        usuarioRepository = new UsuarioRepository(this);
        manageKeyboard = new ManageKeyboard(this);
    }

    public void openButtons(){

        btnRealizaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((email.getText().toString().equals("")) || senha.getText().toString().equals(""))return;

                autenticar();
                manageKeyboard.hideKeyboard();
            }
        });

        btnRealizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CadastroActivity.class));
            }
        });
    }

    public void autenticar() {
        if (isValidForm())return;

        btnRealizaLogin.setEnabled(false);

        String mail = email.getText().toString();
        String pass = senha.getText().toString();

        authenticateFirebaseAuth(mail,pass);//Temporario

        //TODO Aguardando backend
//        authenticateAPI(mail,pass);
    }

    private void createServiceFirebase(){
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() { //Inicializando serviço
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("Firebase", "onAuthStateChanged:signed_out");
                }

            }
        };
    }

    private void authenticateFirebaseAuth(final String email, String password){
        appDialogs.showProgress("Autenticando usuário...");

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()) {
                    appDialogs.closeProgress();
                    appDialogs.showDialogOK("", task.getException().getMessage());
                    btnRealizaLogin.setEnabled(true);
                }else {
                    appDialogs.closeProgress();
                    Usuario usuario = new Usuario();
                    usuario.Token = task.getResult().getUser().getUid();
                    usuario.Email = task.getResult().getUser().getEmail();
                    usuario.Nome = "Usuario Teste";
                    usuario.DtNascimento = "15/05/1998";
                    usuario.Telefone = "519999999";
                    usuario.CPF = "01782918035";
                    usuarioRepository.add(usuario);
                    ActivityNavigator.openActivity(getApplicationContext(), MainActivity.class,true);
                }
            }
        });
    }

    //region Método da API
    private void authenticateAPI(String mail,String pass){
        IAutenticacaoService AuthService = null;
        Call<RetornoAPI<Usuario>> req = AuthService.Login(new LoginViewModel(mail,pass));
        appDialogs.showProgress("Aguarde...");
        req.enqueue(new Callback<RetornoAPI<Usuario>>() {
            @Override
            public void onResponse(Call<RetornoAPI<Usuario>> call, Response<RetornoAPI<Usuario>> response) {
                btnRealizaLogin.setEnabled(true);
                RetornoAPI<Usuario> retorno = response.body();
                appDialogs.closeProgress();

                if(!retorno.Sucesso) {
                    appDialogs.showDialogOK("", retorno.Mensagem);
                }else {
                    Usuario usuario = retorno.Dados;
                    usuarioRepository.add(usuario);

                    ActivityNavigator.openActivity(getApplicationContext(), MainActivity.class,true);
                }
            }

            @Override
            public void onFailure(Call<RetornoAPI<Usuario>> call, Throwable t) {
                appDialogs.closeProgress();
                btnRealizaLogin.setEnabled(true);
                appDialogs.showDialogOK("Atenção","Verifique sua conexão");
                call.cancel();
            }
        });
    }
    //endregion

    private boolean isValidForm(){
        return !(validateForm.validateEmail(email, emailLayout))
                &&!(validateForm.validatePassword(senha, senhaLayout));
    }
}
