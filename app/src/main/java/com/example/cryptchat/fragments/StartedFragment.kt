package com.example.cryptchat.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptchat.R
import com.example.cryptchat.activities.MainActivity
import com.example.cryptchat.mvp.presenters.StartedPresenter
import com.example.cryptchat.mvp.presenters.impl.StartedPresenterImpl
import com.example.cryptchat.mvp.views.StartedView
import kotlinx.android.synthetic.main.fragment_started.*

class StartedFragment : BaseFragment(), StartedView {

    private lateinit var mPresenter: StartedPresenter
    private lateinit var mContext : Context

    companion object{
        fun newInstance() : StartedFragment{
            return StartedFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_started, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListeners()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(StartedPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnStart.setOnClickListener {
            mPresenter.onTapStart()
        }
    }

    override fun navigateToEnterPhone() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, EnterPhoneNoFragment.newInstance()).commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

}