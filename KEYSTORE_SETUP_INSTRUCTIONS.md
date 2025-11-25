# Keystore Setup Instructions for Mandi Tracker

## Problem
Google Play Store rejected your upload because the app was signed with a different keystore than the original upload. The auto-generated keystore from GitHub Actions creates a new key on every build, which is why the fingerprints don't match.

## Solution: Add Production Keystore to GitHub Secrets

### Step 1: Add GitHub Secrets

Go to your GitHub repository:
1. Click **Settings** (top right)
2. Click **Secrets and variables** → **Actions** (left sidebar)
3. Click **New repository secret** button

Add these **4 secrets**:

| Secret Name | Value |
|-------------|-------|
| `SIGNING_KEY` | See `mandi-tracker-keystore-base64.txt` (copy entire contents) |
| `KEY_STORE_PASSWORD` | `MandiTracker2025!` |
| `KEY_PASSWORD` | `MandiTracker2025!` |
| `KEY_ALIAS` | `mandi` |

**Important:** For `SIGNING_KEY`, copy the **entire base64 string** from `mandi-tracker-keystore-base64.txt` (it's very long, make sure you get all of it).

### Step 2: Trigger a New Build

After adding the secrets, push any change to trigger a new build:

```bash
git add .
git commit -m "Use production keystore"
git push origin main
```

The GitHub Actions workflow will now use your permanent keystore.

### Step 3: Get the New Fingerprint

Download the `fingerprint-info` artifact from the GitHub Actions build. This will contain the SHA-256 fingerprint of your new keystore:

- **SHA1**: 6D:F7:54:C7:1C:0A:20:3A:E6:4C:84:B0:26:F0:81:13:D4:57:F9:C8
- **SHA256**: 8E:6E:53:79:C6:F4:FE:51:65:90:EC:F7:83:3C:64:AC:21:65:7E:A2:0B:8E:72:8D:90:95:2B:A4:23:2B:5F:DB

## For Google Play Store

Since you've already uploaded with a different keystore, you have **two options**:

### Option A: Enroll in Play App Signing (Recommended)

1. Go to Google Play Console → Your App
2. Navigate to **Release** → **Setup** → **App signing**
3. Enroll in **App signing by Google Play**
4. Upload your new keystore as the **upload key**
5. Google will handle the migration and allow you to use the new key

### Option B: Contact Google Play Support

If you can't enroll in Play App Signing:
1. Go to Google Play Console
2. Click the **Help** (?) icon
3. Contact support and explain that you lost your signing key
4. They may be able to help reset it (not guaranteed)

### Option C: Create New App Listing

As a last resort, you can publish as a completely new app:
1. Change the package name in `app/build.gradle` (e.g., `com.mandi.tracker.v2`)
2. Create a new app listing in Google Play Console
3. Upload the new AAB

## Files Created

- `mandi-tracker-production.keystore` - Your production keystore (keep this safe!)
- `mandi-tracker-keystore-base64.txt` - Base64 encoded keystore for GitHub
- This instruction file

**IMPORTANT:** Keep `mandi-tracker-production.keystore` in a safe place! If you lose it, you won't be able to update your app. Consider storing it in:
- A password manager
- Encrypted cloud storage
- Multiple secure backup locations
