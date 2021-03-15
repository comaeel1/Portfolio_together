package JeongCo.portfolio_together.Join

import com.google.gson.annotations.SerializedName




class JoinResponse {
    @SerializedName("code")
    private val code = 0

    @SerializedName("message")
    private val message: String? = null

    fun getCode(): Int {
        return code
    }

    fun getMessage(): String? {
        return message
    }
}