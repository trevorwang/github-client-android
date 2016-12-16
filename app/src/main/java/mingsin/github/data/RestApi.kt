package mingsin.github.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Created by trevorwang on 08/12/2016.
 */
class RestApi @Inject constructor() {
    val clientId = "6e4e8221c0dcbd375140"
    val clientSecret = "aadd49231fea44e5e1302cedcbfa757013b20516"
    val githubApi = "https://api.github.com/"

    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(githubApi)
                .build()
    }

    fun okHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }
}