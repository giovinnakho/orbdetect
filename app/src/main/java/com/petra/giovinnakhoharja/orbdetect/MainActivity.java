package com.petra.giovinnakhoharja.orbdetect;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {



    private static final String TAG = "mydebug";
    private static Camera mCamera=null;
    private static CameraPreview mPreview;

    static final int MEDIA_TYPE_IMAGE = 1;

    static Context context;
    static ImageView myImage;
//    static TextView myText;
    private FrameLayout preview;

    //region TANPA UANG FULL
    public String[] nama = {
            "1.000 depan kanan", "1.000 depan kiri",
            "1.000 balik kanan", "1.000 balik kiri",
            "2.000 depan kanan", "2.000 depan kiri",
            "2.000 balik kanan", "2.000 balik kiri",
            "5.000 depan kanan", "5.000 depan kiri",
            "5.000 balik kanan", "5.000 balik kiri",
            "10.000 depan kanan", "10.000 depan kiri",
            "10.000 balik kanan", "10.000 balik kiri",
            "20.000 depan kanan", "20.000 depan kiri",
            "20.000 balik kanan", "20.000 balik kiri",
            "50.000 depan kanan", "50.000 depan kiri",
            "50.000 balik kanan", "50.000 balik kiri",
            "100.000 depan kanan", "100.000 depan kiri",
            "100.000 balik kanan", "100.000 balik kiri"
    };
    public String[] namasatu = {
            "IDR 1.000", "IDR 1.000", "IDR 1.000", "IDR 1.000",
            "IDR 2.000", "IDR 2.000", "IDR 2.000", "IDR 2.000",
            "IDR 5.000", "IDR 5.000", "IDR 5.000", "IDR 5.000",
            "IDR 10.000", "IDR 10.000", "IDR 10.000", "IDR 10.000",
            "IDR 20.000", "IDR 20.000", "IDR 20.000", "IDR 20.000",
            "IDR 50.000", "IDR 50.000", "IDR 50.000", "IDR 50.000",
            "IDR 100.000", "IDR 100.000", "IDR 100.000", "IDR 100.000"
    };

//    public int[] iddescriptor = {
//            R.raw.surf1000depanr800des, R.raw.surf1000depanl800des,
//             R.raw.surf1000balikr800des, R.raw.surf1000balikl800des,
//
//            R.raw.surf2000depanr800des, R.raw.surf2000depanl800des,
//            R.raw.surf2000balikr800des, R.raw.surf2000balikl800des,
//
//            R.raw.surf5000depanr800des, R.raw.surf5000depanl800des,
//            R.raw.surf5000balikr800des, R.raw.surf5000balikl800des,
//
//            R.raw.surf10000depanr800des, R.raw.surf10000depanl800des,
//            R.raw.surf10000balikr800des, R.raw.surf10000balikl800des,
//
//            R.raw.surf20000depanr800des, R.raw.surf20000depanl800des,
//            R.raw.surf20000balikr800des, R.raw.surf20000balikl800des,
//
//            R.raw.surf50000depanr800des, R.raw.surf50000depanl800des,
//            R.raw.surf50000balikr800des, R.raw.surf50000balikl800des,
//
//            R.raw.surf100000depanr800des, R.raw.surf100000depanl800des,
//            R.raw.surf100000balikr800des, R.raw.surf100000balikl800des
//    };
//
//    public int[] idkeypoint = {
//            R.raw.surf1000depanr800, R.raw.surf1000depanl800,
//            R.raw.surf1000balikr800, R.raw.surf1000balikl800,
//
//            R.raw.surf2000depanr800, R.raw.surf2000depanl800,
//            R.raw.surf2000balikr800, R.raw.surf2000balikl800,
//
//            R.raw.surf5000depanr800, R.raw.surf5000depanl800,
//            R.raw.surf5000balikr800, R.raw.surf5000balikl800,
//
//            R.raw.surf10000depanr800, R.raw.surf10000depanl800,
//            R.raw.surf10000balikr800, R.raw.surf10000balikl800,
//
//            R.raw.surf20000depanr800, R.raw.surf20000depanl800,
//            R.raw.surf20000balikr800, R.raw.surf20000balikl800,
//
//            R.raw.surf50000depanr800, R.raw.surf50000depanl800,
//            R.raw.surf50000balikr800, R.raw.surf50000balikl800,
//
//            R.raw.surf100000depanr800, R.raw.surf100000depanl800,
//            R.raw.surf100000balikr800, R.raw.surf100000balikl800
//    };
    //endregion




//    public DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);

//    public List<Mat> trainImage = new ArrayList<Mat>();
//    public List<MatOfKeyPoint>  train_keypoints = new ArrayList<MatOfKeyPoint>();
//    public List<Mat> train_descriptor = new ArrayList<Mat>();

    private boolean lagiproses = false;
    private boolean lagidikamera = true;
    private boolean nyala = true;

    MyApp mApp;

    private long tstart, tend;
    public double getTime(long start, long end) {
        long tdelta = end-start;
        double elapsed = tdelta/1000.0;
        return elapsed;
    }
    RelativeLayout loadingBar;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    loadActivity();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to use your camera", Toast.LENGTH_SHORT).show();

                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview);

        myImage = (ImageView) findViewById(R.id.imageView);
        loadingBar = (RelativeLayout) findViewById(R.id.loadingPanel);
        context = getApplicationContext();
        mApp = ((MyApp)getApplicationContext());
        Log.i(TAG, "asdf" + ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA));

