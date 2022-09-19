package com.henzmontera.cap102_plantapp;

// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
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

import com.henzmontera.cap102_plantapp.ml.MangoIndianRipenessSorter;
import com.henzmontera.cap102_plantapp.ml.MangoIndianSizeSorter;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.nex3z.notificationbadge.NotificationBadge;

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

public class IndianMangoActivity extends AppCompatActivity {

    TextView result,size, brixlevel, firmlevel,rcppercentage,scppercentage;
    StateProgressBar ripenesslevelIM,sizelevelIM,brixlevelIM,penelevelIM;
    ImageView imageView;
    ProgressBar ripebar,sizebar;
    Button picture, addingbrix, RecAndProdIM, addingfirm;
    int imageSize = 224/*, notifBadgeIM = 0*/;
    private String m_Text = "";
    NotificationBadge notificationBadgeIM;
    String[] Mi_Ripeness = {"Ripe","RipeW/Def","Rot","Unripe"};
    String[] Mi_Ripeness_reverse = {"Unripe","Rot","RipeW/Def","Ripe"};
    String[] Mi_Size = {"Large","Medium","Small"};
    String[] Mi_Size_reverse = {"Small","Medium","Large"};
    String[] Mi_Brixlevel = {"Sour","B Sweet","Sweet","P Sweet","V Sweet"};
    String[] Mi_PeneLevel = {"U Firm","BR Firm","B Soft","Ripe Firm","PR Firm"};

/*    int IMbrix;
    String IMsize,IMripeness;*/

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_mango);

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
        setTitle("INDIAN MANGO");

        result = findViewById(R.id.classifiedIM);
        rcppercentage = findViewById(R.id.ripecpercentageIM);
        scppercentage = findViewById(R.id.sizecpercentageIM);
        imageView = findViewById(R.id.imageViewIM);
        picture = findViewById(R.id.buttonIM);
        size = findViewById(R.id.SizesIM);
        addingbrix = findViewById(R.id.addingbrixIM);
        brixlevel = findViewById(R.id.brixlevelsIM);
        notificationBadgeIM = findViewById(R.id.badgeIM);
        RecAndProdIM = findViewById(R.id.recAndProdIM);
        addingfirm = findViewById(R.id.addfirmIM);
        firmlevel = findViewById(R.id.firmlevelIM);
        ripebar = findViewById(R.id.rcpbarIM);
        sizebar = findViewById(R.id.scpbarIM);
        ripenesslevelIM = findViewById(R.id.ripenesslevelIM);
        sizelevelIM = findViewById(R.id.sizelevelIM);
        brixlevelIM = findViewById(R.id.brixlevelIM);
        penelevelIM = findViewById(R.id.penelevelIM);

        ripenesslevelIM.setStateDescriptionData(Mi_Ripeness_reverse);
        sizelevelIM.setStateDescriptionData(Mi_Size_reverse);
        brixlevelIM.setStateDescriptionData(Mi_Brixlevel);
        penelevelIM.setStateDescriptionData(Mi_PeneLevel);

        picture.setOnClickListener(view -> {
            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(IndianMangoActivity.this);
            builder.setTitle("Choose an Action");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
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
                        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                    else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
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
                    brixlevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                }
                else if (a >= 4 && a < 6){
                    brixlevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                }
                else if (a >= 6 && a < 10){
                    brixlevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                }
                else if (a >= 10 && a < 14){
                    brixlevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                }
                else if (a >= 14){
                    brixlevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        // firmness of the fruit can be determine the ripeness also of the fruit by using penetrometer.
        addingfirm.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Input Firmness Percentage (Inner Scale)");
            builder.setMessage("Put the percentage of the output of the Penetrometer which using Firmness meter.");
            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("OK", (dialog, which) -> {
                m_Text = input.getText().toString();
                int a = Integer.parseInt(m_Text);
                // https://www.mango.org/wp-content/uploads/2017/10/Mango_Maturity_And_Ripeness_Guide.pdf
                String b = "";
                if (a > 20){
                    penelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                }
                else if (a <= 20 && a >= 16){
                    penelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                }
                else if (a <= 15 && a >= 11){
                    penelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                }
                else if (a <= 10 && a >= 6){
                    penelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                }
                else if (a <= 5 && a >= 1){
                    penelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                }
                String resultstyledText = "Firm Level: <font color='#249023'>"+ b +"</font>";
                firmlevel.setText(Html.fromHtml(resultstyledText), TextView.BufferType.SPANNABLE);
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

            String resultstyledText = "Ripeness: <font color='#249023'>"+ Mi_Ripeness[maxPosRipeness] +"</font>";
            result.setText(Html.fromHtml(resultstyledText), TextView.BufferType.SPANNABLE);

            String sizestyledText = "Size: <font color='#249023'>"+ Mi_Size[maxPosSize] +"</font>";
            size.setText(Html.fromHtml(sizestyledText), TextView.BufferType.SPANNABLE);

            if (Mi_Ripeness[maxPosRipeness].equals("Ripe")){
                ripenesslevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
            }
            if (Mi_Ripeness[maxPosRipeness].equals("RipeW/Def")){
                ripenesslevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            }
            if (Mi_Ripeness[maxPosRipeness].equals("Rot")){
                ripenesslevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            if (Mi_Ripeness[maxPosRipeness].equals("Unripe")){
                ripenesslevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }

            if (Mi_Size[maxPosSize].equals("Small")){
                sizelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
            if (Mi_Size[maxPosSize].equals("Medium")){
                sizelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            if (Mi_Size[maxPosSize].equals("Large")){
                sizelevelIM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
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
            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            } catch (Exception e) {
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