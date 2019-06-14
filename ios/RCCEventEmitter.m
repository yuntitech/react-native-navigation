//
//  RCCEventEmitter.m
//  ReduxDemo
//
//  Created by 晓哥 on 2019/6/3.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RCCEventEmitter.h"

@interface RCCEventEmitter ()
@property (nonatomic,assign) bool hasListeners;
@end


@implementation RCCEventEmitter

RCT_EXPORT_MODULE();

static RCCEventEmitter * _instance;
+ (id)allocWithZone:(NSZone *)zone {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _instance = [super allocWithZone:zone];
    });
    return _instance;
}

// 在添加第一个监听函数时触发
- (void)startObserving {
    self.hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
- (void)stopObserving {
    self.hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"screenSizeDidChange"];
}

- (void)sendEventWithName:(NSString *)name info:(NSDictionary *)info {
    if (self.hasListeners) {
        [self sendEventWithName:name body:info];
    }
}

@end

