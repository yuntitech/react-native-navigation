#import "UITabBarController+RNNUtils.h"
#import "UIView+Utils.h"


@implementation UITabBarController (RNNUtils)
- (UIView *)getTabView:(int)tabIndex {
    int index = 0;
    
    for (UIView *view in [self fixSubviewSortingProblem]) {
        if ([NSStringFromClass([view class]) isEqualToString:@"UITabBarButton"]) {
            if (index == tabIndex) return view;
            index++;
        }
    }
    return nil;
}

- (UIView *)getTabIcon:(int)tabIndex {
    UIView *tab = [self getTabView:tabIndex];
    return [tab findChildByClass:[UIImageView class]];
}

- (NSArray *)deselectedViewControllers {
    NSMutableArray* childViewControllers = [NSMutableArray arrayWithArray:self.childViewControllers];
    [childViewControllers removeObject:self.selectedViewController];
    return [NSArray arrayWithArray:childViewControllers];
}

/**
 修复 tabbar 的 subviews 排序问题
 排序方式：以 frame 的 x 值升序排列
 */
- (NSMutableArray *)fixSubviewSortingProblem
{
    NSMutableArray *tempArray = [[[self tabBar] subviews] mutableCopy];
    NSMutableArray *tabBarButtonArray = @[].mutableCopy;
    [tempArray enumerateObjectsUsingBlock:^(UIView *subView, NSUInteger idx, BOOL * _Nonnull stop) {
        if ([NSStringFromClass([subView class]) isEqualToString:@"UITabBarButton"]) {
            [tabBarButtonArray addObject:subView];
        }
    }];
    
    tabBarButtonArray = [[tabBarButtonArray sortedArrayUsingComparator:^NSComparisonResult(UIView *subView1, UIView *subView2) {
        CGRect subview1Frame = subView1.frame;
        CGRect subview2Frame = subView2.frame;
        return subview1Frame.origin.x > subview2Frame.origin.x;
    }] mutableCopy];
    
    return tabBarButtonArray;
}

@end
