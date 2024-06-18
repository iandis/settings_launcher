package app.iandis.settings_launcher

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
            "biometrics" -> _openSettings(Settings.ACTION_SECURITY_SETTINGS, result, asAnotherTask)
            else -> result.notImplemented()
        }
    }

    /**
     * Open the settings panel for the given url.
     */
    private fun _openSettings(
            url: String,
            result: Result,
            asAnotherTask: Boolean = false,
    ) {
        try {
            val intent = Intent(url)
            if (asAnotherTask) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            this._activity?.startActivity(intent)
            result.success(null)
        } catch (e: Exception) {
            // If the Activity fails to start, show the app settings instead.
            _openDeviceSettings(result, asAnotherTask)
        }
    }

    /**
     * Open the device settings.
     */
    private fun _openDeviceSettings(result: Result, asAnotherTask: Boolean = false) {
        try {
            val intent = Intent(Settings.ACTION_SETTINGS)
            if (asAnotherTask) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            this._activity?.startActivity(intent)
        } catch (e: Exception) {
            // no-op
        }

        result.success(null)
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
