package io.github.andyradionov.githubuserspaginglibrarytest.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import io.github.andyradionov.githubuserspaginglibrarytest.data.api.GithubService
import io.github.andyradionov.githubuserspaginglibrarytest.data.NetworkState
import io.github.andyradionov.githubuserspaginglibrarytest.data.datasource.UsersDataSource
import io.github.andyradionov.githubuserspaginglibrarytest.data.datasource.UsersDataSourceFactory
import io.github.andyradionov.githubuserspaginglibrarytest.data.model.User
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel : ViewModel() {

    var userList: LiveData<PagedList<User>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 15

    private val sourceFactory: UsersDataSourceFactory

    init {
        sourceFactory = UsersDataSourceFactory(compositeDisposable, GithubService.getService())
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        userList = LivePagedListBuilder<Long, User>(sourceFactory, config).build()

    }

    fun retry() {
        sourceFactory.usersDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        sourceFactory.usersDataSourceLiveData.value!!.invalidate()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<UsersDataSource, NetworkState>(
            sourceFactory.usersDataSourceLiveData, { it.networkState })

    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<UsersDataSource, NetworkState>(
            sourceFactory.usersDataSourceLiveData, { it.initialLoad })

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}