package com.elnemr.floatingwindows

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.elnemr.floatingwindows.adapter.base.OnItemClickInterface
import com.elnemr.floatingwindows.adapter.run.NoteAdapter
import com.elnemr.floatingwindows.databinding.ActivityMainBinding
import com.elnemr.floatingwindows.db.NoteEntity
import com.elnemr.floatingwindows.util.Constants
import com.elnemr.floatingwindows.util.startFloatingService
import com.elnemr.floatingwindows.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClickInterface{

    private val viewModel by viewModels<NoteViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startFloatingService(Constants.SERVICE_START)

        initAdapter()
        initViews()
        loadNotes()
    }

    private fun initAdapter() {
        adapter = NoteAdapter(this)
        binding.rvNotes.adapter = adapter
    }

    private fun loadNotes() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.loadItemsFromDb().buffer().collect {
                updateAdapter(it)
            }
        }
    }

    private fun updateAdapter(notes: List<NoteEntity>) {
        adapter.setDataList(notes)
    }

    private fun initViews() {
        binding.btnSend.setOnClickListener {
            viewModel.insertNote(binding.etNote.text.toString())
        }
    }

    override fun onDeleteClicked(note: NoteEntity) {
        viewModel.deleteNote(note)
    }

}