# How to Fix TWA Issues - MandiMitra

## Current Problems

1. ❌ **Address bar showing** - App doesn't feel like a native Android app
2. ❌ **"Example Domain" showing** - Wrong content loading

## Root Causes

### Issue 1: Digital Asset Links Missing
Your website is missing the Digital Asset Links file that proves your app owns the domain. Without it, Android shows the address bar for security.

**Missing file:** `https://mandi-tracker.vercel.app/.well-known/assetlinks.json`
**Current status:** Returns 404 (not found)

### Issue 2: Website Content
The "Example Domain" message suggests the website might be:
- Redirecting to example.com
- Showing placeholder content
- Not properly deployed

## How to Fix

### Step 1: Build the APK and Get Fingerprint

1. **Push the fixed code to GitHub:**
   ```bash
   git add .
   git commit -m "Fix TWA configuration"
   git push origin main
   ```

2. **Go to GitHub Actions:**
   - Open your repository on GitHub
   - Click the **Actions** tab
   - Wait for the build to complete (~5 minutes)

3. **Download the fingerprint:**
   - Click on the completed workflow run
   - Scroll to **Artifacts** section
   - Download `fingerprint-info`
   - Extract and open `fingerprint-info.txt`
   - Copy the SHA-256 fingerprint (looks like `AA:BB:CC:DD:...`)

### Step 2: Create Digital Asset Links File

1. **Create the file in your web project:**
   
   In your Vercel/Next.js project, create this file:
   ```
   public/.well-known/assetlinks.json
   ```

2. **Add this content** (replace `YOUR_SHA256_FINGERPRINT` with the actual fingerprint from Step 1):

   ```json
   [{
     "relation": ["delegate_permission/common.handle_all_urls"],
     "target": {
       "namespace": "android_app",
       "package_name": "com.mandi.tracker",
       "sha256_cert_fingerprints": [
         "YOUR_SHA256_FINGERPRINT"
       ]
     }
   }]
   ```

   **Example:**
   ```json
   [{
     "relation": ["delegate_permission/common.handle_all_urls"],
     "target": {
       "namespace": "android_app",
       "package_name": "com.mandi.tracker",
       "sha256_cert_fingerprints": [
         "14:6D:E9:83:C5:73:06:50:D8:EE:B9:95:2F:34:FC:64:16:A0:83:42:E6:1D:BE:A8:8A:04:96:B1:3F:CF:44:E5"
       ]
     }
   }]
   ```

3. **Deploy to Vercel:**
   ```bash
   git add public/.well-known/assetlinks.json
   git commit -m "Add Digital Asset Links for TWA"
   git push
   ```

4. **Verify it works:**
   ```bash
   curl https://mandi-tracker.vercel.app/.well-known/assetlinks.json
   ```
   
   Should return your JSON file (not 404).

### Step 3: Fix Website Content (If Needed)

If your Mandi Tracker website is showing "Example Domain", check:

1. **Is the website deployed?**
   - Visit https://mandi-tracker.vercel.app/ in your browser
   - Does it show your actual Mandi Tracker app?

2. **If it shows the wrong content:**
   - Make sure your main web app is properly deployed to Vercel
   - Check Vercel dashboard for deployment status
   - Verify the domain is pointing to the correct project

### Step 4: Test the TWA

1. **Uninstall the old app** from your Android device

2. **Download the new APK** from GitHub Actions:
   - Same build that gave you the fingerprint
   - Download `mandi-tracker-apk` artifact

3. **Install and wait:**
   - Install the APK on your Android device
   - Open the app
   - **Wait 20-30 seconds** for Digital Asset Links verification
   - The app should verify and hide the address bar
   - Full-screen experience! ✅

## Important Notes

### About Auto-Keystore Mode

You're currently using **auto-keystore mode** (for testing). This means:
- ⚠️ **Fingerprint changes on EVERY build**
- You need to update assetlinks.json after each new build
- Not suitable for production

### For Production (Recommended)

To avoid updating assetlinks.json constantly:

1. **Create a custom keystore** (one-time setup):
   ```bash
   keytool -genkey -v -keystore mandi-tracker.keystore \
     -alias mandi \
     -keyalg RSA \
     -keysize 2048 \
     -validity 10000
   ```

2. **Get the fingerprint:**
   ```bash
   keytool -list -v -keystore mandi-tracker.keystore -alias mandi
   ```

3. **Add GitHub secrets** (see SETUP_GUIDE.md for details):
   - `SIGNING_KEY` (base64 of keystore)
   - `KEY_ALIAS`
   - `KEY_STORE_PASSWORD`
   - `KEY_PASSWORD`

4. **Benefits:**
   - Same fingerprint on every build ✅
   - Update assetlinks.json only once ✅
   - Can update the app ✅
   - Required for Google Play Store ✅

## Troubleshooting

### Address bar still showing?

1. **Verify assetlinks.json:**
   ```bash
   curl https://mandi-tracker.vercel.app/.well-known/assetlinks.json
   ```
   - Should return valid JSON (not 404)
   - Should have correct fingerprint
   - Should have Content-Type: application/json

2. **Check fingerprint matches:**
   - Compare fingerprint in assetlinks.json
   - With fingerprint from GitHub Actions artifact
   - They must match exactly (colons included)

3. **Wait for verification:**
   - First launch: wait 20-30 seconds
   - Android verifies in background
   - May need to close and reopen app

4. **Clear data and retry:**
   - Settings → Apps → MandiMitra
   - Clear data
   - Reopen app

### Wrong website showing?

1. **Test the URL directly:**
   ```bash
   curl -L https://mandi-tracker.vercel.app/
   ```

2. **Check for redirects:**
   - Make sure site doesn't redirect to example.com
   - Check Vercel deployment logs

3. **Verify in browser:**
   - Open https://mandi-tracker.vercel.app/ in Chrome
   - Should show your actual Mandi Tracker app

## Quick Checklist

- [ ] Build APK via GitHub Actions
- [ ] Download fingerprint-info artifact
- [ ] Copy SHA-256 fingerprint
- [ ] Create `.well-known/assetlinks.json` in web project
- [ ] Add fingerprint to assetlinks.json
- [ ] Deploy web project to Vercel
- [ ] Verify assetlinks.json is accessible (curl test)
- [ ] Uninstall old app
- [ ] Install new APK
- [ ] Wait 20-30 seconds
- [ ] Enjoy fullscreen TWA experience! 🎉

## Need Help?

- See `SETUP_GUIDE.md` for detailed keystore setup
- See `README.md` for general TWA information
- Test Digital Asset Links: https://developers.google.com/digital-asset-links/tools/generator
