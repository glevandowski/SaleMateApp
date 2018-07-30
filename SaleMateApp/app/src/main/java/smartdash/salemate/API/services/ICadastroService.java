package smartdash.salemate.API.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import smartdash.salemate.API.RetornoAPI;
import smartdash.salemate.API.parameters.CadastroViewModel;
import smartdash.salemate.API.parameters.EditarCadastroViewModel;
import smartdash.salemate.models.Usuario;

public interface ICadastroService {

    @POST("Acesso/Cadastrar")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<RetornoAPI<Usuario>> Cadastrar(@Body CadastroViewModel parametrosCadastro);

    @POST("Passageiro/EditarCadastro")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<RetornoAPI<Usuario>> EditarCadastro(@Body EditarCadastroViewModel parametrosCadastro);
}
