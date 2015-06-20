//
//  SignUpViewController.h
//  ParsePrototype
//
//  Created by Alex French on 4/26/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SignUpViewController : UIViewController

@property (weak, nonatomic) IBOutlet UITextField *areaCode;
@property (weak, nonatomic) IBOutlet UITextField *firstThree;
@property (weak, nonatomic) IBOutlet UITextField *lastFour;
@property (weak, nonatomic) IBOutlet UITextField * password;
- (IBAction)signup:(id)sender;

@end
