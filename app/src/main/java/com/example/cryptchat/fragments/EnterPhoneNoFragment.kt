package com.example.cryptchat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cryptchat.R
import com.example.cryptchat.mvp.presenters.EnterPhoneNoPresenter
import com.example.cryptchat.mvp.presenters.impl.EnterPhoneNoPresenterImpl
import com.example.cryptchat.mvp.views.EnterPhoneNoView
import kotlinx.android.synthetic.main.fragment_enter_phone_no.*

class EnterPhoneNoFragment : BaseFragment(), EnterPhoneNoView {

    private lateinit var mPresenter: EnterPhoneNoPresenter

    companion object{
        fun newInstance() : EnterPhoneNoFragment{
            return EnterPhoneNoFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_phone_no, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListeners()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(EnterPhoneNoPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnContinue.setOnClickListener {
            mPresenter.onTapContinue(etPhoneNo.text.toString())
        }
    }

    override fun navigateToOtpScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, OtpFragment.newInstance())
            .commit()    }
}