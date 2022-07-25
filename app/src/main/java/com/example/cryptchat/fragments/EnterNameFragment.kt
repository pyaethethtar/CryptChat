package com.example.cryptchat.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.cryptchat.R
import com.example.cryptchat.activities.MainActivity
import com.example.cryptchat.mvp.presenters.EnterNamePresenter
import com.example.cryptchat.mvp.presenters.impl.EnterNamePresenterImpl
import com.example.cryptchat.mvp.views.EnterNameView
import kotlinx.android.synthetic.main.fragment_enter_name.*
import java.io.IOException

class EnterNameFragment : BaseFragment(), EnterNameView {

    private lateinit var mPresenter: EnterNamePresenter
    private lateinit var mContext : Context
    private var mProfileImage : Bitmap?= null

    companion object{
        const val PICK_IMAGE_REQUEST = 1111

        fun newInstance() : EnterNameFragment{
            return EnterNameFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpListeners()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(EnterNamePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnSave.setOnClickListener {
            mPresenter.onTapSave(etFirstName.text.toString(), etLastName.text.toString(), mProfileImage)
        }

        btnCamera.setOnClickListener {
            mPresenter.onTapCamera()
        }
    }

    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    override fun navigateToMainScreen() {
        startActivity(MainActivity.newIntent(mContext))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mContext = context
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
                        val source : ImageDecoder.Source = ImageDecoder.createSource(mContext.contentResolver, filepath)
                        mProfileImage = ImageDecoder.decodeBitmap(source)

                    }
                    else{
                        mProfileImage = MediaStore.Images.Media.getBitmap(mContext.contentResolver, filepath)

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