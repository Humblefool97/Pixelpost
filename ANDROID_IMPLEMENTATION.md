# 📱 Android Feed Implementation Guide

## Architecture Overview

Following the **Now in Android** architecture pattern with **offline-first** approach:

```
┌─────────────────────────────────────────┐
│          UI Layer (Compose)              │
│  - FeedScreen.kt                        │
│  - FeedViewModel.kt                     │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│          Domain Layer                    │
│  - GetFeedPostsUseCase.kt               │
│  - LikePostUseCase.kt                   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│          Data Layer                      │
│  - PostRepository                       │
│  - OfflineFirstPostRepository           │
└──┬───────────┬──────────────────────────┘
   │           │
   │           └─────────────────┐
   ▼                             ▼
┌─────────────────┐   ┌──────────────────┐
│  Local (Room)   │   │ Remote (Firestore)│
│  - PostDao      │   │ - FirestoreSource │
│  - PostEntity   │   │ - NetworkPost     │
└─────────────────┘   └──────────────────┘
```

---

## 1️⃣ Setup Dependencies

### `gradle/libs.versions.toml`

```toml
[versions]
# Add Firebase
firebaseFirestore = "24.10.0"

[libraries]
# Add to existing Firebase section
firebase-firestore = { group = "com.google.firebase", name = "firebase-firestore", version.ref = "firebaseFirestore" }

# Room (if not already added)
room-runtime = { group = "androidx.room", name = "room-runtime", version = "2.6.1" }
room-ktx = { group = "androidx.room", name = "room-ktx", version = "2.6.1" }
room-compiler = { group = "androidx.room", name = "room-compiler", version = "2.6.1" }

# Coil for images
coil = { group = "io.coil-kt", name = "coil-compose", version = "2.5.0" }
```

### `core/network/build.gradle.kts`

```kotlin
dependencies {
    implementation(libs.firebase.firestore)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
```

---

## 2️⃣ Models

### `core/model/src/main/kotlin/com/byteshop/pixelpost/model/Post.kt`

```kotlin
package com.byteshop.pixelpost.model

import kotlinx.datetime.Instant

/**
 * External data layer representation of a Post
 */
data class Post(
    val id: String,
    val userId: String,
    val userName: String,
    val userProfileImageUrl: String?,
    val imageUrls: List<String>,      // Multiple images for carousel
    val imageUrl: String,              // First image (backward compatibility)
    val isCarousel: Boolean,           // True if multiple images
    val caption: String?,
    val location: String?,
    val createdAt: Instant,
    val likesCount: Int,
    val commentsCount: Int,
    val shareCount: Int,
    val shareUrl: String?
)

data class Comment(
    val id: String,
    val postId: String,
    val userId: String,
    val userName: String,
    val userProfileImageUrl: String?,
    val text: String,
    val createdAt: Instant,
    val likesCount: Int,
    val parentCommentId: String?      // Null for top-level, ID for replies
)
```

---

## 3️⃣ Network Layer - Firestore Data Source

### `core/network/src/main/kotlin/com/byteshop/network/model/NetworkPost.kt`

```kotlin
package com.byteshop.network.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class NetworkPost(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfileImageUrl: String? = null,
    val imageUrls: List<String> = emptyList(),
    val imageUrl: String = "",
    val isCarousel: Boolean = false,
    val caption: String? = null,
    val location: String? = null,
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val shareCount: Int = 0,
    val shareUrl: String? = null
)

data class NetworkComment(
    @DocumentId
    val id: String = "",
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfileImageUrl: String? = null,
    val text: String = "",
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    val likesCount: Int = 0,
    val parentCommentId: String? = null
)
```

### `core/network/src/main/kotlin/com/byteshop/network/firestore/FirestorePostDataSource.kt`

