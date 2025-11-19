package com.byteshop.data

import com.byteshop.data.model.PostFeedItem
import com.byteshop.data.model.asPostFeedItem
import com.byteshop.network.feed.FeedNetworkSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultPostFeedRepository(
    private val feedNetworkSource: FeedNetworkSource,
    private val dispatcher: CoroutineDispatcher
) : PostFeedRepository {
    override fun getFeed(): Flow<List<PostFeedItem>> = flow {
        feedNetworkSource.getFeed().collect { result ->
            result
                .onSuccess { posts ->
                    emit(posts.map { it.asPostFeedItem() })
                }
        }
    }.flowOn(dispatcher)

    companion object {
        @Volatile
        private var instance: DefaultPostFeedRepository? = null

        fun getInstance(
            feedNetworkSource: FeedNetworkSource,
            dispatcher: CoroutineDispatcher
        ): DefaultPostFeedRepository {
            return instance ?: synchronized(this) {
                instance ?: DefaultPostFeedRepository(
                    feedNetworkSource = feedNetworkSource,
                    dispatcher = dispatcher
                )
            }
        }
    }
}