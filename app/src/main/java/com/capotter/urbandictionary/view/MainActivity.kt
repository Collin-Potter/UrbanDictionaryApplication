package com.capotter.urbandictionary.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capotter.urbandictionary.R
import com.capotter.urbandictionary.model.Definition
import com.capotter.urbandictionary.viewmodel.MainViewModel
import com.capotter.urbandictionary.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mainViewModel: MainViewModel? = null
    var mDefinitionAdapter: DefinitionAdapter? = null

    private var wordToDefine: String? = null
    private var orderBy: String = "thumbs_up"

    /**
     * Assign layout and views and set necessary onClickListeners
     * @param savedInstanceState: Bundle including any saved information from previous lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orderByThumbsUp.setOnClickListener {
            Toast.makeText(this@MainActivity, "Ordering by thumbs up", Toast.LENGTH_SHORT).show()
            if(!wordToDefine.isNullOrEmpty()) {
                orderBy = "thumbs_up"
                getDefinitions(wordToDefine!!, orderBy)
            }
        }

        orderByThumbsDown.setOnClickListener {
            Toast.makeText(this@MainActivity, "Ordering by thumbs down", Toast.LENGTH_SHORT).show()
            if(!wordToDefine.isNullOrEmpty()) {
                orderBy = "thumbs_down"
                getDefinitions(wordToDefine!!, orderBy)
            }
        }

        search_button.setOnClickListener {
            if(!searchEditText.text.toString().isNullOrEmpty()) {
                wordToDefine = searchEditText.text.toString()
                mainViewModel = ViewModelProviders.of(this, ViewModelFactory(wordToDefine!!, orderBy)).get(MainViewModel::class.java)
                getDefinitions(wordToDefine!!, orderBy)
            }
        }
        if(!searchEditText.text.toString().isNullOrEmpty()) {
            swiperefresh.setOnRefreshListener { getDefinitions(wordToDefine!!, orderBy) }
        }
    }

    /**
     * Get definitions from mainViewModel based on desired word
     * @param word: String parameter to define
     * @param order: String parameter to set order
     */
    fun getDefinitions(word: String, order: String) {
        wordToDefine = word
        swiperefresh.isRefreshing = false
        mainViewModel!!.updateTerm(wordToDefine!!, order)
        mainViewModel!!.currentState.observe(this, Observer { state ->
            showLoading(state!!.isLoading)
        })
        mainViewModel!!.allDefinitions.observe(this, Observer { definitionList ->
            prepareRecyclerView(definitionList)
        })
    }

    /**
     * Initialize RecyclerView and assign essential components
     * @param definitionList: Definitions pulled from LiveData on API call
     */
    private fun prepareRecyclerView(definitionList: List<Definition>?) {

        mDefinitionAdapter = DefinitionAdapter(definitionList)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            definitionRecyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            definitionRecyclerView.layoutManager = GridLayoutManager(this, 4)
        }
        definitionRecyclerView.itemAnimator = DefaultItemAnimator()
        definitionRecyclerView.adapter = mDefinitionAdapter
    }

    /**
     * Display progressbar on new search or order change
     * @param state: LiveData boolean state based on status of background task
     */
    private fun showLoading(state: Boolean){
        when(state){
            true -> {
                definitionRecyclerView.visibility = View.GONE
                indeterminateProgressBar.visibility = View.VISIBLE
            }
            false -> {
                definitionRecyclerView.visibility = View.VISIBLE
                indeterminateProgressBar.visibility = View.GONE
            }
        }
    }
}
