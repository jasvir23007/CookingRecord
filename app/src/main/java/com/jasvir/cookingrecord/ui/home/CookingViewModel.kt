package com.jasvir.cookingrecord.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.jasvir.cookingrecord.navigation.Navigator
import com.jasvir.cookingrecord.ui.home.paging.CookingDataSourceFactory
import com.jasvir.cookingrecord.model.Character
import com.jasvir.cookingrecord.services.CookingRecordApi
import com.jasvir.cookingrecord.ui.home.adapter.CookingRecordAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CookingViewModel : ViewModel(), Navigator {

    override fun navigateToDetails(character: Character) {
    }


    lateinit var characterList: Observable<PagedList<Character>>
    private val compositeDisposable = CompositeDisposable()
    private val pagedSize = 20
    lateinit var sourceFactory: CookingDataSourceFactory
    val showProgress = MutableLiveData<Boolean>()
    val marvelApi = CookingRecordApi.getService()
    val adapter: CookingRecordAdapter by lazy {
        CookingRecordAdapter(this)
    }

    init {
        initData()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    fun initData() {
        sourceFactory = CookingDataSourceFactory(
            compositeDisposable,
            marvelApi
        )

        val config = PagedList.Config.Builder()
            .setPageSize(pagedSize)
            .setInitialLoadSizeHint(pagedSize * 3)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
        characterList.subscribe {
            showProgress.value = it.size == 0
        }


    }

}