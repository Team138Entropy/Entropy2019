# -*- coding: utf-8 -*-
"""
Created on Tue Jan 29 10:11:40 2019

@author: jeffrey.f.bryant

Prottype experement for locating a hatch panel object (on the ship on floor; work in progress)

It is assumed that Brad Miller's field pictures are in a directory at the same level as this one

"""

from os import listdir
from os.path import isfile, join
import cv2
import numpy as np
#import matplotlib.pyplot as plt
#import time

maxSize = (640,480)
selectDirectory = True
imgDir = '..\\FieldPhotos-byBradMillerofWPI\\'
    

def readImage(imgDir,fn):
 
    img = cv2.imread(imgDir + fn)
    
    scaleDown = (int)(img.shape[0] / maxSize[0]);
    newSize = ((int)(img.shape[1]/scaleDown),(int)(img.shape[0]/scaleDown))
        
    #Resise the image if needed
    if (img.shape[0] > maxSize[0]) and (img.shape[1] > maxSize[1]):
        imgResized =cv2.resize(img,newSize)
    else:
        imgResized = img
    print (20*'=',fn,img.shape,imgResized.shape)    
    
    img = imgResized    
    
    return img

debugPrint = True

def removeDupContours(inContours):
    """
    Remove duplicates that start at the same location and have the same bounds
    """
    if len(inContours) == 0:
        return []
    ret = []
    last = inContours[0]
    ret.append(last)
    lastx,lasty,lastw,lasth = cv2.boundingRect(last)
    for c in inContours:
        x,y,w,h = cv2.boundingRect(c)
        if (x != lastx) or (y != lasty) or (h != lasth) or (w != lastw):
            ret.append(c);
            last = c;
            lastx,lasty,lastw,lasth = cv2.boundingRect(last)
    return ret

def getYellowImage(imgTest):
    """
    Read in an umage and filter out all but yellow content
    """
    
    # Convert to HSV
    img1 = cv2.cvtColor(imgTest, cv2.COLOR_BGR2HSV)
    
    # filter by color and intensity yellow is about 60 degrees
    lowH = 20
    upperH = 45
    lower_yellow = np.array([lowH,100,100])
    upper_yellow = np.array([upperH,255,255])
    mask = cv2.inRange(img1, lower_yellow, upper_yellow)

    # mask off the grayscale image
    gray = img1[:,:,2]
    masked = cv2.bitwise_and(gray,gray, mask= mask)
    
     # Blur to eliminate pixel dirt
    blured = cv2.blur(masked,(2,2))
    
    # draw the contours
    cv2.imshow('Masked',blured)    
    
    return blured

def findHatchPanel(imgTest):
    """
    Find a game piece using the contours method (work in progress)
    """
    
    # Fetch the image
    masked = getYellowImage(imgTest)
    
    #Find contours Outermost for now
    edged = cv2.Canny(masked, 50, 200)
    #img2, contours, hierarchy = cv2.findContours(edged.copy(),cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
    img2, contours, hierarchy = cv2.findContours(edged.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
    
    # eliminate small contours
    outContours = []
    for c in contours:
        
        # Threshold the area
        x, y, w, h = cv2.boundingRect(c)
        area = w*h
        
        if (w > h):
            flatness = w/h
        else:
            flatness = h/w
            
        #Compute the percentage of lit pixels
        selectedImage = masked[x:x+w,y:y+h]
    
        # Compute average intensity that are yellow (needs work)
        lit = np.sum(selectedImage,axis=0)
        lit = np.sum(lit)
        ratio = lit/area;
        
        print('  Area: ',area,' YellowRatio: ',ratio)
        if (area > 100) and (flatness < 3.0):
            outContours.append(c)
            
    # process what is left
    contours = outContours
    
    # Sort by size
    contours = sorted(contours, key = cv2.contourArea, reverse = True)[:75]
    if (debugPrint):
        print("Contours:",len(contours))
    contours = removeDupContours(contours)
    
    if (debugPrint):    
        print("# of No Duplicate Contours:",len(contours))
                                    
    # combine close contours (overlapping bounds)
    
    # Recognize shape of ring (method TBD)
    
    
    return contours

def processFile(imgdir,fn): 
    """
    Process a single file
    """
    
    imgInput = readImage(imgdir,fn)
    contours = findHatchPanel(imgInput)
    
    for c in contours:
        x, y, w, h = cv2.boundingRect(c)
        # draw a green rectangle to visualize the bounding rect
        cv2.rectangle(imgInput, (x, y), (x+w, y+h), (0, 255, 0), 2)                
        
    cv2.drawContours(imgInput,contours,-1,(255,0,0),3)
    cv2.imshow('imgInput',imgInput)
        
    
def processDirectory(imgdir):
    """
    Perform an analysis on all the .jpg files in a directory
    """
    
    onlyfiles = [f for f in listdir(imgdir) if isfile(join(imgdir, f))]
    for fn in onlyfiles:
       
        if fn[-4:].lower() == '.jpg':
            processFile(imgdir,fn)
            
            ch = 0xFF & cv2.waitKey(1000)
            
            if ch == 27:
                break    
  
    
if __name__ == '__main__':
    """
    Main test program
    """
    print ("OpenCV Version:",cv2.__version__)
    
    imgDir = '..\\FieldPhotos-byBradMillerofWPI\\'
    
    if selectDirectory:
        processDirectory(imgDir)         
    else:
        fn = 'DSC00325.JPG'    
        processFile(imgDir,fn)
    
    ch = 0xFF & cv2.waitKey(1000)
    
    if ch == 27:
        exit(0)
