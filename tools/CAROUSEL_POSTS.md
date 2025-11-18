# 📸 Carousel Posts (Multiple Images)

## ✨ New Feature: Instagram-Style Carousel Posts

Posts can now have **multiple images** that users can swipe through, just like Instagram carousel posts!

---

## 🎯 Features

### Multiple Images Per Post
- **1 image**: Regular single-image post
- **2-5 images**: Carousel post with swipe navigation
- **Smooth scrolling**: Native browser scroll with snap points
- **Visual indicators**: Dots show current position
- **Navigation buttons**: Arrow buttons on hover (desktop)
- **Touch-friendly**: Swipe gestures work on mobile

### UI Elements

**Carousel Controls:**
- 📸 **Image counter**: "1/5" shows current image
- ⚪ **Dot indicators**: Visual dots at bottom
- ◀️ **Previous/Next buttons**: Appear on hover
- 📊 **Photo count badge**: Shows total images in stats

---

## 📊 Data Structure

### Updated Post Model

```javascript
posts/{postId}
  ├── userId: string
  ├── userName: string
  ├── userProfileImageUrl: string
  ├── imageUrls: array<string>    // NEW: Array of image URLs
  ├── imageUrl: string             // First image (backward compatibility)
  ├── isCarousel: boolean          // NEW: true if multiple images
  ├── caption: string
  ├── location: string
  ├── createdAt: timestamp
  ├── likesCount: number
  ├── commentsCount: number
  ├── shareCount: number
  └── shareUrl: string
```

