//
//  RCCEventEmitter.h
//  ReduxDemo
//
//  Created by 晓哥 on 2019/6/3.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCCEventEmitter : RCTEventEmitter <RCTBridgeModule>

- (void)sendEventWithName:(NSString *)name info:(NSDictionary *)info;

@end


