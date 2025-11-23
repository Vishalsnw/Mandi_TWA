# Mandi Tracker TWA - Replit Project

## Overview
This is an Android Trusted Web Activity (TWA) project that wraps the Mandi Tracker web application (https://mandi-tracker.vercel.app/) into a native Android app. The project includes automated GitHub Actions workflows to build release APK and AAB files.

## Project Type
- **Platform**: Android
- **Build System**: Gradle
- **Language**: XML/Java configuration (no Java code needed for basic TWA)
- **CI/CD**: GitHub Actions

## Architecture
- **TWA Target**: https://mandi-tracker.vercel.app/
- **Package Name**: com.mandi.tracker
- **Build Outputs**: APK and AAB files
- **Signing**: Automated via GitHub Actions (requires secrets)

## Recent Changes
- 2024-11-23: Complete Android TWA project setup
  - Created Android project structure with Gradle build system
  - Configured AndroidManifest.xml for TWA
  - Added splash screen and theme configuration
  - Created GitHub Actions workflow for automated APK/AAB builds
  - Added comprehensive documentation and setup instructions
  - Configured Digital Asset Links template

## Project Structure
```
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml      - App configuration and TWA settings
│   │   └── res/                     - Android resources (colors, strings, etc)
│   ├── build.gradle                 - App-level build configuration
│   └── proguard-rules.pro          - Code optimization rules
├── .github/workflows/
│   └── build-release.yml           - GitHub Actions CI/CD workflow
├── build.gradle                     - Project-level build configuration
├── settings.gradle                  - Gradle settings
├── gradle.properties               - Gradle properties
├── assetlinks.json                 - Digital Asset Links configuration
└── README.md                        - Complete documentation
```

## Key Files
- **AndroidManifest.xml**: Defines TWA configuration, target URL, and app permissions
- **build-release.yml**: GitHub Actions workflow for automated builds
- **assetlinks.json**: Must be hosted on the target website for verification
- **colors.xml**: Theme colors (green theme #4CAF50)
- **strings.xml**: App name and text resources

## Build Process
Since this is an Android project, it **cannot be built directly in Replit**. Builds occur via:

1. **GitHub Actions** (Recommended): 
   - Automatic builds on push to main/master
   - Generates signed APK and AAB files
   - Artifacts available for download

2. **Local Build** (Requires Android SDK):
   - Requires Java JDK 17+
   - Requires Android SDK with build tools
   - Run: `./gradlew assembleRelease`

## GitHub Actions Workflow
The `.github/workflows/build-release.yml` file:
- Triggers on push to main/master branches
- Triggers on version tags (v*)
- Builds both APK and AAB files
- Signs releases if secrets are configured
- Uploads artifacts to GitHub Actions
- Creates GitHub releases for version tags

## Required GitHub Secrets
To enable signed builds, configure these secrets in GitHub:
- `SIGNING_KEY`: Base64 encoded keystore
- `KEY_ALIAS`: Keystore alias
- `KEY_STORE_PASSWORD`: Keystore password
- `KEY_PASSWORD`: Key password

## Digital Asset Links Setup
For the TWA to work properly:
1. Generate SHA-256 fingerprint from your keystore
2. Update `assetlinks.json` with the fingerprint
3. Host at: `https://mandi-tracker.vercel.app/.well-known/assetlinks.json`
4. Ensure it returns Content-Type: application/json

## Customization Points
- **App Name**: `app/src/main/res/values/strings.xml`
- **Colors**: `app/src/main/res/values/colors.xml`
- **Target URL**: `app/src/main/AndroidManifest.xml` (metadata and intent-filter)
- **Package Name**: `app/build.gradle` (applicationId)

## Testing & Deployment
1. Push code to GitHub
2. Check Actions tab for build status
3. Download APK/AAB from Artifacts
4. Test APK on Android device
5. Upload AAB to Google Play Console

## User Preferences
- This is an Android build project, not a web application
- Builds should happen via GitHub Actions
- Documentation should be comprehensive for GitHub-based workflow
