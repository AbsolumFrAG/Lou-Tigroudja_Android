package fr.nextu.loutigroudja.animequotes.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuoteDao {
    @Insert
    suspend fun insert(quoteDbModel: QuoteDbModel)

    @Delete
    suspend fun delete(quoteDbModel: QuoteDbModel)

    @Query("SELECT * FROM quote_table WHERE quote_id = :taskId")
    fun get(taskId: Long): LiveData<QuoteDbModel>

    @Query("SELECT * FROM quote_table ORDER BY quote_id DESC")
    fun getAll(): LiveData<List<QuoteDbModel>>
}