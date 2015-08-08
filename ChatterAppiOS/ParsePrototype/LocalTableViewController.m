//
//  LocalTableViewController.m
//  ParsePrototype
//
//  Created by Alex French on 6/14/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import "LocalTableViewController.h"
#import <Parse/Parse.h>
//#import <FBSDKCoreKit/FBSDKCoreKit.h>
//#import <FBSDKShareKit/FBSDKShareKit.h>
//#import <FBSDKLoginKit/FBSDKLoginKit.h>


@interface LocalTableViewController ()
@property (nonatomic, strong) NSArray *allEvents;
@end

@implementation LocalTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    //PFUser *currentUser = [PFUser currentUser];
    //if (currentUser){
    //    NSLog(@"Username: %@", currentUser.username);
    //    [self _loadData];
    //} else {
    //    [self performSegueWithIdentifier:@"showLogin" sender:self];
    //}

}

/*- (void)_loadData {
    // ...
    FBSDKGraphRequest *request = [[FBSDKGraphRequest alloc] initWithGraphPath:@"me" parameters:nil];
    [request startWithCompletionHandler:^(FBSDKGraphRequestConnection *connection, id result, NSError *error) {
        if (!error) {
            // result is a dictionary with the user's Facebook data
            NSDictionary *userData = (NSDictionary *)result;
            
            NSString *facebookID = userData[@"id"];
            NSString *name = userData[@"name"];
            NSString *location = userData[@"location"][@"name"];
            NSString *gender = userData[@"gender"];
            NSString *birthday = userData[@"birthday"];
            NSString *relationship = userData[@"relationship_status"];
            
            NSURL *pictureURL = [NSURL URLWithString:[NSString stringWithFormat:@"https://graph.facebook.com/%@/picture?type=large&return_ssl_resources=1", facebookID]];
            
            NSURLRequest *urlRequest = [NSURLRequest requestWithURL:pictureURL];
            
            // Run network request asynchronously
            [NSURLConnection sendAsynchronousRequest:urlRequest
                                               queue:[NSOperationQueue mainQueue]
                                   completionHandler:
             ^(NSURLResponse *response, NSData *data, NSError *connectionError) {
                 if (connectionError == nil && data != nil) {
                     // Set the image in the imageView
                     // ...
                 }
             }];
        }
    }];
}*/

//- (void)viewWillAppear:(BOOL)animated {
//    if (![PFUser currentUser] || // Check if user is cached
//        ![PFFacebookUtils isLinkedWithUser:[PFUser currentUser]]) { // Check if user is linked to Facebook
//        PFLogInViewController *controller = [[PFLogInViewController alloc] init];
//        logInController.fields = (PFLogInFieldsUsernameAndPassword
//                                  | PFLogInFieldsFacebook
//                                  | PFLogInFieldsDismissButton);
//        [self presentViewController:controller animated:YES];
//    }
//}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
#warning Potentially incomplete method implementation.
    // Return the number of sections.
    return 0;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
#warning Incomplete method implementation.
    // Return the number of rows in the section.
    return 0;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:<#@"reuseIdentifier"#> forIndexPath:indexPath];
    
    // Configure the cell...
    
    return cell;
}




/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

//- (IBAction)logOut:(id)sender {
//    [PFUser logOut];
//    [self performSegueWithIdentifier:@"showLogin" sender:self];
//}

@end
