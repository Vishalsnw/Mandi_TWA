# Quick Setup Guide - Mandi Tracker TWA

## 🚀 Instant Start (No Setup!)

The easiest way to get started is to use **auto-generated keystore**:

```bash
git push origin main
```

That's it! Go to **Actions** tab and download your APK.

---

## 📋 Table of Contents

1. [Quick Testing (Auto-Keystore)](#quick-testing-auto-keystore)
2. [Production Setup (Custom Keystore)](#production-setup-custom-keystore)
3. [Digital Asset Links](#digital-asset-links)
4. [Google Play Publishing](#google-play-publishing)

---

## Quick Testing (Auto-Keystore)

### Step 1: Push to GitHub

```bash
git add .
git commit -m "Setup TWA for Mandi Tracker"
git push origin main
```

### Step 2: Download Your APK

1. Go to **Actions** tab in GitHub
2. Wait for build to complete (green checkmark)
3. Click on the workflow run
4. Download these artifacts:
   - `mandi-tracker-apk` - Install on Android
   - `fingerprint-info` - Contains SHA-256 fingerprint

### Step 3: Test on Android

1. Transfer APK to your Android device
2. Enable "Install from Unknown Sources"
3. Install the APK
4. Open and test the app

### ⚠️ Important Notes

- Keystore is **regenerated on every build**
- Fingerprint changes each time
- **NOT suitable for production**
- Cannot update existing installations
- Perfect for testing and demos

---

## Production Setup (Custom Keystore)

**Required for:** Google Play Store, production releases, app updates

### Step 1: Create Keystore

```bash
keytool -genkey -v -keystore mandi-tracker.keystore \
  -alias mandi \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

You'll be prompted for:
- Keystore password (remember this!)
- Key password (remember this!)
- Your name and organization details

**IMPORTANT:** Store your keystore and passwords securely! Losing them means you cannot update your app.

### Step 2: Get SHA-256 Fingerprint

```bash
keytool -list -v -keystore mandi-tracker.keystore -alias mandi
```

Copy the SHA-256 fingerprint (looks like: `AA:BB:CC:DD:...`)

### Step 3: Configure GitHub Secrets

1. Go to your GitHub repository
2. Click **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Add **all four** secrets:

#### Secret 1: SIGNING_KEY

```bash
base64 mandi-tracker.keystore | tr -d '\n' > keystore-base64.txt
cat keystore-base64.txt
```

Copy the output and paste as `SIGNING_KEY` value.

#### Secret 2: KEY_ALIAS

Value: `mandi` (or whatever alias you used)

#### Secret 3: KEY_STORE_PASSWORD

Your keystore password

#### Secret 4: KEY_PASSWORD

Your key password

### Step 4: Push and Build

```bash
git push origin main
```

The workflow now uses **your custom keystore** instead of auto-generating one!

### Step 5: Verify Custom Keystore

1. Go to **Actions** → latest workflow run
2. Check build logs - should say "Using CUSTOM keystore (production-ready)"
3. Download `fingerprint-info` artifact
4. Verify fingerprint matches Step 2

---

## Digital Asset Links

Digital Asset Links verify that your app owns the website domain.

### Step 1: Get Fingerprint

Download `fingerprint-info` from your build artifacts. It contains everything you need.

### Step 2: Create assetlinks.json

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

Replace `YOUR_SHA256_FINGERPRINT_HERE` with your actual fingerprint.

### Step 3: Host on Your Website

#### For Vercel:

1. In your web project, create: `.well-known/assetlinks.json`
2. Paste the JSON content
3. Deploy to Vercel

#### Verify it's accessible:

```bash
curl https://mandi-tracker.vercel.app/.well-known/assetlinks.json
```

Should return your JSON with correct Content-Type.

### Step 4: Test TWA Verification

1. Install the app on Android
2. Open the app
3. Wait 20 seconds for verification
4. App should show full-screen (no browser UI)

If browser UI shows:
- Check assetlinks.json is publicly accessible
- Verify fingerprint matches exactly
- Check for any typos in package name
- Review logcat: `adb logcat | grep TwaLauncher`

---

## Google Play Publishing

### Prerequisites

✅ Custom keystore configured (Step-by-Step above)
✅ AAB file built and downloaded
✅ Digital Asset Links configured

### Step 1: Prepare AAB

1. Trigger GitHub Actions build
2. Download `mandi-tracker-aab` artifact
3. Extract the AAB file

### Step 2: Create Google Play Account

1. Go to [Google Play Console](https://play.google.com/console/)
2. Sign up ($25 one-time fee)
3. Complete account verification

### Step 3: Create App

1. Click **Create app**
2. Fill in app details:
   - App name: Mandi Tracker
   - Default language
   - App or game: App
   - Free or paid: Free
3. Accept declarations

### Step 4: Upload AAB

1. Go to **Production** → **Create new release**
2. Upload your AAB file
3. Add release notes
4. Save

### Step 5: Complete Store Listing

Required sections:
- App details (description, screenshots, icon)
- Content rating (questionnaire)
- Target audience
- Privacy policy
- App content

### Step 6: Submit for Review

1. Review all sections are complete
2. Click **Send for review**
3. Wait for approval (typically 1-3 days)

---

## 🔄 Common Workflows

### Testing a New Feature

```bash
# Make changes to your web app
# Then rebuild TWA:
git push origin main

# Download and test APK
```

### Creating a Release Version

```bash
git tag v1.0.0
git push origin v1.0.0

# Creates a GitHub Release with APK and AAB
```

### Updating Published App

**Important:** Must use same custom keystore!

1. Increment version in `app/build.gradle`:
   ```gradle
   versionCode 2
   versionName "1.1"
   ```

2. Push changes:
   ```bash
   git push origin main
   ```

3. Download new AAB

4. Upload to Google Play Console

---

## 🆘 Troubleshooting

### "Using AUTO-GENERATED keystore" in logs (but I added secrets)

- Verify all 4 secrets are added correctly
- Secret names must match exactly (case-sensitive)
- SIGNING_KEY should have no spaces or line breaks
- Try re-encoding keystore: `base64 mandi-tracker.keystore | tr -d '\n'`

### Fingerprint keeps changing

- You're using auto-generated keystore
- Add custom keystore secrets to fix this
- Each build generates a new keystore if secrets not found

### App won't verify Digital Asset Links

**Auto-keystore users:**
- Fingerprint changes every build
- Update assetlinks.json after each build
- Or switch to custom keystore

**Custom keystore users:**
- Double-check fingerprint in assetlinks.json
- Ensure file is accessible (curl test)
- Verify Content-Type is `application/json`
- Check package name matches exactly

### Cannot update installed app

- You're using different keystore than original install
- If using auto-keystore: each build has different key
- Solution: Use custom keystore from the start

---

## 📊 Quick Reference

### Auto-Keystore vs Custom Keystore

| Task | Auto-Keystore | Custom Keystore |
|------|---------------|-----------------|
| Initial setup | Just push! | Create keystore + secrets |
| Build time | ~5 minutes | ~5 minutes |
| Fingerprint | Changes every build | Same every build |
| Testing | ✅ Perfect | ✅ Perfect |
| Production | ❌ No | ✅ Yes |
| Google Play | ❌ No | ✅ Yes |
| App updates | ❌ No | ✅ Yes |

### When to Use Which Mode

**Use Auto-Keystore for:**
- Quick testing
- Demos
- Development
- Proof of concept
- Internal testing

**Use Custom Keystore for:**
- Production releases
- Google Play Store
- Apps that need updates
- Public distribution
- Long-term projects

---

## 📞 Getting Help

- Review workflow logs in GitHub Actions
- Check `fingerprint-info` artifact for guidance
- Test Digital Asset Links: https://developers.google.com/digital-asset-links/tools/generator
- Android TWA docs: https://developer.chrome.com/docs/android/trusted-web-activity/

---

**Quick Commands Reference:**

```bash
# Generate keystore
keytool -genkey -v -keystore mandi-tracker.keystore -alias mandi -keyalg RSA -keysize 2048 -validity 10000

# Get fingerprint
keytool -list -v -keystore mandi-tracker.keystore -alias mandi

# Encode to base64
base64 mandi-tracker.keystore | tr -d '\n'

# Build locally (requires Android SDK)
./gradlew assembleRelease
./gradlew bundleRelease

# Create release tag
git tag v1.0.0
git push origin v1.0.0
```
