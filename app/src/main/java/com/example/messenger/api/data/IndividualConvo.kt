package com.example.messenger.api.data

import android.os.Parcelable
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