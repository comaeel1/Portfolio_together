package JeongCo.portfolio_together.Activity

import JeongCo.portfolio_together.Retrofit.INodeJS
import JeongCo.portfolio_together.Retrofit.RetrofitClient
import JeongCo.portfolio_together.databinding.ActivityRegisterBinding
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

var compositeDisposable = CompositeDisposable()


class RegisterActivity : AppCompatActivity() {
    val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater)}
    lateinit var auth : FirebaseAuth
    lateinit var myAPI: INodeJS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val retrofit = RetrofitClient.instance
        myAPI = retrofit.create(INodeJS::class.java)

        binding.regRegisterBtn.setOnClickListener {
            attemptRegister()
        }
    }

    fun attemptRegister(){
        var email = binding.tvRegEmail.text.toString()
        var password = binding.tvRegPwd.text.toString()
        var password_check = binding.tvRegPwdCheck.text.toString()
        var cancel = false

        if(email.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show()
            cancel = true
        }else if(!isUseridValid(email)) {
            Toast.makeText(this@RegisterActivity, "이메일에는 @가 반드시 포함되어야 합니다!", Toast.LENGTH_SHORT).show()
            cancel = true
        }

        if(password.isEmpty() && password_check.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            cancel = true
        }else if(!isUserPwdValid(password)) {
            Toast.makeText(this@RegisterActivity, "비밀번호는 최소 6글자 이상입니다", Toast.LENGTH_SHORT).show()
            cancel = true
        }else if(!isUserPwdSame(password, password_check)) {
            Toast.makeText(this@RegisterActivity, "두 비밀번호가 같지 않습니다", Toast.LENGTH_SHORT).show()
            cancel = true
        }

        if(!cancel){
            createUserId(email,password)
        }

    }

    fun isUserPwdSame(userPwd: String, userPwd_check: String): Boolean = userPwd.equals(userPwd_check)
    fun isUseridValid(UserEmail: String): Boolean = UserEmail.contains('@')
    fun isUserPwdValid(userPwd: String): Boolean = userPwd.length >= 6

    private fun createUserId(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task->
                if(task.isSuccessful){
                    //아이디 생성 완료되었을때
                    val user=auth.currentUser!!

                    //인증용 메일 발송
                    auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener(this){
                                if(it.isSuccessful){
                                    Toast.makeText(this@RegisterActivity, "회원가입에 성공했습니다. 고객님에게 보낸 메일로 이메일 인증을 해주세요", Toast.LENGTH_SHORT).show()
                                    setUserinfo(user)
                                }
                            }
                } else{
                    //아이디 생성 실패했을 경우
                    Toast.makeText(this@RegisterActivity, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setUserinfo(mUser: FirebaseUser){
        var userEmail = binding.tvRegEmail.text.toString()
        var userName = binding.tvRegName.text.toString()
        var userPhone = binding.tvRegPhone.text.toString()

        mUser.getIdToken(true)
                .addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        // Send token to your backend via HTTPS
                        compositeDisposable.add(myAPI.registerUser(idToken!!,userEmail,userName,userPhone)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe())
                        // ...
                    } else {
                        // Handle error -> task.getException();
                    }
                })
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy(){
        compositeDisposable.clear()
        super.onDestroy()
    }

}