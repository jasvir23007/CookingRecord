package com.jasvir.cookingrecord.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val comment: String,
    val recipe_type: String,
    val description: String,
    val image_url: String,
    val recorded_at: String

) : Parcelable