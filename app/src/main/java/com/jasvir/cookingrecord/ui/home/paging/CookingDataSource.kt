package com.jasvir.cookingrecord.ui.home.paging

import androidx.paging.PageKeyedDataSource
import android.util.Log
import com.jasvir.cookingrecord.model.Character
import com.jasvir.cookingrecord.services.CookingRecordApi
import io.reactivex.disposables.CompositeDisposable

class CookingDataSource(
    private val cookingRecordApi: CookingRecordApi,
    private val compositeDisposable: CompositeDisposable

) : PageKeyedDataSource<Int, Character>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallback: LoadInitialCallback<Int, Character>?,
        callback: LoadCallback<Int, Character>?
    ) {
        val dataMap = HashMap<String, String>()




        compositeDisposable.add(
            cookingRecordApi.allCharacters(dataMap, requestedPage * requestedLoadSize)
                .subscribe(
                    { response ->
                        Log.d("", "Loading page: $requestedPage")
                        initialCallback?.onResult(response.cooking_records, null, adjacentPage)
                        callback?.onResult(response.cooking_records, adjacentPage)
                    },
                    { error ->
                        Log.e("", "error", error)
                    }
                )
        )
    }
}