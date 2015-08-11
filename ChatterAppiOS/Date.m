//
//  Date.m
//  ParsePrototype
//
//  Created by Alex French on 8/9/15.
//  Copyright (c) 2015 french.chagrin. All rights reserved.
//

#import "Date.h"

@implementation NSDate (GADate)

+(NSDate *)dateWithoutTimeFromDate:(NSDate *)date
{
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSDateComponents *components = [calendar components:(NSCalendarUnitYear | NSCalendarUnitMonth | NSCalendarUnitDay)
                                               fromDate:date];
    return [calendar dateFromComponents:components];
}

+(NSDate *)dateFromString:(NSString *)dateString
{
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"EEE MM dd yyyy"];
    NSDate *date = [dateFormat dateFromString:dateString];
    return date;
    // "Fri Oct 04 2013"
}


+ (NSString *)formattedStringFromDate:(NSDate *)date
{
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"EEE MMM dd yyyy"];
    NSString *dateString = [dateFormat stringFromDate:date];
    
    return dateString;
}

+ (NSString *)timeStringFormatFromDate:(NSDate *)date
{
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"hh:mm a"];
    NSString *timeString = [dateFormat stringFromDate:date];
    return timeString;
}


@end
