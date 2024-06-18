import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'settings_launcher_method_channel.dart';

enum SettingsType {
  biometrics,
  ;
}

abstract class SettingsLauncherPlatform extends PlatformInterface {
  /// Constructs a SettingsLauncherPlatform.
  SettingsLauncherPlatform() : super(token: _token);

  static final Object _token = Object();

  static SettingsLauncherPlatform _instance = MethodChannelSettingsLauncher();

  /// The default instance of [SettingsLauncherPlatform] to use.
  ///
  /// Defaults to [MethodChannelSettingsLauncher].
  static SettingsLauncherPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SettingsLauncherPlatform] when
  /// they register themselves.
  static set instance(SettingsLauncherPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> launch({
    required SettingsType type,
    bool asAnotherTask = false,
  }) {
    throw UnimplementedError('launch() has not been implemented.');
  }
}
