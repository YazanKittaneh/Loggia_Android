//
//  SignUpViewController.m
//  ParsePrototype
//
//  Created by Alex French on 4/26/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import "SignUpViewController.h"
#import <Parse/Parse.h>

@interface SignUpViewController ()

@end

@implementation SignUpViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}


/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (IBAction)signup:(id)sender {
    
    NSString *areaCode = [self.areaCode.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSString *firstThree = [self.firstThree.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSString *lastFour = [self.lastFour.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSString *password = [self.password.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];

    if ([areaCode length] == 0 || [firstThree length] == 0 || [lastFour length] == 0 || [password length] == 0){
        UIAlertView *Alert = [[UIAlertView alloc] initWithTitle:@"Please try again" message:@"Required information is incomplete" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [Alert show];
    } else {
        PFUser *newuser = [PFUser user];
        newuser.username = [areaCode stringByAppendingString: [firstThree stringByAppendingString: lastFour]];
        newuser.password = password;
        
        [newuser signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
            if (error){
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Error" message:[error.userInfo objectForKey:@"error"] delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
                
                [alertView show];
            } else {
                [self.navigationController popToRootViewControllerAnimated:YES];
            }
            
        }];
    }
    
}
@end
