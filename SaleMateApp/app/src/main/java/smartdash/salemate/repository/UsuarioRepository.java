package smartdash.salemate.repository;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import smartdash.salemate.models.Usuario;
import smartdash.salemate.util.ActivityNavigator;
import smartdash.salemate.util.SharedPreference;
import smartdash.salemate.views.SplashScreenActivity;

public class UsuarioRepository implements Repository<Usuario> {

    private final Context context;
    private final Gson gson;

    public UsuarioRepository(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    @Override
    public void add(Usuario item) {
        SharedPreference.getInstance().save(context, SharedPreference.KEY_USER_CADASTRO, gson.toJson(item));
    }

    @Override
    public void update(Usuario item) {
        Usuario usuario = getUsuario();
        usuario.Nome = item.Nome;
        usuario.Email = item.Email;
        usuario.CPF = item.CPF;
        usuario.DtNascimento = item.DtNascimento;
        usuario.Telefone = item.Telefone;
        SharedPreference.getInstance().save(context, SharedPreference.KEY_USER_CADASTRO, gson.toJson(usuario));
    }

    @Override
    public void remove(Usuario item) {
        SharedPreference.getInstance().clear(context);
        ActivityNavigator.openActivity(context, SplashScreenActivity.class, true);
    }

    @Override
    public List<Usuario> findAll() {
        String values = SharedPreference.getInstance().getValue(context, SharedPreference.KEY_USER_CADASTRO);
        Type userType = new TypeToken<Usuario>() {}.getType();
        return gson.fromJson(values, userType);
    }

    public Usuario getUsuario() {
        String values = SharedPreference.getInstance().getValue(context, SharedPreference.KEY_USER_CADASTRO);
        Type userType = new TypeToken<Usuario>() {}.getType();
        return gson.fromJson(values, userType);
    }

    public boolean isEmpty() {
        String values = SharedPreference.getInstance().getValue(context, SharedPreference.KEY_USER_CADASTRO);
        Type userType = new TypeToken<Usuario>() {}.getType();
        Usuario usuario = gson.fromJson(values, userType);

        if (usuario == null) return false;
        else if (usuario.Token == null) return false;
        else return true;
        }
}
