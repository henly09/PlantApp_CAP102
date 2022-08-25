package com.henzmontera.cap102_plantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.henzmontera.cap102_plantapp.ml.MaRipenessSorter;
import com.henzmontera.cap102_plantapp.ml.MaSizeSorter;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AppleMangoActivity extends AppCompatActivity {

    TextView result, confidence, size;
    ImageView imageView;
    Button picture;
    int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_mango);

        result = findViewById(R.id.resultAM);
        confidence = findViewById(R.id.confidenceAM);
        size = findViewById(R.id.sizeAM);
        imageView = findViewById(R.id.imageViewAM);
        picture = findViewById(R.id.buttonAM);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
    }

    public void classifyImage(Bitmap image){
        try {

            MaRipenessSorter MaRipeness = MaRipenessSorter.newInstance(getApplicationContext());
            MaSizeSorter MaSize = MaSizeSorter.newInstance(getApplicationContext());
            // Creates inputs for reference.

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            // get 1D array of 224 * 224 pixels in image

            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs Ripeness Model inference and gets result.
            MaRipenessSorter.Outputs outputsripeness = MaRipeness.process(inputFeature0);
            TensorBuffer outputFeature0ripeness = outputsripeness.getOutputFeature0AsTensorBuffer();

            float[] confidencesripeness = outputFeature0ripeness.getFloatArray();
            // find the index of the class with the biggest confidence of ripeness.
            int maxPosRipeness = 0;
            float maxConfidenceRipeness = 0;
            for(int i = 0; i < confidencesripeness.length; i++){
                if(confidencesripeness[i] > maxConfidenceRipeness){
                    maxConfidenceRipeness = confidencesripeness[i];
                    maxPosRipeness = i;
                }
            }

            // Runs Size Model inference and gets result.
            MaSizeSorter.Outputs outputssize = MaSize.process(inputFeature0);
            TensorBuffer outputFeature0size = outputssize.getOutputFeature0AsTensorBuffer();

            float[] confidencessize = outputFeature0size.getFloatArray();
            // find the index of the class with the biggest confidence of ripeness.
            int maxPosSize= 0;
            float maxConfidenceSize = 0;
            for(int i = 0; i < confidencessize.length; i++){
                if(confidencessize[i] > maxConfidenceSize){
                    maxConfidenceSize = confidencessize[i];
                    maxPosSize = i;
                }
            }

            String[] Ma_Ripeness = {"Over-Ripe","Ripe","Ripe w/ Defects","Rotten","Unripe"};
            String[] Ma_Size = {"Small","Medium","Large"};

            result.setText(Ma_Ripeness[maxPosRipeness]);
            size.setText(Ma_Size[maxPosSize]);

            confidence.setText(
                    "Class Confidence: "+confidencesripeness[maxPosRipeness] * 100 + " %" +
                    "\n" + "Size Confidence: "+confidencessize[maxPosSize] * 100 + " %");

            // Releases model resources if no longer used.
            MaSize.close();
            MaRipeness.close();

        } catch (IOException e) {
            Toast.makeText(this, "Error Occured!. Please Try Again Later!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}