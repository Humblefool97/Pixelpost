#!/bin/bash

# Pixelpost Admin Tool Launcher
# This script opens the Firestore admin tool in your default browser

echo "🚀 Launching Pixelpost Admin Tool..."
echo ""

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
HTML_FILE="$SCRIPT_DIR/firestore-admin.html"

# Check if the HTML file exists
if [ ! -f "$HTML_FILE" ]; then
    echo "❌ Error: firestore-admin.html not found!"
    echo "Expected location: $HTML_FILE"
    exit 1
fi

echo "📂 Opening: $HTML_FILE"
echo ""

# Detect OS and open accordingly
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    open "$HTML_FILE"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    # Linux
    xdg-open "$HTML_FILE"
elif [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "cygwin" ]]; then
    # Windows (Git Bash or Cygwin)
    start "$HTML_FILE"
else
    echo "⚠️  Could not detect OS. Please open manually:"
    echo "   $HTML_FILE"
    exit 1
fi

echo "✅ Admin tool should open in your default browser"
echo ""
echo "📋 Quick Tips:"
echo "   • Login with your Firebase Authentication credentials"
echo "   • Use '🎲 Generate Random' for sample data"
echo "   • Use '📦 Add 10 Posts' for bulk creation"
echo ""
echo "📖 For setup instructions, see: $SCRIPT_DIR/SETUP_GUIDE.md"
echo ""
echo "Happy posting! 📸"

