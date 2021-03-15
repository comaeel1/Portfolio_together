package JeongCo.portfolio_together.Activity

import JeongCo.portfolio_together.Retrofit.ServiceApi
import JeongCo.portfolio_together.databinding.ActivityLoginBinding
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}
    var compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        val service: ServiceApi =
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
        }
    }

    fun attemptLogin(){
        var userID = binding.tvUserid.text.toString()
        var userPwd = binding.tvPwd.text.toString()
        var cancel = false

        if(userID.isEmpty()) {
            Toast.makeText(this@LoginActivity, "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show()
            cancel = true
        }else if(!isUseridValid(userID)) {
            Toast.makeText(this@LoginActivity, "아이디는 최소 4글자 이상입니다", Toast.LENGTH_SHORT).show()
            cancel = true
        }

        if(userPwd.isEmpty()) {
            Toast.makeText(this@LoginActivity, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            cancel = true
        }else if(!isUserPwdValid(userPwd)) {
            Toast.makeText(this@LoginActivity, "비밀번호는 최소 6글자 이상입니다", Toast.LENGTH_SHORT).show()
            cancel = true
        }

        if(!cancel){

        }

    }

    fun StartLogin(){

    }
    fun isUseridValid(Userid: String): Boolean = Userid.length >= 4
    fun isUserPwdValid(userPwd: String): Boolean = userPwd.length > 6
}