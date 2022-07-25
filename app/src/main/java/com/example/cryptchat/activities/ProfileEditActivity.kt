package com.example.cryptchat.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cryptchat.R
import com.example.cryptchat.fragments.EnterNameFragment
import com.example.cryptchat.mvp.presenters.ProfileEditPresenter
import com.example.cryptchat.mvp.presenters.impl.ProfileEditPresenterImpl
import com.example.cryptchat.mvp.views.ProfileEditView
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_profile_edit.btnCamera
import kotlinx.android.synthetic.main.activity_profile_edit.btnSave
import kotlinx.android.synthetic.main.activity_profile_edit.etFirstName
import kotlinx.android.synthetic.main.activity_profile_edit.etLastName
import kotlinx.android.synthetic.main.activity_profile_edit.ivProfileImage
import kotlinx.android.synthetic.main.fragment_enter_name.*
import java.io.IOException

class ProfileEditActivity : BaseActivity(), ProfileEditView {

    private lateinit var mPresenter: ProfileEditPresenter
    private var mProfileImage : Bitmap?= null

    companion object{
        const val PICK_IMAGE_REQUEST = 1111
        fun newIntent(context: Context) : Intent {
            return Intent(context, ProfileEditActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        setUpPresenter()
        setUpListeners()
        mPresenter.onUiReady()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(ProfileEditPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnBack.setOnClickListener {
            mPresenter.onTapBack()
        }

        btnCamera.setOnClickListener {
            mPresenter.onTapCamera()
        }

        btnSave.setOnClickListener {
            mPresenter.onTapSave(etFirstName.text.toString(), etLastName.text.toString(), mProfileImage)
        }
    }

    override fun displayProfileInfo(firstName: String, lastName: String?, profileImage: String?) {
        etFirstName.setText(firstName)
        lastName?.let {
            etLastName.setText(it)
        }
        profileImage?.let {
            Glide.with(this).load(it).into(ivProfileImage)
        }
    }

    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    override fun navigateToBack() {
        this.finish()
    }

    override fun navigateToMain() {
        startActivity(MainActivity.newIntent(this))
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
                        mProfileImage = ImageDecoder.decodeBitmap(source)

                    }
                    else{
                        mProfileImage = MediaStore.Images.Media.getBitmap(contentResolver, filepath)

                    }
                }
            }
            catch (e : IOException){
                e.printStackTrace()
            }
            ivProfileImage.setImageBitmap(mProfileImage)
        }
    }
}