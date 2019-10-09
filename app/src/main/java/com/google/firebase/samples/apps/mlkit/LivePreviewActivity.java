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
package com.google.firebase.samples.apps.mlkit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.common.annotation.KeepName;
import com.google.firebase.samples.apps.mlkit.facedetection.FaceDetectionProcessor;
import com.google.firebase.samples.apps.mlkit.others.CameraSourcePreview;
import com.google.firebase.samples.apps.mlkit.others.CustomAlertDialog;
import com.google.firebase.samples.apps.mlkit.others.GraphicOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Demo app showing the various features of ML Kit for Firebase. This class is used to
 * set up continuous frame processing on frames from a camera source. */
@KeepName
public final class LivePreviewActivity extends AppCompatActivity
    implements OnRequestPermissionsResultCallback,
        OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener {
  private static final String FACE_DETECTION = "Face Detection";
  private static final String TEXT_DETECTION = "Text Detection";
  private static final String BARCODE_DETECTION = "Barcode Detection";
  private static final String IMAGE_LABEL_DETECTION = "Label Detection";
  private static final String CLASSIFICATION = "Classification";
  private static final String TAG = "LivePreviewActivity";
  private static final int PERMISSION_REQUESTS = 1;
  private int subjectId;
  private ImageView imageView;
  private LinearLayout layout;
  private Context context;
  private CameraSource cameraSource = null;
  private CameraSourcePreview preview;
  private GraphicOverlay graphicOverlay;
  private String selectedModel = FACE_DETECTION;
  public static Set<String> recognizedIds;
  private CustomAlertDialog customAlertDialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");
    context=getApplicationContext();
    setContentView(R.layout.activity_live_preview);
    subjectId = getIntent().getIntExtra("subjectId",-1);
  //  imageView = findViewById(R.id.imgView);
    layout = (LinearLayout) findViewById(R.id.LinearHLayout);
    preview = (CameraSourcePreview) findViewById(R.id.firePreview);
    if (preview == null) {
      Log.d(TAG, "Preview is null");
    }
    graphicOverlay = (GraphicOverlay) findViewById(R.id.fireFaceOverlay);
    if (graphicOverlay == null) {
      Log.d(TAG, "graphicOverlay is null");
    }

    customAlertDialog = new CustomAlertDialog(LivePreviewActivity.this);
    customAlertDialog.setTextViewText("Recognizing");
    recognizedIds = new HashSet<>();

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    List<String> options = new ArrayList<>();
    options.add(FACE_DETECTION);
    options.add(TEXT_DETECTION);
    options.add(BARCODE_DETECTION);
    options.add(IMAGE_LABEL_DETECTION);
    options.add(CLASSIFICATION);
    // Creating adapter for spinner
    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_style, options);
    // Drop down layout style - list view with radio button
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // attaching data adapter to spinner
    spinner.setAdapter(dataAdapter);
    spinner.setOnItemSelectedListener(this);

    ToggleButton facingSwitch = (ToggleButton) findViewById(R.id.facingswitch);
    facingSwitch.setOnCheckedChangeListener(this);

    if (allPermissionsGranted()) {
      createCameraSource(selectedModel);
      Log.i(TAG,"all permissions granted");
    } else {
      getRuntimePermissions();
    }
  }
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.live_preview_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  // handle button activities
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    Log.i("item id",id+"");
    Log.i("item id",R.id.submit_button+"");
    if (id == R.id.submit_button) {
      customAlertDialog.show();
      final Handler handler = new Handler();
      handler.postDelayed(new Runnable() {
        @Override
        public void run() {
          if( FaceDetectionProcessor.threadCount > 0 )
            handler.postDelayed(this, 1000);
          else
          {
            customAlertDialog.dismiss();
            // do something here
            Log.d(TAG, "onOptionsItemSelected: " + recognizedIds);
            Log.d(TAG, "onOptionsItemSelected: " + subjectId);

            Intent intent = new Intent(LivePreviewActivity.this,AttendanceActivity.class);
            intent.putExtra("subjectId",subjectId);
            String[] objects = new String[recognizedIds.size()];
            recognizedIds.toArray(objects);
            final ArrayList<String> list = new ArrayList<>(Arrays.asList(objects));
            intent.putStringArrayListExtra("recognizedIds",list);
            startActivity(intent);
            finish();
          }
        }
      }, 1000);
    }
    return super.onOptionsItemSelected(item);
  }
  @Override
  public synchronized void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    // An item was selected. You can retrieve the selected item using
    // parent.getItemAtPosition(pos)
    selectedModel = parent.getItemAtPosition(pos).toString();
    Log.d(TAG, "Selected model: " + selectedModel);
    preview.stop();
    if (allPermissionsGranted()) {
      createCameraSource(selectedModel);
      startCameraSource();
    } else {
      getRuntimePermissions();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing.
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    Log.d(TAG, "Set facing");
    if (cameraSource != null) {
      if (isChecked) {
        cameraSource.setFacing(CameraSource.CAMERA_FACING_FRONT);
      } else {
        cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
      }
    }
    preview.stop();
    startCameraSource();
  }

  private void createCameraSource(String model) {
    // If there's no existing cameraSource, create one.
    if (cameraSource == null) {
      cameraSource = new CameraSource(this, graphicOverlay);
    }

      switch (model) {
        case FACE_DETECTION:
          Log.i(TAG, "Using Face Detector Processor");
          cameraSource.setMachineLearningFrameProcessor(new FaceDetectionProcessor(layout,this));
          break;
        default:
          Log.e(TAG, "Unknown model: " + model);
      }
  }

  /**
   * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
   * (e.g., because onResume was called before the camera source was created), this will be called
   * again when the camera source is created.
   */
  private void startCameraSource() {
    if (cameraSource != null) {
      try {
        if (preview == null) {
          Log.d(TAG, "resume: Preview is null");
        }
        if (graphicOverlay == null) {
          Log.d(TAG, "resume: graphOverlay is null");
        }
        preview.start(cameraSource, graphicOverlay);
      } catch (IOException e) {
        Log.e(TAG, "Unable to start camera source.", e);
        cameraSource.release();
        cameraSource = null;
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume");
    startCameraSource();
  }

  /** Stops the camera. */
  @Override
  protected void onPause() {
    super.onPause();
    preview.stop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (cameraSource != null) {
      cameraSource.release();
    }
  }

  private String[] getRequiredPermissions() {
    try {
      PackageInfo info =
          this.getPackageManager()
              .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
      String[] ps = info.requestedPermissions;
      if (ps != null && ps.length > 0) {
        return ps;
      } else {
        return new String[0];
      }
    } catch (Exception e) {
      return new String[0];
    }
  }

  private boolean allPermissionsGranted() {
    for (String permission : getRequiredPermissions()) {
      if (!isPermissionGranted(this, permission)) {
        return false;
      }
    }
    return true;
  }

  private void getRuntimePermissions() {
    List<String> allNeededPermissions = new ArrayList<>();
    for (String permission : getRequiredPermissions()) {
      if (!isPermissionGranted(this, permission)) {
        allNeededPermissions.add(permission);
      }
    }

    if (!allNeededPermissions.isEmpty()) {
      ActivityCompat.requestPermissions(
          this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
    }
  }

  @Override
  public void onRequestPermissionsResult(
          int requestCode, String[] permissions, int[] grantResults) {
    Log.i(TAG, "Permission granted!");
    if (allPermissionsGranted()) {
      createCameraSource(selectedModel);
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  private static boolean isPermissionGranted(Context context, String permission) {
    if (ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED) {
      Log.i(TAG, "Permission granted: " + permission);
      return true;
    }
    Log.i(TAG, "Permission NOT granted: " + permission);
    return false;
  }
}
