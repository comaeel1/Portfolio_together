package JeongCo.portfolio_together.Retrofit

import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface INodeJS {
    @POST("/user/register/")
    @FormUrlEncoded
    fun registerUser(@Field("userEmail") userEmail:String,
                     @Field("userName") userName:String,
                     @Field("userPhone") userPhone:String,
                     @Field("userPwd") userPwd:String) :Observable<String>

    @POST("/user/login/")
    @FormUrlEncoded
    fun loginUser(@Field("userEmail") userEmail:String,
                  @Field("userPwd") userPwd:String) :Observable<String>
}