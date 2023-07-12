package com.zexceed.tokoku.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zexceed.tokoku.R
import com.zexceed.tokoku.apiservices.ApiConfig
import com.zexceed.tokoku.databinding.ActivityRegisterBinding
import com.zexceed.tokoku.models.remote.request.users.UserAddressRequest
import com.zexceed.tokoku.models.remote.request.users.UserRequest
import com.zexceed.tokoku.models.remote.response.users.UserResponse
import com.zexceed.tokoku.util.Constants.API_BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            etFirstname.doAfterTextChanged { ilFirstname.isErrorEnabled = false }
            etLastname.doAfterTextChanged { ilLastname.isErrorEnabled = false }
            etUsername.doAfterTextChanged { ilUsername.isErrorEnabled = false }
            etAddress.doAfterTextChanged { ilAddress.isErrorEnabled = false }
            etPhone.doAfterTextChanged { ilPhone.isErrorEnabled = false }
            etPassword.doAfterTextChanged { ilPassword.isErrorEnabled = false }
            etConfirmPassword.doAfterTextChanged { ilConfirmPassword.isErrorEnabled = false }

            btnSubmit.setOnClickListener {
                val firstname = etFirstname.text.toString().trim()
                val lastname = etLastname.text.toString().trim()
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val password_confirm = etConfirmPassword.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val address = etAddress.text.toString().trim()

                if (validateInput(
                        firstname,
                        lastname,
                        username,
                        password,
                        password_confirm,
                        phone,
                        address
                    )
                ) {
                    val req = ApiConfig(API_BASE_URL).apiService.postUser(
                        data = UserRequest(
                            etFirstname.toString(),
                            etLastname.toString(),
                            etUsername.toString(),
                            etPassword.toString(),
                            etPhone.toString(),
                            UserAddressRequest(
                                etAddress.toString()
                            )
                        )
                    )

                    req.enqueue(object: Callback<UserResponse> {
                        override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                        ) {
                            if (response.code() == 200) {
                                MaterialAlertDialogBuilder(this@RegisterActivity, )
                                    .setTitle("Akun berhasil dibuat!")
                                    .setMessage("Login untuk melanjutkan")
                                    .setNeutralButton("Ok") { dialog, which ->
                                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            btnLogin.setOnClickListener {
                intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInput(
        firstname: String,
        lastname: String,
        username: String,
        password: String,
        password_confirm: String,
        phone: String,
        address: String
    ): Boolean {
        binding.apply {
            if (firstname.isEmpty()) {
                ilFirstname.isErrorEnabled = true
                ilFirstname.error = getString(R.string.login_msg1)
            } else if (lastname.isEmpty()) {
                ilLastname.isErrorEnabled = true
                ilLastname.error = getString(R.string.login_msg1)
            } else if (username.isEmpty()) {
                ilUsername.isErrorEnabled = true
                ilUsername.error = getString(R.string.login_msg1)
            } else if (address.isEmpty()) {
                ilAddress.isErrorEnabled = true
                ilAddress.error = getString(R.string.login_msg1)
            } else if (phone.isEmpty()) {
                ilPhone.isErrorEnabled = true
                ilPhone.error = getString(R.string.login_msg1)
            } else if (password.isEmpty()) {
                ilPassword.isErrorEnabled = true
                ilPassword.error = getString(R.string.login_msg1)
            } else if (password_confirm.isEmpty()) {
                ilConfirmPassword.isErrorEnabled = true
                ilConfirmPassword.error = getString(R.string.login_msg1)
            } else if (password_confirm != password) {
                ilConfirmPassword.isErrorEnabled = true
                ilConfirmPassword.error = getString(R.string.password_confirm)
            }

            else {
                return true
            }
        }
        return false
    }
}