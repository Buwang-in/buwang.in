package com.example.buangin_v1.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.buangin_v1.R
import com.example.buangin_v1.api.ApiConfig
import com.example.buangin_v1.custom_view.BtnRegisValid
import com.example.buangin_v1.databinding.ActivityRegisterBinding
import com.example.buangin_v1.response.RegisterRequest
import com.example.buangin_v1.response.RegisterResponse
import com.example.buangin_v1.custom_view.EmailValidation
import com.example.buangin_v1.custom_view.PasswordValidation
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    val signUpResponse = MutableLiveData<RegisterResponse>()
    private lateinit var edtEmail: EmailValidation
    private lateinit var edtPass: PasswordValidation
    private lateinit var edtPassCon: PasswordValidation
    private lateinit var btnRegister: BtnRegisValid
    private lateinit var passwordIcon: Drawable
    private lateinit var passwordIconCon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edtEmail = binding.edRegisterEmail
        edtPass = binding.edRegisterPassword
        edtPassCon = binding.edRegisterPasswordConfirm
        btnRegister = binding.btnRegister
        passwordIcon = binding.edRegisterPassword.compoundDrawablesRelative[2]
        passwordIconCon = binding.edRegisterPasswordConfirm.compoundDrawablesRelative[2]

        setupView()
        playAnimation()

        //untuk show hide password
        edtPass.setOnTouchListener { _, event ->
            val drawableWidth = passwordIcon.bounds.width()
            val touchableAreaEnd = binding.edRegisterPassword.width - binding.edRegisterPassword.paddingEnd
            if (event.action == MotionEvent.ACTION_UP && event.rawX >= touchableAreaEnd - drawableWidth) {
                togglePasswordVisibility(binding.edRegisterPassword)
                return@setOnTouchListener true
            }
            false
        }

        edtPassCon.setOnTouchListener { _, event ->
            val drawableWidth = passwordIconCon.bounds.width()
            val touchableAreaEnd = binding.edRegisterPasswordConfirm.width - binding.edRegisterPasswordConfirm.paddingEnd
            if (event.action == MotionEvent.ACTION_UP && event.rawX >= touchableAreaEnd - drawableWidth) {
                togglePasswordVisibility(binding.edRegisterPasswordConfirm)
                return@setOnTouchListener true
            }
            false
        }



        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0).matches()) setRegisterButton() else binding.btnRegister.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButton()
                if (Patterns.EMAIL_ADDRESS.matcher(p0).matches()) setRegisterButton() else binding.btnRegister.isEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0).matches()) setRegisterButton() else binding.btnRegister.isEnabled = false

            }
        })
        edtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length >= 8) setRegisterButton() else binding.btnRegister.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setRegisterButton()
                if (p0.toString().length >= 8) setRegisterButton() else binding.btnRegister.isEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 8) setRegisterButton() else binding.btnRegister.isEnabled = false
            }

        })
        binding.btnRegister.setOnClickListener{
            registerUser()
        }

        val goLogin: TextView = findViewById(R.id.btn_masuk_akun)
        val text = "Sudah punya akun? Masuk"
        val spannableStringBuilder = SpannableStringBuilder(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Aksi yang ingin dilakukan saat teks di klik
                // Contoh: Berpindah ke halaman pendaftaran
                val intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                // Mengatur penampilan teks saat di klik
                ds.isUnderlineText = false  // Menghapus garis bawah teks
                ds.color = Color.BLUE  // Mengatur warna teks
            }
        }

        spannableStringBuilder.setSpan(
            clickableSpan,
            text.indexOf("Masuk"),
            text.indexOf("Masuk") + "Masuk".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        goLogin.text = spannableStringBuilder
        goLogin.movementMethod = LinkMovementMethod.getInstance()
        goLogin.highlightColor = Color.TRANSPARENT


        binding.btnMasukAkun.setOnClickListener {
            val moveLogin = Intent(this@Register, Login::class.java)
            Toast.makeText(this, "Silakan Login", Toast.LENGTH_SHORT).show()
            startActivity(moveLogin)
        }

    }

    private fun togglePasswordVisibility(passwordValidation: PasswordValidation) {
        val isPasswordVisible = passwordValidation.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

        if (isPasswordVisible) {
            passwordValidation.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            passwordValidation.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.remove_eye, 0)
        } else {
            passwordValidation.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordValidation.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_eye, 0)
        }
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.textTitleDaftar, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.textSubDaftar, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.textTitleUsername, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.textTitleEmailRegis, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.textTitlePasswordRegis, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val passwordConfTextView = ObjectAnimator.ofFloat(binding.textTitlePasswordRegisConfirm, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextConfLayout = ObjectAnimator.ofFloat(binding.edRegisterPasswordConfirm, View.ALPHA, 1f).setDuration(500)
        val daftar = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val masuk = ObjectAnimator.ofFloat(binding.btnMasukAkun, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, message, nameTextView, nameEditTextLayout, emailTextView, emailEditTextLayout, passwordTextView, passwordEditTextLayout,passwordConfTextView, passwordEditTextConfLayout, daftar, masuk)
            startDelay = 1000
        }.start()
    }

    private fun registerUser(){
        val request = RegisterRequest()
        request.name = binding.edRegisterName.text.toString()
        request.email = binding.edRegisterEmail.text.toString()
        request.password = binding.edRegisterPassword.text.toString()
        setLoadingState(true)

        val client = ApiConfig.getApiService().userRegister(request)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    signUpResponse.postValue(response.body())
                    Log.e("success", "Pendaftaran Berhasil")
                    startActivity(Intent(this@Register, Login::class.java))
                    setLoadingState(false)
                }else{
                    val error = JSONObject(response.errorBody()!!.string())
                    Toast.makeText(applicationContext, error.getString("message"), Toast.LENGTH_SHORT).show()
                    setLoadingState(false)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Failed", t.message.toString())
                setLoadingState(false)
            }
        })

        signUpResponse.observe(this){
            if (it.error){
                startActivity(Intent(this@Register, Login::class.java))
                Toast.makeText(applicationContext, "Berhasil Buat Akun", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setRegisterButton() {
        val edtName = findViewById<TextView>(R.id.ed_register_name)

        val email = edtEmail.text.toString()
        val password = edtPass.text.toString()
        val name = edtName.text.toString()

        btnRegister.isEnabled = email != null && email.isNotEmpty() && password != null && password.isNotEmpty() && name != null && name.isNotEmpty()
    }

    private fun setLoadingState(isLoading: Boolean) {
        val edtName = findViewById<TextView>(R.id.ed_register_name)
        binding.apply {
            edtEmail.isEnabled = !isLoading
            edtPass.isEnabled = !isLoading
            edtName.isEnabled = !isLoading
            btnRegister.isEnabled = !isLoading
            btnMasukAkun.isEnabled = !isLoading

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


}