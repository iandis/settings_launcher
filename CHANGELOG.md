## 0.0.3

- Align the Android and iOS build setup with the Flutter 3.47 plugin template: namespace, compileSdk 36, minSdk 24, JVM 17 for Java and Kotlin, AGP 9.1.0 and Kotlin 2.4.0 in the buildscript, and iOS 15.0
- Stop applying `kotlin-android`: Flutter applies it, or AGP 9 compiles Kotlin itself, so the minimum Flutter SDK is now 3.44.0
- Regenerate the example app with Flutter 3.47.9
- Drop the `package` attribute from the plugin manifest, which the namespace replaces

## 0.0.2

- Bump min Flutter SDK to 3.38.0 and compileSdkVersion to be dynamic

## 0.0.1

- Initial version
