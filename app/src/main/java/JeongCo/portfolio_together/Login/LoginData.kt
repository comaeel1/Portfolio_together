package JeongCo.portfolio_together.Login

import com.google.gson.annotations.SerializedName




class LoginData{
    @SerializedName("userEmail")
    var userEmail: String? = null

    @SerializedName("userPwd")
    var userPwd: String? = null

    fun LoginData(userEmail: String?, userPwd: String?) {
        this.userEmail = userEmail
        this.userPwd = userPwd
    }
}