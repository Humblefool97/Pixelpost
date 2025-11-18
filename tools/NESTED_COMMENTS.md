# 💬 Nested Comments (Replies) - Implementation Guide

## ✅ Fixed Issues

1. **Error Loading Comments**: Fixed the Firestore query error when opening comments
2. **Nested Comments Support**: Comments can now have replies (one level deep)
3. **Posts Without Comments**: Some posts (30%) will have no comments

## 🎯 Features

### Nested Comment Structure

Comments now support **one level of nesting** (replies to comments):

```
📝 Comment 1
  ↳ 💬 Reply 1
  ↳ 💬 Reply 2

📝 Comment 2
  (no replies)

📝 Comment 3
  ↳ 💬 Reply 1
```

### Smart Generation

- **Top-level comments**: 2-4 per post (if post has comments)
- **Replies**: 40% chance per comment to have 1-2 replies
- **No comments**: 30% of posts have no comments at all
- **Realistic text**: Different messages for replies vs. top-level comments

## 📊 Data Structure

### Updated Comment Model

```javascript
comments/{commentId}
  ├── postId: string                // References the post
  ├── userId: string
  ├── userName: string
  ├── userProfileImageUrl: string
  ├── text: string
  ├── createdAt: timestamp
  ├── likesCount: number
  └── parentCommentId: string | null  // NEW: null for top-level, commentId for replies
```

**Key Field**: `parentCommentId`
- `null` or missing = Top-level comment
- `{commentId}` = Reply to that comment

## 🎨 UI Display

### Visual Hierarchy

**Top-level comments**:
- Gray background (`#f8f9fa`)
- Full width
- Shows reply count if replies exist

**Replies**:
- White background with border
- Indented 42px to the left
- Blue vertical line connecting to parent
- Slightly smaller appearance

### Example Display

```
┌─────────────────────────────────┐
│ 👤 sarah_johnson               │
│ This is amazing! 😍             │
│ ❤️ 42 likes • 💬 2 replies      │
└─────────────────────────────────┘
    │
    ├──┌───────────────────────────┐
    │  │ 👤 mike_photography       │
    │  │ I totally agree!          │
    │  │ ❤️ 15 likes               │
    │  └───────────────────────────┘
    │
    └──┌───────────────────────────┐
       │ 👤 emma_travels           │
       │ Thanks! 💕                │
       │ ❤️ 8 likes                │
       └───────────────────────────┘
```

## 📝 Sample Data

### Top-Level Comments (20 variants)
- "This is amazing! 😍"
- "Great shot! 📸"
- "Incredible! 🔥"
- etc.

### Reply Comments (20 variants)
- "I totally agree!"
- "Thanks! 💕"
- "Right?! 😊"
- "So true!"
- "Thank you so much! 🙏"
- "You're so sweet! ❤️"
- etc.

## 🎲 Generation Logic

### Single Post Creation
1. User checks "Add sample comments" checkbox
2. Creates **3 top-level comments**
3. Each comment has **40% chance** of getting 1-2 replies
4. Result: **3-9 total comment items** (comments + replies)

### Bulk Creation (10 Posts)
1. **30% of posts** get no comments at all
2. Remaining **70% of posts** get 2-4 comments each
3. Each comment has **40% chance** of replies
4. Result: **~20-40 total comments + ~5-15 replies**

## 🔧 Android Implementation

### 1. Update Comment Model

```kotlin
data class Comment(
    val id: String,
    val postId: String,
    val userId: String,
    val userName: String,
    val userProfileImageUrl: String?,
    val text: String,
    val createdAt: Instant,
    val likesCount: Int = 0,
    val parentCommentId: String? = null // NEW field
)
```

### 2. Update Network Model

```kotlin
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
    val parentCommentId: String? = null // NEW field
)
```

### 3. Firestore Query

```kotlin
// Get all comments for a post (both top-level and replies)
fun getCommentsForPost(postId: String): Flow<List<NetworkComment>> = callbackFlow {
    val listener = firestore.collection("comments")
        .whereEqualTo("postId", postId)
        .orderBy("createdAt", Query.Direction.ASCENDING)
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            
            val comments = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(NetworkComment::class.java)
            } ?: emptyList()
            
            trySend(comments)
        }
    
    awaitClose { listener.remove() }
}
```

### 4. Process Nested Structure

