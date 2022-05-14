#import "TabBarItemIOS15Creator.h"

@implementation TabBarItemIOS15Creator

#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 150000

- (UITabBarItem *)createTabBarItem:(UITabBarItem *)mergeItem {
    UITabBarItem *tabBarItem = [super createTabBarItem:mergeItem];
    tabBarItem.scrollEdgeAppearance =
        mergeItem.scrollEdgeAppearance ?: [[UITabBarAppearance alloc] init];
    return tabBarItem;
}

- (void)setTitleAttributes:(UITabBarItem *)tabItem titleAttributes:(NSDictionary *)titleAttributes {
  // https://stackoverflow.com/a/69457332/5159380
  NSMutableDictionary *fixedTitleAttributes = titleAttributes.mutableCopy;
  fixedTitleAttributes[NSParagraphStyleAttributeName] = [NSParagraphStyle defaultParagraphStyle];
    [super setTitleAttributes:tabItem titleAttributes:fixedTitleAttributes];
    tabItem.scrollEdgeAppearance.stackedLayoutAppearance.normal.titleTextAttributes =
  fixedTitleAttributes;
    tabItem.scrollEdgeAppearance.compactInlineLayoutAppearance.normal.titleTextAttributes =
  fixedTitleAttributes;
    tabItem.scrollEdgeAppearance.inlineLayoutAppearance.normal.titleTextAttributes =
  fixedTitleAttributes;
}

- (void)setSelectedTitleAttributes:(UITabBarItem *)tabItem
           selectedTitleAttributes:(NSDictionary *)selectedTitleAttributes {
  // https://stackoverflow.com/a/69457332/5159380
  NSMutableDictionary *fixedTitleAttributes = selectedTitleAttributes.mutableCopy;
  fixedTitleAttributes[NSParagraphStyleAttributeName] = [NSParagraphStyle defaultParagraphStyle];
    [super setSelectedTitleAttributes:tabItem selectedTitleAttributes:fixedTitleAttributes];
    tabItem.scrollEdgeAppearance.stackedLayoutAppearance.selected.titleTextAttributes =
  fixedTitleAttributes;
    tabItem.scrollEdgeAppearance.compactInlineLayoutAppearance.selected.titleTextAttributes =
  fixedTitleAttributes;
    tabItem.scrollEdgeAppearance.inlineLayoutAppearance.selected.titleTextAttributes =
  fixedTitleAttributes;
}

#endif

@end
