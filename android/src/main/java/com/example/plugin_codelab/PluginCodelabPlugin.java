package com.example.plugin_codelab;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** PluginCodelabPlugin */
public class PluginCodelabPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Synth synth;
  private static final String channelName = "plugin_codelab";

   private static void setup(PluginCodelabPlugin plugin, BinaryMessenger binaryMessenger) {
    plugin.channel = new MethodChannel(binaryMessenger, channelName);
    plugin.channel.setMethodCallHandler(plugin);
    plugin.synth = new Synth();
    plugin.synth.start();
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "plugin_codelab");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } 
     else if (call.method.equals("onKeyDown")) {
    try {
      ArrayList arguments = (ArrayList) call.arguments;
      int numKeysDown = synth.keyDown((Integer) arguments.get(0));
      result.success(numKeysDown);
    } catch (Exception ex) {
      result.error("1", ex.getMessage(), ex.getStackTrace());
    }
  } else if (call.method.equals("onKeyUp")) {
    try {
      ArrayList arguments = (ArrayList) call.arguments;
      int numKeysDown = synth.keyUp((Integer) arguments.get(0));
      result.success(numKeysDown);
    } catch (Exception ex) {
      result.error("1", ex.getMessage(), ex.getStackTrace());
    }
  } 
    
    
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
