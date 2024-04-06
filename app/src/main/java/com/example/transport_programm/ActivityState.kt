package com.example.transport_programm

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ActivityState(
    var selectedItem: Int,
    var current_color: Int
): Parcelable