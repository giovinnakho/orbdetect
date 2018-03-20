package com.petra.giovinnakhoharja.orbdetect;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by giovinnakhoharja on 10/2/16.
 */
public class MyApp extends Application{
    static {
        System.loadLibrary("opencv_java");
    }

    private static final String TAG = "mydebug";

    public MyApp() {
        // this method fires only once per application start.
        // getApplicationContext returns null here


    }

    private long tstart, tend;

    public double getTime(long start, long end) {
        long tdelta = end-start;
        double elapsed = tdelta/1000.0;
        return elapsed;
    }




    public static int batas=13;
    @Override
    public void onCreate() {
        super.onCreate();
//        tstart=System.currentTimeMillis();

        // this method fires once as well as constructor
        // but also application has context here

        Log.i(TAG, "onCreate fired");
        play(getApplicationContext(), 8);
        setKeypointsTemplate();
        setDescriptorsTemplate();
        matcher.add(train_descriptor);
        matcher.train();

        File tempDir = getCacheDir();
        File tempFile = null;
        try {
            tempFile = File.createTempFile("config", ".yml", tempDir);
            String settings = "%YAML:1.0\nnFeatures: 1200\nnLevels: 5\npatchSize: 30\n";
            Log.i(TAG, "Setting : " + settings);

            FileWriter writer = new FileWriter(tempFile, false);
            writer.write(settings);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        detector.read(tempFile.getPath());
        extractor.read(tempFile.getPath());
//        tend=System.currentTimeMillis();
//        Log.i(TAG, "Siap2 : " + getTime(tstart,tend));

    }



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

    public int[] iddescriptor = {
            R.raw.orb1000depanr800des, R.raw.orb1000depanl800des,
            R.raw.orb1000balikr800des, R.raw.orb1000balikl800des,

            R.raw.orb2000depanr800des, R.raw.orb2000depanl800des,
            R.raw.orb2000balikr800des, R.raw.orb2000balikl800des,

            R.raw.orb5000depanr800des, R.raw.orb5000depanl800des,
            R.raw.orb5000balikr800des, R.raw.orb5000balikl800des,

            R.raw.orb10000depanr800des, R.raw.orb10000depanl800des,
            R.raw.orb10000balikr800des, R.raw.orb10000balikl800des,

            R.raw.orb20000depanr800des, R.raw.orb20000depanl800des,
            R.raw.orb20000balikr800des, R.raw.orb20000balikl800des,

            R.raw.orb50000depanr800des, R.raw.orb50000depanl800des,
            R.raw.orb50000balikr800des, R.raw.orb50000balikl800des,

            R.raw.orb100000depanr800des, R.raw.orb100000depanl800des,
            R.raw.orb100000balikr800des, R.raw.orb100000balikl800des
    };

    public int[] idkeypoint = {
            R.raw.orb1000depanr800, R.raw.orb1000depanl800,
            R.raw.orb1000balikr800, R.raw.orb1000balikl800,

            R.raw.orb2000depanr800, R.raw.orb2000depanl800,
            R.raw.orb2000balikr800, R.raw.orb2000balikl800,

            R.raw.orb5000depanr800, R.raw.orb5000depanl800,
            R.raw.orb5000balikr800, R.raw.orb5000balikl800,

            R.raw.orb10000depanr800, R.raw.orb10000depanl800,
            R.raw.orb10000balikr800, R.raw.orb10000balikl800,

            R.raw.orb20000depanr800, R.raw.orb20000depanl800,
            R.raw.orb20000balikr800, R.raw.orb20000balikl800,

            R.raw.orb50000depanr800, R.raw.orb50000depanl800,
            R.raw.orb50000balikr800, R.raw.orb50000balikl800,

            R.raw.orb100000depanr800, R.raw.orb100000depanl800,
            R.raw.orb100000balikr800, R.raw.orb100000balikl800
    };
    //endregion

    public List<MatOfKeyPoint> train_keypoints = new ArrayList<MatOfKeyPoint>();
    public List<Mat> train_descriptor = new ArrayList<Mat>();
    public FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
    public DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
    public DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);


    //region SOUND
    public int[] idsound = {
            R.raw.seribu,
            R.raw.dua_ribu,
            R.raw.lima_ribu,
            R.raw.sepuluh_ribu,
            R.raw.dua_puluh_ribu,
            R.raw.lima_puluh_ribu,
            R.raw.seratus_ribu,
            R.raw.tidak_terdeteksi,
            R.raw.informasi,
            R.raw.diproses
    };
    public MediaPlayer mMediaPlayer;

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void play(Context c, int rid) {
        stop();

        mMediaPlayer = MediaPlayer.create(c, idsound[rid]);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });

        mMediaPlayer.start();
    }
    //endregion


    //region ambil file
    public MatOfKeyPoint keypointsFromJson(String json){
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

    public void setKeypointsTemplate() {
        MatOfKeyPoint keypoints_object;
        String readjson;
        InputStream is;
        for (int i=0; i<idkeypoint.length; i++) {
            is = getResources().openRawResource(idkeypoint[i]);
            readjson = readFromFile(is);
            keypoints_object = keypointsFromJson(readjson);
//            Log.i(TAG, "Jumlah: " + keypoints_object.rows());
            train_keypoints.add(keypoints_object);
        }
    }

    public final Mat loadMat(InputStream is) {
        try {
            int cols;
            byte[] data;
            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                cols = (int) ois.readObject();
                data = (byte[]) ois.readObject();
            }
            Mat mat = new Mat(data.length / cols, cols, CvType.CV_8U);
            mat.put(0, 0, data);
            return mat;
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            System.err.println("ERROR: Could not load mat from file: ");
//            Logger.getLogger(this.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setDescriptorsTemplate() {
        Mat descriptor_object;
        InputStream is;

        for (int i=0; i<iddescriptor.length; i++) {

            is = getResources().openRawResource(iddescriptor[i]);
            descriptor_object = loadMat(is);
            train_descriptor.add(descriptor_object);
        }

    }
    //endregion



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

}
