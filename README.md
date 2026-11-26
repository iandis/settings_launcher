# settings_launcher

Opens a page of the phone's settings from a Flutter app.

```dart
SettingsLauncher.launch(type: SettingsType.biometrics, asAnotherTask: true);
```

## `SettingsType.biometrics`

- **Android:** the screen for adding a fingerprint or face: `Settings.ACTION_BIOMETRIC_ENROLL` on Android 11 and later
  (any biometric of at least `BIOMETRIC_WEAK`, the strength `local_auth` checks), `ACTION_FINGERPRINT_ENROLL` on
  Android 9 and 10. Where that screen doesn't open (Android 8 and earlier, or a phone without it), the security
  settings, else the main settings. Android has no public page that only lists the biometrics.
- **iOS:** the app's page in the Settings app (`UIApplication.openSettingsURLString`): iOS can't open the Face ID or
  Touch ID settings.

`asAnotherTask` (Android) opens the page in a task of its own (`FLAG_ACTIVITY_NEW_TASK`), so Back returns to the app
when it runs in a `singleInstance` activity.
