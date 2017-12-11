#import <Foundation/Foundation.h>

@interface RNNSideMenuOptions : NSObject

@property (nonatomic, strong) NSNumber* visible;

-(instancetype)init;
-(instancetype)initWithDict:(NSDictionary *)sideMenuOptions;
-(void)mergeWith:(NSDictionary*)otherOptions;

@end
