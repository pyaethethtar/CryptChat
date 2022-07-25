package com.example.cryptchat.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptchat.R
import com.example.cryptchat.activities.ChatActivity
import com.example.cryptchat.activities.MainActivity
import com.example.cryptchat.adapters.PeopleAdapter
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.mvp.presenters.PeopleListPresenter
import com.example.cryptchat.mvp.presenters.impl.PeopleListPresenterImpl
import com.example.cryptchat.mvp.views.PeopleListView
import com.example.cryptchat.utils.EMPTY_CHAT_DESCRIPTION
import com.example.cryptchat.utils.EMPTY_CHAT_TITLE
import com.example.cryptchat.utils.EMPTY_PEOPLE_DESCRIPTION
import com.example.cryptchat.utils.EMPTY_PEOPLE_TITLE
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.fragment_people.*


class PeopleFragment : BaseFragment(), PeopleListView {

    private lateinit var mContext: Context
    private lateinit var mPresenter : PeopleListPresenter
    private lateinit var mAdapter : PeopleAdapter

    companion object{
        const val CONTACT_LIST_REQUEST = 3333
        fun newInstance() : PeopleFragment{
            return PeopleFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpAdapter()
        mPresenter.onUiReady(getContactList())

    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(PeopleListPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpAdapter(){
        mAdapter = PeopleAdapter(mPresenter)
        rvPeople.adapter = mAdapter
        rvPeople.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
    }


    override fun displayPeopleList(people: List<UserVO>) {
        mAdapter.setNewData(people)
    }

    override fun displayEmptyList() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, EmptyFragment.newInstance(EMPTY_PEOPLE_TITLE, EMPTY_PEOPLE_DESCRIPTION))
            .commit()
    }

    override fun navigateToChatScreen(chatId: String) {
        mContext.startActivity(ChatActivity.newIntent(mContext, chatId))
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("Range")
    private fun getContactList() : ArrayList<String>{
        val contactList = arrayListOf<String>()
        if(ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            val phones = mContext.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}