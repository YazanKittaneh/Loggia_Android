//
//  EventDetailsViewController.h
//  ParsePrototype
//
//  Created by Alex French on 8/8/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Parse/Parse.h"

@interface EventDetailsViewController : UIViewController
@property (nonatomic, strong) PFObject *eventDetails;
@end
