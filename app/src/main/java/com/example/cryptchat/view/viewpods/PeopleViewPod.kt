package com.example.cryptchat.view.viewpods

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptchat.adapters.PeopleAdapter
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.mvp.presenters.PeopleListPresenter
import com.example.cryptchat.mvp.presenters.impl.PeopleListPresenterImpl
import kotlinx.android.synthetic.main.viewpod_people.view.*

class PeopleViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : NestedScrollView(context, attrs) {

    private lateinit var mAdapter : PeopleAdapter
    private val mPresenter : PeopleListPresenter = PeopleListPresenterImpl()

    override fun onFinishInflate() {
        super.onFinishInflate()

        setUpAdapter()
    }

    private fun setUpAdapter(){
        mAdapter = PeopleAdapter(mPresenter)
        rvPeople.adapter = mAdapter
        rvPeople.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    fun displayPeople(people : List<UserVO>){
        mAdapter.setNewData(people)
    }
}