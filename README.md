# Mandi Tracker - Android WebView App

This is an Android WebView application that wraps the Mandi Tracker web app hosted at [https://mandi-tracker.vercel.app/](https://mandi-tracker.vercel.app/) into a native Android app.

## 📱 What is a WebView App?

A WebView app embeds your web application inside a native Android application using Android's WebView component. This provides full control over the web content and allows for JavaScript-to-native communication via JavaScript bridges.

## 🚀 Features

- ✅ Full-screen web app experience
- ✅ JavaScript enabled with DOM storage support
- ✅ **Voice Search Support** - Microphone permission handling for voice search functionality
- ✅ **WhatsApp Sharing** - Seamless sharing to WhatsApp and other external apps
- ✅ External app integration (WhatsApp, Phone, Email, SMS)
- ✅ Custom splash screen and app icon
- ✅ Back button navigation support
- ✅ Responsive layout for all screen sizes
- ✅ Status bar and navigation bar theming
- ✅ Full JavaScript-to-native bridge capabilities
- ✅ Works on Android 5.0 (API 21) and above

## 🎯 Quick Start

### Prerequisites

- Android Studio (latest version recommended)
- JDK 8 or higher
- Android SDK 34
- Gradle 8.1.4 or higher

### Building the App

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd Mandi_WebView
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project directory and select it

3. **Build the APK**
   - Click "Build" → "Build Bundle(s) / APK(s)" → "Build APK(s)"
   - Or use Gradle:
     ```bash
     ./gradlew assembleRelease
     ```

4. **Install and Test**
   - Transfer APK to Android device
   - Enable "Install from Unknown Sources" if needed
   - Install and test the app

## 📦 Project Structure

```
Mandi_WebView/
├── app/
│   ├── src/main/
│   │   ├── java/com/mandi/tracker/
│   │   │   └── MainActivity.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   └── colors.xml
│   │   │   └── drawable/
│   │   │       └── splash.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── README.md
```

## 🔧 WebView Configuration

The app uses the following WebView settings for optimal performance:

```java
WebSettings webSettings = webView.getSettings();
webSettings.setJavaScriptEnabled(true);          // Enable JavaScript
webSettings.setDomStorageEnabled(true);          // Enable DOM storage
webSettings.setDatabaseEnabled(true);            // Enable database
webSettings.setLoadWithOverviewMode(true);       // Load with overview
webSettings.setUseWideViewPort(true);            // Use wide viewport
```

## 📱 App Configuration

- **Package Name**: `com.mandi.tracker`
- **Target URL**: `https://mandi-tracker.vercel.app/`
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **Theme Color**: #4CAF50 (Green)

## 🎤 Voice Search & 📤 Sharing Features

### Voice Search
The app includes full voice search support with runtime permission handling:
- **Microphone Permission**: The app requests microphone access when voice search is activated
- **WebChromeClient Integration**: Handles permission requests from the web app
- **Runtime Permissions**: Follows Android 6.0+ best practices for permission requests
- **Secure**: Only grants microphone access when explicitly requested by the web app

### WhatsApp & External App Sharing
The app seamlessly handles sharing to external apps:
- **WhatsApp**: Direct sharing via `whatsapp://`, `wa.me`, and `api.whatsapp.com` URLs
- **Intent URLs**: Proper handling of `intent://` scheme with fallback support
- **Phone Calls**: `tel:` links open the phone dialer
- **Email**: `mailto:` links open email apps
- **SMS**: `sms:` links open messaging apps
- **Fallback Handling**: If an app isn't installed, falls back to browser or Play Store

### Permissions Required
- `INTERNET` - For loading web content
- `ACCESS_NETWORK_STATE` - For checking network status
- `RECORD_AUDIO` - For voice search functionality
- `MODIFY_AUDIO_SETTINGS` - For audio configuration

## 🎨 Customization

### Change App Name

Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### Change Website URL

Edit `app/src/main/java/com/mandi/tracker/MainActivity.java`:
```java
webView.loadUrl("https://your-website-url.com/");
```

### Change Colors

Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="colorPrimary">#YourColor</color>
```

### Change Package Name

1. Update `app/build.gradle` → `applicationId`
2. Update `app/src/main/AndroidManifest.xml` if needed
3. Refactor package in Android Studio (right-click package → Refactor → Rename)

## 🔐 Creating a Keystore for Signing

For production releases, you need to sign your app:

```bash
keytool -genkey -v -keystore mandi-tracker.keystore \
  -alias mandi \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

Then update `app/build.gradle` to add signing configuration:

```gradle
android {
    signingConfigs {
        release {
            storeFile file("../mandi-tracker.keystore")
            storePassword "your-store-password"
            keyAlias "mandi"
            keyPassword "your-key-password"
        }
    }
    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
        }
    }
}
```

## 🏪 Publishing to Google Play Store

1. Create a production keystore (see above)
2. Build a signed AAB file:
   ```bash
   ./gradlew bundleRelease
   ```
3. Create Google Play Console account
4. Create new app
5. Upload AAB file (located in `app/build/outputs/bundle/release/`)
6. Complete store listing, content rating, privacy policy, etc.
7. Submit for review

## 🐛 Troubleshooting

### WebView shows blank page
- Check internet connection
- Verify the URL is correct in MainActivity.java
- Check Android logs: `adb logcat | grep WebView`
- Ensure INTERNET permission is in AndroidManifest.xml

### JavaScript not working
- Verify `setJavaScriptEnabled(true)` is set
- Check for JavaScript errors in web console
- Enable WebView debugging: `WebView.setWebContentsDebuggingEnabled(true)`

### "Cannot install app" on Android
- Enable "Install from Unknown Sources" in device settings
- Make sure APK is fully downloaded
- Try uninstalling any existing version first

### App crashes on startup
- Check Android logs: `adb logcat`
- Verify all resources (icons, layouts) exist
- Check for missing dependencies in build.gradle

## 🔍 Debugging WebView

Enable remote debugging in your MainActivity:

```java
if (BuildConfig.DEBUG) {
    WebView.setWebContentsDebuggingEnabled(true);
}
```

Then open Chrome and navigate to `chrome://inspect` to debug the WebView.

## 📊 WebView vs TWA Comparison

| Feature | WebView | TWA |
|---------|---------|-----|
| **Rendering** | Embedded browser | User's Chrome browser |
| **JavaScript Bridge** | ✅ Yes | ❌ No |
| **Full Control** | ✅ Yes | ❌ Limited |
| **Offline Support** | ✅ Via cache | ✅ Service workers |
| **APK Size** | Larger | Smaller |
| **Updates** | Depends on device | Auto-updated |
| **Web State Access** | ✅ Full access | ❌ No access |

## 🚀 Advanced Features

### Adding JavaScript Interface

You can add a JavaScript interface to communicate between web and native:

```java
public class WebAppInterface {
    Context mContext;
    
    WebAppInterface(Context c) {
        mContext = c;
    }
    
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}

// In onCreate:
webView.addJavascriptInterface(new WebAppInterface(this), "Android");
```

Then in your web JavaScript:
```javascript
Android.showToast("Hello from web!");
```

## 📄 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## 🔗 Links

- Website: https://mandi-tracker.vercel.app/
- Android WebView Guide: https://developer.android.com/guide/webapps/webview
- WebView Best Practices: https://developer.android.com/guide/webapps/best-practices
