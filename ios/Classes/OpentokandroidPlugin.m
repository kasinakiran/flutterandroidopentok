#import "OpentokandroidPlugin.h"
#if __has_include(<opentokandroid/opentokandroid-Swift.h>)
#import <opentokandroid/opentokandroid-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "opentokandroid-Swift.h"
#endif

@implementation OpentokandroidPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftOpentokandroidPlugin registerWithRegistrar:registrar];
}
@end
