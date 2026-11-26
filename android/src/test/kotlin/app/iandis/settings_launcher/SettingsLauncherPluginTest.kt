package app.iandis.settings_launcher

import android.os.Build
import android.provider.Settings
import kotlin.test.Test
import kotlin.test.assertEquals

/*
 * Run with `./gradlew :settings_launcher:testDebugUnitTest` in `example/android/` (after `flutter build apk
 * --config-only` in `example/`, which writes the Gradle wrapper).
 */
@Suppress("DEPRECATION") // ACTION_FINGERPRINT_ENROLL
internal class SettingsLauncherPluginTest {
  @Test
  fun biometricSettingsActions_android11AndLater_addsABiometricFirst() {
    for (sdkInt in listOf(Build.VERSION_CODES.R, Build.VERSION_CODES.TIRAMISU, 36)) {
      assertEquals(
        listOf(Settings.ACTION_BIOMETRIC_ENROLL, Settings.ACTION_SECURITY_SETTINGS, Settings.ACTION_SETTINGS),
        biometricSettingsActions(sdkInt),
        "API $sdkInt",
      )
    }
  }

  @Test
  fun biometricSettingsActions_android9And10_addsAFingerprintFirst() {
    for (sdkInt in listOf(Build.VERSION_CODES.P, Build.VERSION_CODES.Q)) {
      assertEquals(
        listOf(Settings.ACTION_FINGERPRINT_ENROLL, Settings.ACTION_SECURITY_SETTINGS, Settings.ACTION_SETTINGS),
        biometricSettingsActions(sdkInt),
        "API $sdkInt",
      )
    }
  }

  @Test
  fun biometricSettingsActions_beforeAndroid9_opensTheSecuritySettings() {
    for (sdkInt in listOf(Build.VERSION_CODES.N, Build.VERSION_CODES.O_MR1)) {
      assertEquals(
        listOf(Settings.ACTION_SECURITY_SETTINGS, Settings.ACTION_SETTINGS),
        biometricSettingsActions(sdkInt),
        "API $sdkInt",
      )
    }
  }
}
