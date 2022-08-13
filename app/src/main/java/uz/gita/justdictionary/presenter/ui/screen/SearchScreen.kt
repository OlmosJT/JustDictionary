package uz.gita.justdictionary.presenter.ui.screen

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.justdictionary.R
import uz.gita.justdictionary.databinding.ScreenSearchBinding
import uz.gita.justdictionary.presenter.ui.adapter.CursorAdapter
import uz.gita.justdictionary.presenter.viewmodel.SearchViewModel
import uz.gita.justdictionary.presenter.viewmodel.impl.SearchViewModelImpl

@AndroidEntryPoint
class SearchScreen: Fragment(R.layout.screen_search) {
    private val binding by viewBinding(ScreenSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl>()
    private val adapter: CursorAdapter = CursorAdapter()
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerWords.adapter = adapter
        binding.recyclerWords.layoutManager = LinearLayoutManager(requireContext())
        viewModel.loadAllWords()

        adapter.setOnClickRememberListener {
            viewModel.onCLickRememberBtn(id)
        }



        // Searchview
        binding.searchView.isIconified = false
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if(query.isNullOrBlank()) viewModel.loadAllWords()
                else {
                    viewModel.searchWord(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    if(newText.isNullOrBlank()) viewModel.loadAllWords()
                    else {
                        viewModel.searchWord(newText)
                    }
                }, 1000)
                return true
            }
        })




        // Observers
        viewModel.allWordsLiveData.observe(viewLifecycleOwner, Observer<Cursor> {
            if(it.count > 0 && it != null) {
                binding.emptyCursor.visibility = View.GONE
            }
            else {
                binding.emptyCursor.visibility = View.VISIBLE
            }
            adapter.submitCursor(it)
        })

        viewModel.completeLiveData.observe(viewLifecycleOwner, Observer<Unit> {
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }
}