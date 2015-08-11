//
//  EventDetailsViewController.m
//  ParsePrototype
//
//  Created by Alex French on 8/8/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import "EventDetailsViewController.h"
#import "Parse/Parse.h"

@interface EventDetailsViewController ()
@property (weak, nonatomic) IBOutlet UILabel *whenLabel;
@property (weak, nonatomic) IBOutlet UILabel *whereLabel;
@property (weak, nonatomic) IBOutlet UILabel *descriptionLabel;
@property (weak, nonatomic) UIImage *eventImage;
@property (strong, nonatomic) IBOutlet UIImageView *bgView;

@end

@implementation EventDetailsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.whenLabel = self.eventDetails[@"Time"];
    self.whereLabel = self.eventDetails[@"Location"];
    self.descriptionLabel = self.eventDetails[@"Description"];
    self.eventImage = self.eventDetails[@"Image"];
    self.bgView = [[UIImageView alloc] initWithImage:self.eventImage];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
