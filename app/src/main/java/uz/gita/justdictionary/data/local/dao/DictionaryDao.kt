package uz.gita.justdictionary.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM entries ORDER by isRemember DESC")
    fun getAllWords(): Cursor

    @Query("UPDATE entries set isRemember = 1 where id = :id")
    fun rememberWord(id: Long)

    @Query("UPDATE entries set isRemember = 0 where id = :id")
    fun forgetWord(id: Long)

    @Query("SELECT * FROM entries WHERE word LIKE  :query || '%' ")
    fun searchWord(query: String): Cursor
}