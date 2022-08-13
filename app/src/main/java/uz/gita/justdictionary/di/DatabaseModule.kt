package uz.gita.justdictionary.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.justdictionary.data.local.AppDatabase
import uz.gita.justdictionary.data.local.dao.DictionaryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @[Provides Singleton]
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "dictionary.db"
        )
            .createFromAsset("dictionary.db")
            .build()

    @[Provides Singleton]
    fun getDictionaryDao(database: AppDatabase): DictionaryDao = database.DictionaryDao()
}