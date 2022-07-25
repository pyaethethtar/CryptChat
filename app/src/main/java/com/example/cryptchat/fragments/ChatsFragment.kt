package com.example.cryptchat.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptchat.R
import com.example.cryptchat.activities.ChatActivity
import com.example.cryptchat.adapters.ChatsAdapter
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.mvp.presenters.ChatListPresenter
import com.example.cryptchat.mvp.presenters.impl.ChatListPresenterImpl
import com.example.cryptchat.mvp.views.ChatListView
import com.example.cryptchat.utils.EMPTY_CHAT_DESCRIPTION
import com.example.cryptchat.utils.EMPTY_CHAT_TITLE
import kotlinx.android.synthetic.main.fragment_chats.*


class ChatsFragment : BaseFragment(), ChatListView {

    private lateinit var mContext: Context
    private lateinit var mAdapter : ChatsAdapter
    private lateinit var mPresenter: ChatListPresenter

    companion object{
        fun newInstance(): ChatsFragment{
            return ChatsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpAdapter()
        mPresenter.onUiReady()
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this).get(ChatListPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpAdapter(){
        mAdapter = ChatsAdapter(mPresenter)
        rvChats.adapter = mAdapter
        rvChats.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
    }

    override fun displayChatList(chatList: List<ChatVO>) {
        mAdapter.setNewData(chatList)
    }

    override fun displayEmptyList() {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, EmptyFragment.newInstance(EMPTY_CHAT_TITLE, EMPTY_CHAT_DESCRIPTION))
                .commit()
        }
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.flContainer, EmptyFragment.newInstance(EMPTY_CHAT_TITLE, EMPTY_CHAT_DESCRIPTION))
//            .commit()
    }

    override fun navigateToChatScreen(id: String) {
        startActivity(ChatActivity.newIntent(mContext, id))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


}