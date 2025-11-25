# Mandi Tracker - Android WebView App

## Overview
This is an Android WebView application that wraps the Mandi Tracker web app (https://mandi-tracker.vercel.app/) into a native Android app. The app was recently converted from a Trusted Web Activity (TWA) to a WebView implementation.

## Recent Changes (November 25, 2025)
- **Updated Version to 1.2**: Incremented versionCode to 2 and versionName to "1.2"
- **Updated API Level to 35**: Upgraded compileSdk and targetSdk from 34 to 35 (Android 15)
- **Updated App Icon and Splash Screen**: Replaced with new Mandi Tracker basket logo featuring vegetables and rupee symbol
  - Updated all mipmap densities (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
  - Updated both regular and round icon variants
  - Splash screen now displays the new logo
- **Integrated Google AdMob**: Added Google Mobile Ads SDK for monetization
  - Implemented interstitial ad that shows after splash screen before app opens
  - Implemented native advanced ad displayed at bottom of WebView in MainActivity
  - Added AdMob App ID: ca-app-pub-5538218540896625~6869629863
  - Interstitial Ad Unit ID: ca-app-pub-5538218540896625/7579365861
  - Native Advanced Ad Unit ID: ca-app-pub-5538218540896625/1205529204
- **Added Animated Splash Screen**: Implemented professional splash screen with fade-in animation and proper lifecycle management
- **Added Voice Search Support**: Implemented microphone permission handling with WebChromeClient
- **Added WhatsApp Sharing**: Enabled sharing to WhatsApp and external apps with proper intent handling
- **Added Runtime Permissions**: Implemented Android 6.0+ runtime permission requests
- **Added Package Queries**: Configured Android 11+ package visibility for WhatsApp and other apps
- **Round Icon**: Configured adaptive/round icon support for Android 7.1+ devices

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
1. **SplashActivity.java**: Splash screen activity with animated logo, Mobile Ads SDK initialization, and interstitial ad
2. **MainActivity.java**: Main activity that hosts the WebView, loads the web app, and displays native advanced ad
3. **activity_main.xml**: Layout file containing the WebView component and native ad container
4. **activity_splash.xml**: Splash screen layout with animated logo
5. **ad_native_advanced.xml**: Layout file for native advanced ad
6. **AndroidManifest.xml**: App manifest with WebView permissions, AdMob configuration, and activity declarations
7. **build.gradle**: Build configuration with WebView and AdMob dependencies

### Dependencies
- `androidx.appcompat:appcompat:1.6.1` - AppCompat support library
- `androidx.webkit:webkit:1.9.0` - WebView support library
- `com.google.android.material:material:1.11.0` - Material Design components
- `com.google.android.gms:play-services-ads:23.5.0` - Google Mobile Ads SDK for AdMob
- `com.android.tools:desugar_jdk_libs:2.0.4` - Core library desugaring for backward compatibility

### Configuration
- **Package Name**: com.mandi.tracker
- **Target URL**: https://mandi-tracker.vercel.app/
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 35 (Android 15)
- **WebView Features**: JavaScript enabled, DOM storage, database support, back navigation, voice search, WhatsApp sharing
- **Permissions**: INTERNET, ACCESS_NETWORK_STATE, RECORD_AUDIO, MODIFY_AUDIO_SETTINGS
- **AdMob Integration**: 
  - Interstitial ad shown after splash screen
  - Native advanced ad displayed at bottom of WebView
  - Automatic ad lifecycle management with proper cleanup

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
