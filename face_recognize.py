import face_recognition
from PIL import Image,ImageDraw
import pickle
import sys
import cv2
# Load PICKLE
test_image_name=""
argcnt=0
for arg in sys.argv:
	argcnt+=1
	if(argcnt==2):
		test_image_name=arg
		# print(test_image_name)
test_image=None;
while(test_image is None):
	test_image=cv2.imread(test_image_name)
	# print("wait"+test_image_name)

dict_enc_name=pickle.loads(open('model.pickle','rb').read())
known_face_names=dict_enc_name['ids']
known_face_encodings=dict_enc_name['encodings']
# Load test image
if(test_image_name==""):
	exit(0)
test_image= face_recognition.load_image_file(test_image_name)

# find faces in test
face_locations=face_recognition.face_locations(test_image)
face_encodings=face_recognition.face_encodings(test_image,face_locations)

#PIL
# pil_image=Image.fromarray(test_image)
# draw
# loop

name="unknown"
for (top,right,bottom,left),face_encoding in zip(face_locations,face_encodings):
	matches=face_recognition.compare_faces(known_face_encodings,face_encoding,0.45)

	if True in matches:
		first_match_index=matches.index(True)
		name=known_face_names[first_match_index]
		break
print(name)

