package JeongCo.portfolio_together.Retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private val BASE_URL = "http://ec2-13-209-41-88.ap-northeast-2.compute.amazonaws.com:3000"
    private var retrofit: Retrofit? = null
    val instance:Retrofit
    get() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) // 요청을 보낼 base url을 설정한다.
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                .build()
        }
        return retrofit!!
    }

}