## 0.0.5

- Android: `SettingsType.biometrics` opens the screen for adding a fingerprint or face (`Settings.ACTION_BIOMETRIC_ENROLL`, any biometric of at least `BIOMETRIC_WEAK`, on Android 11 and later; `ACTION_FINGERPRINT_ENROLL` on Android 9 and 10) instead of the security settings. Where that screen doesn't open, it falls back to the security settings, then to the main settings, as before
- Replace the template's Android unit test, which tested a `getPlatformVersion` call the plugin never had, with tests of the screens `biometrics` tries

## 0.0.4

- Support Swift Package Manager on iOS: the sources and the privacy manifest move to `ios/settings_launcher/Sources/settings_launcher`, with a `Package.swift` that depends on Flutter's `FlutterFramework` package; CocoaPods builds keep working

## 0.0.3

- Align the Android and iOS build setup with the Flutter 3.47 plugin template: namespace, compileSdk 36, minSdk 24, JVM 17 for Java and Kotlin, AGP 9.1.0 and Kotlin 2.4.0 in the buildscript, and iOS 15.0
- Stop applying `kotlin-android`: Flutter applies it, or AGP 9 compiles Kotlin itself, so the minimum Flutter SDK is now 3.44.0
- Regenerate the example app with Flutter 3.47.9
- Drop the `package` attribute from the plugin manifest, which the namespace replaces

## 0.0.2

- Bump min Flutter SDK to 3.38.0 and compileSdkVersion to be dynamic

## 0.0.1

- Initial version
