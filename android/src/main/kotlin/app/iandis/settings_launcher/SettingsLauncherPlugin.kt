package app.iandis.settings_launcher

import android.app.Activity
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.os.Build
import android.provider.Settings

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** SettingsLauncherPlugin */
class SettingsLauncherPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private var _activity: Activity? = null

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "app.iandis/settings_launcher")
        channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method != "launch") return

        val asAnotherTask = call.argument<Boolean>("asAnotherTask") ?: false
        when (call.argument<String>("type")) {
            "biometrics" -> _openFirst(biometricSettingsActions(Build.VERSION.SDK_INT), result, asAnotherTask)
            else -> result.notImplemented()
        }
    }

    /**
     * Opens the first of [actions] that the phone has an activity for, and answers [result] once.
     */
    private fun _openFirst(
            actions: List<String>,
            result: Result,
            asAnotherTask: Boolean = false,
    ) {
        for (action in actions) {
            try {
                val intent = _intentFor(action)
                if (asAnotherTask) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                this._activity?.startActivity(intent)
                break
            } catch (e: Exception) {
                // No screen for this action on this phone (ActivityNotFoundException), or it isn't open to other apps
                // (SecurityException): try the next one.
            }
        }

        result.success(null)
    }

    private fun _intentFor(action: String): Intent {
        val intent = Intent(action)
        if (action == Settings.ACTION_BIOMETRIC_ENROLL && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // The strength local_auth checks: any fingerprint or face that it accepts can be added here.
            intent.putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BiometricManager.Authenticators.BIOMETRIC_WEAK,
            )
        }
        return intent
    }


    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        this._activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        this._activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        this._activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        this._activity = null
    }
}

/**
 * The screens the `biometrics` type tries, in order, on a phone with [sdkInt]: where the user adds a fingerprint or
 * face (Android 11 and later; a fingerprint on Android 9 and 10), else the security settings, where the phone's
 * biometrics are listed, else the main settings.
 */
@Suppress("DEPRECATION") // ACTION_FINGERPRINT_ENROLL, which Android 9 and 10 still need.
internal fun biometricSettingsActions(sdkInt: Int): List<String> = buildList {
    when {
        sdkInt >= Build.VERSION_CODES.R -> add(Settings.ACTION_BIOMETRIC_ENROLL)
        sdkInt >= Build.VERSION_CODES.P -> add(Settings.ACTION_FINGERPRINT_ENROLL)
    }
    add(Settings.ACTION_SECURITY_SETTINGS)
    add(Settings.ACTION_SETTINGS)
}
