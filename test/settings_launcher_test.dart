import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:settings_launcher/settings_launcher.dart';
import 'package:settings_launcher/src/settings_launcher_method_channel.dart';
import 'package:settings_launcher/src/settings_launcher_platform_interface.dart';

class MockSettingsLauncherPlatform
    with MockPlatformInterfaceMixin
    implements SettingsLauncherPlatform {
  @override
  Future<void> launch({
    required SettingsType type,
    bool asAnotherTask = false,
  }) =>
      Future.value();
}

void main() {
  final SettingsLauncherPlatform initialPlatform =
      SettingsLauncherPlatform.instance;

  test('$MethodChannelSettingsLauncher is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSettingsLauncher>());
  });

  test('getPlatformVersion', () async {
    MockSettingsLauncherPlatform fakePlatform = MockSettingsLauncherPlatform();
    SettingsLauncherPlatform.instance = fakePlatform;

    expectLater(
      () => SettingsLauncher.launch(type: SettingsType.biometrics),
      completes,
    );
  });
}
