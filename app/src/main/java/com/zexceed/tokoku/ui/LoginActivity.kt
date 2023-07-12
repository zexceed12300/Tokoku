package com.zexceed.tokoku.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.zexceed.tokoku.R
import com.zexceed.tokoku.apiservices.ApiConfig
import com.zexceed.tokoku.databinding.ActivityLoginBinding
import com.zexceed.tokoku.models.remote.request.LoginRequest
import com.zexceed.tokoku.models.remote.response.LoginResponse
import com.zexceed.tokoku.util.AuthPreferences
import com.zexceed.tokoku.util.Constants.API_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: AuthPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = AuthPreferences(this@LoginActivity)

        binding.apply {

            etUsername.doAfterTextChanged { ilUsername.isErrorEnabled = false }
            etPassword.doAfterTextChanged { ilPassword.isErrorEnabled = false }

            btnRegister.setOnClickListener {
                intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            btnSubmit.setOnClickListener {

                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (validateInput(username, password)) {

                    val req = ApiConfig(API_BASE_URL).apiService.login(
                        loginRequest = LoginRequest(
                            username,
                            password
                        )
                    )
                    req.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.code() == 400) {
                                tvAlert.text = getString(R.string.login_msg2)
                                tvAlert.visibility = View.VISIBLE
                            } else if (response.code() == 200) {
                                val res = response.body()
                                if (res != null) {
                                    preferences.setToken(res.id, res.token)
                                }
                                if(preferences.isTokenExist()) {
                                    intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        binding.apply {
            if (username.isEmpty()) {
                ilUsername.isErrorEnabled = true
                ilUsername.error = getString(R.string.login_msg1)
            }
            else if (password.isEmpty()) {
                ilPassword.isErrorEnabled = true
                ilPassword.error = getString(R.string.login_msg1)
            }
            else {
                return true
            }
        }
        return false
    }
}