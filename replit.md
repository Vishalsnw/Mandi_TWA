# Mandi Tracker - Android WebView App

## Overview
This is an Android WebView application that wraps the Mandi Tracker web app (https://mandi-tracker.vercel.app/) into a native Android app. The app was recently converted from a Trusted Web Activity (TWA) to a WebView implementation.

## Recent Changes (November 25, 2025)
- **Added Animated Splash Screen**: Implemented professional splash screen with fade-in animation and proper lifecycle management
- **Added Voice Search Support**: Implemented microphone permission handling with WebChromeClient
- **Added WhatsApp Sharing**: Enabled sharing to WhatsApp and external apps with proper intent handling
- **Added Runtime Permissions**: Implemented Android 6.0+ runtime permission requests
- **Added Package Queries**: Configured Android 11+ package visibility for WhatsApp and other apps

## Previous Changes (November 24, 2025)
- **Converted from TWA to WebView**: Complete migration from Trusted Web Activity to WebView implementation
- Created `MainActivity.java` with full WebView configuration
- Created `activity_main.xml` layout for WebView
- Updated `AndroidManifest.xml` to use MainActivity instead of LauncherActivity
- Removed TWA dependencies (`androidbrowserhelper`, `browser`) from `build.gradle`
- Added WebView support dependencies (`androidx.webkit`)
- Updated README.md with WebView-specific documentation

## Project Architecture

### Key Components
1. **MainActivity.java**: Main activity that hosts the WebView and loads the web app
2. **activity_main.xml**: Layout file containing the WebView component
3. **AndroidManifest.xml**: App manifest with WebView permissions and MainActivity configuration
4. **build.gradle**: Build configuration with WebView dependencies

### Dependencies
- `androidx.appcompat:appcompat:1.6.1` - AppCompat support library
- `androidx.webkit:webkit:1.9.0` - WebView support library
- `com.google.android.material:material:1.11.0` - Material Design components

### Configuration
- **Package Name**: com.mandi.tracker
- **Target URL**: https://mandi-tracker.vercel.app/
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **WebView Features**: JavaScript enabled, DOM storage, database support, back navigation, voice search, WhatsApp sharing
- **Permissions**: INTERNET, ACCESS_NETWORK_STATE, RECORD_AUDIO, MODIFY_AUDIO_SETTINGS

## Build Instructions
This is an Android project that should be built using:
- Android Studio
- Gradle build system
- Command: `./gradlew assembleRelease` for release builds

## User Preferences
Not yet documented - to be added as user preferences are expressed.

## Notes
- The app uses WebView instead of TWA for better control and JavaScript bridge capabilities
- All TWA-specific configurations and dependencies have been removed
- The app maintains the same visual appearance (splash screen, icons, colors)
