# Mandi Tracker TWA - Trusted Web Activity Android App

This is an Android Trusted Web Activity (TWA) application that wraps the Mandi Tracker web app hosted at [https://mandi-tracker.vercel.app/](https://mandi-tracker.vercel.app/) into a native Android app.

## 📱 What is a TWA?

Trusted Web Activities allow you to package your Progressive Web App (PWA) as a native Android application that can be distributed via Google Play Store. The app runs your website in full-screen mode without any browser UI.

## 🚀 Features

- ✅ Full-screen web app experience
- ✅ Automatic builds via GitHub Actions
- ✅ Release APK and AAB generation
- ✅ Digital Asset Links verification
- ✅ Custom splash screen and app icon
- ✅ Status bar and navigation bar theming

## 📦 Project Structure

```
Mandi_TWA/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   └── colors.xml
│   │   │   ├── drawable/
│   │   │   │   └── splash.xml
│   │   │   └── xml/
│   │   │       └── file_paths.xml
│   │   └── java/com/mandi/tracker/
│   ├── build.gradle
│   └── proguard-rules.pro
├── .github/workflows/
│   └── build-release.yml
├── build.gradle
├── settings.gradle
└── assetlinks.json
```

## 🔧 Setup Instructions

### 1. Configure GitHub Secrets

To build signed APK and AAB files, add these secrets to your GitHub repository:

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Add the following secrets:

   - `SIGNING_KEY`: Base64 encoded keystore file
   - `KEY_ALIAS`: Your keystore alias
   - `KEY_STORE_PASSWORD`: Keystore password
   - `KEY_PASSWORD`: Key password

#### Creating a Keystore

```bash
keytool -genkey -v -keystore mandi-tracker.keystore -alias mandi -keyalg RSA -keysize 2048 -validity 10000
```

#### Encoding Keystore to Base64

```bash
base64 mandi-tracker.keystore | tr -d '\n' > keystore-base64.txt
```

Use the content of `keystore-base64.txt` for the `SIGNING_KEY` secret.

### 2. Digital Asset Links

To verify your TWA with your website:

1. Get your app's SHA-256 fingerprint:
   ```bash
   keytool -list -v -keystore mandi-tracker.keystore -alias mandi
   ```

2. Update `assetlinks.json` with your SHA-256 fingerprint

3. Host the file at:
   ```
   https://mandi-tracker.vercel.app/.well-known/assetlinks.json
   ```

4. Ensure CORS and content-type are correct:
   - Content-Type: `application/json`
   - Must be publicly accessible

### 3. Customize the App

#### Change App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

#### Change Colors
Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="colorPrimary">#YourColor</color>
```

#### Change Package Name & URL
If you need to change the package name or URL:
1. Update `app/build.gradle` → `applicationId`
2. Update `app/src/main/AndroidManifest.xml` → host and package references
3. Rename the package directory structure

### 4. Build Locally (Optional)

If you want to build locally instead of using GitHub Actions:

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
./gradlew bundleRelease
```

Output files:
- APK: `app/build/outputs/apk/release/app-release.apk`
- AAB: `app/build/outputs/bundle/release/app-release.aab`

## 🤖 GitHub Actions Workflow

The workflow automatically:
1. ✅ Builds on every push to main/master
2. ✅ Creates release APK and AAB files
3. ✅ Signs the builds (if secrets are configured)
4. ✅ Uploads artifacts for download
5. ✅ Creates GitHub releases for version tags

### Triggering a Build

**Automatic**: Push to main/master branch
```bash
git push origin main
```

**Manual**: Go to Actions → Build Release APK and AAB → Run workflow

**Release**: Create and push a version tag
```bash
git tag v1.0.0
git push origin v1.0.0
```

## 📥 Download Built Files

After a successful build:

1. Go to **Actions** tab in GitHub
2. Click on the latest workflow run
3. Scroll to **Artifacts**
4. Download:
   - `mandi-tracker-apk` - APK file
   - `mandi-tracker-aab` - AAB file for Play Store

## 📱 App Configuration

- **Package Name**: `com.mandi.tracker`
- **Target URL**: `https://mandi-tracker.vercel.app/`
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **Theme Color**: #4CAF50 (Green)

## 🔐 Publishing to Google Play

1. Build a signed AAB file (via GitHub Actions or locally)
2. Create a Google Play Console account
3. Create a new app
4. Upload the AAB file
5. Complete store listing, content rating, etc.
6. Submit for review

## 🐛 Troubleshooting

### TWA not opening website
- Verify Digital Asset Links are correctly configured
- Check that the website is HTTPS
- Ensure the SHA-256 fingerprint matches

### Build fails
- Check GitHub Actions logs
- Verify all secrets are correctly set
- Ensure gradle files are properly formatted

### Website shows browser UI
- Digital Asset Links verification failed
- Check assetlinks.json is accessible
- Verify package name and SHA-256 match

## 📄 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## 🔗 Links

- Website: https://mandi-tracker.vercel.app/
- Android Trusted Web Activities: https://developer.chrome.com/docs/android/trusted-web-activity/
- Google Play Console: https://play.google.com/console/
