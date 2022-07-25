package com.example.cryptchat.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.cryptchat.R
import com.example.cryptchat.mvp.views.BaseView

abstract class BaseFragment : Fragment(), BaseView {

    private var mAlertDialog : SweetAlertDialog?= null
    private var mErrorDialog : SweetAlertDialog?= null
    private var mSuccessDialog : SweetAlertDialog?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun displayError(msg: String, title: String?, onTapOkay: () -> Unit) {
        hideLoading()

        mErrorDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
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

    override fun displaySuccessDialog(msg: String, title: String?, onTapOkay: () -> Unit) {
        hideLoading()

        mSuccessDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
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

        mAlertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.NORMAL_TYPE)
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
        }    }

    override fun displayLoading(msg: String?) {
        mAlertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        mAlertDialog?.let {dialog->
            dialog.titleText = msg ?: "Loading"
            dialog.setCancelable(false)
            dialog.progressHelper.barColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
            dialog.show()
        }
    }

    override fun hideLoading() {
        mAlertDialog?.hide()
    }

    override fun onPause() {
        super.onPause()

        hideLoading()
    }

    override fun onStop() {
        super.onStop()

        mAlertDialog?.dismiss()
        mErrorDialog?.dismiss()
        mSuccessDialog?.dismiss()
    }

}