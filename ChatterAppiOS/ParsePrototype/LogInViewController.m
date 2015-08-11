//
//  LogInViewController.m
//  ParsePrototype
//
//  Created by Alex French on 4/26/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import "LogInViewController.h"
#import <Parse/Parse.h>
//#import <FBSDKCoreKit/FBSDKCoreKit.h>
//#import <FBSDKLoginKit/FBSDKLoginKit.h>

@interface LogInViewController ()

@end

@implementation LogInViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.hidesBackButton = YES;
    // Do any additional setup after loading the view.
    //FBSDKLoginButton *loginButton = [[FBSDKLoginButton alloc] init];
    //loginButton.center = self.view.center;
    //[self.view addSubview:loginButton];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

- (IBAction)login:(id)sender {
    
    NSString *areaCode = [self.areaCode.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSString *firstThree = [self.firstThree.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSString *lastFour = [self.lastFour.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSString *password = [self.password.text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
    NSString *username = [areaCode stringByAppendingString: [firstThree stringByAppendingString: lastFour]];
    
    if ([username length] == 0 || [password length] == 0){
        UIAlertView *Alert = [[UIAlertView alloc] initWithTitle:@"Notice" message:@"Please enter username and password" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
        [Alert show];
    } else {
        [PFUser logInWithUsernameInBackground:username password:password block:^(PFUser *user, NSError *error) {
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
