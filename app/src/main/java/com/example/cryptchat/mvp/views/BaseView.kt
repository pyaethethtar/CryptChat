package com.example.cryptchat.mvp.views

import android.content.Context

interface BaseView {

    fun displayError(msg : String , title : String ? = "Error", onTapOkay : () -> Unit)
    fun displaySuccessDialog(msg : String , title: String? = "Successful!", onTapOkay : () -> Unit)
    fun displayAlertDialog(msg : String , title: String? = "Information", onTapOkay : () -> Unit)
    fun displayLoading(msg : String? = null)
    fun hideLoading()
}