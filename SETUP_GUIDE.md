# Quick Setup Guide - Mandi Tracker TWA

## Step-by-Step Instructions

### Step 1: Create Keystore (One-time)

```bash
keytool -genkey -v -keystore mandi-tracker.keystore \
  -alias mandi \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

**Important**: Save the passwords you enter!

### Step 2: Get SHA-256 Fingerprint

```bash
keytool -list -v -keystore mandi-tracker.keystore -alias mandi
```

Copy the SHA-256 fingerprint (looks like: `AA:BB:CC:DD:...`)

### Step 3: Configure GitHub Secrets

1. Go to your GitHub repository
2. Click **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Add these four secrets:

#### SIGNING_KEY
```bash
# Encode your keystore to base64
base64 mandi-tracker.keystore | tr -d '\n'
```
Copy the output and paste as `SIGNING_KEY`

#### KEY_ALIAS
Value: `mandi` (or whatever alias you used)

#### KEY_STORE_PASSWORD
Your keystore password

#### KEY_PASSWORD
Your key password

### Step 4: Setup Digital Asset Links

1. Edit `assetlinks.json`:
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

2. Host this file at:
   ```
   https://mandi-tracker.vercel.app/.well-known/assetlinks.json
   ```

   **For Vercel**: Create `.well-known/assetlinks.json` in your web project

### Step 5: Push to GitHub

```bash
git add .
git commit -m "Setup TWA for Mandi Tracker"
git push origin main
```

### Step 6: Get Your APK/AAB

1. Go to **Actions** tab in GitHub
2. Wait for the build to complete (green checkmark)
3. Click on the workflow run
4. Scroll down to **Artifacts**
5. Download:
   - `mandi-tracker-apk` - Install on Android devices
   - `mandi-tracker-aab` - Upload to Google Play Store

### Step 7: Test the APK

1. Transfer APK to Android device
2. Enable "Install from Unknown Sources"
3. Install the APK
4. Open the app - it should load your website in full-screen

### Step 8: Publish to Google Play (Optional)

1. Create account at [Google Play Console](https://play.google.com/console/)
2. Create new app
3. Upload the AAB file
4. Complete store listing
5. Submit for review

## Troubleshooting

### "Website cannot be verified"
- Check assetlinks.json is publicly accessible
- Verify SHA-256 fingerprint matches exactly
- Ensure Content-Type is `application/json`

### Build fails on GitHub
- Verify all 4 secrets are added correctly
- Check that SIGNING_KEY has no line breaks
- Review GitHub Actions logs for specific errors

### App shows browser UI
- Digital Asset Links verification failed
- Wait 20 seconds after first launch for verification
- Check logcat: `adb logcat | grep TwaLauncher`

## Quick Commands Reference

```bash
# Generate keystore
keytool -genkey -v -keystore mandi-tracker.keystore -alias mandi -keyalg RSA -keysize 2048 -validity 10000

# Get SHA-256
keytool -list -v -keystore mandi-tracker.keystore -alias mandi

# Encode to base64
base64 mandi-tracker.keystore | tr -d '\n'

# Local build (if you have Android SDK)
./gradlew assembleRelease
./gradlew bundleRelease

# Create version tag
git tag v1.0.0
git push origin v1.0.0
```

## Need Help?

- [Android TWA Documentation](https://developer.chrome.com/docs/android/trusted-web-activity/)
- [Digital Asset Links Testing](https://developers.google.com/digital-asset-links/tools/generator)
- Check GitHub Actions logs for build errors
