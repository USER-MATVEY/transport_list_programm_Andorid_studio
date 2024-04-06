package com.example.transport_programm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Character (
    val id: Long,
    var name: String,
    val isCustom: Boolean,
    val type: String,
    var axesCount: Int,
    var liftingCapacity: Int
): Parcelable