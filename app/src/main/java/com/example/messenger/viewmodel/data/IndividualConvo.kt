package com.example.messenger.viewmodel.data

import android.os.Parcelable
import android.text.BoringLayout
import kotlinx.parcelize.Parcelize

@Parcelize
data class IndividualConvo(
    val messages: MutableList<Messages> = arrayListOf()
) : Parcelable

@Parcelize
data class Messages(
    val isMine: Boolean = true,
    val messages: String = ""
) : Parcelable