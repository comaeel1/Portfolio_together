package JeongCo.portfolio_together.Activity

import JeongCo.portfolio_together.Retrofit.INodeJS
import JeongCo.portfolio_together.Retrofit.RetrofitClient
import JeongCo.portfolio_together.databinding.ActivityLoginBinding
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class LoginActivity : AppCompatActivity() {
    val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}
    lateinit var myAPI: INodeJS
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        //API 초기화
        val retrofit = RetrofitClient.instance
        myAPI = retrofit.create(INodeJS::class.java)

        binding.loginBtn.setOnClickListener {
            attemptLogin() //로그인에 시도할때 실행
        }

        binding.registerBtn.setOnClickListener {
            //회원가입 뷰로 넘겨준다
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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

    private fun StartLogin(email: String, password: String){
        //파이어베이스 로그인 시도
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    if(it.isSuccessful){
                        if(auth.currentUser!!.isEmailVerified) {
                            Toast.makeText(this@LoginActivity, "로그인에 성공했습니다!", Toast.LENGTH_SHORT).show()

                            //메인 액티비티로 넘겨줌
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@LoginActivity, "이메일 인증을 해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this@LoginActivity, "비밀번호가 틀렸거나, 이메일 인증이 되지 않은 계정입니다", Toast.LENGTH_SHORT).show()
                    }
                }
    }
    fun isUseridValid(UserEmail: String): Boolean = UserEmail.contains('@')
    fun isUserPwdValid(userPwd: String): Boolean = userPwd.length >= 6

}