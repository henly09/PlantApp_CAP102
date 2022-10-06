package com.henzmontera.cap102_plantapp.IPfragments;

import android.Manifest;
import android.app.AlertDialog;
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
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.henzmontera.cap102_plantapp.R;
import com.henzmontera.cap102_plantapp.ml.CmRipenessSorter;
import com.henzmontera.cap102_plantapp.ml.CmSizeSorter;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class CMfragment extends Fragment {

    TextView /*result, size, brixlevel, */rcppercentage,scppercentage;
    StateProgressBar ripenesslevelCM,sizelevelCM,brixlevelCM;
    ImageView imageView,ripdialogCM,confidialogCM,sizedialogCM,brixdialogCM;
    ProgressBar ripebar,sizebar;
    Button picture, addingbrix/*, RecAndProdCm*/;
    int imageSize = 224/*, notifBadgeCM = 0*/;
    private String m_Text = "";
    /*    NotificationBadge notificationBadgeCM;*/
    String[] Cm_Ripeness = {"Ripe","RipeW/Def","Rot","Unripe"};
    String[] Cm_Ripeness_reverse = {"Unripe","Rot","RipeW/Def","Ripe"};
    String[] Cm_Size = {"Large","Medium","Small"};
    String[] Cm_Size_reverse = {"Small","Medium","Large"};
    String[] Cm_Brixlevel = {"Sour","B Sweet","Sweet","P Sweet","V Sweet"};

    /*    int CMbrix;
        String CMsize,CMripeness;*/
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_carabao_mango, container, false);

