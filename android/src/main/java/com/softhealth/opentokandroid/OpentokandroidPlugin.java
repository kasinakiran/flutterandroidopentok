package com.softhealth.opentokandroid;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** OpentokandroidPlugin */
public class OpentokandroidPlugin implements FlutterPlugin{

  private MethodChannel channel;

  public OpentokandroidPlugin() {}


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {


    BinaryMessenger messenger = flutterPluginBinding.getBinaryMessenger();
    flutterPluginBinding
            .getFlutterEngine()
            .getPlatformViewsController()
            .getRegistry()
            .registerViewFactory(
                    "opentokandroid", new OpenTokFactory(messenger,null /*containerView= null*/));


  }


  public static void registerWith(Registrar registrar) {

    registrar
            .platformViewRegistry()
            .registerViewFactory(
                    "opentokandroid",
                    new OpenTokFactory(registrar.messenger(), registrar.view()));
  }

  /*@Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }*/

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
   return;
  }
}
