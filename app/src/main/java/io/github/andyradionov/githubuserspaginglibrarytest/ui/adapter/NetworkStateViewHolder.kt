package io.github.andyradionov.githubuserspaginglibrarytest.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.andyradionov.githubuserspaginglibrarytest.R
import io.github.andyradionov.githubuserspaginglibrarytest.data.NetworkState
import io.github.andyradionov.githubuserspaginglibrarytest.data.Status
import kotlinx.android.synthetic.main.item_network_state.view.*

class NetworkStateViewHolder(val view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retryLoadingButton.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: NetworkState?) {
        //error message
        itemView.errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            itemView.errorMessageTextView.text = networkState.message
        }

        //loading and retry
        itemView.retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
            return NetworkStateViewHolder(view, retryCallback)
        }
    }

}