package com.byteshop.network.feed

import com.byteshop.network.model.NetworkPostFeedItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseNetworkPostFeedSource(
    private val dispatcher: CoroutineDispatcher
) : FeedNetworkSource {

    private val firestore = FirebaseFirestore.getInstance()

    /**
     * OPTION 1: Suspend function - One-time fetch
     * Returns once and completes. Use for pull-to-refresh.
     */
    suspend fun getFeedOnce(): Result<List<NetworkPostFeedItem>> {
        return withContext(dispatcher) {
            try {
                val snapshot = firestore.collection("posts")
                    .orderBy("createdAt", Direction.DESCENDING)
                    .limit(20)
                    .get()    // One-time fetch
                    .await()  // Suspend until complete
                
                val posts = snapshot.documents.mapNotNull { document ->
                    document.toObject(NetworkPostFeedItem::class.java)
                }
                
                Result.success(posts)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }
    }

    /**
     * OPTION 2: Flow with real-time updates
     * Emits every time data changes in Firestore. Use for live feed.
     */
    override suspend fun getFeed(): Flow<Result<List<NetworkPostFeedItem>>> =
        callbackFlow {
            val listener = firestore.collection("posts")
                .orderBy("createdAt", Direction.DESCENDING)
                .limit(20)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        // Send error result and close
                        trySend(Result.failure(error))
                        close(error)
                        return@addSnapshotListener
                    }
                    
                    // Convert Firestore documents to NetworkPostFeedItem
                    val posts = snapshot?.documents?.mapNotNull { document ->
                        document.toObject(NetworkPostFeedItem::class.java)
                    } ?: emptyList()
                    
                    // Send success result - fires every time data changes!
                    trySend(Result.success(posts))
                }
            
            // Clean up listener when Flow is cancelled
            awaitClose { listener.remove() }
        }.flowOn(dispatcher)
}