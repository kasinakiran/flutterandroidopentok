import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

typedef void OpenTokViewCreatedCallback(OpenTokController controller);

class OpenTokView extends StatefulWidget {

  final OpenTokViewCreatedCallback onOpenTokCreated;
  final OpenTokViewCreatedCallback onSubChanges;



  OpenTokView({
    Key key,
    @required this.onOpenTokCreated, @required this.onSubChanges,
  });

  @override
  _OpenTokViewState createState() => _OpenTokViewState();
}

class _OpenTokViewState extends State<OpenTokView> {

  @override
  Widget build(BuildContext context) {
    if(defaultTargetPlatform == TargetPlatform.android) {
      return AndroidView(
        viewType: 'opentokandroid',
        onPlatformViewCreated: onPlatformViewCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    } else if(defaultTargetPlatform == TargetPlatform.iOS) {
      return UiKitView(
        viewType: 'opentokandroid',
        onPlatformViewCreated: onPlatformViewCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    }

    return new Text('$defaultTargetPlatform is not yet supported by this plugin');
  }


  // OpenTokCustomFunction onCustomFun;
  Future<void> onPlatformViewCreated(id) async {
    if (widget.onOpenTokCreated == null) {
      return;
    }
  //  onCustomFun(new OpenTokController.custom(id));
    widget.onOpenTokCreated(new OpenTokController.init(id));
    widget.onSubChanges(new OpenTokController.init(id));
  }

}


class OpenTokController {

  MethodChannel _channel;
  String _message;

  OpenTokController.init(int id) {
    _channel =  new MethodChannel('opentokandroid_$id');
  }

  OpenTokController.custom(int id) {
    _channel =  new MethodChannel('opentokandroid_$id');
  }

  Future<void> openTokInitilize(Map<String,String> payload) async {

    final String result = await _channel.invokeMethod('openTokInitializer',payload);
    /*_message = result;
    print('-------------------------------');
    print(result);*/

  }

  Future<String> openTokSubChanged() async {

    final String result = await _channel.invokeMethod('openTokSubChanged');
    _message = result;
    print('-------------------------------');
    print(result);
    return result;
  }
  Future<String> openTokPublisherCameraChanged() async {

    final String result = await _channel.invokeMethod('publisherCameraSwap');
    _message = result;
    print('-------------------------------');
    print(result);
    return result;
  }
  Future<dynamic> openTokPublisherAudioChanged(bool isAudioenabled) async {

     var result = await _channel.invokeMethod('publisherAudioChanged',{
      "audioenable":isAudioenabled
    });
    print(result);
    return result;
  }



  Future<dynamic> openTokPublisherVideoChanged(bool isVideoenabled) async {

    var result = await _channel.invokeMethod('publisherVideoChanged',{
      "videoenable":isVideoenabled
    });
    print(result);
    return result;
  }
  Future<dynamic> openTokCallEnd() async {

    var result = await _channel.invokeMethod('callEnd');
    print(result);
    return result;
  }

}
