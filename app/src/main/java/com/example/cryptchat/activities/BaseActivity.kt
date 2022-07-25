package com.example.cryptchat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.cryptchat.R
import com.example.cryptchat.mvp.views.BaseView

abstract class BaseActivity : AppCompatActivity(), BaseView{

    private var mAlertDialog : SweetAlertDialog ?= null
    private var mErrorDialog : SweetAlertDialog ?= null
    private var mSuccessDialog : SweetAlertDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun displayError(msg: String, title: String?, onTapOkay: () -> Unit) {
        hideLoading()

        mErrorDialog = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        mErrorDialog?.let {dialog->
            dialog.titleText = title
            dialog.contentText = msg
            dialog.confirmText = "OK"
            dialog.setConfirmClickListener {
                onTapOkay.invoke()
                it.dismiss()
            }
            dialog.show()
        }
    }

    override fun displaySuccessDialog(msg: String, title: String?, onTapOkay : () -> Unit) {
        hideLoading()

        mSuccessDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        mSuccessDialog?.let { dialog->
            dialog.titleText = title
            dialog.contentText = msg
            dialog.confirmText = "OK"
            dialog.setConfirmClickListener {
                onTapOkay.invoke()
                it.dismiss()
            }
            dialog.show()
        }
    }

    override fun displayAlertDialog(msg: String, title: String?, onTapOkay : () -> Unit) {
        hideLoading()

        mAlertDialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
        mAlertDialog?.let { dialog->
            dialog.titleText = title
            dialog.contentText = msg
            dialog.confirmText = "OK"
            dialog.cancelText = "Cancel"
            dialog.setConfirmClickListener {
                onTapOkay.invoke()
                it.dismiss()
            }
            dialog.setCancelClickListener {
                it.dismiss()
            }
            dialog.show()
        }
    }

    override fun displayLoading(msg: String?) {
        mAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        mAlertDialog?.let {dialog->
            dialog.titleText = msg ?: "Loading"
            dialog.setCancelable(false)
            dialog.progressHelper.barColor = ContextCompat.getColor(this, R.color.colorPrimary)
            dialog.show()
        }
    }

    override fun hideLoading() {
        mAlertDialog?.hide()
    }

    override fun onStop() {
        super.onStop()

        mAlertDialog?.dismiss()
        mErrorDialog?.dismiss()
        mSuccessDialog?.dismiss()
    }

    override fun onPause() {
        super.onPause()

        hideLoading()
    }
}