/*              result = findViewById(R.id.classifiedCM);
                size = findViewById(R.id.SizesCM);
                brixlevel = findViewById(R.id.brixlevelsCM);
                notificationBadgeCM = findViewById(R.id.badgeCM);
                RecAndProdCm = findViewById(R.id.RecAndProdCM);         */

        imageView = rootview.findViewById(R.id.imageViewCM);
        picture = rootview.findViewById(R.id.buttonCM);
        addingbrix = rootview.findViewById(R.id.addingbrixCM);

        // initialization for progressbar
        ripebar = rootview.findViewById(R.id.rcpbarCM);
        sizebar = rootview.findViewById(R.id.scpbarCM);
        rcppercentage = rootview.findViewById(R.id.ripecpercentageCM);
        scppercentage = rootview.findViewById(R.id.sizecpercentageCM);

        // initialization for gauges
        ripenesslevelCM = rootview.findViewById(R.id.ripenesslevelCM);
        sizelevelCM = rootview.findViewById(R.id.sizelevelCM);
        brixlevelCM = rootview.findViewById(R.id.brixlevelCM);

        // set dialog box
        ripdialogCM = rootview.findViewById(R.id.ripdialogCM);
        confidialogCM = rootview.findViewById(R.id.confidialogCM);
        sizedialogCM = rootview.findViewById(R.id.sizedialogCM);
        brixdialogCM = rootview.findViewById(R.id.brixdialogCM);

        // set gauge meter description
        ripenesslevelCM.setStateDescriptionData(Cm_Ripeness_reverse);
        sizelevelCM.setStateDescriptionData(Cm_Size_reverse);
        brixlevelCM.setStateDescriptionData(Cm_Brixlevel);

        ripdialogCM.setOnClickListener(view-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Ripeness Level");
            builder.setMessage(Html.fromHtml("Checking the " +
                    "<span style='color:#249023;'>\uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A\uD835\uDE63\uD835\uDE5A\uD835\uDE68\uD835\uDE68</span> of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> whether " +
                    "it is unripe, rot, ripe, ripe with defect." +
                    "<br><br> <span style='color:#ec941c;'> 1 = \uD835\uDE50\uD835\uDE63\uD835\uDE67\uD835\uDE5E\uD835\uDE65\uD835\uDE5A</span>" +
                    "<br> <span style='color:#ec941c;'> 2 = \uD835\uDE4D\uD835\uDE64\uD835\uDE69</span>" +
                    "<br> <span style='color:#ec941c;'> 3 = \uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A \uD835\uDE6C\uD835\uDE5E\uD835\uDE69\uD835\uDE5D \uD835\uDE3F\uD835\uDE5A\uD835\uDE5B\uD835\uDE5A\uD835\uDE58\uD835\uDE69</span>" +
                    "<br> <span style='color:#ec941c;'> 4 = \uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A</span>"));            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        confidialogCM.setOnClickListener(view-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Analyzer Confidence Level");
            builder.setMessage(Html.fromHtml("The output tells on how <span style='color:#249023;'>\uD835\uDE56\uD835\uDE58\uD835\uDE58\uD835\uDE6A\uD835\uDE67\uD835\uDE56\uD835\uDE69\uD835\uDE5A</span> " +
                    "the analyzer of the results given after the image undergo in process." +
                    "<br><br> <span style='color:#ec941c;'> \uD835\uDE42\uD835\uDE67\uD835\uDE5A\uD835\uDE56\uD835\uDE69\uD835\uDE5A\uD835\uDE67 \uD835\uDE69\uD835\uDE5D\uD835\uDE56\uD835\uDE63 85% = \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>" +
                    "<br> <span style='color:#ec941c;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE69\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE63 30% \uD835\uDE69\uD835\uDE64 84% = \uD835\uDE42\uD835\uDE64\uD835\uDE64\uD835\uDE59 \uD835\uDE40\uD835\uDE63\uD835\uDE64\uD835\uDE6A\uD835\uDE5C\uD835\uDE5D </span>" +
                    "<br> <span style='color:#ec941c;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE61\uD835\uDE64\uD835\uDE6C 29% = \uD835\uDE49\uD835\uDE64\uD835\uDE69 \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>"));            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        sizedialogCM.setOnClickListener(view-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Size Level");
            builder.setMessage(Html.fromHtml("Checking the size of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> whether it is Large, Medium, or Small" +
                    "<br><br> <span style='color:#ec941c;'> \uD835\uDFED = \uD835\uDDE6\uD835\uDDFA\uD835\uDDEE\uD835\uDDF9\uD835\uDDF9 , <br>Ave.Length: 5.2cm ; <br>Ave.Diameter: 3.2cm ; <br>Ave.Circumference: 10.1cm ;<br></span>" +
                    "<br> <span style='color:#ec941c;'> \uD835\uDFEE = \uD835\uDDE0\uD835\uDDF2\uD835\uDDF1\uD835\uDDF6\uD835\uDE02\uD835\uDDFA , <br>Ave. Length: 8.5cm ; <br>Ave. Diameter: 4.6cm ; <br>Ave.Circumference: 14.4cm ;<br></span>" +
                    "<br> <span style='color:#ec941c;'> \uD835\uDFEF = \uD835\uDDDF\uD835\uDDEE\uD835\uDDFF\uD835\uDDF4\uD835\uDDF2 , <br>Ave. Length: 10.9cm ; <br>Ave. Diameter: 6.8cm ; <br>Ave.Circumference: 21.36cm ;<br></span>"));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        brixdialogCM.setOnClickListener(view-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Brix Level");
            builder.setMessage(Html.fromHtml("Checking the <span style='color:#249023;'>\uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69\uD835\uDE63\uD835\uDE5A\uD835\uDE68\uD835\uDE68</span> of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> by putting the result percentage from the refractometer." +
                    "<br><br> <span style='color:#ec941c;'> 1 / \uD835\uDE4E\uD835\uDE64\uD835\uDE6A\uD835\uDE67 = \uD835\uDE4E\uD835\uDE64\uD835\uDE6A\uD835\uDE67 </span>" +
                    "<br> <span style='color:#ec941c;'> 2 / \uD835\uDE3D \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDD5\uD835\uDDEE\uD835\uDDFF\uD835\uDDF2\uD835\uDDF9\uD835\uDE06 \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>" +
                    "<br> <span style='color:#ec941c;'> 3 / \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>" +
                    "<br> <span style='color:#ec941c;'> 4 / \uD835\uDE4B \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDE3\uD835\uDDF2\uD835\uDDFF\uD835\uDDF3\uD835\uDDF2\uD835\uDDF0\uD835\uDE01 \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>" +
                    "<br> <span style='color:#ec941c;'> 5 / \uD835\uDE51 \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDE9\uD835\uDDF2\uD835\uDDFF\uD835\uDE06 \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>"));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        picture.setOnClickListener(view -> {
            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose an Action");
            builder.setItems(options, (dialog, item) -> {
                if (options[item].equals("Take Photo"))
                {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Input Brix Percentage");
            builder.setMessage("Put the percentage of the output of \nthe refractometer which using brix meter.");
            // Set up the input
            final EditText input = new EditText(getContext());
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("OK", (dialog, which) -> {
                m_Text = input.getText().toString();
                int a = Integer.parseInt(m_Text);
                String b = "";
                // https://www.healthy-vegetable-gardening.com/brix-scale.html
                if (a < 4){
                    brixlevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                }
                else if (a >= 4 && a < 6){
                    brixlevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                }
                else if (a >= 6 && a < 10){
                    brixlevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                }
                else if (a >= 10 && a < 14){
                    brixlevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                }
                else if (a >= 14){
                    brixlevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        return rootview;
    }

    public void classifyImage(Bitmap image){ // Add model and add text files

        try {

            CmRipenessSorter CmRipeness = CmRipenessSorter.newInstance(getContext());
            CmSizeSorter CmSize = CmSizeSorter.newInstance(getContext());

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
            CmRipenessSorter.Outputs outputsripness = CmRipeness.process(inputFeature0);
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

            CmSizeSorter.Outputs outputssize = CmSize.process(inputFeature0);
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

/*            String resultstyledText = "Ripeness: <font color='#249023'>"+ Cm_Ripeness[maxPosRipeness] +"</font>";
            result.setText(Html.fromHtml(resultstyledText), TextView.BufferType.SPANNABLE);

            String sizestyledText = "Size: <font color='#249023'>"+ Cm_Size[maxPosSize] +"</font>";
            size.setText(Html.fromHtml(sizestyledText), TextView.BufferType.SPANNABLE);*/

            if (Cm_Ripeness[maxPosRipeness].equals("Ripe")){
                ripenesslevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
            }
            if (Cm_Ripeness[maxPosRipeness].equals("RipeW/Def")){
                ripenesslevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            }
            if (Cm_Ripeness[maxPosRipeness].equals("Rot")){
                ripenesslevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            if (Cm_Ripeness[maxPosRipeness].equals("Unripe")){
                ripenesslevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }

            if (Cm_Size[maxPosSize].equals("Small")){
                sizelevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
            if (Cm_Size[maxPosSize].equals("Medium")){
                sizelevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }
            if (Cm_Size[maxPosSize].equals("Large")){
                sizelevelCM.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
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
            CmRipeness.close();
            CmSize.close();
        } catch (IOException e) {
            Log.d("Error: ","Error: "+e);
            Toast.makeText(getContext(), "Error Occured!. Please Try Again Later!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            } catch (Exception e){
                Toast.makeText(getContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
            }

        }
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Bitmap bitmap = null;
            ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
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
                Toast.makeText(getContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


}