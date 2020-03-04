package com.jasvir.cookingrecord.ui.home

import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jasvir.cookingrecord.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_cooking_record.*

class CookingActivity : AppCompatActivity() {

    private val viewModel: CookingViewModel by lazy {
        ViewModelProvider(this).get(CookingViewModel::class.java)
    }


    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking_record)
        initRecycler()
       // initSearch()
        setObserver()
    }

    private fun initRecycler() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerCharacters.layoutManager = linearLayoutManager
        recyclerCharacters.hasFixedSize()
        recyclerCharacters.adapter = viewModel.adapter
        subscribeToList()
    }



    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable("lmState", recyclerCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }


    fun setObserver() {
        viewModel.showProgress.observe(this, Observer {
            if (it) {
                pbCharacters.visibility = View.VISIBLE
            } else {
                pbCharacters.visibility = View.GONE
            }
        })



    }


    private fun subscribeToList() {
        val disposable = viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    viewModel.adapter.submitList(list)
                    if (recyclerState != null) {
                        recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
                },
                { e ->
                    Log.e("NGVL", "Error", e)
                }
            )

    }

}
