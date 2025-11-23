# Don't obfuscate - keep everything for TWA
-dontobfuscate

# Keep all attributes
-keepattributes *Annotation*,Signature,Exception,InnerClasses,EnclosingMethod

# Keep all classes for Android Browser Helper (TWA)
-keep class com.google.androidbrowserhelper.** { *; }
-keep class androidx.browser.** { *; }

# Keep AppCompat
-keep class androidx.appcompat.** { *; }
-keep class androidx.core.** { *; }

# Keep WebView related classes
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep Custom Tabs
-keep class android.support.customtabs.** { *; }
-keep class androidx.browser.customtabs.** { *; }

# Don't warn about missing classes
-dontwarn com.google.androidbrowserhelper.**
-dontwarn androidx.browser.**
