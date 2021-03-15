package JeongCo.portfolio_together.Retrofit

import JeongCo.portfolio_together.Join.JoinData
import JeongCo.portfolio_together.Join.JoinResponse
import JeongCo.portfolio_together.Login.LoginData
import JeongCo.portfolio_together.Login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ServiceApi {
    @POST("/user/login")
    fun userLogin(@Body data: LoginData?): Call<LoginResponse?>?

    @POST("/user/join")
    fun userJoin(@Body data: JoinData?): Call<JoinResponse?>?
}