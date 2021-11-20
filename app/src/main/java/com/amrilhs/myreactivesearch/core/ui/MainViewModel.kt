package com.amrilhs.myreactivesearch.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amrilhs.myreactivesearch.BuildConfig
import com.amrilhs.myreactivesearch.core.data.source.remote.network.ApiConfig
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {
     companion object {
        const val API_KEY = BuildConfig.MAPBOX_KEY
    }
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchResult =queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, API_KEY).features
        }
        .asLiveData()
}