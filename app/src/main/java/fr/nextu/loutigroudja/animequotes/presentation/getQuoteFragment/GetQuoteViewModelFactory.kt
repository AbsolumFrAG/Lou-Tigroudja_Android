package fr.nextu.loutigroudja.animequotes.presentation.getQuoteFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.nextu.loutigroudja.animequotes.data.retrofit.QuoteApiHelper
import fr.nextu.loutigroudja.animequotes.data.retrofit.QuoteRepositoryImpl
import fr.nextu.loutigroudja.animequotes.data.room.QuoteDao

class GetQuoteViewModelFactory(
    private val dao: QuoteDao,
    private val quoteApiHelper: QuoteApiHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetQuoteViewModel::class.java)) {
            return GetQuoteViewModel(dao, QuoteRepositoryImpl(quoteApiHelper)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}