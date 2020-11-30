#import "TabBarItemAppearanceCreator.h"

@implementation TabBarItemAppearanceCreator

+ (UITabBarItem *)createTabBarItem:(UITabBarItem *)mergeItem {
    UITabBarItem* tabBarItem = [super createTabBarItem:mergeItem];
    tabBarItem.standardAppearance = mergeItem.standardAppearance ?: [[UITabBarAppearance alloc] init];
    return tabBarItem;
}

// TODO: leejunhui 修复 iPadOS 14 下 tabbar 文字被截断的问题 https://github.com/wix/react-native-navigation/pull/6784
+ (void)setTitleAttributes:(UITabBarItem *)tabItem titleAttributes:(NSDictionary *)titleAttributes {
    tabItem.standardAppearance.stackedLayoutAppearance.normal.titleTextAttributes = titleAttributes;
    tabItem.standardAppearance.compactInlineLayoutAppearance.normal.titleTextAttributes = titleAttributes;
    tabItem.standardAppearance.inlineLayoutAppearance.normal.titleTextAttributes = titleAttributes;
}

// TODO: leejunhui 修复 iPadOS 14 下 tabbar 文字被截断的问题 https://github.com/wix/react-native-navigation/pull/6784
+ (void)setSelectedTitleAttributes:(UITabBarItem *)tabItem selectedTitleAttributes:(NSDictionary *)selectedTitleAttributes {
    tabItem.standardAppearance.stackedLayoutAppearance.selected.titleTextAttributes = selectedTitleAttributes;
    tabItem.standardAppearance.compactInlineLayoutAppearance.selected.titleTextAttributes = selectedTitleAttributes;
    tabItem.standardAppearance.inlineLayoutAppearance.selected.titleTextAttributes = selectedTitleAttributes;
}

@end
