package com.elnemr.floatingwindows

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<NoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFloatingService(Constants.SERVICE_START)

        initViews()
        loadNotes()
    }

    private fun loadNotes() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.loadItemsFromDb().buffer().collect {
                updateAdapter(it)
            }
        }
    }

    private fun updateAdapter(notes: List<NoteEntity>) {
        Toast.makeText(this@MainActivity, "${notes.size}", Toast.LENGTH_SHORT).show()
    }


    private fun initViews() {
        findViewById<Button>(R.id.btn_send).setOnClickListener {
            viewModel.insertNote(findViewById<EditText>(R.id.et_note).text.toString())
        }
    }

}