package com.byteshop.data.model

import com.byteshop.network.model.NetworkPostFeedItem

fun NetworkPostFeedItem.asPostFeedItem() = PostFeedItem(
    id = id,
    userId = userId,
    userName = userName,
    userProfileImageUrl = userProfileImageUrl,
    imageUrls = imageUrls,
    imageUrl = imageUrl,
    isCarousel = isCarousel,
    caption = caption,
    location = location,
    createdAt = createdAt,
    likesCount = likesCount,
    commentsCount = commentsCount,
    shareCount = shareCount,
    shareUrl = shareUrl
)
