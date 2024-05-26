package fr.nextu.loutigroudja.animequotes.presentation.getQuoteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import fr.nextu.loutigroudja.animequotes.R
import fr.nextu.loutigroudja.animequotes.data.retrofit.QuoteApiHelper
import fr.nextu.loutigroudja.animequotes.data.retrofit.RetrofitBuilder
import fr.nextu.loutigroudja.animequotes.data.room.QuoteDatabase
import fr.nextu.loutigroudja.animequotes.databinding.FragmentGetQuoteBinding
import fr.nextu.loutigroudja.animequotes.domain.Quote
import fr.nextu.loutigroudja.animequotes.presentation.savedQuotesFragment.SavedQuotesFragment
import fr.nextu.loutigroudja.animequotes.utils.Status

class GetQuoteFragment : Fragment() {
    private var _binding: FragmentGetQuoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GetQuoteViewModel
    private lateinit var bGetNewQuote: Button
    private lateinit var bAdd: Button
    private lateinit var iBSaved: ImageButton

    private var _dataFromApi: Quote? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetQuoteBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dao = QuoteDatabase.getInstance(application).quoteDao
        val newQuotesViewModelFactory =
            GetQuoteViewModelFactory(dao, QuoteApiHelper(RetrofitBuilder.quoteApiService))
        viewModel =
            ViewModelProvider(this, newQuotesViewModelFactory)[GetQuoteViewModel::class.java]

        bGetNewQuote = binding.bGetNewQuote
        bAdd = binding.bAdd
        iBSaved = binding.iBSaved

        setupObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bGetNewQuote.setOnClickListener {
            setupObservers()
            if (_dataFromApi != null) {
                showNewQuote(binding.tvQuote, binding.tvCharacter, binding.tvAnime)
                binding.tvQuote.isVisible = true
                binding.tvCharacter.isVisible = true
                binding.tvAnime.isVisible = true
                binding.tvDescription.isGone = true
                bAdd.isEnabled = true
            } else {
                Toast.makeText(context, "Aucune citation disponible", Toast.LENGTH_SHORT).show()
            }
        }

        bAdd.setOnClickListener {
            viewModel.newQuote = binding.tvQuote.text.toString()
            viewModel.newCharacter = binding.tvCharacter.text.toString()
            viewModel.newAnime = binding.tvAnime.text.toString()
            viewModel.addQuote()
            Toast.makeText(context, "Citation ajoutée", Toast.LENGTH_SHORT).show()
        }

        iBSaved.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, SavedQuotesFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        viewModel.getQuoteFromApi().observe(viewLifecycleOwner) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        _dataFromApi = resource.data
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showNewQuote(tvQuote: TextView, tvCharacter: TextView, tvAnime: TextView) {
        _dataFromApi?.let {
            tvQuote.text = it.quote
            tvCharacter.text = it.author
            tvAnime.text = it.from
        } ?: run {
            Toast.makeText(context, "Les données de la citation sont null", Toast.LENGTH_SHORT).show()
        }
    }
}