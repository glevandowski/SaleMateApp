package smartdash.salemate.API;

import android.content.Context;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smartdash.salemate.API.Interceptors.InterceptorAPI;
import smartdash.salemate.API.Interceptors.NetworkStatusInterceptor;
import smartdash.salemate.API.services.IAutenticacaoService;
import smartdash.salemate.API.services.ICadastroService;

public class APIClient {

    private static String URL_BASE = "";
    private static Retrofit retrofit = null;

    public static Retrofit getInstance(Context ctx) {

        if(retrofit != null)
            return  retrofit;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new NetworkStatusInterceptor())
                .addInterceptor(new InterceptorAPI(ctx))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(APIClient.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


    public static IAutenticacaoService getAutenticacaoService(Context context) {
        return getInstance(context).create(IAutenticacaoService.class);
    }

    public static ICadastroService getCadastroService(Context context) {
        return getInstance(context).create(ICadastroService.class);
    }
}