package com.example.cryptchat.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cryptchat.R
import com.example.cryptchat.activities.MainActivity
import com.example.cryptchat.mvp.presenters.ConfirmPinPresenter
import com.example.cryptchat.mvp.presenters.impl.ConfirmPinPresenterImpl
import com.example.cryptchat.mvp.views.ConfirmPinView
import kotlinx.android.synthetic.main.fragment_confirm_pin.*


class ConfirmPinFragment : BaseFragment(), ConfirmPinView {

    private lateinit var mContext: Context
    private lateinit var mPresenter : ConfirmPinPresenter

    companion object{
        fun newInstance() : ConfirmPinFragment{
            return ConfirmPinFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_pin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListeners()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(ConfirmPinPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnNext.setOnClickListener {
            mPresenter.onTapConfirm(etConfirmPin.text.toString())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun navigateToMainScreen() {
        startActivity(MainActivity.newIntent(mContext))
    }


}