```kotlin
data class CommentWithReplies(
    val comment: Comment,
    val replies: List<Comment>
)

fun List<Comment>.toNestedStructure(): List<CommentWithReplies> {
    // Separate top-level comments and replies
    val topLevel = filter { it.parentCommentId == null }
    val repliesMap = filter { it.parentCommentId != null }
        .groupBy { it.parentCommentId }
    
    // Combine them
    return topLevel.map { comment ->
        CommentWithReplies(
            comment = comment,
            replies = repliesMap[comment.id] ?: emptyList()
        )
    }
}
```

### 5. Compose UI

```kotlin
@Composable
fun CommentsScreen(comments: List<CommentWithReplies>) {
    LazyColumn {
        items(comments) { commentWithReplies ->
            CommentItem(
                comment = commentWithReplies.comment,
                onLike = { /* handle like */ }
            )
            
            // Show replies with indentation
            commentWithReplies.replies.forEach { reply ->
                ReplyItem(
                    reply = reply,
                    onLike = { /* handle like */ },
                    modifier = Modifier.padding(start = 48.dp)
                )
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment, onLike: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F9FA))
            .padding(12.dp)
    ) {
        // Avatar
        AsyncImage(
            model = comment.userProfileImageUrl,
            modifier = Modifier.size(32.dp).clip(CircleShape)
        )
        
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(comment.userName, fontWeight = FontWeight.Bold)
            Text(comment.text)
            Row {
                Text("❤️ ${comment.likesCount} likes")
                if (comment.replies.isNotEmpty()) {
                    Text(" • 💬 ${comment.replies.size} replies")
                }
            }
        }
    }
}

@Composable
fun ReplyItem(reply: Comment, onLike: () -> Unit, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color(0xFFE1E8ED))
            .padding(12.dp)
    ) {
        // Similar to CommentItem but styled as reply
    }
}
```

### 6. Add Reply Functionality

```kotlin
suspend fun addReply(
    postId: String,
    parentCommentId: String,
    text: String
): Result<String> {
    val user = auth.currentUser ?: return Result.failure(Exception("Not logged in"))
    
    val reply = NetworkComment(
        postId = postId,
        userId = user.uid,
        userName = user.displayName ?: "User",
        userProfileImageUrl = user.photoURL?.toString(),
        text = text,
        parentCommentId = parentCommentId, // Link to parent
        createdAt = FieldValue.serverTimestamp() as Timestamp
    )
    
    return try {
        val docRef = firestore.collection("comments").add(reply).await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

## 🎯 Testing Scenarios

### Test Cases

1. **Posts without comments**
   - 30% of bulk-created posts
   - "View comments" button doesn't appear
   - Handle gracefully in UI

2. **Comments without replies**
   - Most common scenario (~60% of comments)
   - Display normally without reply section

3. **Comments with replies**
   - ~40% of comments have replies
   - Test indentation and styling
   - Test reply like functionality

4. **Edge Cases**
   - Post with only 1 comment, no replies
   - Comment with maximum 2 replies
   - Loading state while fetching comments

## 🔒 Security Rules

Update Firestore rules to handle nested comments:

```javascript
match /comments/{commentId} {
  allow read: if request.auth != null;
  
  allow create: if request.auth != null && 
                  request.resource.data.userId == request.auth.uid &&
                  // If it's a reply, ensure parent comment exists
                  (request.resource.data.parentCommentId == null ||
                   exists(/databases/$(database)/documents/comments/$(request.resource.data.parentCommentId)));
  
  allow update, delete: if request.auth.uid == resource.data.userId;
}
```

## 📊 Statistics

### When Creating 10 Posts:

- **Posts**: 10 total
- **Posts with comments**: ~7 posts (70%)
- **Posts without comments**: ~3 posts (30%)
- **Top-level comments**: ~20-28 comments
- **Replies**: ~4-12 replies
- **Total comment items**: ~24-40 items

Perfect distribution for testing all scenarios!

## 💡 Pro Tips

1. **Query Optimization**: Fetch all comments at once (both top-level and replies) with a single query
2. **UI Performance**: Use LazyColumn for smooth scrolling with many comments
3. **Real-time Updates**: Use Firestore listeners to update comments live
4. **Reply Button**: Add a "Reply" button to each comment in your app
5. **Mention Support**: Later, add @mentions in replies for better UX

## 🚀 What's Working Now

✅ Error fixed - Comments load without errors
✅ Nested comments supported (one level)
✅ Visual hierarchy (indentation, borders, colors)
✅ Reply count displayed on parent comments
✅ Realistic reply text (different from top-level comments)
✅ Some posts have no comments (30%)
✅ Comments can have 0-2 replies each

---

Made with 💜 for Pixelpost | Now with Nested Replies! 💬

