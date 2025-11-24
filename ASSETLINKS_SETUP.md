# Critical: Digital Asset Links Setup Required

## Why Address Bar Shows

Your app shows an address bar because **Digital Asset Links verification is failing**. This is the ONLY reason TWA apps show address bars.

## What You Must Do

### Step 1: Build the App First

```bash
git add .
git commit -m "Update to AndroidX and fix TWA configuration"
git push origin main
```

Go to GitHub Actions and wait for the build to complete.

### Step 2: Get the Fingerprint

1. In GitHub Actions, download the **`fingerprint-info`** artifact
2. Open `fingerprint-info.txt`
3. Copy the SHA-256 fingerprint (e.g., `14:6D:E9:83:C5:73:...`)

### Step 3: Add to Your Website

In your **mandi-tracker** web project (the one deployed to Vercel), create:

**File location:** `public/.well-known/assetlinks.json`

**File content:**
```json
[{
  "relation": ["delegate_permission/common.handle_all_urls"],
  "target": {
    "namespace": "android_app",
    "package_name": "com.mandi.tracker",
    "sha256_cert_fingerprints": [
      "PASTE_YOUR_FINGERPRINT_HERE"
    ]
  }
}]
```

### Step 4: Deploy Your Website

```bash
cd /path/to/your/mandi-tracker-website
git add public/.well-known/assetlinks.json
git commit -m "Add assetlinks for Android app"
git push
```

### Step 5: Verify It's Live

```bash
curl https://mandi-tracker.vercel.app/.well-known/assetlinks.json
```

Should return your JSON file (not 404).

### Step 6: Install and Test

1. Uninstall old app from your device
2. Install the new APK (from GitHub Actions)
3. Open the app
4. **Wait 20-30 seconds** - Android verifies in background
5. Close and reopen the app
6. **Address bar will be gone!** ✅

## Why This Is Required

- Android TWA security requires proof that your app owns the website
- This proof is the `assetlinks.json` file on your website
- The file contains your app's signing fingerprint
- Without it, Android shows the address bar for safety
- **This cannot be bypassed or disabled** - it's a security feature

## Current Status

✅ AndroidManifest.xml is now correctly configured
✅ All meta-data keys updated to AndroidX
✅ URL set to: https://mandi-tracker.vercel.app/
❌ Digital Asset Links file missing from website
❌ This is why address bar shows

## Fix the "Example Domain" Issue

If you see "Example Domain" instead of your actual website:

1. **Check your website directly:**
   - Open https://mandi-tracker.vercel.app/ in Chrome
   - Does it show your real Mandi Tracker app?

2. **If it shows Example Domain:**
   - Your website deployment might have an issue
   - Check Vercel dashboard
   - Make sure your actual Mandi Tracker app is deployed

The Android app is correctly configured to load https://mandi-tracker.vercel.app/ - the issue is on the website side, not the app side.
