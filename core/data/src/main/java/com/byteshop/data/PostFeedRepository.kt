package com.byteshop.data

import com.byteshop.data.model.PostFeedItem
import kotlinx.coroutines.flow.Flow

interface PostFeedRepository {
    fun getFeed(): Flow<List<PostFeedItem>>
}