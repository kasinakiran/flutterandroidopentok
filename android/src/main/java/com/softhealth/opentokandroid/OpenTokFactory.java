package com.softhealth.opentokandroid;

import android.content.Context;
import android.view.View;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class OpenTokFactory extends PlatformViewFactory {
    private final BinaryMessenger messenger;
    private final View containerView;

    OpenTokFactory(BinaryMessenger messenger, View containerView) {
        super(StandardMessageCodec.INSTANCE);
        this.messenger = messenger;
        this.containerView = containerView;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new FlutterOpenTokView(context, messenger, viewId, params, containerView);
    }

}
