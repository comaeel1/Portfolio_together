package JeongCo.portfolio_together.Activity

import JeongCo.portfolio_together.databinding.ActivityLoginBinding
import JeongCo.portfolio_together.databinding.ActivityRegisterBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegisterActivity : AppCompatActivity() {
    val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.regCertificateBtn.setOnClickListener {
            certification()
        }

        binding.regRegisterBtn.setOnClickListener {
            register()
        }
    }

    fun certification(){

    }

    fun register(){

    }

}