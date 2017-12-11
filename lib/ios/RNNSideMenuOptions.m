#import "RNNSideMenuOptions.h"

@implementation RNNSideMenuOptions

-(instancetype)init {
	return [self initWithDict:@{}];
}

-(instancetype)initWithDict:(NSDictionary *)sideMenuOptions {
	self = [super init];
	
	self.visible = [sideMenuOptions valueForKey:@"visible"];
	
	return self;
}

-(void)mergeWith:(NSDictionary *)otherOptions {
	for (id key in otherOptions) {
		[self setValue:[otherOptions objectForKey:key] forKey:key];
	}
}

@end
