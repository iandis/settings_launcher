import Flutter
import UIKit

public class SettingsLauncherPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "app.iandis/settings_launcher", binaryMessenger: registrar.messenger())
        let instance = SettingsLauncherPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(
        _ call: FlutterMethodCall,
        result: @escaping FlutterResult
    ) {
        guard call.method == "launch",
              let args = call.arguments as? [String:Any],
              let type = args["type"] as? String else { return }
        
        switch type {
        case "biometrics":
            _openAppSettings(result)
        default:
            result(FlutterMethodNotImplemented)
        }
    }
    
    /// iOS does not support opening up passcode settings, so we'll just open up the app settings
    /// instead
    private func _openAppSettings(_ result: @escaping FlutterResult) {
        guard let url = URL(string: UIApplication.openSettingsURLString) else { return }
        if UIApplication.shared.canOpenURL(url) {
            UIApplication.shared.open(url)
        }
        result(nil)
    }
}
