package com.example.messenger.viewmodel.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConvoListData(
    val user: User = User()
) : Parcelable


@Parcelize
data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val imgUrl: String = "",
    val online: Boolean = false
) : Parcelable
