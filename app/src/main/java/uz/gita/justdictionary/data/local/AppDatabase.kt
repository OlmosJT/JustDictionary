package uz.gita.justdictionary.data.local

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.justdictionary.data.local.dao.DictionaryDao
import uz.gita.justdictionary.data.local.entity.DictionaryEntity
import javax.inject.Inject

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun DictionaryDao(): DictionaryDao
}