```kotlin
package com.byteshop.network.firestore

import com.byteshop.network.model.NetworkPost
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Firestore data source for posts
 * NO REST API - Direct Firestore SDK communication
 */
interface PostNetworkDataSource {
    /**
     * Get feed posts as a Flow (real-time updates)
     */
    fun getFeedPosts(limit: Int = 20): Flow<List<NetworkPost>>
    
    /**
     * Get posts by user
     */
    fun getPostsByUser(userId: String): Flow<List<NetworkPost>>
    
    /**
     * Get single post
     */
    suspend fun getPost(postId: String): Result<NetworkPost>
    
    /**
     * Create new post
     */
    suspend fun createPost(post: NetworkPost): Result<String>
}

class FirestorePostDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : PostNetworkDataSource {

    override fun getFeedPosts(limit: Int): Flow<List<NetworkPost>> = callbackFlow {
        // Real-time listener - NO API CALL!
        val listener = firestore.collection("posts")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val posts = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(NetworkPost::class.java)
                } ?: emptyList()
                
                trySend(posts)
            }
        
        awaitClose { listener.remove() }
    }
    
    override fun getPostsByUser(userId: String): Flow<List<NetworkPost>> = callbackFlow {
        val listener = firestore.collection("posts")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val posts = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(NetworkPost::class.java)
                } ?: emptyList()
                
                trySend(posts)
            }
        
        awaitClose { listener.remove() }
    }

    override suspend fun getPost(postId: String): Result<NetworkPost> = try {
        val document = firestore.collection("posts")
            .document(postId)
            .get()
            .await()
            
        val post = document.toObject(NetworkPost::class.java)
        if (post != null) {
            Result.success(post)
        } else {
            Result.failure(Exception("Post not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun createPost(post: NetworkPost): Result<String> = try {
        val docRef = firestore.collection("posts")
            .add(post)
            .await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

### `core/network/src/main/kotlin/com/byteshop/network/di/NetworkModule.kt`

```kotlin
package com.byteshop.network.di

import com.byteshop.network.firestore.FirestorePostDataSource
import com.byteshop.network.firestore.PostNetworkDataSource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance().apply {
            // Enable offline persistence
            firestoreSettings = firestoreSettings.toBuilder()
                .setPersistenceEnabled(true)
                .build()
        }
    }

    @Provides
    @Singleton
    fun providePostNetworkDataSource(
        firestore: FirebaseFirestore
    ): PostNetworkDataSource {
        return FirestorePostDataSource(firestore)
    }
}
```

---

## 4️⃣ Database Layer - Room (Offline Cache)

### `core/database/src/main/kotlin/com/byteshop/database/model/PostEntity.kt`

```kotlin
package com.byteshop.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val userName: String,
    val userProfileImageUrl: String?,
    val imageUrls: List<String>,      // Room will use TypeConverter
    val imageUrl: String,
    val isCarousel: Boolean,
    val caption: String?,
    val location: String?,
    val createdAt: Instant,
    val likesCount: Int,
    val commentsCount: Int,
    val shareCount: Int,
    val shareUrl: String?
)
```

### `core/database/src/main/kotlin/com/byteshop/database/dao/PostDao.kt`

```kotlin
package com.byteshop.database.dao

import androidx.room.*
import com.byteshop.database.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    
    @Query("SELECT * FROM posts ORDER BY createdAt DESC LIMIT :limit")
    fun getFeedPosts(limit: Int = 20): Flow<List<PostEntity>>
    
    @Query("SELECT * FROM posts WHERE userId = :userId ORDER BY createdAt DESC")
    fun getPostsByUser(userId: String): Flow<List<PostEntity>>
    
    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPost(postId: String): PostEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)
    
    @Query("DELETE FROM posts")
    suspend fun clearAll()
}
```

### `core/database/src/main/kotlin/com/byteshop/database/util/Converters.kt`

```kotlin
package com.byteshop.database.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
    
    @TypeConverter
    fun fromInstant(value: Instant?): Long? {
        return value?.toEpochMilliseconds()
    }
    
    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }
}
```

---

## 5️⃣ Data Layer - Repository (Offline-First)

### `core/data/src/main/kotlin/com/byteshop/data/repository/PostRepository.kt`

```kotlin
package com.byteshop.data.repository

import com.byteshop.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    /**
     * Get feed posts with offline-first approach
     * Returns cached data immediately, updates from network
     */
    fun getFeedPosts(limit: Int = 20): Flow<List<Post>>
    
    /**
     * Get posts by specific user
     */
    fun getPostsByUser(userId: String): Flow<List<Post>>
    
    /**
     * Get single post
     */
    fun getPost(postId: String): Flow<Post?>
    
    /**
     * Sync posts from network to local cache
     */
    suspend fun syncPosts()
}
```

### `core/data/src/main/kotlin/com/byteshop/data/repository/OfflineFirstPostRepository.kt`

```kotlin
package com.byteshop.data.repository

import com.byteshop.data.model.asEntity
import com.byteshop.data.model.asExternalModel
import com.byteshop.database.dao.PostDao
import com.byteshop.model.Post
import com.byteshop.network.firestore.PostNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Offline-first repository following Now in Android pattern
 */
