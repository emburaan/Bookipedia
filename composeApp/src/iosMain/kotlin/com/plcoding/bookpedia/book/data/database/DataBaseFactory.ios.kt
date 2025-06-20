@file:OptIn(ExperimentalForeignApi::class)

package com.plcoding.bookpedia.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSFileManagerItemReplacementUsingNewMetadataOnly
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDataBase> {
        val dbFile = documentDirectory() + "/${FavouriteBookDataBase.DB_NAME}"
        return Room.databaseBuilder<FavouriteBookDataBase>(
            name = dbFile
        )
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            appropriateForURL = null,
            inDomain = NSUserDomainMask,
            create = false,
            error = null
        )

        return requireNotNull(documentDirectory?.path())
    }
}