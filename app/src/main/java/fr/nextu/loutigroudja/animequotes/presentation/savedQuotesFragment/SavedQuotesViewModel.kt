package fr.nextu.loutigroudja.animequotes.presentation.savedQuotesFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.nextu.loutigroudja.animequotes.data.room.QuoteDao
import fr.nextu.loutigroudja.animequotes.data.room.QuoteDbModel
import kotlinx.coroutines.launch

class SavedQuotesViewModel(private val dao: QuoteDao) : ViewModel() {
    val quotesFromDb = dao.getAll()

    fun deleteQuote(quoteDbModel: QuoteDbModel) {
        viewModelScope.launch {
            dao.delete(quoteDbModel)
        }
    }
}