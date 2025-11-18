# 🚀 Firebase Firestore Setup Guide

Follow these steps to set up your Firebase project and start using the admin tool.

## Step 1: Enable Cloud Firestore

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project: **pixelpost-3804b**
3. In the left sidebar, click on **Build** → **Firestore Database**
4. Click **Create database**
5. Choose **Start in test mode** (we'll update rules later)
6. Select a location (choose one close to you):
   - For US: `us-central` or `us-east`
   - For Europe: `europe-west`
   - For Asia: `asia-south1` or `asia-southeast1`
7. Click **Enable**

## Step 2: Set Up Security Rules

1. In Firestore Database, click on the **Rules** tab
2. Copy the content from `firestore.rules` file
3. Paste it into the rules editor
4. Click **Publish**

Your rules are now secure! ✅

## Step 3: Create Test User (if not already done)

1. In Firebase Console, go to **Build** → **Authentication**
2. Click on **Get started** (if first time)
3. Enable **Email/Password** sign-in method:
   - Click **Email/Password**
   - Toggle **Enable**
   - Click **Save**
4. Go to **Users** tab
5. Click **Add user**
6. Enter:
   - **Email**: `test@pixelpost.com` (or your preferred email)
   - **Password**: `Test123!` (or your preferred password)
7. Click **Add user**

## Step 4: Open the Admin Tool

1. Navigate to: `tools/firestore-admin.html`
2. Double-click to open in your default browser, OR
3. Right-click → Open with → Choose your browser

**Recommended browsers**: Chrome, Firefox, Safari (latest versions)

## Step 5: Login & Create Posts

1. In the admin tool, enter your credentials:
   - Email: `test@pixelpost.com`
   - Password: `Test123!`
2. Click **Sign In**
3. You should see the main interface ✨

### Quick Actions:

**Create a single post**:
- Fill in the form fields
- Click "Create Post"

**Generate random data**:
- Click "🎲 Generate Random"
- Review the generated data
- Click "Create Post"

**Bulk create posts**:
- Click "📦 Add 10 Posts"
- Confirm the action
- Wait for all posts to be created

## Step 6: Verify in Firebase Console

1. Go back to Firebase Console
2. Navigate to **Firestore Database**
3. You should see a `posts` collection
4. Click on it to see your created posts

## Step 7: Test in Android App

Now you're ready to integrate with your Android app!

The posts you created are now available through Firestore and will be fetched by your Android app's data layer.

---

## 🎯 Common Issues & Solutions

### Issue: "Authentication failed"
**Solution**: 
- Make sure Email/Password authentication is enabled in Firebase Console
- Verify the email/password you're using exists in Authentication → Users
- Check browser console (F12) for detailed error messages

### Issue: "Permission denied" when creating posts
**Solution**:
- Verify Firestore security rules are published
- Make sure you're logged in
- Check that the rules in Firestore match the `firestore.rules` file

### Issue: "Posts not appearing"
**Solution**:
- Refresh the page
- Check Firebase Console → Firestore to verify posts were created
- Look for JavaScript errors in browser console (F12)

### Issue: "Firestore not initialized"
**Solution**:
- Make sure you enabled Firestore in Firebase Console (Step 1)
- Verify your internet connection
- Try in a different browser

---

## 🔐 Security Notes

- **Never commit** credentials to your repository
- The current rules allow authenticated users to read all posts
- Only post owners can delete/update their posts
- Consider adding pagination for production use
- For production, implement proper rate limiting

---

## 🎨 Customization

Want to customize the admin tool?

Edit `firestore-admin.html`:
- **Colors**: Look for the gradient values in CSS
- **Sample data**: Edit `window.sampleCaptions` and `window.sampleLocations`
- **Form fields**: Add/remove fields in the form section
- **Post display**: Modify `createPostElement()` function

---

## 📞 Need Help?

If you encounter any issues:

1. Check the browser console (F12 → Console tab)
2. Check Firebase Console for errors
3. Verify all setup steps were completed
4. Try in incognito/private mode
5. Clear browser cache and reload

---

## ✅ Next Steps After Setup

Once you have posts in Firestore:

1. **Android App Integration**:
   - Add Firestore dependency to your Android project
   - Implement `FirestorePostDataSource`
   - Create `PostRepository` with offline-first pattern
   - Build the Feed UI with Compose

2. **Additional Features**:
   - Add comments support
   - Implement likes functionality
   - Add user profiles
   - Enable follow/unfollow
   - Add stories support
   - Implement search

3. **Testing**:
   - Test with different users
   - Test offline mode
   - Test pagination
   - Test with large datasets

---

Made with 💜 for Pixelpost | Happy Coding! 🚀

