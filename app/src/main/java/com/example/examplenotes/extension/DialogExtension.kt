package com.example.examplenotes.extension

import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showDialog(
    @StringRes title: Int,
    @StringRes description: Int,
    @StringRes positiveButton: Int,
    @StringRes negativeButton: Int,
    onClickPositiveButton: () -> Unit,
): AlertDialog =
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(description)
        .setPositiveButton(positiveButton) { dialog: DialogInterface, _: Int ->
            onClickPositiveButton()
            dialog.dismiss()
        }
        .setNegativeButton(negativeButton) { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        .show()
