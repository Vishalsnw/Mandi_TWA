# Mandi Tracker TWA - Trusted Web Activity Android App

This is an Android Trusted Web Activity (TWA) application that wraps the Mandi Tracker web app hosted at [https://mandi-tracker.vercel.app/](https://mandi-tracker.vercel.app/) into a native Android app.

## 📱 What is a TWA?

Trusted Web Activities allow you to package your Progressive Web App (PWA) as a native Android application that can be distributed via Google Play Store. The app runs your website in full-screen mode without any browser UI.

## 🚀 Features

- ✅ **Auto-Keystore Generation** - Start building immediately without manual setup!
- ✅ Full-screen web app experience
- ✅ Automatic builds via GitHub Actions
- ✅ Release APK and AAB generation
- ✅ Digital Asset Links verification
- ✅ Custom splash screen and app icon
- ✅ Status bar and navigation bar theming

## 🎯 Quick Start (No Setup Required!)

1. **Push to GitHub**
   ```bash
   git push origin main
   ```

2. **Get Your APK**
   - Go to **Actions** tab
   - Wait for build to complete
   - Download `mandi-tracker-apk` and `fingerprint-info` artifacts

3. **Install and Test**
   - Transfer APK to Android device
   - Install and test the app

That's it! The workflow automatically generates a keystore and signs your APK.

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

## 🔧 Two Build Modes

### Mode 1: Auto-Generated Keystore (Testing)

**No configuration needed!** Just push to GitHub.

✅ **Pros:**
- Works immediately out of the box
- Perfect for testing and development
- No manual keystore creation

⚠️ **Cons:**
- Keystore changes on EVERY build (new fingerprint each time)
- NOT suitable for production or Google Play Store
- Cannot update existing installations

**Use Case:** Quick testing, demos, internal testing

### Mode 2: Custom Keystore (Production)

**For production releases and Google Play Store.**

✅ **Pros:**
- Consistent fingerprint across builds
- Can update existing installations
- Required for Google Play Store

⚠️ **Setup Required:**
- Must create and configure keystore
- Manage GitHub secrets

**Use Case:** Production releases, Google Play Store

## 🔐 Production Setup (Custom Keystore)

### 1. Create Your Keystore

```bash
keytool -genkey -v -keystore mandi-tracker.keystore \
  -alias mandi \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

**Important:** Save your passwords! You'll need them for GitHub secrets.

### 2. Configure GitHub Secrets

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add these four secrets:

#### SIGNING_KEY
Encode your keystore to base64:
```bash
base64 mandi-tracker.keystore | tr -d '\n' > keystore-base64.txt
```
Copy the content and create secret `SIGNING_KEY`

#### KEY_ALIAS
Your keystore alias (e.g., `mandi`)

#### KEY_STORE_PASSWORD
Your keystore password

#### KEY_PASSWORD
Your key password

### 3. Push and Build

Once secrets are configured, the workflow automatically uses your custom keystore!

```bash
git push origin main
```

## 📥 Getting Your Build Artifacts

After any build (auto or custom keystore):

1. Go to **Actions** tab in GitHub
2. Click on the latest workflow run
3. Scroll to **Artifacts** section
4. Download:
   - `mandi-tracker-apk` - APK file for Android devices
   - `mandi-tracker-aab` - AAB file for Google Play Store
   - `fingerprint-info` - SHA-256 fingerprint and setup instructions

## 🔗 Digital Asset Links Setup

To enable full TWA functionality (no browser UI):

### 1. Get Your SHA-256 Fingerprint

Download the `fingerprint-info` artifact from your build. It contains your SHA-256 fingerprint.

### 2. Update assetlinks.json

```json
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.mandi.tracker",
    "sha256_cert_fingerprints": [
      "YOUR_SHA256_FINGERPRINT_HERE"
    ]
  }
}]
```

### 3. Host on Your Website

Place the file at:
```
https://mandi-tracker.vercel.app/.well-known/assetlinks.json
```

**For Vercel:** Create `.well-known/assetlinks.json` in your web project

**Important for Auto-Keystore Users:**
- The fingerprint changes on every build
- You'll need to update assetlinks.json after each build
- For persistent fingerprints, use a custom keystore

## 🎨 Customization

### Change App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### Change Colors
Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="colorPrimary">#YourColor</color>
```

### Change Package Name & URL
1. Update `app/build.gradle` → `applicationId`
2. Update `app/src/main/AndroidManifest.xml` → host and package references
3. Update `assetlinks.json` → `package_name`

## 🤖 GitHub Actions Workflow

The workflow automatically:
1. ✅ Generates keystore (if secrets not provided)
2. ✅ Builds release APK and AAB files
3. ✅ Signs the builds
4. ✅ Extracts SHA-256 fingerprint
5. ✅ Creates fingerprint info file
6. ✅ Uploads artifacts for download
7. ✅ Creates GitHub releases for version tags

### Triggering a Build

**Automatic:** Push to main/master branch
```bash
git push origin main
```

**Manual:** Go to Actions → Build Release APK and AAB → Run workflow

**Release:** Create and push a version tag
```bash
git tag v1.0.0
git push origin v1.0.0
```

## 📱 App Configuration

- **Package Name**: `com.mandi.tracker`
- **Target URL**: `https://mandi-tracker.vercel.app/`
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34 (Android 14)
- **Theme Color**: #4CAF50 (Green)

## 🏪 Publishing to Google Play Store

**You MUST use a custom keystore for Google Play!**

1. Configure custom keystore secrets (see above)
2. Build AAB file via GitHub Actions
3. Download the `mandi-tracker-aab` artifact
4. Create Google Play Console account
5. Create new app
6. Upload AAB file
7. Complete store listing, content rating, etc.
8. Submit for review

## 🔄 Build Comparison

| Feature | Auto-Keystore | Custom Keystore |
|---------|--------------|-----------------|
| Setup Required | None ✅ | Create keystore & secrets |
| Fingerprint | Changes every build ⚠️ | Persistent ✅ |
| Good for Testing | Yes ✅ | Yes ✅ |
| Google Play Store | No ❌ | Yes ✅ |
| Can Update App | No ❌ | Yes ✅ |
| Recommended for | Testing & demos | Production releases |

## 🐛 Troubleshooting

### TWA shows browser UI instead of full-screen
- Digital Asset Links verification failed
- Check that assetlinks.json is accessible at `/.well-known/assetlinks.json`
- Verify SHA-256 fingerprint matches (download `fingerprint-info` artifact)
- For auto-keystore: fingerprint changes on every build

### Build fails on GitHub Actions
- Check GitHub Actions logs for specific errors
- If using custom keystore, verify all 4 secrets are correctly set
- Ensure SIGNING_KEY has no line breaks (use `tr -d '\n'`)

### "Cannot install app" on Android
- Enable "Install from Unknown Sources" in device settings
- Make sure APK is fully downloaded
- Try uninstalling any existing version first

### Different fingerprint on each build
- This is normal for auto-generated keystore
- For consistent fingerprint, use custom keystore (see Production Setup)

## 📄 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## 🔗 Links

- Website: https://mandi-tracker.vercel.app/
- Android Trusted Web Activities: https://developer.chrome.com/docs/android/trusted-web-activity/
- Google Play Console: https://play.google.com/console/
- Digital Asset Links Tester: https://developers.google.com/digital-asset-links/tools/generator
