package com.henzmontera.cap102_plantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.henzmontera.cap102_plantapp.ml.MangoIndianRipenessSorter;
import com.henzmontera.cap102_plantapp.ml.MangoIndianSizeSorter;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

public class IndianMangoActivity extends AppCompatActivity {

    TextView result, confidence, size, brixlevel;
    ImageView imageView;
    Button picture, addingbrix;
    int imageSize = 224;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_mango);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.side_nav_bar));
        setTitle("INDIAN MANGO");

        result = findViewById(R.id.resultIM);
        confidence = findViewById(R.id.confidenceIM);
        imageView = findViewById(R.id.imageViewIM);
        picture = findViewById(R.id.buttonIM);
        size = findViewById(R.id.sizeIM);
        addingbrix = findViewById(R.id.addingbrixIM);
        brixlevel = findViewById(R.id.brixlevelIM);

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

        addingbrix.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Input Brix Percentage");
            builder.setMessage("Put the percentage of the output of the refractometer which using brix meter.");
            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    int a = Integer.parseInt(m_Text);
                    String b = "";
                    if (a < 4){
                        b = "Sour";
                    }
                    else if (a >= 4 && a < 6){
                        b = "Barely Sweet";
                    }
                    else if (a >= 6 && a < 10){
                        b = "Sweet";
                    }
                    else if (a >= 10 && a < 14){
                        b = "Perfect Sweet";
                    }
                    else if (a >= 14){
                        b = "Very Sweet";
                    }
                    brixlevel.setText(b);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        });
    }

    public void classifyImage(Bitmap image){ // Add model and add text files

        try {

            MangoIndianRipenessSorter MiRipeness = MangoIndianRipenessSorter.newInstance(getApplicationContext());
            MangoIndianSizeSorter MiSize = MangoIndianSizeSorter.newInstance(getApplicationContext());

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


            // Runs model inference and gets result.
            MangoIndianRipenessSorter.Outputs outputsripness = MiRipeness.process(inputFeature0);
            TensorBuffer outputFeature0ripeness = outputsripness.getOutputFeature0AsTensorBuffer();

            float[] confidencesripeness = outputFeature0ripeness.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPosRipeness = 0;
            float maxConfidenceRipeness = 0;
            for(int i = 0; i < confidencesripeness.length; i++){
                if(confidencesripeness[i] > maxConfidenceRipeness){
                    maxConfidenceRipeness = confidencesripeness[i];
                    maxPosRipeness = i;
                }
            }

            MangoIndianSizeSorter.Outputs outputssize = MiSize.process(inputFeature0);
            TensorBuffer outputFeature0size = outputssize.getOutputFeature0AsTensorBuffer();

            float[] confidencessize = outputFeature0size.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPosSize = 0;
            float maxConfidenceSize = 0;
            for(int i = 0; i < confidencessize.length; i++){
                if(confidencessize[i] > maxConfidenceSize){
                    maxConfidenceSize = confidencessize[i];
                    maxPosSize = i;
                }
            }

            String[] Mi_Ripeness = {"Ripe","Ripe W/ Defect","Rotten","Unripe"};
            String[] Mi_Size = {"Large","Medium","Small"};

            result.setText(Mi_Ripeness[maxPosRipeness]);
            size.setText(Mi_Size[maxPosSize]);

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            confidence.setText(
                    "Ripeness: "+df.format(confidencesripeness[maxPosRipeness] * 100) + "%" +
                            "\n" + "Size: "+df.format(confidencesripeness[maxPosRipeness] * 100) + "%");

            // Releases model resources if no longer used.
            MiRipeness.close();
            MiSize.close();

        } catch (IOException e) {
            Log.d("Error: ","Error: "+e);
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