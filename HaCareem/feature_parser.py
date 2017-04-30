# -*- coding: utf-8 -*-
"""
Created on Sat Apr 29 16:22:20 2017

@author: Javeria Nisar
"""

import time as t
import datetime as dt
import pandas as pd

def decodeEpoch(datetime):
    return t.strftime("%Y-%m-%d %H:%M:%S", t.gmtime(int(datetime)))    
 
def getWeekDay(datetime): 
    h = dt.datetime.strptime(datetime,"%Y-%m-%d %H:%M:%S").strftime('%d')
    p = (int(h)%7)+1
    return p
   
def getIsWeekend(weekday):
    return weekday=="Saturday" or weekday=="Sunday"

def getHour(datetime):
    try:
    	h = map(int,datetime.split(' ')[1].split(':'))[0]
    except:
    	h =1
    return h
   
def getDay(datetime):
    return map(int,datetime.split(' ')[0].split('-'))[2]

def getMonth(datetime):
    return map(int,datetime.split(' ')[0].split('-'))[1]

def getYear(datetime):
    return map(int,datetime.split(' ')[0].split('-'))[0]


def getHour_3(hour):
    if(int(hour)>=0 and int(hour)<=3):
        return 1
    elif(int(hour)>3 and int(hour)<=6):
        return 2
    elif(int(hour)>6 and int(hour)<=9):
        return 3
    elif(int(hour)>=9 and int(hour)<=12):
        return 4
    elif(int(hour)>12 and int(hour)<=15):
        return 5
    elif(int(hour)>15 and int(hour)<=18):
        return 6
    elif(int(hour)>18 and int(hour)<=21):
        return 7
    elif(int(hour)>21 and int(hour)<24):
        return 8
    else:
        return -1

def getHour_6(hour):
    if(int(hour)>=0 and int(hour)<=6):
        print("Reached")        
        return 1
    elif(int(hour)>6 and int(hour)<=12):
        return 2
    elif(int(hour)>12 and int(hour)<=18):
        return 3
    elif(int(hour)>18 and int(hour)<24):
        return 4
    else:
        return -1
        
def getIsHoliday(Day,Month):
     if(Day==5 and Month==2): #Kashmir Day
         return True
     elif(Day==23 and Month==3): #Pakistan Day
         return True
     elif(Day==1 and Month==5): #Labour Day
         return True
     elif(Day==14 and Month==8): #Independence Day
         return True
     else:
         return False
                  


