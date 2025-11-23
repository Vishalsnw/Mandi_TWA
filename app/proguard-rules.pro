# Don't optimize or obfuscate - TWA needs all classes intact
-dontoptimize
-dontobfuscate
-dontshrink

# Keep all attributes
-keepattributes *Annotation*,Signature,Exception,InnerClasses,EnclosingMethod

# Keep everything - TWA is lightweight and needs all classes
-keep class ** { *; }

# Specific keeps for TWA components
-keep class com.google.androidbrowserhelper.** { *; }
-keep class androidx.browser.** { *; }
-keep class androidx.appcompat.** { *; }
-keep class androidx.core.** { *; }

# Don't warn
-dontwarn com.google.androidbrowserhelper.**
-dontwarn androidx.browser.**
-dontwarn androidx.appcompat.**