//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//        }

        //SETTING KEYPOINTS DAN DESCRIPTOR AWAL-AWAL

//            setKeypointsTemplate();
//            setDescriptorsTemplate();
//            matcher.add(train_descriptor);
//            matcher.train();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Permission denied to use your camera", Toast.LENGTH_SHORT).show();
//            Log.i(TAG, "why");

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 1);

        } else {
//            loadActivity();
        }
        loadActivity();

    }

    public void loadActivity() {
            mCamera = getCameraInstance();

        if (mCamera==null) {
            Toast.makeText(MainActivity.this, "Camera is in use or doesn't exist", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {

            //        Log.i(TAG, "Setting Detector parameters");
            //        File outputDir = getCacheDir(); // If in an Activity (otherwise getActivity.getCacheDir();
            //        File outputFile = null;
            //        try {
            //            outputFile = File.createTempFile("orbDetectorParams", ".YAML", outputDir);
            //        } catch (IOException e) {
            //            e.printStackTrace();
            //        }
            //        try {
            //            writeToFile(outputFile, "%YAML:1.0\nscaleFactor: 1.2\nnLevels: 8\nfirstLevel: 0 \nedgeThreshold: 31\npatchSize: 31\nWTA_K: 2\nscoreType: 1\nnFeatures: 1000\n");
            ////                writeToFile(outputFile, "%YAML:1.0\nnFeatures: 0\nnOctaveLayers: 1\ncontrastTreshold: 1\nedgeTreshold: 1\nsigma:1.3\n");
            //
            //        } catch (IOException e) {
            //            e.printStackTrace();
            //        }
            //        detector.read(outputFile.getPath());

            // Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(this, mCamera);
            preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);


            this.gestureDetector = new GestureDetectorCompat(this, this);
            gestureDetector.setOnDoubleTapListener(this);
        }

    }

    private GestureDetectorCompat gestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
//        Log.i(TAG, "onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (lagiproses==false && lagidikamera==true) {
            mApp.stop();
            Log.i(TAG, "onDoubleTap");
            lagiproses = true;
            lagidikamera = false;
//            Thread thread = new Thread() {
//                @Override
//                public void run() {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.i(TAG, "setvisible");
//                            loadingBar.setVisibility(View.VISIBLE);
//                        }
//                    });
//                    try {
//                        takePhoto();
//                    } catch (Exception e){
//
//                    }
//                }
//            };
            takePhoto();
//            thread.start();
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
//        Log.i(TAG, "onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
//        Log.i(TAG, "onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
//        Log.i(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
//        Log.i(TAG, "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        Log.i(TAG, "onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
//        Log.i(TAG, "onLongPress");
        if (lagiproses==false && lagidikamera==true) {
            mApp.play(context, 8);
        }
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        Log.i(TAG, "onFling");
        return true;
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
//        c.unlock();
        try {
            releaseCamera();
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            e.printStackTrace();


        }
        return c; // returns null if camera is unavailable
    }

    private static void releaseCameraAndPreview() {
//        CameraPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public void takePhoto(){
        mCamera.takePicture(null, null, mPicture);
    }


    public void toImageView(Mat inputImage) {
        Bitmap image_out = Bitmap.createBitmap(inputImage.cols(), inputImage.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(inputImage, image_out);

        Log.i(TAG, "Set Image View");
        myImage.setVisibility(View.VISIBLE);
        myImage.setImageBitmap(image_out);
    }

    public static MatOfKeyPoint keypointsFromJson(String json){
        MatOfKeyPoint result = new MatOfKeyPoint();

        JsonParser parser = new JsonParser();
        JsonArray jsonArr = parser.parse(json).getAsJsonArray();

        int size = jsonArr.size();

        KeyPoint[] kpArray = new KeyPoint[size];

        for(int i=0; i<size; i++){
            KeyPoint kp = new KeyPoint();

            JsonObject obj = (JsonObject) jsonArr.get(i);

            Point point = new Point(
                    obj.get("x").getAsDouble(),
                    obj.get("y").getAsDouble()
            );

            kp.pt       = point;
            kp.class_id = obj.get("class_id").getAsInt();
            kp.size     =     obj.get("size").getAsFloat();
            kp.angle    =    obj.get("angle").getAsFloat();
            kp.octave   =   obj.get("octave").getAsInt();
            kp.response = obj.get("response").getAsFloat();

            kpArray[i] = kp;
        }

        result.fromArray(kpArray);

        return result;
    }

    private String readFromFile(InputStream inputStream) {

        String ret = "";

        try {

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public final Mat loadMat(InputStream is) {
        try {
            int cols;
            float[] data;
            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                cols = (int) ois.readObject();
                data = (float[]) ois.readObject();
            }
            Mat mat = new Mat(data.length / cols, cols, CvType.CV_32F);
            mat.put(0, 0, data);
            return mat;
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            System.err.println("ERROR: Could not load mat from file: ");
//            Logger.getLogger(this.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean niceHomography(Point a, Point b, Point c, Point d) {
//
//        double ab = Math.sqrt(Math.pow(a.x - b.x,2.0) + Math.pow(a.y - b.y, 2.0));
//        double bc = Math.sqrt(Math.pow(b.x - c.x,2.0) + Math.pow(b.y - c.y, 2.0));
//        double cd = Math.sqrt(Math.pow(c.x - d.x,2.0) + Math.pow(c.y - d.y, 2.0));
//        double da = Math.sqrt(Math.pow(d.x - a.x,2.0) + Math.pow(d.y - a.y, 2.0));
//        if (ab < 200)
//            return false;
//        if (bc < 200)
//            return false;
//        if (cd < 200)
//            return false;
//        if (da < 200)
//            return false;

        double x1 = a.x;
        double y1 = a.y;
        double x2 = b.x;
        double y2 = b.y;
        double x3 = c.x;
        double y3 = c.y;
        double x4 = d.x;
        double y4 = d.y;

        double asdf = (x1*y2 - x2*y1) + (x2*y3 - x3*y2) + (x3*y4 - x4*y3) + (x4*y1 - x1*y4);
//        Log.i(TAG, "Area = " + asdf);
        if (asdf <= 6000)
            return false;

        return true;
    }

    public int countInlier(Mat mask, List<DMatch> gmatch) {
        List<DMatch> matches_homo = new ArrayList<DMatch>();
        int size = (int) mask.size().height;
        for (int i = 0; i < size; i++) {
            if (mask.get(i, 0)[0] == 1) {
                DMatch dd = gmatch.get(i);
                matches_homo.add(dd);
            }
        }
        MatOfDMatch mat = new MatOfDMatch();
        mat.fromList(matches_homo);
        int mat_homo = (int) mat.size().height;
        return mat_homo;
    }


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            mApp.play(context, 9);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);
//
//            if(bitmap!=null){
//
//                File file=new File(Environment.getExternalStorageDirectory()+"/moneydetect");
//                if(!file.isDirectory()){
//                    file.mkdir();
//                }
//
//                file=new File(Environment.getExternalStorageDirectory()+"/moneydetect",System.currentTimeMillis()+".jpg");
//
//
//                try
//                {
//                    FileOutputStream fileOutputStream=new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);
//
//                    fileOutputStream.flush();
//                    fileOutputStream.close();
//                }
//                catch(IOException e){
//                    e.printStackTrace();
//                }
//                catch(Exception exception)
//                {
//                    exception.printStackTrace();
//                }
//
//            }

            lagiproses=true;
            System.out.println("\nRunning FindObject");
//
            Mat img_scene = Highgui.imdecode(new MatOfByte(data), Highgui.CV_LOAD_IMAGE_GRAYSCALE);

//            Mat img_scene = new Mat();
//            BitmapDrawable drawable = (BitmapDrawable) getDrawable(R.drawable.fotouang2000);
//            Bitmap bitmap = drawable.getBitmap();
//            Utils.bitmapToMat(bitmap, img_scene);
//            Imgproc.cvtColor(img_scene, img_scene, Imgproc.COLOR_BGR2GRAY);


//            MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
            MatOfKeyPoint keypoints_scene  = new MatOfKeyPoint();

//            setTrainImage();
//            Log.i(TAG, "Get object keypoints");
//            detector.detect(trainImage, train_keypoints);
//            setKeypointsTemplate();
            Log.i(TAG, "Get scene keypoints");
//            tstart = System.currentTimeMillis();
            mApp.detector.detect(img_scene, keypoints_scene);
//            tend = System.currentTimeMillis();
//            Log.i(TAG, "Jumlah : " + keypoints_scene.rows());
//            Log.i(TAG, "Keypoint : " + getTime(tstart, tend));


            //GET DESCRIPTOR
//            Mat descriptor_object = new Mat();
            Mat descriptor_scene = new Mat() ;
//            Log.i(TAG, "Get object descriptor");
//            extractor.compute(img_object, keypoints_object, descriptor_object);
//            extractor.compute(trainImage, train_keypoints, train_descriptor);
//            setDescriptorsTemplate();
            Log.i(TAG, "Get scene descriptor");
//            tstart = System.currentTimeMillis();
            mApp.extractor.compute(img_scene, keypoints_scene, descriptor_scene);
//            tend = System.currentTimeMillis();
//            Log.i(TAG, "Descriptor : " + getTime(tstart, tend));

            //MATCHING
            Log.i(TAG, "Get matching keypoints");
//            matcher.match(descriptor_object, descriptor_scene, matches);
            int maxDist = -1;
            int ke = 0;




            //region MATCHING MENGGUNAKAN KNN MATCH
            List<MatOfDMatch> allmatch = new ArrayList<MatOfDMatch>();
            List<DMatch> gmatch = new ArrayList<DMatch>();

            mApp.matcher.knnMatch(descriptor_scene,allmatch,2);
//            matcher.radiusMatch(descriptor_scene,allmatch,10);
//            List<Mat> new_traindescriptor = matcher.getTrainDescriptors();
//            for (Iterator<MatOfDMatch> iterator = allmatch.iterator(); iterator.hasNext();) {
//                MatOfDMatch matOfDMatch = (MatOfDMatch) iterator.next();
//                if (matOfDMatch.toArray()[0].distance / matOfDMatch.toArray()[1].distance < 0.9) {
//                    gmatch.add(matOfDMatch.toArray()[0]);
//                }
//            }
            for (int j=0; j<allmatch.size(); j++) {
                MatOfDMatch temp = allmatch.get(j);
                if (temp.toArray()[0].distance/temp.toArray()[1].distance<0.8) {
                    gmatch.add(temp.toArray()[0]);
                }
            }
//            Log.i(TAG, "size gmatch = " + gmatch.size());

            Mat img_save = new Mat();
            Mat img_matches = new Mat();
            int maxmatches=0;
            for (int count=0; count<mApp.train_keypoints.size(); count++) {
                LinkedList<Point> objList = new LinkedList<Point>();
                LinkedList<Point> sceneList = new LinkedList<Point>();

                List<KeyPoint> keypoints_objectList = mApp.train_keypoints.get(count).toList();
                List<KeyPoint> keypoints_sceneList = keypoints_scene.toList();
//                Log.i(TAG, "size keypoint objectlist = " + keypoints_objectList.size());
//                Log.i(TAG, "size keypoint scenelist = " + keypoints_sceneList.size());

                for (int i = 0; i < gmatch.size(); i++) {
                    if (gmatch.get(i).imgIdx == count) {
                        objList.addLast(keypoints_objectList.get(gmatch.get(i).trainIdx).pt);
                        sceneList.addLast(keypoints_sceneList.get(gmatch.get(i).queryIdx).pt);
                    }
                }

//                if (objList.size()>maxmatches) {
//                    maxmatches=objList.size();
//                    img_matches = getImageNoRansac(count, img_scene, keypoints_scene, gmatch);
//                }

                if (objList.size() > 4) {
                    Log.i(TAG, "Template " + nama[count]);
                    MatOfPoint2f obj = new MatOfPoint2f();
                    obj.fromList(objList);

                    MatOfPoint2f scene = new MatOfPoint2f();
                    scene.fromList(sceneList);

                    Mat mask = new Mat();
                    Mat hg = Calib3d.findHomography(obj, scene, Calib3d.RANSAC, 10, mask);

                    Mat obj_corners = new Mat(4, 1, CvType.CV_32FC2);
                    Mat scene_corners = new Mat(4, 1, CvType.CV_32FC2);

                    obj_corners.put(0, 0, new double[]{0, 0});
                    obj_corners.put(1, 0, new double[]{1200, 0});
                    obj_corners.put(2, 0, new double[]{1200, 1050});
                    obj_corners.put(3, 0, new double[]{0, 1050});

                    Core.perspectiveTransform(obj_corners, scene_corners, hg);

//                    for (int i = 0; i < 4; i++) {
//                        Point mypoint = new Point(scene_corners.get(i, 0));
//                        double myx = mypoint.x + 2400;
//                        double myy = mypoint.y;
//                        scene_corners.put(i, 0, new double[]{myx, myy});
//                    }

                    Point a = new Point(scene_corners.get(0, 0));
                    Point b = new Point(scene_corners.get(1, 0));
                    Point c = new Point(scene_corners.get(2, 0));
                    Point d = new Point(scene_corners.get(3, 0));

                    boolean nicehomo = niceHomography(a, b, c, d);

                    int mat_homo;
                    if (nicehomo == true) {
                        mat_homo= countInlier(mask,gmatch);
//                        List<DMatch> matches_homo = new ArrayList<DMatch>();
//                        int size = (int) mask.size().height;
//                        for (int i = 0; i < size; i++) {
//                            if (mask.get(i, 0)[0] == 1) {
//                                DMatch dd = gmatch.get(i);
//                                matches_homo.add(dd);
//                            }
//                        }
//
//                        MatOfDMatch mat = new MatOfDMatch();
//                        mat.fromList(matches_homo);
//
//                        mat_homo = (int) mat.size().height;
//
//
                        Log.i(TAG, "Matches = " + objList.size());
                        Log.i(TAG, "Inlier = " + mat_homo);
                    } else {
                        mat_homo = 0;
                    }

                    if (mat_homo > maxDist) {
//                        img_save = getImage(count,img_scene,keypoints_scene,gmatch,scene_corners);
                        maxDist = mat_homo;
                        ke = count;
                    }
                }
            }
            //endregion

//            if (maxDist>0)
//                saveImage(img_save, "withransacorb");
//            if (maxmatches>0)
//                saveImage(img_matches, "noransacorb");

            //region MATCHING BIASA TAPI LAMA
//            for (int j=0; j<train_keypoints.size(); j++) {
////
//                MatOfDMatch matches = new MatOfDMatch();
//                Mat descriptor = (Mat) train_descriptor.get(j);
////
//                matcher.match(descriptor, descriptor_scene, matches);
//                List<DMatch> matchesList = matches.toList();
////                Log.i(TAG, "MATCHES = " + matches.rows());
//
////                MatOfDMatch matches = allmatch.get(j);
////                List<DMatch> matchesList = new ArrayList<DMatch>();
////                matchesList.addAll(matches.toList());
////                Log.i(TAG, "MATCHES = " + matchesList.size());
////                Log.i(TAG, "Size desc = " + descriptor.size());
//
//                Double max_dist = 0.0;
//                Double min_dist = 100.0;
//
//                for (int i = 0; i < descriptor.rows(); i++) {
//                    Double dist = (double) matchesList.get(i).distance;
////                    Log.i(TAG, " " + dist);
//                    if (dist < min_dist) min_dist = dist;
//                    if (dist > max_dist) max_dist = dist;
//                }
//
//                LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
//                MatOfDMatch gm = new MatOfDMatch();
//
////                Log.i(TAG, "Get good matches only");
//                for (int i = 0; i < descriptor.rows(); i++) {
//                    if (matchesList.get(i).distance < 2 * min_dist) {
//                        good_matches.addLast(matchesList.get(i));
//                    }
//                }
//
//                gm.fromList(good_matches);
//
//                if (good_matches.size() > 30) {
//
//                    LinkedList<Point> objList = new LinkedList<Point>();
//                    LinkedList<Point> sceneList = new LinkedList<Point>();
//
//                    List<KeyPoint> keypoints_objectList = train_keypoints.get(j).toList();
//                    List<KeyPoint> keypoints_sceneList = keypoints_scene.toList();
//
//                    for (int i = 0; i < good_matches.size(); i++) {
//                        objList.addLast(keypoints_objectList.get(good_matches.get(i).queryIdx).pt);
//                        sceneList.addLast(keypoints_sceneList.get(good_matches.get(i).trainIdx).pt);
//                    }
//
//                    MatOfPoint2f obj = new MatOfPoint2f();
//                    obj.fromList(objList);
//
//                    MatOfPoint2f scene = new MatOfPoint2f();
//                    scene.fromList(sceneList);
//
//                    Mat maskInline = new Mat();
////                    Log.i(TAG, "Get RANSAC homography");
//                    Mat mask = new Mat();
//                    Mat hg = Calib3d.findHomography(obj, scene, Calib3d.RANSAC, 10, mask);
//
//                    Mat obj_corners = new Mat(4,1,CvType.CV_32FC2);
//                    Mat scene_corners = new Mat(4,1,CvType.CV_32FC2);
//
//                    obj_corners.put(0, 0, new double[]{0, 0});
//                    obj_corners.put(1, 0, new double[]{2400, 0});
//                    obj_corners.put(2, 0, new double[]{2400, 1050});
//                    obj_corners.put(3, 0, new double[]{0, 1050});
//
//                    Core.perspectiveTransform(obj_corners, scene_corners, hg);
//
//                    for(int i=0; i<4; i++) {
//                        Point mypoint = new Point(scene_corners.get(i, 0));
//                        double myx = mypoint.x + 2400;
//                        double myy = mypoint.y;
//                        scene_corners.put(i, 0, new double[]{myx, myy});
//                    }
//
//                    Point a = new Point(scene_corners.get(0, 0));
//                    Point b = new Point(scene_corners.get(1, 0));
//                    Point c = new Point(scene_corners.get(2, 0));
//                    Point d = new Point(scene_corners.get(3, 0));
//
//                    boolean nicehomo = niceHomography(a, b, c, d);
//
//                    int mat_homo;
//                    if (nicehomo == true ) {
//                        List<DMatch> matches_homo = new ArrayList<DMatch>();
//                        int size = (int) mask.size().height;
//                        for (int i = 0; i < size; i++) {
//                            if (mask.get(i, 0)[0] == 1) {
//                                DMatch dd = good_matches.get(i);
//                                matches_homo.add(dd);
//                            }
//                        }
//
//                        MatOfDMatch mat = new MatOfDMatch();
//                        mat.fromList(matches_homo);
//
//                        mat_homo = (int) mat.size().height;
//
//
//                        Log.i(TAG, "Template " + nama[j]);
//                        System.out.println("-- Max dist : " + max_dist);
//                        System.out.println("-- Min dist : " + min_dist);
//                        Log.i (TAG, "Good matches = " + good_matches.size());
//                        Log.i(TAG, "Banyak match = " + mat_homo);
//                    }
//                    else {
//                        mat_homo = 0;
//                    }
//
//                    if (mat_homo > maxDist) {
//                        maxDist = mat_homo;
//                        ke = j;
//                    }
//                }
//            }

            //endregion


            lagiproses=false;
            lagidikamera=false;
//            nyala=false;

            Intent intent = new Intent(context, ResultActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if (maxDist>=mApp.batas)
                intent.putExtra("hasil", namasatu[ke] + "");
            else
                intent.putExtra("hasil", "Tolong diulang\nMirip dengan " + namasatu[ke]);
            intent.putExtra("indeks", ke);
            intent.putExtra("maks", maxDist);
            startActivity(intent);
            finish();
//            Mat obj_corners = new Mat(4,1, CvType.CV_32FC2);
//            Mat scene_corners = new Mat(4,1,CvType.CV_32FC2);
//
//            obj_corners.put(0, 0, new double[]{0, 0});
//            obj_corners.put(1, 0, new double[]{img_object.cols(), 0});
//            obj_corners.put(2, 0, new double[]{img_object.cols(), img_object.rows()});
//            obj_corners.put(3, 0, new double[]{0, img_object.rows()});
//
//            Core.perspectiveTransform(obj_corners, scene_corners, hg);
//
//            for(int i=0; i<4; i++) {
//                Point mypoint = new Point(scene_corners.get(i, 0));
//                double myx = mypoint.x + img_object.cols();
//                double myy = mypoint.y;
//                scene_corners.put(i, 0, new double[]{myx, myy});
//            }
//
//            Core.line(img_matches, new Point(scene_corners.get(0, 0)), new Point(scene_corners.get(1, 0)), new Scalar(0, 255, 0), 4);
//            Core.line(img_matches, new Point(scene_corners.get(1, 0)), new Point(scene_corners.get(2, 0)), new Scalar(255, 255, 0), 4);
//            Core.line(img_matches, new Point(scene_corners.get(2, 0)), new Point(scene_corners.get(3, 0)), new Scalar(0, 0, 255), 4);
//            Core.line(img_matches, new Point(scene_corners.get(3, 0)), new Point(scene_corners.get(0, 0)), new Scalar(0, 255, 255), 4);
//
//            toImageView(img_matches);
        }
    };

//    public int[] iddrawable = {
//            R.drawable.uang1000depanr, R.drawable.uang1000depanl,
//            R.drawable.uang1000balikr, R.drawable.uang1000balikl,
//
//            R.drawable.uang2000depanr, R.drawable.uang2000depanl,
//            R.drawable.uang2000balikr, R.drawable.uang2000balikl,
//
//            R.drawable.uang5000depanr, R.drawable.uang5000depanl,
//            R.drawable.uang5000balikr, R.drawable.uang5000balikl,
//
//            R.drawable.uang10000depanr, R.drawable.uang10000depanl,
//            R.drawable.uang10000balikr, R.drawable.uang10000balikl,
//
//            R.drawable.uang20000depanr, R.drawable.uang20000depanl,
//            R.drawable.uang20000balikr, R.drawable.uang20000balikl,
//
//            R.drawable.uang50000depanr, R.drawable.uang50000depanl,
//            R.drawable.uang50000balikr, R.drawable.uang50000balikl,
//
//            R.drawable.uang100000depanr, R.drawable.uang100000depanl,
//            R.drawable.uang100000balikr, R.drawable.uang100000balikl
//    };
////
//    public void saveImage(Mat img_save, String name) {
//
//        Bitmap bmp = Bitmap.createBitmap(img_save.cols(), img_save.rows(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(img_save, bmp);
//        FileOutputStream out = null;
//
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        String filename = name + System.currentTimeMillis()+currentDateTimeString+".jpg";
//
//
//        File sd = new File(Environment.getExternalStorageDirectory() + "/moneydetect");
//        boolean success = true;
//        if (!sd.exists()) {
//            success = sd.mkdir();
//        }
//        if (success) {
//            File dest = new File(sd, filename);
//
//            try {
//                out = new FileOutputStream(dest);
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
//                // PNG is a lossless format, the compression factor (100) is ignored
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.d(TAG, e.getMessage());
//            } finally {
//                try {
//                    if (out != null) {
//                        out.close();
//                        Log.d(TAG, "OK!!");
//                    }
//                } catch (IOException e) {
//                    Log.d(TAG, e.getMessage() + "Error");
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public Mat getImageNoRansac(int count, Mat img_scene, MatOfKeyPoint keypoints_scene, List<DMatch> gmatch){
//        Mat img_object = new Mat();
//        BitmapDrawable drawable = (BitmapDrawable) getDrawable(iddrawable[count]);
//        Bitmap bitmap = drawable.getBitmap();
//        Utils.bitmapToMat(bitmap, img_object);
//        Imgproc.cvtColor(img_object, img_object, Imgproc.COLOR_BGR2GRAY);
//        MatOfKeyPoint keypoints_object = mApp.train_keypoints.get(count);
//        Mat img_matches = new Mat();
//        MatOfDMatch gm = new MatOfDMatch();
//        List<DMatch> thismatch = new ArrayList<DMatch>();
//        for (int i = 0; i < gmatch.size(); i++) {
//            DMatch temp = gmatch.get(i);
//            if (gmatch.get(i).imgIdx == count) {
//                thismatch.add(temp);
//            }
//        }
//        gm.fromList(thismatch);
//        Features2d.drawMatches(
//                img_scene,
//                keypoints_scene,
//                img_object,
//                keypoints_object,
//                gm,
//                img_matches,
//                new Scalar(0, 255, 255),
//                new Scalar(255, 255, 0),
//                new MatOfByte(),
//                0);
//
//        return img_matches;
//    }
////
//    public Mat getImage(int count, Mat img_scene, MatOfKeyPoint keypoints_scene, List<DMatch> gmatch, Mat scene_corners) {
//        Mat img_object = new Mat();
//        BitmapDrawable drawable = (BitmapDrawable) getDrawable(iddrawable[count]);
//        Bitmap bitmap = drawable.getBitmap();
//        Utils.bitmapToMat(bitmap, img_object);
//        Imgproc.cvtColor(img_object, img_object, Imgproc.COLOR_BGR2GRAY);
//        MatOfKeyPoint keypoints_object = mApp.train_keypoints.get(count);
//        MatOfDMatch gm = new MatOfDMatch();
//        List<DMatch> thismatch = new ArrayList<DMatch>();
//        for (int i = 0; i < gmatch.size(); i++) {
//            DMatch temp = gmatch.get(i);
//            if (gmatch.get(i).imgIdx == count) {
//                thismatch.add(temp);
//            }
//        }
//        gm.fromList(thismatch);
//        Mat img_matches = new Mat();
//        Features2d.drawMatches(
//                img_scene,
//                keypoints_scene,
//                img_object,
//                keypoints_object,
//                gm,
//                img_matches,
//                new Scalar(0, 255, 255),
//                new Scalar(255, 255, 0),
//                new MatOfByte(),
//                0);
//
//
//        Core.line(img_matches, new Point(scene_corners.get(0, 0)), new Point(scene_corners.get(1, 0)), new Scalar(0, 255, 0), 4);
//        Core.line(img_matches, new Point(scene_corners.get(1, 0)), new Point(scene_corners.get(2, 0)), new Scalar(0, 255, 0), 4);
//        Core.line(img_matches, new Point(scene_corners.get(2, 0)), new Point(scene_corners.get(3, 0)), new Scalar(0, 255, 0), 4);
//        Core.line(img_matches, new Point(scene_corners.get(3, 0)), new Point(scene_corners.get(0, 0)), new Scalar(0, 255, 0), 4);
//
//        return img_matches;
//    }

    private void writeToFile(File file, String data) throws IOException {
        FileOutputStream stream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream);
        outputStreamWriter.write(data);
        outputStreamWriter.close();
        stream.close();
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("TAG", "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }

    private static void releaseCamera(){
        if (mCamera != null){
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }
}