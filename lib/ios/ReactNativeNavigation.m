#import "ReactNativeNavigation.h"

#import <React/RCTUIManager.h>

#import "RNNBridgeManager.h"
#import "RNNSplashScreen.h"
#import "RNNLayoutManager.h"

@interface ReactNativeNavigation()

@property (nonatomic, strong) RNNBridgeManager *bridgeManager;

@end

@implementation ReactNativeNavigation
static UIDeviceOrientation _devicePhysicalOrientation = UIDeviceOrientationPortrait;
# pragma mark - public API

+ (void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions {
	[[ReactNativeNavigation sharedInstance] bootstrap:jsCodeLocation launchOptions:launchOptions];
}

+ (void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions bridgeManagerDelegate:(id<RCTBridgeDelegate>)delegate {
	[[ReactNativeNavigation sharedInstance] bootstrap:jsCodeLocation launchOptions:launchOptions bridgeManagerDelegate:delegate];
}

+ (void)bootstrapWithDelegate:(id<RCTBridgeDelegate>)bridgeDelegate launchOptions:(NSDictionary *)launchOptions {
    [[ReactNativeNavigation sharedInstance] bootstrapWithDelegate:bridgeDelegate launchOptions:launchOptions];
}

+ (void)registerExternalComponent:(NSString *)name callback:(RNNExternalViewCreator)callback {
	[[ReactNativeNavigation sharedInstance].bridgeManager registerExternalComponent:name callback:callback];
}

+ (RCTBridge *)getBridge {
	return [[ReactNativeNavigation sharedInstance].bridgeManager bridge];
}

+ (UIViewController *)findViewController:(NSString *)componentId {
    return [RNNLayoutManager findComponentForId:componentId];
}

+ (void)setJSCodeLocation:(NSURL *)jsCodeLocation {
	[[ReactNativeNavigation sharedInstance].bridgeManager setJSCodeLocation:jsCodeLocation];
}

+ (UIDeviceOrientation)getDeivcePhysicalOrientation
{
    return _devicePhysicalOrientation;
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

# pragma mark - instance

+ (instancetype) sharedInstance {
	static ReactNativeNavigation *instance = nil;
	static dispatch_once_t onceToken = 0;
	dispatch_once(&onceToken,^{
		if (instance == nil) {
			instance = [[ReactNativeNavigation alloc] init];
		}
	});
	
	return instance;
}

- (instancetype)init
{
    if ((self = [super init])) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(deviceOrientationDidChange:) name:UIDeviceOrientationDidChangeNotification object:nil];
    }
    return self;
    
}

- (void)deviceOrientationDidChange:(NSNotification *)notification {
    dispatch_async(dispatch_get_main_queue(), ^{
        UIDeviceOrientation orientation = [[UIDevice currentDevice] orientation];
        if (orientation != UIDeviceOrientationPortrait && orientation != UIDeviceOrientationFaceUp && orientation != UIDeviceOrientationFaceDown) {
            _devicePhysicalOrientation = orientation;
        }
    });
}

- (void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions {
	[self bootstrap:jsCodeLocation launchOptions:launchOptions bridgeManagerDelegate:nil];
}

- (void)bootstrapWithDelegate:(id<RCTBridgeDelegate>)bridgeDelegate launchOptions:(NSDictionary *)launchOptions {
    [self bootstrap:nil launchOptions:launchOptions bridgeManagerDelegate:bridgeDelegate];
}

- (void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions bridgeManagerDelegate:(id<RCTBridgeDelegate>)delegate {
	UIWindow* mainWindow = [self initializeKeyWindow];
	
	self.bridgeManager = [[RNNBridgeManager alloc] initWithJsCodeLocation:jsCodeLocation launchOptions:launchOptions bridgeManagerDelegate:delegate mainWindow:mainWindow];
	[RNNSplashScreen showOnWindow:mainWindow];
}

- (UIWindow *)initializeKeyWindow {
	UIWindow* keyWindow = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
	UIApplication.sharedApplication.delegate.window = keyWindow;
	return keyWindow;
}

@end
