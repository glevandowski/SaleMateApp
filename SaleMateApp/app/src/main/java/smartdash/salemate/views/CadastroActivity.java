package smartdash.salemate.views;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.lang.ref.WeakReference;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smartdash.salemate.API.RetornoAPI;
import smartdash.salemate.API.parameters.CadastroViewModel;
import smartdash.salemate.API.services.ICadastroService;
import smartdash.salemate.R;
import smartdash.salemate.models.Usuario;
import smartdash.salemate.repository.UsuarioRepository;
import smartdash.salemate.util.ActivityNavigator;
import smartdash.salemate.util.AppDialogs;
import smartdash.salemate.util.BrPhoneNumberFormatter;
import smartdash.salemate.util.RegisterTextWatcher;
import smartdash.salemate.util.ValidateForm;

public class CadastroActivity extends AppCompatActivity {

    //region PROPRIEDADES
    private TextInputEditText etNome, etEmail, etCPF, etTelefone, etDtNascimento, etSenha, etConfirmarSenha;
    private TextInputLayout layoutNome, layoutEmail, layoutCPF, layoutTelefone, layoutDtNascimento, layoutSenha, layoutConfirmarSenha;
    private Button btnCadastrar;
    int day,mon,year;
    private String current = "";

//    private ICadastroService CadService; TODO aguardando backend
    private AppDialogs appDialogs;
    private ValidateForm validateForm;
    private UsuarioRepository usuarioRepository;
    private FirebaseAuth firebaseAuth;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.findInView();
        this.init();
        this.openButtons();
    }

    //region Init e findviews
    public void findInView() {
        etNome = findViewById(R.id.et_nome_completo);
        etEmail = findViewById(R.id.et_email);
        etCPF = findViewById(R.id.et_cpf);
        etTelefone = findViewById(R.id.et_telefone);
        etDtNascimento = findViewById(R.id.et_dt_nascimento);
        etSenha = findViewById(R.id.et_senha);
        etConfirmarSenha = findViewById(R.id.et_confirmar_senha);

        layoutNome = findViewById(R.id.layout_nome_completo);
        layoutEmail = findViewById(R.id.layout_email);
        layoutCPF = findViewById(R.id.layout_cpf);
        layoutTelefone = findViewById(R.id.layout_telefone);
        layoutDtNascimento = findViewById(R.id.layout_dt_nascimento);
        layoutSenha = findViewById(R.id.layout_senha);
        layoutConfirmarSenha = findViewById(R.id.layout_confirmar_senha);
        btnCadastrar = findViewById(R.id.btn_signup);

        etNome.addTextChangedListener(new RegisterTextWatcher(this,etNome,layoutNome));
        etEmail.addTextChangedListener(new RegisterTextWatcher(this, etEmail, layoutEmail));
        etCPF.addTextChangedListener(new RegisterTextWatcher(this, etCPF,layoutCPF));
        etDtNascimento.addTextChangedListener(new DateTextWatcher(etDtNascimento, layoutDtNascimento));
        etSenha.addTextChangedListener(new RegisterTextWatcher(this,etSenha, layoutSenha));

        //MASK
        etTelefone.addTextChangedListener( new BrPhoneNumberFormatter(new WeakReference<EditText>(etTelefone)));
    }

    public void init(){
        //Todo aguardando backend
//        CadService = APIClient.getInstance(CadastroActivity.this).create(ICadastroService.class);
        firebaseAuth = FirebaseAuth.getInstance();//Firebase

        appDialogs = new AppDialogs(CadastroActivity.this);
        validateForm = new ValidateForm(this);
        usuarioRepository = new UsuarioRepository(getApplicationContext());
    }
    //endregion

    private void openButtons(){
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }

    //region Registrando Cadastro
    public void register(final View v ) {
        if (isValidForm()) return;

        v.setEnabled(false);

        Usuario usuario = new Usuario();
        usuario.Nome = etNome.getText().toString();
        usuario.Email = etEmail.getText().toString();
        usuario.CPF = etCPF.getText().toString();
        usuario.Telefone = etTelefone.getText().toString();
        usuario.DtNascimento = etDtNascimento.getText().toString();

        String Senha = etSenha.getText().toString();

        createUserFirebase(usuario, Senha);//Create account firebase

        //Todo Aguardando Backend
        //registerAPI(v,Senha,Nome,Email,CPF,Telefone,DataNascimento);
    }
    private void createUserFirebase(final Usuario usuario, String  password){
       appDialogs.showProgress("Cadastrando novo usuário...");

        firebaseAuth.createUserWithEmailAndPassword(usuario.Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            appDialogs.closeProgress();

                            usuarioRepository.add(usuario);

                            //abrindo mapa
                            ActivityNavigator.openActivity(getApplicationContext(), MainActivity.class,true);
                        }else{
                            appDialogs.closeProgress();
                            appDialogs.showDialogOK("Atenção!", task.getException().getMessage());
                        }
                    }
                });
    }
    //endregion

    //region MÉTODO DA API
    //TODO Aguardando Backend
    private void registerAPI(final View v, String Senha, String Nome, String Email, String CPF, String Telefone, String DataNascimento){
       ICadastroService CadService = null;
        Call<RetornoAPI<Usuario>> req = CadService.Cadastrar(new CadastroViewModel(Senha,Nome,Email,CPF,Telefone,DataNascimento));
        appDialogs.showProgress(getString(R.string.activity_cadastro_progressDialog_msg_add_user));
        req.enqueue(new Callback<RetornoAPI<Usuario>>() {
            @Override
            public void onResponse(Call<RetornoAPI<Usuario>> call, Response<RetornoAPI<Usuario>> response) {
                v.setEnabled(true);
                RetornoAPI<Usuario> retorno = response.body();
                if(!retorno.Sucesso) {
                    appDialogs.closeProgress();
                    appDialogs.showDialogOK(getString(R.string.dialog_api_msg), retorno.getErrosValidacao());
                }else {
                    appDialogs.closeProgress();
                    Usuario usuario = retorno.Dados;

                    usuarioRepository.add(usuario);

                    //abrindo mapa
                    ActivityNavigator.openActivity(getApplicationContext(), MainActivity.class,true);
                }
            }

            @Override
            public void onFailure(Call<RetornoAPI<Usuario>> call, Throwable t) {
                appDialogs.closeProgress();
                appDialogs.showDialogOK(getString(R.string.snackbar_alert_title),getString(R.string.snackbar_isconnected));
                call.cancel();
            }
        });
    }
    //endregion

    //region MÉTODO AUXILIAR
    private boolean isValidForm(){
        return !validateForm.validateNome(etNome, layoutNome) || !validateForm.validateEmail(etEmail, layoutEmail) || !validateForm.validateCPF(etCPF, layoutCPF)|| !validateForm.validatePhoneNumber(etTelefone, layoutTelefone)|| !validateForm.validateDate(etDtNascimento, layoutDtNascimento, current)|| !validateForm.validatePassword(etSenha, layoutSenha)|| !validateForm.validateConfirmPassword(etSenha, etConfirmarSenha, layoutSenha);
    }
    //endregion

    //region CLASSE PARA VALIDAÇÃO
    private class DateTextWatcher implements TextWatcher {

        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();
        private TextInputEditText editText;
        private TextInputLayout textInputLayout;

        private DateTextWatcher(TextInputEditText editText, TextInputLayout textInputLayout) {
            this.editText = editText;
            this.textInputLayout = textInputLayout;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8) {
                    clean = clean + ddmmyyyy.substring(clean.length());
                } else {
                    day = Integer.parseInt(clean.substring(0, 2));
                    mon = Integer.parseInt(clean.substring(2, 4));
                    year = Integer.parseInt(clean.substring(4, 8));

                    //puxa o ano atual para inserçao na data de nascimento
                    Calendar calendarAtual = Calendar.getInstance();
                    int atualYear = calendarAtual.get(Calendar.YEAR);

                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon - 1);
                    year = (year < 1900) ? 1900 : (year > atualYear) ? atualYear : year;
                    cal.set(Calendar.YEAR, year);

                    day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                    clean = String.format("%02d%02d%02d", day, mon, year);

                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                etDtNascimento.setText(current);
                etDtNascimento.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        public void afterTextChanged(Editable editable) {
                    validateForm.validateDate(etDtNascimento, layoutDtNascimento, current);
            }
        }
    //endregion
}
