import random
import string
import cherrypy
import simplejson
import json
import numpy as np
import Geohash
from sqlalchemy import create_engine
import pandas as pd
import feature_parser as fp
from sklearn.metrics.pairwise import cosine_similarity
import heapq



cherrypy.config.update({'server.socket_host': '0.0.0.0',
                        'server.socket_port': 8081
                       })

def CORS():
	cherrypy.response.headers["Access-Control-Allow-Origin"] = "*"

class RecGenerator(object):
    @cherrypy.expose
    def index(self):
	return ""
    @cherrypy.expose
    def encodeGeohash(self, lat=42.6, lon=-5.6 ):
	TLocation = Geohash.encode(lat,lon)
	Location = json.dumps({'GeoHash': Geohash.encode(float(lat), float(lon))})
        return Location
    @cherrypy.expose
    def decodeGeohash(self, hashstr='abc' ):
	TLocation = Geohash.decode(hashstr)
	Location = json.dumps({'Lat': TLocation[0], 'Lon':TLocation[1]})
        return Location

    @cherrypy.expose
    def GenerateReccom(self, UID='abc', datetime=1470765028, pickup_lat=42.6, pickup_long=-5.6):
	#['pick_up_lat','pick_up_long','Week_Of_Day','Time_Bucket_3','Time_Bucket_6','Is_Weekend','Is_Holiday']
	#Generate Vector and Reccomendation
	user_feature_vector = [0] * 7
	user_feature_vector[0]=pickup_lat
	user_feature_vector[1]=pickup_long
	user_feature_vector[2]=fp.getWeekDay(fp.decodeEpoch(str(datetime)))
	user_feature_vector[3]=fp.getHour_3(fp.getHour(str(datetime)))
	user_feature_vector[4]=fp.getHour_6(fp.getHour(str(datetime)))
	user_feature_vector[5]=int(fp.getIsWeekend(fp.getWeekDay(fp.decodeEpoch(str(datetime)))))
	user_feature_vector[6]=int(fp.getIsHoliday(fp.getWeekDay(fp.decodeEpoch(str(datetime))),fp.getMonth(fp.decodeEpoch(str(datetime)))))
	u1 =  np.array(user_feature_vector)

	engine = create_engine('mysql+mysqldb://root:root@localhost:3306/hacareem', echo=False)
	_str = 'SELECT * FROM hacareem.feature_vector where user_id=\''+UID+"\'"
	f = pd.read_sql_query(_str, engine)
	feature_vectors=pd.DataFrame(f,columns=['pick_up_lat','pick_up_lng','Week_Of_Day','Time_Bucket_3','Time_Bucket_6','Is_Weekend','Is_Holiday'])
	print feature_vectors.head()	
	u2 = np.array(feature_vectors.values)
	print u1[0:7]
	print u2[0]
	sim = (1-cosine_similarity(u2,u1))
	h = heapq.nlargest(3, range(len(sim)), sim.take)
	print feature_vectors.loc(h[0]['pick_up_lat']),feature_vectors.loc(h[0]['pick_up_lat']),feature_vectors.loc(h[0]['pick_up_lat']))
	rec_arr = ['tkrtm6kd9g0y','tkrv8kytn6kz','tkrtjd1v1pf4']
	reccomendation = json.dumps({'geohash_1':rec_arr[0],'geohash_2':rec_arr[1],'geohash_3':rec_arr[2]})
        return reccomendation


if __name__ == '__main__':
	conf = {
        '/': {
            'tools.response_headers.on': True,
            'tools.response_headers.headers': [('Content-Type', 'text/plain')],
			'tools.CORS.on': True
        }
    }
	cherrypy.tools.CORS = cherrypy.Tool('before_handler', CORS)	
	cherrypy.quickstart(RecGenerator(), '/', conf)
