import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'settings_launcher_platform_interface.dart';

/// An implementation of [SettingsLauncherPlatform] that uses method channels.
class MethodChannelSettingsLauncher extends SettingsLauncherPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('app.iandis/settings_launcher');

  @override
  Future<void> launch({
    required SettingsType type,
    bool asAnotherTask = false,
  }) async {
    await methodChannel.invokeMethod<void>(
      'launch',
      {
        'type': type.name,
        'asAnotherTask': asAnotherTask,
      },
    );
  }
}
