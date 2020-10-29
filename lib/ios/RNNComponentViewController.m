#import "RNNComponentViewController.h"
#import "UIViewController+RNNOptions.h"

#define DeviceIsPad ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad)

@implementation RNNComponentViewController

@synthesize previewCallback;

- (instancetype)initWithLayoutInfo:(RNNLayoutInfo *)layoutInfo rootViewCreator:(id<RNNComponentViewCreator>)creator eventEmitter:(RNNEventEmitter *)eventEmitter presenter:(RNNComponentPresenter *)presenter options:(RNNNavigationOptions *)options defaultOptions:(RNNNavigationOptions *)defaultOptions {
    self = [super initWithLayoutInfo:layoutInfo creator:creator options:options defaultOptions:defaultOptions presenter:presenter eventEmitter:eventEmitter childViewControllers:nil];
    if (@available(iOS 13.0, *)) {
        self.navigationItem.standardAppearance = [UINavigationBarAppearance new];
        self.navigationItem.scrollEdgeAppearance = [UINavigationBarAppearance new];
    }
    return self;
}

- (void)setDefaultOptions:(RNNNavigationOptions *)defaultOptions {
    _defaultOptions = defaultOptions;
    [_presenter setDefaultOptions:defaultOptions];
}

- (void)overrideOptions:(RNNNavigationOptions *)options {
    [self.options overrideOptions:options];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [_presenter applyOptions:self.resolveOptions];
    [self.parentViewController onChildWillAppear];
    
    // 判断 Options 中是否有横屏字段
    if (self.options.layout.orientation && [self.options.layout.orientation isKindOfClass:[NSArray class]]) {
        NSString *orientation = [self.options.layout.orientation lastObject];
        if ([orientation isEqualToString:@"landscape"]) {
            UIInterfaceOrientation currentOrientation = [RCTSharedApplication() statusBarOrientation];
            if (DeviceIsPad) {
                // Pad 端特殊处理
                // 如果当前设备是 UIInterfaceOrientationLandscapeLeft ，即 homeButton/homeIndicator 在右侧，则不处理
                if (currentOrientation == UIInterfaceOrientationLandscapeLeft) {
                    return;
                } else {
                    // 如果当前设备是 UIInterfaceOrientationLandscapeRight ，即 homeButton/homeIndicator 在左侧，则需要手动处理
                    // 如果当前设备是 landscape 之外的方向，则需要手动处理
                    NSNumber *orientationUnknown = [NSNumber numberWithInt:UIInterfaceOrientationUnknown];
                    [[UIDevice currentDevice] setValue:orientationUnknown forKey:@"orientation"];
                    NSNumber *orientationTarget = [NSNumber numberWithInteger:UIInterfaceOrientationLandscapeRight];
                    [[UIDevice currentDevice] setValue:orientationTarget forKey:@"orientation"];
                }
            } else {
                NSNumber *orientationUnknown = [NSNumber numberWithInt:UIInterfaceOrientationUnknown];
                [[UIDevice currentDevice] setValue:orientationUnknown forKey:@"orientation"];
                NSNumber *orientationTarget = [NSNumber numberWithInt:UIInterfaceOrientationLandscapeRight];
                [[UIDevice currentDevice] setValue:orientationTarget forKey:@"orientation"];
            }
        }
    }
}

- (BOOL)shouldAutorotate
{
    return YES;
}

- (UIInterfaceOrientationMask)supportedInterfaceOrientations
{
    if (self.options.layout.orientation && [self.options.layout.orientation isKindOfClass:[NSArray class]]) {
        NSString *orientation = [self.options.layout.orientation lastObject];
        if ([orientation isEqualToString:@"landscape"]) {
            if (DeviceIsPad) {
                return UIInterfaceOrientationMaskPortrait | UIInterfaceOrientationMaskLandscape;
            } else {
                return UIInterfaceOrientationMaskPortrait | UIInterfaceOrientationMaskLandscape;
            }
        } else {
            if (DeviceIsPad) {
                return UIInterfaceOrientationMaskAll;
            } else {
                return UIInterfaceOrientationMaskPortrait;
            }
        }
    } else {
        if (DeviceIsPad) {
            return UIInterfaceOrientationMaskAll;
        } else {
            return UIInterfaceOrientationMaskPortrait;
        }
    }
}

- (void)viewWillDisappear:(BOOL)animated
{
    // 判断 Options 中是否有横屏字段
    if (self.options.layout.orientation && [self.options.layout.orientation isKindOfClass:[NSArray class]]) {
        NSString *orientation = [self.options.layout.orientation lastObject];
        if ([orientation isEqualToString:@"landscape"]) {
            NSNumber *orientationUnknown = [NSNumber numberWithInt:UIInterfaceOrientationUnknown];
            [[UIDevice currentDevice] setValue:orientationUnknown forKey:@"orientation"];
            if (DeviceIsPad) {
                NSNumber *orientationTarget = [NSNumber numberWithInt:UIInterfaceOrientationMaskAll];
                [[UIDevice currentDevice] setValue:orientationTarget forKey:@"orientation"];
            } else {
                NSNumber *orientationTarget = [NSNumber numberWithInt:UIInterfaceOrientationPortrait];
                [[UIDevice currentDevice] setValue:orientationTarget forKey:@"orientation"];
            }
        }
    } else {
        if (DeviceIsPad) {
            NSNumber *orientationTarget = [NSNumber numberWithInt:UIInterfaceOrientationMaskAll];
            [[UIDevice currentDevice] setValue:orientationTarget forKey:@"orientation"];
        } else {
            NSNumber *orientationTarget = [NSNumber numberWithInt:UIInterfaceOrientationPortrait];
            [[UIDevice currentDevice] setValue:orientationTarget forKey:@"orientation"];
        }
    }
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    [self componentDidAppear];
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    [self componentDidDisappear];
}

