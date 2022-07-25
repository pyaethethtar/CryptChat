package com.example.cryptchat.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cryptchat.R
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.mvp.presenters.ProfilePresenter
import com.example.cryptchat.mvp.presenters.impl.ProfilePresenterImpl
import com.example.cryptchat.mvp.views.ProfileView
import com.example.cryptchat.utils.USER_AUTHOR
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity(), ProfileView {

    private lateinit var mUserType : String
    private var mUserId : String = ""
    private lateinit var mPresenter : ProfilePresenter

    companion object{
        const val EXTRA_USER_TYPE = "EXTRA_USER_TYPE"
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
        fun newIntent(context: Context, userType : String) : Intent{
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra(EXTRA_USER_TYPE, userType)
            return intent
        }

        fun newIntent(context: Context, userType : String, userId: String) : Intent{
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra(EXTRA_USER_TYPE, userType)
            intent.putExtra(EXTRA_USER_ID, userId)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mUserType = intent.getStringExtra(EXTRA_USER_TYPE) ?: USER_AUTHOR
        mUserId = intent.getStringExtra(EXTRA_USER_ID) ?: ""
        setUpPresenter()
        setUpListeners()
        showProfileTitle()
        mPresenter.onUiReady(mUserId)
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(ProfilePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnBack.setOnClickListener {
            mPresenter.onTapBack()
        }
        btnEdit.setOnClickListener {
            mPresenter.onTapEdit()
        }
    }

    private fun showProfileTitle(){
        if (mUserType== USER_AUTHOR){
            tvProfileTitle.visibility = View.VISIBLE
            btnEdit.visibility = View.VISIBLE
        }
        else{
            tvProfileTitle.visibility = View.INVISIBLE
            btnEdit.visibility = View.INVISIBLE
        }
    }

    override fun displayProfile(profile: UserVO) {
        tvProfileName.text = profile.firstName+" "+profile.lastName
        tvPhoneNo.text = profile.phoneNo
        if (profile.profileImage!=null && profile.profileImage!=""){
            Glide.with(this).load(profile.profileImage).circleCrop().into(ivProfileImage)
        }
    }

    override fun navigateToBack() {
        this.finish()
    }

    override fun navigateToProfileEdit() {
        startActivity(ProfileEditActivity.newIntent(this))
    }

}