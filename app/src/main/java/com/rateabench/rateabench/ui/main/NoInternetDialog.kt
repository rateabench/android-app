package com.rateabench.rateabench.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.rateabench.rateabench.R
import timber.log.Timber

class NoInternetDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.no_internet).setPositiveButton(R.string.try_again) { dialog, id ->
                Timber.i("Positive: $id")
            }.setNegativeButton(R.string.exit) { dialog, id ->
                Timber.i("Positive: $id")
            }
            builder.create()
        }
    }
}