- (void)loadView {
    [self renderReactViewIfNeeded];
}

- (void)render {
    if (!self.waitForRender)
        [self readyForPresentation];
    
    [self renderReactViewIfNeeded];
}

- (void)renderReactViewIfNeeded {
    if (!self.isViewLoaded) {
        self.view = [self.creator createRootView:self.layoutInfo.name
                                      rootViewId:self.layoutInfo.componentId
                                          ofType:RNNComponentTypeComponent
                             reactViewReadyBlock:^{
            [self->_presenter renderComponents:self.resolveOptions perform:^{
                [self readyForPresentation];
            }];
        }];
    } else {
        [self readyForPresentation];
    }
}

- (UIViewController *)getCurrentChild {
    return nil;
}

-(void)updateSearchResultsForSearchController:(UISearchController *)searchController {
    [self.eventEmitter sendOnSearchBarUpdated:self.layoutInfo.componentId
                                         text:searchController.searchBar.text
                                    isFocused:searchController.searchBar.isFirstResponder];
}

- (void)searchBarCancelButtonClicked:(UISearchBar *)searchBar {
    [self.eventEmitter sendOnSearchBarCancelPressed:self.layoutInfo.componentId];
}

- (UIViewController *)previewingContext:(id<UIViewControllerPreviewing>)previewingContext viewControllerForLocation:(CGPoint)location{
    return self.previewController;
}

- (void)previewingContext:(id<UIViewControllerPreviewing>)previewingContext commitViewController:(UIViewController *)viewControllerToCommit {
    if (self.previewCallback) {
        self.previewCallback(self);
    }
}

- (void)onActionPress:(NSString *)id {
    [_eventEmitter sendOnNavigationButtonPressed:self.layoutInfo.componentId buttonId:id];
}

- (UIPreviewAction *) convertAction:(NSDictionary *)action {
    NSString *actionId = action[@"id"];
    NSString *actionTitle = action[@"title"];
    UIPreviewActionStyle actionStyle = UIPreviewActionStyleDefault;
    if ([action[@"style"] isEqualToString:@"selected"]) {
        actionStyle = UIPreviewActionStyleSelected;
    } else if ([action[@"style"] isEqualToString:@"destructive"]) {
        actionStyle = UIPreviewActionStyleDestructive;
    }
    
    return [UIPreviewAction actionWithTitle:actionTitle style:actionStyle handler:^(UIPreviewAction * _Nonnull action, UIViewController * _Nonnull previewViewController) {
        [self onActionPress:actionId];
    }];
}

- (NSArray<id<UIPreviewActionItem>> *)previewActionItems {
    NSMutableArray *actions = [[NSMutableArray alloc] init];
    for (NSDictionary *previewAction in self.resolveOptions.preview.actions) {
        UIPreviewAction *action = [self convertAction:previewAction];
        NSDictionary *actionActions = previewAction[@"actions"];
        if (actionActions.count > 0) {
            NSMutableArray *group = [[NSMutableArray alloc] init];
            for (NSDictionary *previewGroupAction in actionActions) {
                [group addObject:[self convertAction:previewGroupAction]];
            }
            UIPreviewActionGroup *actionGroup = [UIPreviewActionGroup actionGroupWithTitle:action.title style:UIPreviewActionStyleDefault actions:group];
            [actions addObject:actionGroup];
        } else {
            [actions addObject:action];
        }
    }
    return actions;
}

-(void)onButtonPress:(RNNUIBarButtonItem *)barButtonItem {
    [self.eventEmitter sendOnNavigationButtonPressed:self.layoutInfo.componentId buttonId:barButtonItem.buttonId];
}

# pragma mark - UIViewController overrides

- (void)willMoveToParentViewController:(UIViewController *)parent {
    [self.presenter willMoveToParentViewController:parent];
}

- (UIStatusBarStyle)preferredStatusBarStyle {
    return [self.presenter getStatusBarStyle];
}

- (BOOL)prefersStatusBarHidden {
    return [self.presenter getStatusBarVisibility];
}


- (BOOL)hidesBottomBarWhenPushed {
    return [self.presenter hidesBottomBarWhenPushed];
}

- (UIRectEdge)preferredScreenEdgesDeferringSystemGestures {
    UIRectEdge edge = [self.presenter preferredScreenEdgesDeferringSystemGestures];
    return edge;
}

- (BOOL)prefersHomeIndicatorAutoHidden {
    if ([self respondsToSelector:@selector(homeIndicatorAutoHidden)]) {
        return self.homeIndicatorAutoHidden;
    } else {
        return NO;
    }
}

@end
