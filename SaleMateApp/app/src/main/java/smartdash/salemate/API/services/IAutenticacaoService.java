package smartdash.salemate.API.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import smartdash.salemate.API.RetornoAPI;
import smartdash.salemate.API.parameters.LoginViewModel;
import smartdash.salemate.models.Usuario;


public interface IAutenticacaoService {


    @POST("Acesso/LoginMotorista")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<RetornoAPI<Usuario>> Login(@Body LoginViewModel parametros);
}