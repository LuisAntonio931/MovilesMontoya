package com.example.upp_jobs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val imageResource: Int? = null,
    val videoUri: String? = null
) : Parcelable
