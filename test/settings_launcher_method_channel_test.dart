import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:settings_launcher/settings_launcher.dart';
import 'package:settings_launcher/src/settings_launcher_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelSettingsLauncher platform = MethodChannelSettingsLauncher();
  const MethodChannel channel = MethodChannel('com.aboitizpower/settings_launcher');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return true;
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('launch', () async {
    expectLater(
      () => platform.launch(type: SettingsType.biometrics),
      completes,
    );
  });
}
