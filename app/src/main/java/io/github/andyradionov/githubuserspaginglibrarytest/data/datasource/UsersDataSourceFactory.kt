package io.github.andyradionov.githubuserspaginglibrarytest.data.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import io.github.andyradionov.githubuserspaginglibrarytest.data.api.GithubService
import io.github.andyradionov.githubuserspaginglibrarytest.data.model.User
import io.reactivex.disposables.CompositeDisposable

class UsersDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                             private val githubService: GithubService)
    : DataSource.Factory<Long, User>() {

    val usersDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Long, User> {
        val usersDataSource = UsersDataSource(githubService, compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }
}
