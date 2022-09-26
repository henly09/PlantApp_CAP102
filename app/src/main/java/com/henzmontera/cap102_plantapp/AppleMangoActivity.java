package com.henzmontera.cap102_plantapp;
// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.henzmontera.cap102_plantapp.ml.MaRipenessSorter;
import com.henzmontera.cap102_plantapp.ml.MaSizeSorter;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AppleMangoActivity extends AppCompatActivity {

    TextView /*result,size, brixlevel,*/rcppercentage,scppercentage;
    StateProgressBar ripenesslevelAM,sizelevelAM,brixlevelAM;
    ImageView imageView,ripdialogAM,confidialogAM,sizedialogAM,brixdialogAM;
    ProgressBar ripebar,sizebar;
    Button picture, addingbrix/*, RecAndProdAM*/;
    int imageSize = 224/*, notifBadgeAM = 0*/;
    private String m_Text = "";
    /*NotificationBadge notificationBadgeAM;*/
    String[] Ma_Ripeness = {"Ripe","RipeW/Def","Rot","Unripe"};
    String[] Ma_Ripeness_reverse = {"Unripe","Rot","RipeW/Def","Ripe"};
    String[] Ma_Size = {"Small","Medium","Large"};
    String[] Ma_Brixlevel = {"Sour","B Sweet","Sweet","P Sweet","V Sweet"};

/*    int AMbrix;
    String AMsize,AMripeness;*/

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_mango);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbartheme));
        setTitle("APPLE MANGO");

        /*result = findViewById(R.id.classifiedAM);
        size = findViewById(R.id.SizesAM);
        brixlevel = findViewById(R.id.brixlevelsAM);
        notificationBadgeAM = findViewById(R.id.badgeAM);
        RecAndProdAM = findViewById(R.id.recAndProdAM);*/


        imageView = findViewById(R.id.imageViewAM);
        picture = findViewById(R.id.buttonAM);
        addingbrix = findViewById(R.id.addingbrixAM);

        // initialization for progressbar
        ripebar = findViewById(R.id.rcpbarAM);
        sizebar = findViewById(R.id.scpbarAM);
        rcppercentage = findViewById(R.id.ripecpercentageAM);
        scppercentage = findViewById(R.id.sizecpercentageAM);

        // initialization for gauges
        ripenesslevelAM = findViewById(R.id.ripenesslevelAM);
        sizelevelAM = findViewById(R.id.sizelevelAM);
        brixlevelAM = findViewById(R.id.brixlevelAM);

        // set dialog box
        ripdialogAM = findViewById(R.id.ripdialogAM);
        confidialogAM = findViewById(R.id.confidialogAM);
        sizedialogAM = findViewById(R.id.sizedialogAM);
        brixdialogAM = findViewById(R.id.brixdialogAM);

        // set gauge meter description
        ripenesslevelAM.setStateDescriptionData(Ma_Ripeness_reverse);
        sizelevelAM.setStateDescriptionData(Ma_Size);
        brixlevelAM.setStateDescriptionData(Ma_Brixlevel);

        ripdialogAM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ripeness Level");
            builder.setMessage("\nChecking the ripeness of the Mango fruit whether it is unripe, rot, ripe, ripe with defect.");
            builder.setIcon(getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        confidialogAM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Analyzer Confidence Level");
            builder.setMessage("\nThe output tells on how accurate the analyzer of the results given after the image undergo in process.");
            builder.setIcon(getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        sizedialogAM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Size Level");
            builder.setMessage("\nChecking the size of the Mango fruit whether it is Large, Medium, or Small");
            builder.setIcon(getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        brixdialogAM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Brix Level");
            builder.setMessage("\nChecking the Sweetness of the Mango fruit by putting the result percentage from the refractometer. \n\nInstructions: Place a small amount (usually 2–5 drops) of liquid (The Mango Juice) on the prism, and secure the cover plate. This will evenly distribute the liquid on the prism. Point the prism end of the refractometer toward a light source and focus the eyepiece until the scale is clearly visible.");
            builder.setIcon(getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        picture.setOnClickListener(view -> {
            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(AppleMangoActivity.this);
            builder.setTitle("Choose an Action");
            builder.setItems(options, (dialog, item) -> {
                if (options[item].equals("Take Photo"))
                {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                    } else {
                        //Request camera permission if we don't have it.
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            });
            builder.show();
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
            builder.setPositiveButton("OK", (dialog, which) -> {

                m_Text = input.getText().toString();
                int a = Integer.parseInt(m_Text);
                // https://www.healthy-vegetable-gardening.com/brix-scale.html
                if (a < 4){
                    brixlevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                }
                else if (a >= 4 && a < 6){
                    brixlevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                }
                else if (a >= 6 && a < 10){
                    brixlevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                }
                else if (a >= 10 && a < 14){
                    brixlevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                }
                else if (a >= 14){
                    brixlevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                }

            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

/*            String resultstyledText = "Ripeness: <font color='#249023'>"+ Ma_Ripeness[maxPosRipeness] +"</font>";
            result.setText(Html.fromHtml(resultstyledText), TextView.BufferType.SPANNABLE);

            String sizestyledText = "Size: <font color='#249023'>"+ Ma_Size[maxPosSize] +"</font>";
            size.setText(Html.fromHtml(sizestyledText), TextView.BufferType.SPANNABLE);*/

            if (Ma_Ripeness[maxPosRipeness].equals("Ripe")){
                ripenesslevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
            }
            if (Ma_Ripeness[maxPosRipeness].equals("RipeW/Def")){
                ripenesslevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            }
            if (Ma_Ripeness[maxPosRipeness].equals("Rot")){
                ripenesslevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            if (Ma_Ripeness[maxPosRipeness].equals("Unripe")){
                ripenesslevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }

            if (Ma_Size[maxPosSize].equals("Small")){
                sizelevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
            if (Ma_Size[maxPosSize].equals("Medium")){
                sizelevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            if (Ma_Size[maxPosSize].equals("Large")){
                sizelevelAM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            }


            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(0);

            String a = df.format(confidencesripeness[maxPosRipeness] * 100);
            String b = df.format(confidencessize[maxPosSize] * 100);

            rcppercentage.setText(a +"%");
            scppercentage.setText(b +"%");
            ripebar.setProgress(Math.round(confidencesripeness[maxPosRipeness] * 100));
            sizebar.setProgress(Math.round(confidencessize[maxPosSize] * 100));

            // Releases model resources if no longer used.
            MaSize.close();
            MaRipeness.close();

        } catch (IOException e) {
            Log.d("Error: ","Error: "+e);
            Toast.makeText(this, "Error Occured!. Please Try Again Later!" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            } catch (Exception e){
                Toast.makeText(this, "Error: "+e, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            ContentResolver contentResolver = getContentResolver();
            try {
                if(Build.VERSION.SDK_INT < 28) {
                    Uri selectedImage = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                    imageView.setImageBitmap(bitmap);
                    bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);
                    classifyImage(bitmap);
                } else {
                    Uri selectedImage = data.getData();
                    ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, selectedImage);
                    bitmap = ImageDecoder.decodeBitmap(source);
                    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                    imageView.setImageBitmap(bitmap);
                    bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);
                    Bitmap softwareBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
                    classifyImage(softwareBitmap);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error: "+e, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}