class OfflineFirstPostRepository @Inject constructor(
    private val postDao: PostDao,
    private val networkDataSource: PostNetworkDataSource
) : PostRepository {

    override fun getFeedPosts(limit: Int): Flow<List<Post>> {
        // Return local data immediately, sync in background
        return postDao.getFeedPosts(limit)
            .map { entities -> entities.map { it.asExternalModel() } }
            .onStart {
                // Sync from network in background
                syncPosts()
            }
    }

    override fun getPostsByUser(userId: String): Flow<List<Post>> {
        return postDao.getPostsByUser(userId)
            .map { entities -> entities.map { it.asExternalModel() } }
    }

    override fun getPost(postId: String): Flow<Post?> {
        return postDao.getPost(postId)
            .map { it?.asExternalModel() }
    }

    override suspend fun syncPosts() {
        // Listen to network and save to local database
        networkDataSource.getFeedPosts()
            .collect { networkPosts ->
                val entities = networkPosts.map { it.asEntity() }
                postDao.insertPosts(entities)
            }
    }
}
```

### `core/data/src/main/kotlin/com/byteshop/data/model/PostMapper.kt`

```kotlin
package com.byteshop.data.model

import com.byteshop.database.model.PostEntity
import com.byteshop.model.Post
import com.byteshop.network.model.NetworkPost
import com.google.firebase.Timestamp
import kotlinx.datetime.Instant

// Network to Entity
fun NetworkPost.asEntity() = PostEntity(
    id = id,
    userId = userId,
    userName = userName,
    userProfileImageUrl = userProfileImageUrl,
    imageUrls = imageUrls,
    imageUrl = imageUrl,
    isCarousel = isCarousel,
    caption = caption,
    location = location,
    createdAt = createdAt?.toInstant() ?: Instant.fromEpochMilliseconds(0),
    likesCount = likesCount,
    commentsCount = commentsCount,
    shareCount = shareCount,
    shareUrl = shareUrl
)

// Entity to External Model
fun PostEntity.asExternalModel() = Post(
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

// Helper to convert Firebase Timestamp to Instant
fun Timestamp.toInstant(): Instant {
    return Instant.fromEpochMilliseconds(seconds * 1000 + nanoseconds / 1_000_000)
}
```

---

## 6️⃣ Domain Layer - Use Cases

### `core/domain/src/main/kotlin/com/byteshop/domain/GetFeedPostsUseCase.kt`

```kotlin
package com.byteshop.domain

import com.byteshop.data.repository.PostRepository
import com.byteshop.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get feed posts
 * Encapsulates business logic if needed
 */
class GetFeedPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(limit: Int = 20): Flow<List<Post>> {
        return postRepository.getFeedPosts(limit)
    }
}
```

---

## 7️⃣ UI Layer - Compose

### `feature/feed/src/main/kotlin/com/byteshop/feed/FeedViewModel.kt`

```kotlin
package com.byteshop.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteshop.domain.GetFeedPostsUseCase
import com.byteshop.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

sealed interface FeedUiState {
    object Loading : FeedUiState
    data class Success(val posts: List<Post>) : FeedUiState
    data class Error(val message: String) : FeedUiState
}

@HiltViewModel
class FeedViewModel @Inject constructor(
    getFeedPostsUseCase: GetFeedPostsUseCase
) : ViewModel() {

    val uiState: StateFlow<FeedUiState> = getFeedPostsUseCase()
        .map<List<Post>, FeedUiState> { posts ->
            FeedUiState.Success(posts)
        }
        .onStart { emit(FeedUiState.Loading) }
        .catch { emit(FeedUiState.Error(it.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FeedUiState.Loading
        )
}
```

### `feature/feed/src/main/kotlin/com/byteshop/feed/FeedScreen.kt`

```kotlin
package com.byteshop.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    FeedScreen(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
internal fun FeedScreen(
    uiState: FeedUiState,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is FeedUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is FeedUiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = uiState.posts,
                    key = { it.id }
                ) { post ->
                    PostItem(
                        post = post,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        is FeedUiState.Error -> {
            Box(modifier = modifier.fillMaxSize()) {
                Text("Error: ${uiState.message}")
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column {
            // Post header
            // Images (carousel or single)
            // Caption
            // Stats
            // Comments
        }
    }
}
```

---

## 🎯 Key Points

### No REST API!
- ✅ Firebase SDK communicates directly with Firestore
- ✅ Real-time updates via listeners
- ✅ Built-in offline support
- ✅ No backend server needed

### Offline-First
- ✅ Room database caches all data
- ✅ App works offline
- ✅ Data syncs when online

### Following NowInAndroid Pattern
- ✅ Proper layer separation
- ✅ Use cases for business logic
- ✅ Repository pattern
- ✅ Reactive Flows
- ✅ Hilt dependency injection

---

Made with 💜 for Pixelpost

