package com.gdsc.a7minutesworkout

import android.app.Application
import com.gdsc.a7minutesworkout.database.HistoryDatabase

class WorkOutApp: Application() {
    val db: HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}