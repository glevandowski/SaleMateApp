package smartdash.salemate.API.Interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


public class NetworkStatusInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        if (!Util.isInternet(mContext)) {
//            return new Response.Builder()
//                    .code(1007)
//                    .request(chain.request())
//                    .protocol(Protocol.HTTP_2)
//                    .body(ResponseBody.create(MediaType.parse("{}"),"{}"))
//                    .message("Sem conexão de internet.")
//                    .build();
//        }
        return chain.proceed(chain.request());
    }
}