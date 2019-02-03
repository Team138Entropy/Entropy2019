# -*- coding: utf-8 -*-
"""
Created on Wed Dec 02 19:49:34 2015

@author: jeffrey.f.bryant
"""

#!/usr/bin/env python
import cv2
import numpy as np
import math


# local modules
#import video

def nothing(x):
    pass

def sortCirclesBySize(circles):
    """
    Sort a list of circles by size;
    The imput is a list of [x,y,r] items where r is the size
    The return is the list sorted
    """
    sortedCircles = sorted(circles,key=lambda circles: circles[2])
    return sortedCircles


def medianColor(image,circle):
    """
    Compute te average color by creating a rectangle inside the circle,
    converting it to hsv colorspace and then computing the average
    hue, saturation and brightness
    """

    # Get a box inside the circle
    x = circle[0]
    y = circle[1]
    r = circle[2]
    span = int(r * .707)
    x1 = x-span
    x2 = x+span+1
    y1 = y-span
    y2 = y+span+1
    #img1 = image[x1:x2, y1:y2]
    img1 = image[y1:y2, x1:x2]
    if np.size(img1) > 4:
        cv2.imshow('inside',img1)
        
    #Compute the average color in the HSV colorspace
    hsv = cv2.cvtColor(img1, cv2.COLOR_BGR2HSV)
    hsv = np.ravel(hsv)
    try:        
        hmed = np.median(hsv[0::3])
        smed = np.median(hsv[1::3])
        vmed = np.median(hsv[2::3])
    except:
        print (hsv.size)
        hmed = 255
        smed = 255
        vmed = 255
       
    # Display the average color in a window
    colorImage = np.zeros(64*64*3,np.uint8)
    colorImage.resize(64,64,3)
    colorImage[:,:,0] = hmed
    colorImage[:,:,1] = smed
    colorImage[:,:,2] = vmed           
    dispImage = cv2.cvtColor(colorImage,cv2.COLOR_HSV2BGR)
    cv2.imshow('Avg Color',dispImage)
    
    return hmed,smed,vmed


def removeNestedCircles(circles):
    return circles

def persistantCircles(circles):
    return 
    
def processLines(frame,cimg,edges):
#            line1 = cv2.getTrackbarPos('Line1','Detected')
#            line2 = cv2.getTrackbarPos('Line2','Detected')
#            line3 = cv2.getTrackbarPos('Line3','Detected')

    line1 = 50
    line2 = 5
    line3 = 1            
            # Find and draw the first 100 lines
    lines = cv2.HoughLinesP(edges,line3, math.pi/180.0, 3, np.array([]), line1, line2)
    if lines is not None:
        a,b,c = lines.shape
        lim = b
        if lim > 100: lim = 100
        for i in range(lim):
            cv2.line(cimg, (lines[i][0][0], lines[i][0][1]), (lines[i][0][2], lines[i][0][3]), (0, 0, 255), 3, cv2.LINE_AA)
        

def processCircles(frame,cimg,img,cir1,cir2):
         # Find and draw the circles
 
        circles = cv2.HoughCircles(img,cv2.HOUGH_GRADIENT,1,20,
                                param1=cir1,param2=cir2,minRadius=70,maxRadius=600)
  
        if circles is not None:
            
           circles = np.round(circles[0, :]).astype("int")
           scircles = sortCirclesBySize(circles)
           
           h,s,v = medianColor(cimg,scircles[0])
           print (h,s,v)
                   
           # loop over the (x, y) coordinates and radius of the circles
           for (x, y, r) in scircles:              		
               cv2.circle(cimg, (x, y), r, (0, 255, 0), 4)
               cv2.rectangle(cimg, (x - 5, y - 5), (x + 5, y + 5), (0, 128, 255), -1)   
               
def main():
    
    showLines = False
    showCircles = False
  
    cv2.namedWindow('Detected')

    # create trackbars for thresholds
    cv2.createTrackbar('Cir1','Detected',250,1000,nothing)
    cv2.createTrackbar('Cir2','Detected',30,100,nothing)

#    cv2.createTrackbar('Line1','Detected',50,255,nothing)
#    cv2.createTrackbar('Line2','Detected',5,100,nothing)
#    cv2.createTrackbar('Line3','Detected',1,10,nothing)
    
    cap = cv2.VideoCapture(0)

    
    k = 0;
    while True:
        flag, frame = cap.read()
        # cv2.imshow('camera', frame)  
        
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        
        img = cv2.medianBlur(gray, 5)
        cimg = frame.copy() # numpy 
        #cv2.imshow('Blur',img)
        
        cir1 = cv2.getTrackbarPos('Cir1','Detected') 
        cir2 = cv2.getTrackbarPos('Cir2','Detected')
 
        edges = cv2.Canny(img,cir1,cir2)
        
        if showLines:
            processLines(frame,cimg,edges)
            
        if showCircles:     
            processCircles(frame,cimg,img,cir1,cir2)
         
        cv2.imshow('Detected',cimg)
        cv2.imshow('Edges',edges)
     
        # print k
        k = k + 1
        
        ch = 0xFF & cv2.waitKey(1)
        if ch == 27:
            break
        
    cv2.destroyAllWindows()

if __name__ == '__main__':
    main()

