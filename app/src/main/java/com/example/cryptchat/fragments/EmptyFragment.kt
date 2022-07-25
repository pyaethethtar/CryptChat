package com.example.cryptchat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptchat.R
import com.google.protobuf.Empty
import kotlinx.android.synthetic.main.fragment_empty.*

class EmptyFragment : BaseFragment() {

    private var mTitle = ""
    private var mDescription = ""

    companion object{
        const val EMPTY_TITLE_EXTRA = "EMPTY_TITLE_EXTRA"
        const val EMPTY_DESCRIPTION_EXTRA = "EMPTY_DESCRIPTION_EXTRA"
        fun newInstance(title: String, description: String) : EmptyFragment{
            val fragment = EmptyFragment()
            fragment.arguments = Bundle().apply {
                putString(EMPTY_TITLE_EXTRA, title)
                putString(EMPTY_DESCRIPTION_EXTRA, description)
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTitle = arguments?.getString(EMPTY_TITLE_EXTRA)?:"Empty Title"
        mDescription = arguments?.getString(EMPTY_DESCRIPTION_EXTRA)?:"Empty Description"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEmptyTitle.text= mTitle
        tvEmptyDescription.text = mDescription
    }

}