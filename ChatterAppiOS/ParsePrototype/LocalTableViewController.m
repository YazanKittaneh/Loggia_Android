//
//  LocalTableViewController.m
//  ParsePrototype
//
//  Created by Alex French on 6/14/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import "AppDelegate.h"
#import "LocalTableViewController.h"
#import "EventDetailsViewController.h"
#import <Parse/Parse.h>
#import "Date.h"

//#import <FBSDKCoreKit/FBSDKCoreKit.h>
//#import <FBSDKShareKit/FBSDKShareKit.h>
//#import <FBSDKLoginKit/FBSDKLoginKit.h>


@interface LocalTableViewController ()
@property (nonatomic, strong) NSMutableDictionary *calendarTable;
@property (nonatomic, strong) NSMutableArray *currentDay;
@property (nonatomic, strong) NSArray *cronKeys;
@end

@implementation LocalTableViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.tableView.scrollEnabled = NO;
    
    /*for (int i = 1; i < 11; i++){
        PFObject *bacon = [PFObject objectWithClassName:@"event"];
        bacon[@"Description"] = [NSString stringWithFormat:@"Who reads the description these days anyway?"];
        bacon[@"Name"] = @"Palapalooza";
        bacon[@"Location"] = @"Mac Field";
        bacon[@"Host"] = @"Grinnell College";
        bacon[@"Date"] = [NSString stringWithFormat: @"10/20/2015"];
        bacon[@"Time"] = [NSString stringWithFormat:@"2:30 - 4:40 pm"];
        //if (i % 2 == 0){
            UIImage *img = [UIImage imageNamed:@"Unknown-2"];
            NSData *imageData = UIImagePNGRepresentation(img);
            PFFile *imageFile = [PFFile fileWithName:@"image.png" data:imageData];
            bacon[@"Image"] = imageFile;
        //} else {
        //    UIImage *img = [UIImage imageNamed:@"Unknown"];
        //    NSData *imageData = UIImagePNGRepresentation(img);
        //    PFFile *imageFile = [PFFile fileWithName:@"image.png" data:imageData];
        //    bacon[@"Image"] = imageFile;
        //}
        
        [bacon saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error){
            if (succeeded){
                NSLog(@"Yay!");
            } else {
                NSLog(@"Well shit.");
            }
        }];
    }*/

    PFQuery *query = [PFQuery queryWithClassName:@"event"];
    [query findObjectsInBackgroundWithBlock:^(NSArray *events, NSError *error){
            if (error){
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Error" message:[error.userInfo objectForKey:@"error"] delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil];
            
                [alertView show];
            } else {
                self.calendarTable = [[NSMutableDictionary alloc] init];
                self.currentDay = [[NSMutableArray alloc] init];
                for (PFObject *event in events) {
                    NSString *date = event[@"Date"];
                    
                    if (date == nil){
                        continue;
                    } else if (self.calendarTable[date]){
                        [self.calendarTable[date] addObject:event];
                    } else {
                        self.calendarTable[date] = [[NSMutableArray alloc] init];
                        [self.calendarTable[date] addObject:event];
                    }
                }
                
                NSArray *keys = [self.calendarTable allKeys];
                NSLog(@"%@", keys);
                self.cronKeys =  [keys sortedArrayUsingComparator: ^(NSString *d1, NSString *d2) {
                    NSDate *date1 = [NSDate dateFromString:d1];
                    NSDate *date2 = [NSDate dateFromString:d2];
                    return [date1 compare:date2];
                }];
                //NSLog(@"%@", self.cronKeys);
                [self.tableView reloadData];
                self.tableView.scrollEnabled = YES;
            }
        }];
    
    
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

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    // Return the number of sections.
    return [self.cronKeys count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.
    return [[self.calendarTable objectForKey:[self.cronKeys objectAtIndex:section]] count];
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    
    return [self.cronKeys objectAtIndex:section];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    NSString *SimpleIdentifier = @"simpleIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:SimpleIdentifier forIndexPath:indexPath];
    
    if (cell == nil){
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:SimpleIdentifier];
    }
    
    NSString *dateKey =  self.cronKeys[indexPath.section];
    PFObject *event = self.calendarTable[dateKey][indexPath.row];
    
    cell.textLabel.text = event[@"Name"];
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}


/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/


#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
    if ([segue.identifier isEqualToString:@"eventDetails"]){
        NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
        NSString *dateKey = self.cronKeys[indexPath.section];
        PFObject *passingEvent = self.calendarTable[dateKey][indexPath.row];
        ((EventDetailsViewController*)segue.destinationViewController).eventDetails = passingEvent;
    }
    
}


//- (IBAction)logOut:(id)sender {
//    [PFUser logOut];
//    [self performSegueWithIdentifier:@"showLogin" sender:self];
//}

@end