**Key Fields:**
- `imageUrls`: Array of all image URLs
- `imageUrl`: First image (for apps that don't support carousels yet)
- `isCarousel`: Flag to easily identify carousel posts

---

## 🎨 How to Use

### Single Image Post
1. Enter one URL in the "Image URLs" field:
   ```
   https://picsum.photos/400/600?random=1
   ```
2. Click "Create Post"
3. Creates a regular single-image post

### Carousel Post (Multiple Images)
1. Enter multiple URLs, **one per line**:
   ```
   https://picsum.photos/400/600?random=1
   https://picsum.photos/400/600?random=2
   https://picsum.photos/400/600?random=3
   ```
2. Click "Create Post"
3. Creates a carousel post with swipe navigation!

### Random Generation
- Click "🎲 Generate Random"
- **40% chance** of generating a carousel (2-5 images)
- **60% chance** of single image
- Automatically fills image URLs

### Bulk Creation
- Click "📦 Add 10 Posts"
- Creates mix of single and carousel posts (~4 carousel, ~6 single)
- Each carousel has 2-5 images
- Fully automated!

---

## 🎮 Navigation

### Desktop
- **Hover** over carousel → Navigation arrows appear
- **Click** left/right arrows to navigate
- **Scroll** horizontally through images

### Mobile/Touch
- **Swipe** left/right to change images
- **Tap** on dots to jump to specific image
- Smooth snap-to-grid scrolling

### Keyboard (future)
- Arrow keys can be added for navigation

---

## 💻 Implementation Details

### Carousel HTML Structure
```html
<div class="carousel-container">
  <div class="carousel-images">
    <img class="carousel-image" src="image1.jpg">
    <img class="carousel-image" src="image2.jpg">
    <img class="carousel-image" src="image3.jpg">
  </div>
  <button class="carousel-nav prev">‹</button>
  <button class="carousel-nav next">›</button>
  <div class="carousel-count">1/3</div>
  <div class="carousel-indicators">
    <div class="carousel-dot active"></div>
    <div class="carousel-dot"></div>
    <div class="carousel-dot"></div>
  </div>
</div>
```

### CSS Features
- **Scroll snap**: `scroll-snap-type: x mandatory`
- **Smooth scroll**: `scroll-behavior: smooth`
- **Hidden scrollbar**: Custom CSS to hide scrollbar
- **Hover effects**: Buttons fade in on hover
- **Responsive**: Works on all screen sizes

### JavaScript Functions
- `setupCarouselScroll()`: Detects scroll position, updates indicators
- `navigateCarousel()`: Handles button clicks, smooth scroll
- Auto-updates counter and indicators

---

## 📱 Android Implementation

### 1. Update Post Model

```kotlin
data class Post(
    val id: String,
    val userId: String,
    val userName: String,
    val userProfileImageUrl: String?,
    val imageUrls: List<String>, // NEW: Multiple images
    val imageUrl: String,         // First image
    val isCarousel: Boolean,      // NEW: Carousel flag
    val caption: String?,
    val location: String?,
    val createdAt: Instant,
    val likesCount: Int,
    val commentsCount: Int,
    val shareCount: Int,
    val shareUrl: String?
)
```

### 2. Network Model

```kotlin
data class NetworkPost(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfileImageUrl: String? = null,
    val imageUrls: List<String> = emptyList(), // NEW
    val imageUrl: String = "",
    val isCarousel: Boolean = false,           // NEW
    val caption: String? = null,
    val location: String? = null,
    @ServerTimestamp
    val createdAt: Timestamp? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val shareCount: Int = 0,
    val shareUrl: String? = null
)
```

### 3. Compose UI - HorizontalPager

```kotlin
@Composable
fun PostItem(post: Post) {
    Column {
        // User header
        PostHeader(post)
        
        // Images (carousel or single)
        if (post.isCarousel && post.imageUrls.size > 1) {
            PostCarousel(
                images = post.imageUrls,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        } else {
            AsyncImage(
                model = post.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Crop
            )
        }
        
        // Rest of post (caption, stats, etc.)
        PostContent(post)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostCarousel(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { images.size })
    
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = "Post image ${page + 1}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        // Indicators
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(images.size) { index ->
                val isActive = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(
                            width = if (isActive) 20.dp else 6.dp,
                            height = 6.dp
                        )
                        .background(
                            color = if (isActive) Color.White else Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        .animateContentSize()
                )
            }
        }
        
        // Image counter
        Text(
            text = "${pagerState.currentPage + 1}/${images.size}",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.7f), CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
```

### 4. Dependencies

Add HorizontalPager to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("androidx.compose.foundation:foundation:1.5.4")
}
```

---

## 🎯 Testing Scenarios

### Test Cases

1. **Single image post**
   - Create post with 1 image
   - Should display as regular post (no carousel)
   - No navigation controls

2. **Carousel with 2 images**
   - Minimum carousel
   - Should show navigation
   - Can swipe between images

3. **Carousel with 5 images**
   - Maximum carousel
   - Test all navigation methods
   - Verify indicators update correctly

4. **Mixed feed**
   - Bulk create 10 posts
   - Should have mix of single and carousel
   - Test scrolling through feed

5. **Mobile swipe**
   - Test on mobile device/emulator
   - Swipe gestures should work smoothly
   - Snap to each image

---

## 📊 Statistics

### When Creating 10 Posts:

**Image Distribution:**
- **Single image posts**: ~6 posts (60%)
- **Carousel posts**: ~4 posts (40%)
- **Total images**: ~20-25 images across all posts

**Carousel Breakdown:**
- 2 images: ~25% of carousels
- 3 images: ~25% of carousels
- 4 images: ~25% of carousels
- 5 images: ~25% of carousels

Perfect variety for testing!

---

## 🎨 Visual Design

### Single Image Post
```
┌─────────────────────────┐
│ 👤 username             │
│ 📍 Location            │
├─────────────────────────┤
│                         │
│      [  IMAGE  ]        │
│                         │
├─────────────────────────┤
│ Caption text...         │
│ ❤️ 42  💬 5  🔗 3      │
└─────────────────────────┘
```

### Carousel Post
```
┌─────────────────────────┐
│ 👤 username             │
│ 📍 Location            │
├─────────────────────────┤
│  [◀]      1/3      [📸]│
│                         │
│      [  IMAGE  ]        │
│                         │
│         ⚪⚫⚪           │
├─────────────────────────┤
│ Caption text...         │
│ ❤️ 42  💬 5  📸 3     │
└─────────────────────────┘
```

---

## 💡 Pro Tips

1. **Image URLs**: Use unique random IDs to ensure different images
2. **Performance**: Images use lazy loading (`loading="lazy"`)
3. **Accessibility**: Each image has proper alt text
4. **Backward compatibility**: Single `imageUrl` field maintained
5. **Responsive**: Works on all screen sizes and devices

---

## 🚀 What's Working Now

✅ Multiple images per post (1-5 images)
✅ Smooth swipe navigation
✅ Visual indicators (dots)
✅ Image counter (1/5)
✅ Hover navigation buttons (desktop)
✅ Touch-friendly (mobile)
✅ Backward compatible (single imageUrl field)
✅ 40% carousel rate in bulk creation
✅ Photo count badge in stats

---

## 🔄 Future Enhancements

Ideas for improvement:
- [ ] Pinch to zoom on images
- [ ] Double-tap to like
- [ ] Video support in carousel
- [ ] Lazy load images as user scrolls
- [ ] Keyboard navigation (arrow keys)
- [ ] Thumbnail preview strip

---

Made with 💜 for Pixelpost | Swipe Away! 📸✨

