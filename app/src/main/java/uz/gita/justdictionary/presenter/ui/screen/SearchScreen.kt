package uz.gita.justdictionary.presenter.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
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
    private val REQ_CODE_SPEECH_INPUT = 100
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // checkPermission
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), 1)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerWords.adapter = adapter
        binding.recyclerWords.layoutManager = LinearLayoutManager(requireContext())
        viewModel.loadAllWords()

        adapter.setOnClickRememberListener { _id, _isRemember, _cursor ->
            viewModel.onCLickRememberBtn(_id,_isRemember)
        }


        // Searchview
        binding.searchView.apply {
            setIconifiedByDefault(false)
            onActionViewExpanded()
            doOnLayout { clearFocus() }
            requestFocusFromTouch()
//            isIconified = false
            setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(handler != null )handler.removeCallbacksAndMessages(null)
                    if(query.isNullOrBlank()) viewModel.loadAllWords()
                    else {
                        viewModel.searchWord(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(handler != null )handler.removeCallbacksAndMessages(null)
                    if(handler != null )handler.postDelayed({
                        if(newText.isNullOrBlank()) viewModel.loadAllWords()
                        else {
                            viewModel.searchWord(newText)
                        }
                    }, 500)
                    return true
                }
            })
        }


        // Voice Button
        binding.voiceButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            try {
                activityResultLauncher.launch(intent)
            } catch (e: ActivityNotFoundException){
                Toast.makeText(requireContext(), "Device not support google voice api", Toast.LENGTH_SHORT).show()
            }
        }

        // activityLauncher
        activityResultLauncher = requireActivity().registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if(it.resultCode == AppCompatActivity.RESULT_OK && it.data != null) {
                val speechText = it.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                Log.d("TTT", speechText?.get(0)?.toString() ?: "null")
                binding.searchView.setQuery(speechText?.get(0) ?: "", true)
            }
        }

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

        viewModel.completeLiveData.observe(viewLifecycleOwner, Observer<Unit>{
            viewModel.searchWord(binding.searchView.query.toString())
        })

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer<Boolean>{
            if(it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

    } // end of OnViewCreated

    override fun onDestroy() {
        super.onDestroy()
        if(handler != null ) handler.removeCallbacksAndMessages(null)
    }
}