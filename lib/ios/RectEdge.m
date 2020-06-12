#import "RectEdge.h"

@interface RectEdge ()
@property (nonatomic) UIRectEdge value;
@end

@implementation RectEdge

+ (instancetype)withValue:(nullable NSArray<NSString *> *)value {
    return [[RectEdge alloc] initWithValue:value];
}

- (instancetype)initWithValue:(nullable NSArray<NSString *> *)value {
    self = [super init];

    UIRectEdge defaultEdge = UIRectEdgeNone;
    UIRectEdge edge = defaultEdge;
    if (value == nil) {
        // defaultEdge
    } else if ([value isKindOfClass:NSArray.class]) {
        for (NSString *edgeItem in value) {
            if ([edgeItem isKindOfClass:NSString.class]) {
                if ([edgeItem isEqualToString:@"all"]) {
                    edge = UIRectEdgeAll;
                    break;
                } else if ([edgeItem isEqualToString:@"none"]) {
                    edge = UIRectEdgeNone;
                    break;
                } else if ([edgeItem isEqualToString:@"left"]) {
                    edge = edge | UIRectEdgeLeft;
                } else if ([edgeItem isEqualToString:@"right"]) {
                    edge = edge | UIRectEdgeRight;
                } else if ([edgeItem isEqualToString:@"top"]) {
                    edge = edge | UIRectEdgeTop;
                } else if ([edgeItem isEqualToString:@"bottom"]) {
                    edge = edge | UIRectEdgeBottom;
                }
            } else {
                @throw [NSException exceptionWithName:@"RectEdge init" reason:@"value item is not a string" userInfo:nil];
            }
        }
    } else {
        @throw [NSException exceptionWithName:@"RectEdge init" reason:@"value is not an array" userInfo:nil];
    }
    
    self.value = edge;
    return self;
}

- (UIRectEdge)get {
    return self.value;
}

- (UIRectEdge)getWithDefaultValue:(UIRectEdge)defaultValue {
    return self.value;
}

- (BOOL)hasValue {
    return YES;
}

@end
