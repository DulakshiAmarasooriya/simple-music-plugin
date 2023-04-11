import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'plugin_codelab_platform_interface.dart';

/// An implementation of [PluginCodelabPlatform] that uses method channels.
class MethodChannelPluginCodelab extends PluginCodelabPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  static const MethodChannel _channel = const MethodChannel('plugin_codelab');
  @override
  Future<String?> getPlatformVersion() async {
    final version = await _channel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
   static Future<int?> onKeyDown(int key) async {
    final int? numNotesOn = await _channel.invokeMethod('onKeyDown', [key]);
    return numNotesOn;
  }

  static Future<int?> onKeyUp(int key) async {
    final int? numNotesOn = await _channel.invokeMethod('onKeyUp', [key]);
    return numNotesOn;
  }

  
}
