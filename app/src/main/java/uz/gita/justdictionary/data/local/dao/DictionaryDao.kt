package uz.gita.justdictionary.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM entries")
    fun getAllWords(): Cursor

    @Query("UPDATE entries SET isRemember = 1 WHERE id =:id")
    fun rememberWord(id: Int)

    @Query("UPDATE entries SET isRemember = 0 WHERE id =:id")
    fun forgetWord(id: Int)

    @Query("SELECT * FROM entries WHERE word LIKE '%' || :query || '%' ")
    fun searchWord(query: String): Cursor
}