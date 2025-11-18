package com.byteshop.network.feed

import com.byteshop.network.model.NetworkPostFeedItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class FirebaseNetworkPostFeedSource(
    private val dispatcher: CoroutineDispatcher
) : FeedNetworkSource {

    private val firestore = FirebaseFirestore.getInstance()

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
                    
                    // Send success result
                    trySend(Result.success(posts))
                }
            
            // Clean up listener when Flow is cancelled
            awaitClose { listener.remove() }
        }.flowOn(dispatcher)
}