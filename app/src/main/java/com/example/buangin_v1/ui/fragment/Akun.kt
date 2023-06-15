package com.example.buangin_v1.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.buangin_v1.MainActivity
import com.example.buangin_v1.R
import com.example.buangin_v1.data.DataExtra
import com.example.buangin_v1.data.UserPreference
import com.example.buangin_v1.response.LoginResult
import com.example.buangin_v1.ui.Login
import com.google.android.material.button.MaterialButton

class Akun : Fragment() {

    private lateinit var sharedPreferences: UserPreference
    private lateinit var fullNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var dobTextView: TextView
    private lateinit var welcomeTextView: TextView
    private lateinit var logOut: MaterialButton
    private lateinit var profilPicture: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_akun, container, false)
        fullNameTextView = view.findViewById(R.id.textView_show_full_name)
        emailTextView = view.findViewById(R.id.textView_show_email)
        dobTextView = view.findViewById(R.id.textView_show_dob)
        logOut = view.findViewById(R.id.btn_logout)
        welcomeTextView = view.findViewById(R.id.textView_show_welcome)
        profilPicture = view.findViewById(R.id.imageView_profile_dp)

        logOut.setOnClickListener {
            val isUserLogin = sharedPreferences.getLogin("is_user_loged")
            if (isUserLogin){
                val builder = AlertDialog.Builder(requireContext())
                    .setTitle("Yakin mau keluar?")
                    .setMessage("Akun kamu akan keluar dari aplikasi")
                    .setPositiveButton("Yes") { _, _ ->
                        logout()
                        Toast.makeText(requireContext(), "Berhasil LogOut", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No") { _, _ ->}

                builder.create().show()
            } else{
                performLogin()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = UserPreference(requireContext())

        val isLoggedIn = sharedPreferences.getLogin(DataExtra.IS_LOGIN)
        if (isLoggedIn) {
            // Jika pengguna sudah login, ubah teks tombol menjadi "Keluar"
            logOut.text = "Keluar"
            profilPicture.setImageResource(R.drawable.zakar)

            // Dapatkan data name dan email dari SharedPreferences
            val name = sharedPreferences.getString(DataExtra.NAME)
            val email = sharedPreferences.getString(DataExtra.EMAIL)

            Log.d("Nama Pengguna", name ?: "Nama kosong")

            // Setel nilai TextView dengan data name dan email yang ditemukan
            welcomeTextView.text = "Welcome Paiman !"
            fullNameTextView.text = "Paiman"
            emailTextView.text = "paiman@gmail.com"


        } else {
            // Jika pengguna belum login, ubah teks tombol menjadi "Login"
            logOut.text = "Login"
            profilPicture.setImageResource(R.drawable.default_profil_image)
            welcomeTextView.text = "Anda Belum login!"
        }

    }


    private fun performLogin() {
        val loginIntent = Intent(requireActivity(), Login::class.java)
        startActivity(loginIntent)
        requireActivity().finish()
    }

    private fun logout(){
        sharedPreferences.clear()
        val logoutUser = Intent(requireActivity(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(logoutUser)
        requireActivity().finish()
    }
}