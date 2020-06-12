#import "Param.h"
#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface RectEdge : Param

+ (instancetype)withValue:(nullable NSArray<NSString *> *)value;

- (instancetype)initWithValue:(nullable NSArray<NSString *> *)value;

- (UIRectEdge)get;

- (UIRectEdge)getWithDefaultValue:(UIRectEdge)defaultValue;

@end

NS_ASSUME_NONNULL_END
