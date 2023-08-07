package com.darwish.currency.core.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.darwish.currency.R
import com.darwish.currency.core.network.RequestError
import com.darwish.currency.databinding.DialogPopupErrorBinding

object DialogUtils {

    fun showErrorPopup(
        context: Context,
        retryListener: () -> Unit,
        cancelListener: () -> Unit?,
        error: RequestError?
    ): AlertDialog {
        val layout = DialogPopupErrorBinding.inflate(LayoutInflater.from(context), null, false)

        val alertDialog = AlertDialog.Builder(context).apply {
            setView(layout.root)
        }.create()

        alertDialog.apply {
            layout.tvError.text = error?.getErrorTitle(context)
            layout.tvMessageError.text = error?.getErrorMessage(context)

            layout.btnErrorRetry.setOnClickListener {
                retryListener.invoke()
                dismiss()
            }

            layout.apply {
                btnErrorCancel.setOnClickListener {
                    cancelListener.invoke()
                    dismiss()
                }
//                if (isCancelable)
//                    btnErrorCancel.visibility = View.VISIBLE
//                else
//                    btnErrorCancel.visibility = View.GONE
            }

            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }.show()

        return alertDialog

    }


}
