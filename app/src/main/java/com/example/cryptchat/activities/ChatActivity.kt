package com.example.cryptchat.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cryptchat.R
import com.example.cryptchat.adapters.MessagesAdapter
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.fragments.EnterNameFragment
import com.example.cryptchat.mvp.presenters.ChatScreenPresenter
import com.example.cryptchat.mvp.presenters.impl.ChatScreenPresenterImpl
import com.example.cryptchat.mvp.views.ChatScreenView
import com.example.cryptchat.utils.USER_OTHER
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.btnCamera
import kotlinx.android.synthetic.main.activity_chat.ivProfileImage
import kotlinx.android.synthetic.main.fragment_enter_name.*
import java.io.IOException

class ChatActivity : BaseActivity(), ChatScreenView {

    private lateinit var mPresenter: ChatScreenPresenter
    private lateinit var mAdapter : MessagesAdapter
    private lateinit var mChatId : String
    private var mOtherUserId : String = " "

    companion object{
        const val PICK_IMAGE_REQUEST = 2222
        const val CHAT_ID_EXTRA = "CHAT_ID_EXTRA"
        fun newIntent(context: Context, chatId : String) : Intent{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(CHAT_ID_EXTRA, chatId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mChatId = intent.getStringExtra(CHAT_ID_EXTRA) ?: " "

        setUpPresenter()
        setUpAdapter()
        setUpListeners()
        mPresenter.onUiReady(mChatId)

    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(ChatScreenPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpAdapter(){
        mAdapter = MessagesAdapter()
        rvChatMessages.adapter = mAdapter
        rvChatMessages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpListeners(){
        btnProfileImage.setOnClickListener {
            mPresenter.onTapOtherProfile(mOtherUserId)
        }
        btnBack.setOnClickListener {
            mPresenter.onTapBack()
        }
        btnCamera.setOnClickListener {
            mPresenter.onTapCamera()
        }
        btnSend.setOnClickListener {
            mPresenter.onTapSend(etMessage.text.toString())
        }
    }

    override fun displayUserInfo(info: UserVO){
        mOtherUserId = info.userId
        tvAccountName.text = info.firstName+" "+info.lastName
        tvProfileName.text = info.firstName+" "+info.lastName
        tvPhoneNo.text = info.phoneNo
        if (info.profileImage!=null && info.profileImage!=""){
            Glide.with(this).load(info.profileImage).circleCrop().into(btnProfileImage)
            Glide.with(this).load(info.profileImage).circleCrop().into(ivProfileImage)
        }
    }

    override fun displayMessages(messages: List<MessageVO>) {
        mAdapter.setNewData(messages)
    }

    override fun navigateToUserProfile(userId: String) {
        startActivity(ProfileActivity.newIntent(this, USER_OTHER, userId))
    }

    override fun navigateToBack() {
        this.finish()
    }

    override fun clearInputTextBox() {
        etMessage.text?.clear()
    }

    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== PICK_IMAGE_REQUEST && resultCode== Activity.RESULT_OK){
            if (data==null || data.data==null){
                return
            }
            val filepath = data.data
            try {
                filepath?.let {
                    if (Build.VERSION.SDK_INT>=29){
                        val source : ImageDecoder.Source = ImageDecoder.createSource(contentResolver, filepath)
                        val mProfileImage = ImageDecoder.decodeBitmap(source)
                        mPresenter.onTapSendImage(mProfileImage)
                    }
                    else{
                        val mProfileImage = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                        mPresenter.onTapSendImage(mProfileImage)
                    }
                }
            }
            catch (e : IOException){
                e.printStackTrace()
            }
            //mPresenter.onTapSendImage(mProfileImage!!)
        }


    }
}