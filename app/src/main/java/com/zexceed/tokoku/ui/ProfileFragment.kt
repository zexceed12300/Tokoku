package com.zexceed.tokoku.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.zexceed.tokoku.apiservices.ApiConfig
import com.zexceed.tokoku.databinding.FragmentProfileBinding
import com.zexceed.tokoku.models.remote.response.users.UserResponse
import com.zexceed.tokoku.util.AuthPreferences
import com.zexceed.tokoku.util.Constants
import com.zexceed.tokoku.util.Constants.createImageProgress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding!!
    private lateinit var preferences: AuthPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = AuthPreferences(requireActivity())

        binding.apply {
            val req = ApiConfig(Constants.API_BASE_URL).apiService.getUser(
                    preferences.getUserId()
            )

            req.enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val res = response.body()
                    if (response.code() == 200) {
                        Glide.with(view)
                            .load(res?.image)
                            .placeholder(view.context.createImageProgress())
                            .error(android.R.color.darker_gray)
                            .into(imgProfile)
                        tvName.text = "${res?.firstName} ${res?.lastName}"
                        tvPhone.text = res?.phone
                        tvAddress.text = res?.address?.address
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

            btnLogout.setOnClickListener {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                preferences.removeAuth()
                startActivity(intent)
            }
        }
    }
}