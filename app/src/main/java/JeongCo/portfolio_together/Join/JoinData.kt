package JeongCo.portfolio_together.Join

import com.google.gson.annotations.SerializedName




class JoinData {
    @SerializedName("userName")
    private var userName: String? = null

    @SerializedName("userEmail")
    private var userEmail: String? = null

    @SerializedName("userPwd")
    private var userPwd: String? = null

    fun JoinData(userName: String?, userEmail: String?, userPwd: String?) {
        this.userName = userName
        this.userEmail = userEmail
        this.userPwd = userPwd
    }
}