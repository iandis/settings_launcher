import 'settings_launcher_platform_interface.dart';

abstract final class SettingsLauncher {
  static Future<void> launch({
    required SettingsType type,
    bool asAnotherTask = false,
  }) {
    return SettingsLauncherPlatform.instance.launch(
      type: type,
      asAnotherTask: asAnotherTask,
    );
  }
}
