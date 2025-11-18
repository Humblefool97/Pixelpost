# Pixelpost Firestore Admin Tool

A web-based tool to manage your Instagram-like feed data in Firebase Firestore.

## 🚀 Quick Start

1. **Open the tool**: Simply open `firestore-admin.html` in any modern web browser (Chrome, Firefox, Safari, Edge)

2. **Login**: Use the same credentials you created for your Firebase Authentication:
   - Email: Your test email
   - Password: Your test password

3. **Create Posts**: 
   - Fill in the form manually, OR
   - Click "🎲 Generate Random" to fill with sample data, OR
   - Click "📦 Add 10 Posts" to create 10 random posts instantly

## ✨ Features

- **🔐 Secure Authentication**: Uses Firebase Authentication
- **✏️ Create Posts**: Add individual posts with custom data
- **🎲 Random Data Generator**: Generate realistic sample data
- **📦 Bulk Creation**: Add multiple posts at once
- **📱 Live Feed View**: See your posts in real-time
- **💅 Beautiful UI**: Modern, responsive design

## 📝 Post Fields

Each post includes:
- **Image URL**: Link to the post image (uses Lorem Picsum for samples)
- **Caption**: Text description with emojis
- **Location**: Geographic location
- **Likes Count**: Initial number of likes
- **Comments Count**: Initial number of comments
- **Share Count**: Number of times post has been shared
- **Sample Comments**: Option to add 3 realistic comments with likes
- **User Info**: Automatically pulled from your logged-in account
- **Timestamp**: Automatically set to current time

## 💬 Comments Features

- **Automatic Comments**: Check "Add sample comments" to create 3 comments with each post
- **Comment Likes**: Each comment has a like count (randomly generated)
- **View Comments**: Click "View all X comments" on any post to see comments
- **Realistic Data**: Comments use diverse usernames and realistic text
- **Separate Collection**: Comments stored in `comments` collection for scalability

## 🔧 Setup Firestore Security Rules

Before using this tool, make sure you've set up Firestore security rules:

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select project: **pixelpost-3804b**
3. Navigate to **Firestore Database**
4. Click on **Rules** tab
5. Copy and paste the rules from `firestore.rules` file (see below)
6. Click **Publish**

## 🎯 Usage Tips

1. **First Time Setup**:
   - Create a user account in Firebase Authentication first
   - Login with those credentials in the tool
   - Start creating posts!

2. **Image URLs**:
   - Uses Lorem Picsum for placeholder images
   - Format: `https://picsum.photos/400/600?random=X`
   - You can use any public image URL

3. **Bulk Data**:
   - Perfect for testing the Android app
   - Creates varied, realistic sample data
   - Each post has random likes/comments counts

4. **Keyboard Shortcuts**:
   - `Ctrl+R`: Generate random post data

## 🔒 Security

- All operations require authentication
- Only logged-in users can create posts
- Posts are associated with the logged-in user's account
- Firebase security rules enforce access control

## 🐛 Troubleshooting

**"Login failed" error**:
- Make sure you've created a user in Firebase Authentication console
- Check that Email/Password sign-in is enabled in Firebase

**"Error creating post" error**:
- Check Firebase Console for Firestore setup
- Verify Firestore security rules are published
- Make sure Firestore is in the same region as your Firebase project

**Posts not showing**:
- Refresh the page
- Check browser console (F12) for errors
- Verify Firestore has the "posts" collection

## 📦 Data Structure

Posts and comments are stored in Firestore with this structure:

```
posts (collection)
  └── {postId} (document)
      ├── userId: string
      ├── userName: string
      ├── userProfileImageUrl: string
      ├── imageUrl: string
      ├── caption: string
      ├── location: string
      ├── createdAt: timestamp
      ├── likesCount: number
      ├── commentsCount: number
      ├── shareCount: number
      └── shareUrl: string (nullable)

comments (collection)
  └── {commentId} (document)
      ├── postId: string (reference to post)
      ├── userId: string
      ├── userName: string
      ├── userProfileImageUrl: string
      ├── text: string
      ├── createdAt: timestamp
      └── likesCount: number

likes (collection) - for tracking user likes
  └── {postId}_{userId} (document)
      ├── postId: string
      ├── userId: string
      └── createdAt: timestamp
```

## 🔗 Next Steps

After creating posts with this tool:

1. Open your Android app
2. Login with the same credentials
3. Navigate to the feed screen
4. Your posts should appear in the feed!

## 📱 Android Integration

The posts created here will be automatically available to your Android app through:
- `FirestorePostDataSource` (in `core/network`)
- `PostRepository` (in `core/data`)
- Room database for offline caching
- Feed screen UI

---

Made with 💜 for Pixelpost

