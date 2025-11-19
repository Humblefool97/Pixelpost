package com.byteshop.data.model

data class PostFeedItem(
    val id: String,
    val userId: String,
    val userName: String,
    val userProfileImageUrl: String?,
    val imageUrls: List<String>,
    val imageUrl: String,
    val isCarousel: Boolean,
    val caption: String?,
    val location: String?,
    val createdAt: Long,
    val likesCount: Int,
    val commentsCount: Int,
    val shareCount: Int,
    val shareUrl: String?
)
