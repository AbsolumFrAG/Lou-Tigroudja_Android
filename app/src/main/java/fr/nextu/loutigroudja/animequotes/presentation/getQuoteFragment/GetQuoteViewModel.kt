package fr.nextu.loutigroudja.animequotes.presentation.getQuoteFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import fr.nextu.loutigroudja.animequotes.data.retrofit.QuoteRepositoryImpl
import fr.nextu.loutigroudja.animequotes.data.room.QuoteDao
import fr.nextu.loutigroudja.animequotes.data.room.QuoteDbModel
import fr.nextu.loutigroudja.animequotes.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetQuoteViewModel(
    private val dao: QuoteDao,
    private val quoteRepositoryImpl: QuoteRepositoryImpl
) : ViewModel() {
    var newQuote = ""
    var newCharacter = ""
    var newAnime = ""

    fun getQuoteFromApi() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = quoteRepositoryImpl.getQuote()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Erreur"))
        }
    }

    fun addQuote() {
        viewModelScope.launch {
            val quote = QuoteDbModel()
            quote.quoteText = newQuote
            quote.quoteAuthor = newCharacter
            quote.anime = newAnime
            dao.insert(quote)
        }
    }
}