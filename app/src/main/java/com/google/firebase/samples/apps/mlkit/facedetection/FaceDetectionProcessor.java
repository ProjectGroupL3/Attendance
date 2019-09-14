// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.firebase.samples.apps.mlkit.facedetection;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.samples.apps.mlkit.FrameMetadata;
import com.google.firebase.samples.apps.mlkit.GraphicOverlay;
import com.google.firebase.samples.apps.mlkit.VisionProcessorBase;

import java.io.IOException;
import java.util.List;

/** Face Detector Demo. */
public class FaceDetectionProcessor extends VisionProcessorBase<List<FirebaseVisionFace>> {

  private static final String TAG = "FaceDetectionProcessor";
  private ImageView imageView;
  private Bitmap bitmap;
  private final FirebaseVisionFaceDetector detector;

  public FaceDetectionProcessor() {
    FirebaseVisionFaceDetectorOptions options =
        new FirebaseVisionFaceDetectorOptions.Builder()
            .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .setTrackingEnabled(true)
            .build();


    detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
  }

  public FaceDetectionProcessor(ImageView imageView) {
    FirebaseVisionFaceDetectorOptions options =
            new FirebaseVisionFaceDetectorOptions.Builder()
                    .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                    .setTrackingEnabled(true)
                    .build();
    this.imageView = imageView;
    detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
  }

  @Override
  public void stop() {
    try {
      detector.close();
    } catch (IOException e) {
      Log.e(TAG, "Exception thrown while trying to close Face Detector: " + e);
    }
  }

  @Override
  protected Task<List<FirebaseVisionFace>> detectInImage(FirebaseVisionImage image) {
    this.bitmap =  image.getBitmapForDebugging();
    return detector.detectInImage(image);
  }

  @Override
  protected void onSuccess(
      @NonNull List<FirebaseVisionFace> faces,
      @NonNull FrameMetadata frameMetadata,
      @NonNull GraphicOverlay graphicOverlay) {
    graphicOverlay.clear();
    for (int i = 0; i < faces.size(); ++i) {
      FirebaseVisionFace face = faces.get(i);
      FaceGraphic faceGraphic = new FaceGraphic(graphicOverlay);
      graphicOverlay.add(faceGraphic);
      faceGraphic.updateFace(face, frameMetadata.getCameraFacing());
    }

    if(faces.size() > 0 )
    {
      FirebaseVisionFace firebaseVisionFace = faces.get(0);
      int x = firebaseVisionFace.getBoundingBox().left;
      int y = firebaseVisionFace.getBoundingBox().top;
      int w = firebaseVisionFace.getBoundingBox().width();
      int h = firebaseVisionFace.getBoundingBox().height();
      Log.d(TAG, "onSuccess: x=" + x + " y=" + y + " w=" + w + " h=" + h);
      if( x < 0 )
        x = 0;
      if( y < 0 )
        y = 0;
      if (x + w >= bitmap.getWidth())
        w = (bitmap.getWidth() - x);
      if( y + h >= bitmap.getHeight())
        h = (bitmap.getHeight() - y);
      Log.d(TAG, "onSuccess: x=" + x + " y=" + y + " w=" + w + " h=" + h);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap,x,y,w,h);
        // resizedBitmap.setWidth(x+w);
        imageView.setImageBitmap(resizedBitmap);
      }
    }
//    FirebaseVisionFace firebaseVisionFace = faces.get(0);
//    firebaseVisionFace.getBoundingBox();

  @Override
  protected void onFailure(@NonNull Exception e) {
    Log.e(TAG, "Face detection failed " + e);
  }
}
