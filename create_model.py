import os
import face_recognition as fr
import cv2
import pickle
train_dir_address='/home/sid/code/ml/sdl/res'

list_of_file=os.listdir(train_dir_address)

face_encodings=[]
face_ids=[]
for file_name in list_of_file:
	# FILE NAME MUST END WITH JPEG
	label_name=file_name
	if(label_name[-4:]=='.jpg'):
		label_name=label_name[:len(label_name)-4]
	elif(label_name[-4:]=='.png'):
		label_name=label_name[:len(label_name)-4]
	elif(label_name[-5:]=='.jpeg'):
		label_name=label_name[:len(label_name)-5]
	else:
		print("Invalid file format: "+file_name)
		exit(0)
	train_img=fr.load_image_file('./res/'+file_name)
	train_encoding=fr.face_encodings(train_img)[0]
	face_encodings.append(train_encoding)
	face_ids.append(label_name)

# STORE MODEL
dict_enc_name={'encodings':face_encodings ,'ids':face_ids}	

f=open('model.pickle','wb')
f.write(pickle.dumps(dict_enc_name))
f.close()






