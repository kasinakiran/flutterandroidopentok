package com.softhealth.opentokandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.Result;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class FlutterOpenTokView  extends AppCompatActivity
        implements Session.SessionListener,
        PublisherKit.PublisherListener,
        PlatformView,
        MethodChannel.MethodCallHandler,
        SubscriberKit.SubscriberListener,SubscriberKit.VideoListener {

    Context context;
    MethodChannel channel;
    private Result _re;
    TextView TV;
    LinearLayout myLInearLayout;
    RelativeLayout myRelativeLayout;
    FrameLayout subscriber_container;
    FrameLayout publisher_container;
    FrameLayout.LayoutParams subscriberparams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
    FrameLayout.LayoutParams publisherparams=new FrameLayout.LayoutParams(300,400);
    private static final String LOG_TAG = FlutterOpenTokView.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private String _apikey ="";
    private String _session ="";
    private String _token ="";
    boolean _isAudioChecked=false;
    boolean _isVideoChecked=false;
    ImageView _videoIcon;
    ImageView _audioIcon;
    FrameLayout.LayoutParams _videoMuteparams=new FrameLayout.LayoutParams(80,80);
    FrameLayout.LayoutParams _audioMuteparams=new FrameLayout.LayoutParams(80,80);

    LinearLayout llForAudioAndVideoIcons;
    LinearLayout.LayoutParams _paramsForIcons=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    RelativeLayout rl;
    RelativeLayout.LayoutParams _abc=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);




            @SuppressLint("SetJavaScriptEnabled")
    FlutterOpenTokView(Context context, BinaryMessenger messenger, int id, Map<String, Object> params,View containerview) {
        this.context = context;

        myLInearLayout = new LinearLayout(context);
        myLInearLayout.setOrientation(LinearLayout.VERTICAL);

        myRelativeLayout=new RelativeLayout(context);


        TV = new TextView(context);
        TV.setTextColor(Color.RED);
        TV.setTextSize(50);
        TV.setGravity(View.TEXT_ALIGNMENT_CENTER);
        TV.setGravity(Gravity.CENTER_VERTICAL);
        TV.setGravity(Gravity.CENTER_HORIZONTAL);
        TV.setBackgroundColor(Color.GREEN);
        TV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));



        subscriber_container=new FrameLayout(context);
        subscriber_container.setLayoutParams(subscriberparams);
        subscriber_container.setBackgroundColor(Color.CYAN);

        publisher_container=new FrameLayout(context);
        publisher_container.setLayoutParams(publisherparams);
        publisherparams.gravity=Gravity.BOTTOM|Gravity.RIGHT;
        publisherparams.setMargins(0, 10, 20, 150);
        publisher_container.setBackgroundColor(Color.WHITE);


        llForAudioAndVideoIcons=new LinearLayout(context);
        llForAudioAndVideoIcons.setLayoutParams(_paramsForIcons);
        llForAudioAndVideoIcons.setOrientation(LinearLayout.HORIZONTAL);

        rl =new RelativeLayout(context);
        rl.setLayoutParams(_abc);


        _videoIcon=new ImageView(context);
       // _audioIcon.setImageResource(R.drawable.video_green);
        _videoIcon.setLayoutParams(_videoMuteparams);
        _videoIcon.setPadding(50,0,0,0);
       // _videoIcon.setColorFilter(Color.GREEN);
        //_videoIcon.setColorFilter(selectedColor);
        _videoMuteparams.gravity=Gravity.TOP|Gravity.LEFT;
        _videoMuteparams.setMargins(0,10,20,0);


        _audioIcon=new ImageView(context);
        _audioIcon.setLayoutParams(_audioMuteparams);
        _audioIcon.setPadding(50,0,0,0);
        _audioMuteparams.gravity=Gravity.TOP|Gravity.LEFT;
        _audioMuteparams.setMargins(10,10,10,0);
        llForAudioAndVideoIcons.addView(_videoIcon);
        llForAudioAndVideoIcons.addView(_audioIcon);
       // rl=new RelativeLayout(context);


        subscriber_container.addView(publisher_container);
        //subscriber_container.addView(_videoIcon);
        //myLInearLayout.addView(_videoIcon);

        myRelativeLayout.addView(subscriber_container);
        myRelativeLayout.addView(llForAudioAndVideoIcons);
      //  myLInearLayout.addView(llForAudioAndVideoIcons);
        //myLInearLayout.addView(subscriber_container);


        requestPermissions(context);
        //onVideoDisabled(mSubscriber);


        channel = new MethodChannel(messenger, "opentokandroid_" + id);
        channel.setMethodCallHandler(this);
       // channel.setMessageHandler();
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {

        switch (call.method) {
            case "openTokInitializer":
                _apikey = call.argument("apikey").toString();
                _session = call.argument("session").toString();
                _token = call.argument("opentoktoken").toString();
                requestPermissions(context);
                break;

            case "openTokSubChanged":

                mSession.getCapabilities();
                result.success("lllklk");
                break;

            case "publisherCameraSwap":
                onPublisherCameraChanged();
                break;

            case "publisherAudioChanged":
                _isAudioChecked=call.argument("audioenable");
                onPublisherAudioChanged(_isAudioChecked);
                result.success(_isAudioChecked);
                break;

            case "publisherVideoChanged":
                _isVideoChecked=call.argument("videoenable");
                onPublisherVideoChanged(_isVideoChecked);
                result.success(_isVideoChecked);
                break;
            case "callEnd":
                onCallEnd();
                break;

            default:
                result.notImplemented();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions(Context context) {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(context, perms)) {

            mSession = new Session.Builder(context, _apikey, _session).build();
            mSession.setSessionListener(this);
            mSession.connect(_token);


        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }





    @Override
    public View getView() {
        return myRelativeLayout;
    }

    @Override
    public void dispose() { }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(context).build();
        mPublisher.setPublisherListener(this);
        mPublisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

        publisher_container.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView){
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);

    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
        mSession=null;
    }

    private SubscriberKit.VideoListener subscribervideo=new SubscriberKit.VideoListener(){

        @Override
        public void onVideoDataReceived(SubscriberKit subscriberKit) {

        }

        @Override
        public void onVideoDisabled(SubscriberKit subscriberKit, String s) {
           // selectedColor=Color.RED;
            _videoIcon.setImageResource(R.drawable.video_red);
            Log.d(LOG_TAG, "Video Disabled from Subscriber ");
        }

        @Override
        public void onVideoEnabled(SubscriberKit subscriberKit, String s) {
          //  selectedColor=Color.GREEN;
            _videoIcon.setImageResource(R.drawable.video_green);
            Log.d(LOG_TAG, "Video Disabled from Subscriber ");
        }

        @Override
        public void onVideoDisableWarning(SubscriberKit subscriberKit) {

        }

        @Override
        public void onVideoDisableWarningLifted(SubscriberKit subscriberKit) {

        }
    };

    private SubscriberKit.StreamListener subscriberAudio=new SubscriberKit.StreamListener() {

        @Override
        public void onReconnected(SubscriberKit subscriberKit) {

        }

        @Override
        public void onDisconnected(SubscriberKit subscriberKit) {

        }

        @Override
        public void onAudioEnabled(SubscriberKit subscriberKit) {
            _audioIcon.setImageResource(R.drawable.mic_green);
        }

        @Override
        public void onAudioDisabled(SubscriberKit subscriberKit) {
            _audioIcon.setImageResource(R.drawable.mic_red);
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (stream.hasAudio()) {
            _audioIcon.setImageResource(R.drawable.mic_green);
        }
        else{
            _audioIcon.setImageResource(R.drawable.mic_gray);
        }

        if (stream.hasVideo()) {
            _videoIcon.setImageResource(R.drawable.video_green);
        }
        else{
            _videoIcon.setImageResource(R.drawable.video_grey);
        }

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(context, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            mSubscriber.setSubscriberListener(this);
            mSubscriber.setVideoListener(subscribervideo);
            mSubscriber.setStreamListener(subscriberAudio);
            subscriber_container.addView(mSubscriber.getView());

        }

    }


    public void onPublisherAudioChanged( boolean isChecked){
        if(mPublisher==null){
            return;
        }
        if (isChecked) {
            mPublisher.setPublishAudio(false);
        } else {
            mPublisher.setPublishAudio(true);
        }
    }
    public void onPublisherVideoChanged( boolean isChecked){
        if(mPublisher==null){
            return;
        }
        if (isChecked) {
            mPublisher.setPublishVideo(false);
        } else {
            mPublisher.setPublishVideo(true);
        }
    }

    public void onPublisherCameraChanged(){

        if (mPublisher == null) {
            return;
        }
        mPublisher.cycleCamera();
    }
    public void onCallEnd(){

        if (mPublisher == null) {
            return;
        }
        mSession.disconnect();
        finish();
    }


    @Override
    public void onStreamDropped(Session session, Stream stream) {

        Log.i(LOG_TAG, "Stream Dropped");
        if (mSubscriber != null) {
            mSubscriber = null;
            subscriber_container.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    @Override
    public void onConnected(SubscriberKit subscriberKit) {

        Log.d(LOG_TAG, "onConnected: Subscriber connected. Stream: ");
    }

    @Override
    public void onDisconnected(SubscriberKit subscriberKit) {

        Log.d(LOG_TAG, "onDisconnected: Subscriber disconnected. Stream: ");

    }

    @Override
    public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Subscriber error: " + opentokError.getMessage());
    }

    @Override
    protected void onDestroy() {
        //mSession.disconnect();
        disconnectSession();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        mSession.disconnect();
        super.onStop();
    }

    private void disconnectSession() {
        if (mSession == null ) {
            return;
        }
       /* sessionConnected = false;

        if (mSubscriber.size() > 0) {
            for (SubscriberContainer c : mSubscribers) {
                if (c.subscriber != null) {
                    mSession.unsubscribe(c.subscriber);
                    c.subscriber.destroy();
                }
            }
        }*/

        if (mPublisher != null) {
            publisher_container.removeView(mPublisher.getView());
            mSession.unpublish(mPublisher);
            mPublisher.destroy();
            mPublisher = null;
        }
        mSession.disconnect();
    }

    @Override
    public void onVideoDataReceived(SubscriberKit subscriberKit) {
        mSubscriber.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
        Toast.makeText(this, "Subscriber Video Data received", Toast.LENGTH_LONG).show();
        //mSubscriberViewContainer.addView(mSubscriber.getView());
    }

    @Override
    public void onVideoDisabled(SubscriberKit subscriberKit, String s) {

        Toast.makeText(this, "Subscriber Turned off video", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onVideoEnabled(SubscriberKit subscriberKit, String s) {

    }

    @Override
    public void onVideoDisableWarning(SubscriberKit subscriberKit) {

    }

    @Override
    public void onVideoDisableWarningLifted(SubscriberKit subscriberKit) {

    }
}
