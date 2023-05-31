package com.example.buangin_v1

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.buangin_v1.api.ApiConfig
import com.example.buangin_v1.custom_view.BtnLoginValid
import com.example.buangin_v1.databinding.ActivityLoginBinding
import com.example.buangin_v1.response.LoginRequest
import com.example.buangin_v1.response.LoginResponse
import com.example.submissionstoryapp.customview.EmailValidation
import com.example.submissionstoryapp.customview.PasswordValidation
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var etEmail: EmailValidation
    private lateinit var etPass: PasswordValidation
    private lateinit var btnLogin: BtnLoginValid
    val loginResponse = MutableLiveData<LoginResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etEmail = binding.edLoginEmail
        etPass = binding.edLoginPassword
        btnLogin = binding.btnMasuk

        setupView()
        playAnimation()
        setLoginButton()

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (Patterns.EMAIL_ADDRESS.matcher(p0).matches()) setLoginButton() else binding.btnMasuk.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setLoginButton()
                if (Patterns.EMAIL_ADDRESS.matcher(p0).matches()) setLoginButton() else binding.btnMasuk.isEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0).matches()) setLoginButton() else binding.btnMasuk.isEnabled = false

            }
        })

        etPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length >= 8) setLoginButton() else binding.btnMasuk.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setLoginButton()
                if (p0.toString().length >= 8) setLoginButton() else binding.btnMasuk.isEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 8) setLoginButton() else binding.btnMasuk.isEnabled = false

            }
        })

        binding.btnMasuk.setOnClickListener {
            login()

        }

        val goRegister: TextView = findViewById(R.id.btn_buat_akun)
        goRegister.setOnClickListener {
            val moveRegister = Intent(this@Login, Register::class.java)
            startActivity(moveRegister)
            Toast.makeText(this, "Create your Account", Toast.LENGTH_SHORT).show()
        }
        setupView()

    }
    private fun login(){
        val request = LoginRequest()
        request.email = binding.edLoginEmail.text.toString()
        request.password = binding.edLoginPassword.text.toString()
        setLoadingState(true)

        val client = ApiConfig.getApiService().userLogin(request)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val userResponse = response.body()
                    if (userResponse != null){
                        Log.e("success", "Berhasil Login ${userResponse.message}")
                    }
                    Log.e("success", "Success")
                    loginResponse.postValue(response.body())
                }else{
                    val error = JSONObject(response.errorBody()!!.string())
                    Toast.makeText(applicationContext, error.getString("message"), Toast.LENGTH_SHORT).show()
                    Log.e("Failed", "Failed to Sign Up")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Failed", t.message.toString())
                setLoadingState(false)
            }

        })


    }

    fun setLoginButton(){

        val email = etEmail.text.toString()
        val password = etPass.text.toString()

        btnLogin.isEnabled = email != null && email.isNotEmpty() && password != null && password.isNotEmpty()

    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.loginAnim, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleWelcome, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.subTitle, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.textTitleEmail, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.textTitlePassword, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val lupaPassword = ObjectAnimator.ofFloat(binding.btnLupa, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnMasuk, View.ALPHA, 1f).setDuration(500)
        val daftar = ObjectAnimator.ofFloat(binding.btnBuatAkun, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(image, title, message, emailTextView, emailEditTextLayout, passwordTextView, passwordEditTextLayout,lupaPassword, login, daftar)
            startDelay = 1000
        }.start()
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.apply {
            etEmail.isEnabled = !isLoading
            etPass.isEnabled = !isLoading
            btnLogin.isEnabled = !isLoading
            btnBuatAkun.isEnabled = !isLoading

            // Animate views alpha
            if (isLoading) {
                viewLoading.animateVisibility(true)
            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }
    fun View.animateVisibility(isVisible: Boolean, duration: Long = 400) {
        ObjectAnimator
            .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
            .setDuration(duration)
            .start()
    }
}