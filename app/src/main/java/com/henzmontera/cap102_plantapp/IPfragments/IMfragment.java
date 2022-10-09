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
import com.henzmontera.cap102_plantapp.ml.MangoIndianRipenessSorter;
import com.henzmontera.cap102_plantapp.ml.MangoIndianSizeSorter;
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

public class IMfragment extends Fragment {

    TextView /*result,size, brixlevel, firmlevel,*/rcppercentage,scppercentage;
    StateProgressBar ripenesslevelIM,sizelevelIM,brixlevelIM,penelevelIM;
    ImageView imageView,ripdialogIM,confidialogIM,sizedialogIM,brixdialogIM,firmdialogIM;
    ProgressBar ripebar,sizebar;
    Button picture, addingbrix, /*RecAndProdIM,*/ addingfirm;
    int imageSize = 224/*, notifBadgeIM = 0*/;
    private String m_Text = "";
    /*NotificationBadge notificationBadgeIM;*/
    String[] Mi_Ripeness = {"Ripe","RipeW/Def","Rot","Unripe"};
    String[] Mi_Ripeness_reverse = {"Unripe","Rot","RipeW/Def","Ripe"};
    String[] Mi_Size = {"Large","Medium","Small"};
    String[] Mi_Size_reverse = {"Small","Medium","Large"};
    String[] Mi_Brixlevel = {"Sour","B Sweet","Sweet","P Sweet","V Sweet"};
    String[] Mi_PeneLevel = {"U Firm","Ex Firm","B Soft","Ripe Firm","PR Firm"};
    View rootview;

    /*    int IMbrix;
    String IMsize,IMripeness;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_indian_mango, container, false);

        /*        result = findViewById(R.id.classifiedIM);
          brixlevel = findViewById(R.id.brixlevelsIM);
          size = findViewById(R.id.SizesIM);
          RecAndProdIM = findViewById(R.id.recAndProdIM);
          notificationBadgeIM = findViewById(R.id.badgeIM);
          firmlevel = findViewById(R.id.firmlevelIM);       */

        imageView = rootview.findViewById(R.id.imageViewIM);
        picture = rootview.findViewById(R.id.buttonIM);

        // initialization for button add brix and penetrometer
        addingbrix = rootview.findViewById(R.id.addingbrixIM);
        addingfirm = rootview.findViewById(R.id.addfirmIM);

        // initialization for progressbar
        ripebar = rootview.findViewById(R.id.rcpbarIM);
        sizebar = rootview.findViewById(R.id.scpbarIM);
        rcppercentage = rootview.findViewById(R.id.ripecpercentageIM);
        scppercentage = rootview.findViewById(R.id.sizecpercentageIM);

        // initialization for gauges
        ripenesslevelIM = rootview.findViewById(R.id.ripenesslevelIM);
        sizelevelIM = rootview.findViewById(R.id.sizelevelIM);
        brixlevelIM = rootview.findViewById(R.id.brixlevelIM);
        penelevelIM = rootview.findViewById(R.id.penelevelIM);

        // set dialog box
        ripdialogIM = rootview.findViewById(R.id.ripdialogIM);
        confidialogIM = rootview.findViewById(R.id.confidialogIM);
        sizedialogIM = rootview.findViewById(R.id.sizedialogIM);
        brixdialogIM = rootview.findViewById(R.id.brixdialogIM);
        firmdialogIM = rootview.findViewById(R.id.firmdialogIM);

        // set gauge meter description
        ripenesslevelIM.setStateDescriptionData(Mi_Ripeness_reverse);
        sizelevelIM.setStateDescriptionData(Mi_Size_reverse);
        brixlevelIM.setStateDescriptionData(Mi_Brixlevel);
        penelevelIM.setStateDescriptionData(Mi_PeneLevel);

