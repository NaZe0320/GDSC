package com.gdsc.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class EmployeeDatabase: RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE : EmployeeDatabase ?= null
        //SingleTon 방식

        fun getInstance(context: Context) :EmployeeDatabase {

            //직렬화 (한번에 1개씩만 처리하도록 해주는 함수)
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract fun employeeDao() : EmployeeDao


}