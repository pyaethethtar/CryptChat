package com.example.cryptchat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cryptchat.R
import com.example.cryptchat.mvp.presenters.CreatePinPresenter
import com.example.cryptchat.mvp.presenters.impl.CreatePinPresenterImpl
import com.example.cryptchat.mvp.views.CreatePinView
import kotlinx.android.synthetic.main.fragment_create_pin.*

class CreatePinFragment : BaseFragment(), CreatePinView {

    private lateinit var mPresenter : CreatePinPresenter

    companion object{
        fun newInstance() : CreatePinFragment{
            return CreatePinFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_pin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListeners()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(CreatePinPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnNext.setOnClickListener {
            mPresenter.onTapNext(etPin.text.toString())
        }
    }

    override fun navigateToConfirmPin() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, ConfirmPinFragment.newInstance())
            .commit()
    }


}