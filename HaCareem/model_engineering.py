# -*- coding: utf-8 -*-
"""
Created on Sun Apr 30 00:49:20 2017

@author: Javeria Nisar
"""

import feature_parser as fp
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sqlalchemy import create_engine



#************************Feature vector table**********************************************
#engine = create_engine('dialect://user:pass@host:port/schema', echo=False)
#f = pd.read_sql_query('SELECT * FROM mytable', engine, index_col = 'ID')
#feature_vectors=pd.DataFrame(f,columns=['pick_up_lat','pick_up_long','Week_Of_Day','Time_Bucket_3','Time_Bucket_6','Is_Weekend','Is_Holiday'])
#*************************************************************************************
feature_vector=[]
train=pd.read_csv("train.csv")
test=pd.read_csv("test.csv")
columns=['pick_up_time']
print(len(test))
#***********************Test Feature Vectors*************************************
for i in range(len(test)):
    feature=pd.DataFrame(test.loc[i].reshape(1,len(test.loc[i])))
    feature_vector=pd.DataFrame(feature,columns=[2,4,5])
    r=feature_vector[2]
    feature_vector['Week_Of_Day']=fp.getWeekDay(fp.decodeEpoch(r))
    feature_vector['Time_Bucket_3']=fp.getHour_3(fp.getHour(fp.decodeEpoch(r)))
    feature_vector['Time_Bucket_6']=fp.getHour_6(fp.getHour(fp.decodeEpoch(r)))
    feature_vector['Is_Weekend']=int(fp.getIsWeekend(fp.getWeekDay(fp.decodeEpoch(r))))
    feature_vector['Is_Holiday']=int(fp.getIsHoliday(fp.getWeekDay(fp.decodeEpoch(r)),fp.getMonth(fp.decodeEpoch(r))))   
    feature_vector=feature_vector.rename(index=int, columns={'2': 'pick_up_time', '4': 'pick_up_lat'})
#********************************************************************************