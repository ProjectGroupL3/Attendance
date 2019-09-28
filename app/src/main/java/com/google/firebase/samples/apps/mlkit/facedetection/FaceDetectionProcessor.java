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

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.samples.apps.mlkit.FrameMetadata;
import com.google.firebase.samples.apps.mlkit.GraphicOverlay;
import com.google.firebase.samples.apps.mlkit.LivePreviewActivity;
import com.google.firebase.samples.apps.mlkit.VisionProcessorBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/** Face Detector Demo. */
public class FaceDetectionProcessor extends VisionProcessorBase<List<FirebaseVisionFace>> {

    private static final String TAG = "FaceDetectionProcessor";
    private Bitmap bitmap;
    private final FirebaseVisionFaceDetector detector;
    private LinearLayout layout;
    View v;
    Context c;
    Resources res;
    int total;
    public Activity activity;
    String packageName;
    HashMap<Integer, ImageView> integerImageViewHashMap;
    HashMap<Integer, Integer> singleFaceDetectCount;
    public FaceDetectionProcessor(Activity _activity) {
        activity=_activity;
        //res=context.getResources();
        //c=context;
        //packageName=context.getPackageName();
        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .enableTracking()
                        .build();


        detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
    }

    public FaceDetectionProcessor(LinearLayout layout, LivePreviewActivity livePreviewActivity) {
        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .enableTracking()
                        .build();
        this.layout = layout;
        c=livePreviewActivity;
        detector = FirebaseVision.getInstance().getVisionFaceDetector(options);
//        idlist=new ArrayList<>();
//        imageViewArr = new ArrayList<>();
        integerImageViewHashMap = new HashMap<>();
        singleFaceDetectCount = new HashMap<>();
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
        this.bitmap =  image.getBitmap();
        return detector.detectInImage(image);
    }

    @Override
    protected void onSuccess(
            @NonNull List<FirebaseVisionFace> faces,
            @NonNull FrameMetadata frameMetadata,
            @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        total++;
            Log.d(TAG, "onSuccess: Total: " + total);
            for (int i = 0; i < faces.size(); ++i) {
                FirebaseVisionFace face = faces.get(i);
                FaceGraphic faceGraphic = new FaceGraphic(graphicOverlay);
                graphicOverlay.add(faceGraphic);
                faceGraphic.updateFace(face, frameMetadata.getCameraFacing());
                if( total % 3 == 0 )
                {
                    int id = face.getTrackingId();
                    int x = face.getBoundingBox().left;
                    int y = face.getBoundingBox().top;
                    int w = face.getBoundingBox().width();
                    int h = face.getBoundingBox().height();
                    if (x < 0) x = 0;
                    if (y < 0) y = 0;
                    if (x + w >= bitmap.getWidth()) w = (bitmap.getWidth() - x);
                    if (y + h >= bitmap.getHeight()) h = (bitmap.getHeight() - y);

                    if (!integerImageViewHashMap.containsKey(id)) {
                        ImageView imageView = new ImageView(c);
                        imageView.setImageBitmap(Bitmap.createBitmap(bitmap, x, y, w, h));
                        // layout.addView(imageView);
                        integerImageViewHashMap.put(id, imageView);
                        singleFaceDetectCount.put(id,0);
                    } else {
                        int prev_count = singleFaceDetectCount.get(id);
                        prev_count++;
                        if( prev_count < 1 )
                        {
                            singleFaceDetectCount.put(id,prev_count);
                            ImageView img = integerImageViewHashMap.get(id);
                            img.setImageBitmap(Bitmap.createBitmap(bitmap, x, y, w, h));
                        }
                        else if( prev_count == 1 )
                        {
                            layout.removeView(integerImageViewHashMap.get(id));
                            layout.addView(integerImageViewHashMap.get(id));
                        }
                    }
                }
            }
    }
    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Face detection failed " + e);
    }
}
