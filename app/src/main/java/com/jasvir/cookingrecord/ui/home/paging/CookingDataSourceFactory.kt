package com.jasvir.cookingrecord.ui.home.paging

import androidx.paging.DataSource
import com.jasvir.cookingrecord.model.Character
import com.jasvir.cookingrecord.services.CookingRecordApi
import io.reactivex.disposables.CompositeDisposable

class CookingDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val cookingRecordApi: CookingRecordApi) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CookingDataSource(cookingRecordApi, compositeDisposable)
    }
}