package smartdash.salemate.API.Interceptors;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import smartdash.salemate.API.RetornoAPI;
import smartdash.salemate.repository.UsuarioRepository;
import smartdash.salemate.util.SharedPreference;
import smartdash.salemate.views.SplashScreenActivity;


public class InterceptorAPI implements Interceptor {

    private Context context;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private UsuarioRepository usuarioRepository;

    public InterceptorAPI(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        usuarioRepository = new UsuarioRepository(context);

        Request originalRequest = chain.request();

        if(isAuthenticated()) {
            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .addHeader("ResolveAiAuth", usuarioRepository.getUsuario().Token);

            originalRequest = requestBuilder.build();
        }

        Response response = chain.proceed(originalRequest);
        response.code();

        int statusCode = response.code();

        if(statusCode == HttpURLConnection.HTTP_OK)
        {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();


            //Log.v("InterceptorAPI", teste);

            return response;
        }

        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        String teste = buffer.clone().readString(UTF8).toString();


        if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED)
            unauthorizedAccess();

        if (statusCode >= HttpURLConnection.HTTP_NOT_FOUND && statusCode <= HttpURLConnection.HTTP_UNAVAILABLE)
            return processReturnFail(originalRequest);

        return response;
    }

    public boolean isAuthenticated() {
        return usuarioRepository.isEmpty();
    }


    private void unauthorizedAccess() {
        SharedPreference.getInstance().clear(context);
        Intent myIntent = new Intent(context, SplashScreenActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    private Response processReturnFail(Request request)
    {

        RetornoAPI retorno = new RetornoAPI<>();
        retorno.Sucesso = false;
        retorno.Mensagem = "Não foi possível completar a operação";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return new Response.Builder()
                .code(200)
                .request(request)
                .protocol(Protocol.HTTP_2)
                .body(ResponseBody.create(MediaType.parse("{}"), gson.toJson(retorno)))
                .message("Response tratado no Interceptor")
                .build();
    }
}
