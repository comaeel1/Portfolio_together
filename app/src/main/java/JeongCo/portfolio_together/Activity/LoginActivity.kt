package JeongCo.portfolio_together.Activity

import JeongCo.portfolio_together.Retrofit.INodeJS
import JeongCo.portfolio_together.Retrofit.RetrofitClient
import JeongCo.portfolio_together.databinding.ActivityLoginBinding
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginActivity : AppCompatActivity() {
    val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}
    lateinit var myAPI: INodeJS
    var compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //API 초기화
        val retrofit = RetrofitClient.instance
        myAPI = retrofit.create(INodeJS::class.java)

        binding.loginBtn.setOnClickListener {
            attemptLogin()
        }
    }

    fun attemptLogin(){
        var userEmail = binding.tvUserEmail.text.toString()
        var userPwd = binding.tvPwd.text.toString()
        var cancel = false

        if(userEmail.isEmpty()) {
            Toast.makeText(this@LoginActivity, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show()
            cancel = true
        }else if(!isUseridValid(userEmail)) {
            Toast.makeText(this@LoginActivity, "이메일에는 @가 반드시 포함되어야 합니다!", Toast.LENGTH_SHORT).show()
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
            StartLogin(userEmail, userPwd)
        }

    }

    fun StartLogin(userEmail: String, userPwd: String){
            compositeDisposable.add(myAPI.loginUser(userEmail, userPwd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { message ->
                        if (message.contains("userPwd"))
                            Toast.makeText(this@LoginActivity, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                    })
    }
    fun isUseridValid(UserEmail: String): Boolean = UserEmail.contains('@')
    fun isUserPwdValid(userPwd: String): Boolean = userPwd.length >= 6

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy(){
        compositeDisposable.clear()
        super.onDestroy()
    }
}