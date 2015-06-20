//
//  FriendsViewController.h
//  ParsePrototype
//
//  Created by Alex French on 5/3/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>

@interface FriendsViewController : UITableViewController
@property (nonatomic, strong) PFRelation *friendsRelation;
@property (nonatomic, strong) NSArray *friends;

@end
