//
//  ViewController.h
//  HelloOpenGL
//
//  Created by peteryfren on 15/2/12.
//  Copyright (c) 2015年 peteryfren. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "OpenGLES1View.h"
// #import "OpenGLView.h"

@interface ViewController : UIViewController
{
}
- (IBAction)zoomIn:(id)sender;

@property (nonatomic, strong) IBOutlet OpenGLES1View *glView;
// @property (nonatomic, strong) IBOutlet OpenGLView *glView;

@property (nonatomic, strong) IBOutlet UIButton* button;


@end

