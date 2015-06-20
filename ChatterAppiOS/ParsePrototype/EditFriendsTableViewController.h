//
//  EditFriendsTableViewController.h
//  ParsePrototype
//
//  Created by Alex French on 4/28/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Parse/Parse.h>

@interface EditFriendsTableViewController : UITableViewController

@property (nonatomic, strong) NSArray *allUsers;
@property (nonatomic, strong) PFUser *currentUser;

@end
