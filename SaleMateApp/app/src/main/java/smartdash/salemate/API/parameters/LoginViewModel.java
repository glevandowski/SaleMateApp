package smartdash.salemate.API.parameters;

import com.google.gson.annotations.SerializedName;

public class LoginViewModel {

    @SerializedName("Email")
    public String Email;
    @SerializedName("Senha")
    public String Senha;

    public LoginViewModel(String email, String senha) {
        Email = email;
        Senha = senha;
    }
}
