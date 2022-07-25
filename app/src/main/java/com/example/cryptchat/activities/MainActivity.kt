package com.example.cryptchat.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cryptchat.R
import com.example.cryptchat.fragments.ChatsFragment
import com.example.cryptchat.fragments.PeopleFragment
import com.example.cryptchat.mvp.presenters.MainPresenter
import com.example.cryptchat.mvp.presenters.impl.MainPresenterImpl
import com.example.cryptchat.mvp.views.MainView
import com.example.cryptchat.utils.USER_AUTHOR
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    private lateinit var mPresenter : MainPresenter

    companion object{
        const val CONTACT_LIST_REQUEST = 3333
        fun newIntent(context: Context) : Intent{
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter()
        setUpNavigation()
        setUpListeners()
        mPresenter.onUiReady(getContactList())

    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(MainPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners(){
        btnCreate.setOnClickListener {
            startActivity(CreateChatActivity.newIntent(this))
        }

        btnProfile.setOnClickListener {
            startActivity(ProfileActivity.newIntent(this, USER_AUTHOR))
        }
    }

    private fun setUpNavigation(){
        openFragment(ChatsFragment.newInstance())

        btmNavi.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuChats -> openFragment(ChatsFragment.newInstance())
                R.id.menuPeople -> openFragment(PeopleFragment.newInstance())
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
    }

    override fun navigateToRegister() {
        startActivity(RegisterActivity.newIntent(this))
    }

    override fun displayProfileImage(image: String?) {
        if(image!=null && image!=""){
            Glide.with(this).load(image).circleCrop().into(btnProfile)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("Range")
    private fun getContactList() : ArrayList<String>{
        val contactList = arrayListOf<String>()
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
            while (phones!!.moveToNext()) {
                //val name = phones.getString(phones.getColumnIndex(Phone.DISPLAY_NAME))
                val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactList.add(phoneNumber)
            }
            phones.close()
        }
        else{
            requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), CONTACT_LIST_REQUEST)
        }

        return contactList
    }

}