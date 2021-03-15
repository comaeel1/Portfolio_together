package JeongCo.portfolio_together.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val BASE_URL = "ec2-13-209-41-88.ap-northeast-2.compute.amazonaws.com:3000"
    private var retrofit: Retrofit? = null

    get() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) // 요청을 보낼 base url을 설정한다.
                .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                .build()
        }
        return retrofit!!
    }

}