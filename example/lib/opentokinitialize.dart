import 'package:flutter/material.dart';
import 'package:opentokandroid/opentokandroid.dart';


class OpenTokInitializer extends StatefulWidget {
  @override
  _OpenTokInitializerState createState() => _OpenTokInitializerState();
}

class _OpenTokInitializerState extends State<OpenTokInitializer> {

  OpenTokController openTokController;
  var url="https://flutter.io/";

  Icon _volumeIcon=Icon(Icons.mic,color: Colors.green,);
  Icon _unmuteIcon=Icon(Icons.mic_off,color: Colors.green);
  bool isAudioChecked=false;
  Icon _videoOff=Icon(Icons.videocam_off,color: Colors.green,);
  Icon _videoOn=Icon(Icons.videocam,color: Colors.green);
  bool isVideoChecked=false;
  Icon _cameraOff=Icon(Icons.switch_camera,color: Colors.green,);
  Icon _cameraOn=Icon(Icons.camera_alt,color: Colors.green);
  bool isCameraChecked=false;



  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    Widget demo = Stack(
      children: <Widget>[
        Column(
          children: <Widget>[
            //first element in the column is the white background (the Image.asset in your case)
            DecoratedBox(
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20.0),
                    color: Colors.white
                ),
                child: Container(
                  child: OpenTokView(
                    onOpenTokCreated: onOpenTokCreated,
                    onSubChanges: (openTokController) {
                      this.openTokController = openTokController;
                    },

                  ),
                  width: 430.0,
                  height: 560.0,
                )
            ),
          ],
        ),
        //for the button i create another column
        Column(
            children:<Widget>[
              Container(
                  height: 520.0
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  GestureDetector(
                    child: isAudioChecked?_unmuteIcon:_volumeIcon,
                    onTap: (){
                      setState(() {
                        if(isAudioChecked){
                          isAudioChecked=false;
                          openTokController.openTokPublisherAudioChanged(isAudioChecked);

                        }else{
                          isAudioChecked=true;
                          openTokController.openTokPublisherAudioChanged(isAudioChecked);
                        }
                      });
                    },
                  ),
                  GestureDetector(
                    child: isCameraChecked?_cameraOff:_cameraOn,
                    onTap: (){
                      setState(() {
                        if(isCameraChecked){
                          isCameraChecked=false;
                          openTokController.openTokPublisherCameraChanged();
                        }
                        else{
                          isCameraChecked=true;
                          openTokController.openTokPublisherCameraChanged();
                        }
                      });
                    },
                  ),
                  GestureDetector(
                    child: isVideoChecked?_videoOff:_videoOn,
                    onTap: (){
                      setState(() {
                        if(isVideoChecked){
                          isVideoChecked=false;
                        openTokController.openTokPublisherVideoChanged(isVideoChecked);
                        }
                        else{
                          isVideoChecked=true;
                          openTokController.openTokPublisherVideoChanged(isVideoChecked);
                        }
                      });
                    },
                  ),
                  /*GestureDetector(
                    child:Icon(Icons.call_end,color: Colors.red,),
                    onTap: (){
                      setState(() {
                        openTokController.openTokCallEnd();
                      });
                    },
                  ),*/
                ],
              ),
            ]
        )
      ],
    );

    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('OpenTok app'),
          ),
          body: demo
      ),
    );
  }

  void onOpenTokCreated(openTokController) {
    Map<String,String> openTokPayload = {
      'apikey': '46645522',
      'session':'1_MX40NjY0NTUyMn5-MTU5NjY4OTY0NDk1N35mVkE4czJVblhLUUtaK3hiNGtmcjc0Wi9-QX4',
      'opentoktoken':'T1==cGFydG5lcl9pZD00NjY0NTUyMiZzaWc9OGQ1ODlhZTUxY2FkOWYzZTQ4NTczZjg5NzlhNDkxZmZjMDkzZjlhMzpzZXNzaW9uX2lkPTFfTVg0ME5qWTBOVFV5TW41LU1UVTVOalk0T1RZME5EazFOMzVtVmtFNGN6SlZibGhMVVV0YUszaGlOR3RtY2pjMFdpOS1RWDQmY3JlYXRlX3RpbWU9MTU5NjY4OTYzMSZub25jZT0wLjY5MDU5ODM1ODM2NjM1NiZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTk2Nzc2MDMxJmNvbm5lY3Rpb25fZGF0YT11c2VybmFtZSUzRGJvYiZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ=='
    };

    this.openTokController = openTokController;
    this.openTokController.openTokInitilize(openTokPayload);
  }
}
