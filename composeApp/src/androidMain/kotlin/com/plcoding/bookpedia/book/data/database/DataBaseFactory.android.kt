package com.plcoding.bookpedia.book.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDataBase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(FavouriteBookDataBase.DB_NAME)

        return Room.databaseBuilder(
            context = context,
            name = dbFile.absolutePath
        )
    }
}