-keepattributes *Annotation*

# Keep all classes for Android Browser Helper (TWA)
-keep class com.google.androidbrowserhelper.** { *; }
-keep class androidx.browser.** { *; }

# Keep WebView related classes
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}

# Keep Custom Tabs related classes
-keep class android.support.customtabs.** { *; }
-keep class androidx.browser.customtabs.** { *; }

# Keep FileProvider
-keep class androidx.core.content.FileProvider { *; }
