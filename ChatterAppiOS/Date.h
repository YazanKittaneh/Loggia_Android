//
//  Date.h
//  ParsePrototype
//
//  Created by Alex French on 8/9/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSDate (GADate)

+(NSDate *)dateWithoutTimeFromDate:(NSDate *)date;

+(NSDate *)dateFromString:(NSString *)dateString;
+ (NSString *)formattedStringFromDate:(NSDate *)date;
+ (NSString *)timeStringFormatFromDate:(NSDate *)date;


@end
