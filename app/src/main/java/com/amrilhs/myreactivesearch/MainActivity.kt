package com.amrilhs.myreactivesearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.amrilhs.myreactivesearch.core.ui.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val edPlace = findViewById<AutoCompleteTextView>(R.id.ed_place)
        val viewModel: MainViewModel by viewModels()
        edPlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(s.toString())
                }
            }
        })

        viewModel.searchResult.observe(this, androidx.lifecycle.Observer { placesItem ->
            val placeName = arrayListOf<String?>()
            placesItem.map {
                placeName.add(it.placeName)
            }
            val adapter = ArrayAdapter(this@MainActivity,android.R.layout.select_dialog_item,placeName)
            adapter.notifyDataSetChanged()
            edPlace.setAdapter(adapter)
        })
    }
}