package com.example.cryptchat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cryptchat.R
import com.example.cryptchat.mvp.presenters.OtpPresenter
import com.example.cryptchat.mvp.presenters.impl.OtpPresenterImpl
import com.example.cryptchat.mvp.views.OtpView
import com.example.cryptchat.utils.OTP_DESCRIPTION
import kotlinx.android.synthetic.main.fragment_otp.*

class OtpFragment : BaseFragment(), OtpView {

    private lateinit var mPresenter : OtpPresenter

    companion object{
        fun newInstance() : OtpFragment{
            return OtpFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListeners()
        mPresenter.onUiReady(requireActivity())
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(OtpPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnVerify.setOnClickListener {
            mPresenter.onTapVerify(etOtp.text.toString())
        }
    }

    override fun displayPhoneNumber(phoneNO: String) {
        tvDescription.text = OTP_DESCRIPTION+phoneNO
    }


    override fun navigateToEnterName() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, EnterNameFragment.newInstance())
            .commit()
    }


}