package com.byteshop.network.feed

import com.byteshop.network.model.NetworkPostFeedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface FeedNetworkSource {
    suspend fun getFeed(): Flow<Result<List<NetworkPostFeedItem>>>
}