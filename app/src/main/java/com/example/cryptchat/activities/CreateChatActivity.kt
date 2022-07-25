package com.example.cryptchat.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.cryptchat.R
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.fragments.EmptyFragment
import com.example.cryptchat.fragments.PeopleFragment
import com.example.cryptchat.mvp.presenters.CreateChatPresenter
import com.example.cryptchat.mvp.presenters.impl.CreateChatPresenterImpl
import com.example.cryptchat.mvp.views.CreateChatView
import com.example.cryptchat.utils.EMPTY_PEOPLE_DESCRIPTION
import com.example.cryptchat.utils.EMPTY_PEOPLE_TITLE
import com.example.cryptchat.view.viewpods.PeopleViewPod
import kotlinx.android.synthetic.main.activity_create_chat.*

class CreateChatActivity : CreateChatView, BaseActivity() {

    private lateinit var mPresenter : CreateChatPresenter

    companion object{
        fun newIntent(context: Context) : Intent{
            val intent = Intent(context, CreateChatActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_chat)

        setUpPresenter()
        setUpListeners()
        mPresenter.onUiReady()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(CreateChatPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }


    private fun setUpListeners(){
        btnCancel.setOnClickListener {
            mPresenter.onTapCancel()
        }
    }

    override fun displayPeopleFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flPeople, PeopleFragment.newInstance())
            .commit()
    }

    override fun displayEmptyFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flPeople, EmptyFragment.newInstance(EMPTY_PEOPLE_TITLE, EMPTY_PEOPLE_DESCRIPTION))
            .commit()
    }

    override fun navigateToBack() {
        this.finish()
    }


}