        ripdialogIM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Ripeness Level");
            builder.setMessage(Html.fromHtml("Checking the " +
                    "<span style='color:#249023;'>\uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A\uD835\uDE63\uD835\uDE5A\uD835\uDE68\uD835\uDE68</span> of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> whether " +
                    "it is unripe, rot, ripe, ripe with defect." +
                    "<br><br> <span style='color:#249023;'> 1 = \uD835\uDE50\uD835\uDE63\uD835\uDE67\uD835\uDE5E\uD835\uDE65\uD835\uDE5A</span>" +
                    "<br> <span style='color:#249023;'> 2 = \uD835\uDE4D\uD835\uDE64\uD835\uDE69</span>" +
                    "<br> <span style='color:#249023;'> 3 = \uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A \uD835\uDE6C\uD835\uDE5E\uD835\uDE69\uD835\uDE5D \uD835\uDE3F\uD835\uDE5A\uD835\uDE5B\uD835\uDE5A\uD835\uDE58\uD835\uDE69</span>" +
                    "<br> <span style='color:#249023;'> 4 = \uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A</span>"));            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        confidialogIM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Analyzer Confidence Level");
            builder.setMessage(Html.fromHtml("The output tells on how <span style='color:#249023;'>\uD835\uDE56\uD835\uDE58\uD835\uDE58\uD835\uDE6A\uD835\uDE67\uD835\uDE56\uD835\uDE69\uD835\uDE5A</span> " +
                    "the analyzer of the results given after the image undergo in process." +
                    "<br><br> <span style='color:#249023;'> \uD835\uDE42\uD835\uDE67\uD835\uDE5A\uD835\uDE56\uD835\uDE69\uD835\uDE5A\uD835\uDE67 \uD835\uDE69\uD835\uDE5D\uD835\uDE56\uD835\uDE63 85% = \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE69\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE63 30% \uD835\uDE69\uD835\uDE64 84% = \uD835\uDE42\uD835\uDE64\uD835\uDE64\uD835\uDE59 \uD835\uDE40\uD835\uDE63\uD835\uDE64\uD835\uDE6A\uD835\uDE5C\uD835\uDE5D </span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE61\uD835\uDE64\uD835\uDE6C 29% = \uD835\uDE49\uD835\uDE64\uD835\uDE69 \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>"));            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        sizedialogIM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Size Level");
            builder.setMessage(Html.fromHtml("Checking the size of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> whether it is Large, Medium, or Small" +
                    "<br><br> <span style='color:#249023;'> \uD835\uDFED = \uD835\uDDE6\uD835\uDDFA\uD835\uDDEE\uD835\uDDF9\uD835\uDDF9 , <br>Ave.Length: 6cm ; <br>Ave.Diameter: 4.3cm ; <br>Ave.Circumference: 13.5cm ;<br></span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDFEE = \uD835\uDDE0\uD835\uDDF2\uD835\uDDF1\uD835\uDDF6\uD835\uDE02\uD835\uDDFA , <br>Ave. Length: 11cm ; <br>Ave. Diameter: 7.8cm ; <br>Ave.Circumference: 25cm ;<br></span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDFEF = \uD835\uDDDF\uD835\uDDEE\uD835\uDDFF\uD835\uDDF4\uD835\uDDF2 , <br>Ave. Length: 15cm ; <br>Ave. Diameter: 11cm ; <br>Ave.Circumference: 34.5cm ;<br></span>"));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        brixdialogIM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Brix Level");
            builder.setMessage(Html.fromHtml("Checking the <span style='color:#249023;'>\uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69\uD835\uDE63\uD835\uDE5A\uD835\uDE68\uD835\uDE68</span> of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> by putting the result percentage from the refractometer." +
                    "<br><br> <span style='color:#249023;'> 1 / \uD835\uDE4E\uD835\uDE64\uD835\uDE6A\uD835\uDE67 = \uD835\uDE4E\uD835\uDE64\uD835\uDE6A\uD835\uDE67 </span>" +
                    "<br> <span style='color:#249023;'> 2 / \uD835\uDE3D \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDD5\uD835\uDDEE\uD835\uDDFF\uD835\uDDF2\uD835\uDDF9\uD835\uDE06 \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>" +
                    "<br> <span style='color:#249023;'> 3 / \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>" +
                    "<br> <span style='color:#249023;'> 4 / \uD835\uDE4B \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDE3\uD835\uDDF2\uD835\uDDFF\uD835\uDDF3\uD835\uDDF2\uD835\uDDF0\uD835\uDE01 \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>" +
                    "<br> <span style='color:#249023;'> 5 / \uD835\uDE51 \uD835\uDE4E\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE69 = \uD835\uDDE9\uD835\uDDF2\uD835\uDDFF\uD835\uDE06 \uD835\uDDE6\uD835\uDE04\uD835\uDDF2\uD835\uDDF2\uD835\uDE01</span>"));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        firmdialogIM.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Firm Level");
            builder.setMessage(Html.fromHtml("Checking the <span style='color:#249023;'>\uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62\uD835\uDE63\uD835\uDE5A\uD835\uDE68\uD835\uDE68</span> of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> by putting the result percentage from the penetrometer." +
                    "<br><br> <span style='color:#249023;'> \uD835\uDE50\uD835\uDE63\uD835\uDE67\uD835\uDE5E\uD835\uDE65\uD835\uDE5A \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62 \uD835\uDE64\uD835\uDE67 1 / \uD835\uDE50 \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62</span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDE40\uD835\uDE6D\uD835\uDE69\uD835\uDE67\uD835\uDE56 \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62 \uD835\uDE64\uD835\uDE67 2 / \uD835\uDE40\uD835\uDE6D \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62</span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDE3D\uD835\uDE56\uD835\uDE67\uD835\uDE5A\uD835\uDE61\uD835\uDE6E \uD835\uDE4E\uD835\uDE64\uD835\uDE5B\uD835\uDE69 \uD835\uDE64\uD835\uDE67 3 / \uD835\uDE3D \uD835\uDE4E\uD835\uDE64\uD835\uDE5B\uD835\uDE69</span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62 \uD835\uDE64\uD835\uDE67 4 / \uD835\uDE4D\uD835\uDE5E\uD835\uDE65\uD835\uDE5A \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62</span>" +
                    "<br> <span style='color:#249023;'> \uD835\uDE4B\uD835\uDE5A\uD835\uDE67\uD835\uDE5B\uD835\uDE5A\uD835\uDE58\uD835\uDE69 \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62 \uD835\uDE64\uD835\uDE67 5 / \uD835\uDE4B\uD835\uDE4D \uD835\uDE41\uD835\uDE5E\uD835\uDE67\uD835\uDE62</span>"));
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
            builder.setMessage("Put the percentage of the output of the refractometer which using brix meter.");
            // Set up the input
            final EditText input = new EditText(getContext());
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Input Firmness Percentage (Inner Scale)");
            builder.setMessage("Put the percentage of the output of the Penetrometer which using Firmness meter.");
            // Set up the input
            final EditText input = new EditText(getContext());
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("OK", (dialog, which) -> {
                m_Text = input.getText().toString();
                int a = Integer.parseInt(m_Text);
                // https://www.mango.org/wp-content/uploads/2017/10/Mango_Maturity_And_Ripeness_Guide.pdf
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
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        // Inflate the layout for this fragment
        return rootview;
    }


    public void classifyImage(Bitmap image){ // Add model and add text files

        try {

            MangoIndianRipenessSorter MiRipeness = MangoIndianRipenessSorter.newInstance(getContext());
            MangoIndianSizeSorter MiSize = MangoIndianSizeSorter.newInstance(getContext());

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

/*            String resultstyledText = "Ripeness: <font color='#249023'>"+ Mi_Ripeness[maxPosRipeness] +"</font>";
            result.setText(Html.fromHtml(resultstyledText), TextView.BufferType.SPANNABLE);

            String sizestyledText = "Size: <font color='#249023'>"+ Mi_Size[maxPosSize] +"</font>";
            size.setText(Html.fromHtml(sizestyledText), TextView.BufferType.SPANNABLE);*/

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
            df.setMaximumFractionDigits(2);

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
            } catch (Exception e) {
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