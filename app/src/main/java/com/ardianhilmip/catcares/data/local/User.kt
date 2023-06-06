package com.ardianhilmip.catcares.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class User(
    val name: String? = null,
    val noHp: String? = null,
    val address: String? = null,
    val email: String? = null,
    val uid: String? = null,
)