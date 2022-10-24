package com.henzmontera.cap102_plantapp.IPfragments;

// Developed by: John Henly A. Montera
// BSIT-4th-Year
// Cap102-Project

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.henzmontera.cap102_plantapp.R;
import com.henzmontera.cap102_plantapp.ml.MiIdentifier;
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

public class IDMangoFragment extends Fragment {

    ImageView imageViewMI,idAsMI,highPercMI,confidialogMI;
    Button buttonMI;
    StateProgressBar levelfruitMI;
    TextView highPercNameMI,highPercNoMI,CMpercNoMI,IMpercNoMI,AMpercNoMI;
    ProgressBar highpercbarMI,CMpercProgBarMI,IMpercProgBarMI,AMpercProgBarMI;
    int imageSize = 224;
    String[] MangoIdentifier = {"Carabao Mango","Indian Mango","Apple Mango"};
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_i_d_mango, container, false);

        // Initialization for ImageViews
        imageViewMI = rootview.findViewById(R.id.imageViewMI);
        idAsMI = rootview.findViewById(R.id.idAsMI);
        highPercMI = rootview.findViewById(R.id.highPercMI);
        confidialogMI = rootview.findViewById(R.id.confidialogMI);

        // Initialization for Button
        buttonMI = rootview.findViewById(R.id.buttonMI);

        // Initialization for StateProgressBar
        levelfruitMI = rootview.findViewById(R.id.levelfruitMI);

        // Initialization for TextView
        highPercNameMI = rootview.findViewById(R.id.highPercNameMI);
        highPercNoMI = rootview.findViewById(R.id.highPercNoMI);
        CMpercNoMI = rootview.findViewById(R.id.CMpercNoMI);
        IMpercNoMI = rootview.findViewById(R.id.IMpercNoMI);
        AMpercNoMI = rootview.findViewById(R.id.AMpercNoMI);

        // Initialization for ProgressBar
        highpercbarMI = rootview.findViewById(R.id.highpercbarMI);
        CMpercProgBarMI = rootview.findViewById(R.id.CMpercProgBarMI);
        IMpercProgBarMI = rootview.findViewById(R.id.IMpercProgBarMI);
        AMpercProgBarMI = rootview.findViewById(R.id.AMpercProgBarMI);

        // Set Gauge Meter
        levelfruitMI.setStateDescriptionData(MangoIdentifier);

        buttonMI.setOnClickListener(view -> {
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

        idAsMI.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Mango Classification");
            builder.setMessage(Html.fromHtml("Checking the " +
                    "<span style='color:#249023;'>\uD835\uDE3E\uD835\uDE61\uD835\uDE56\uD835\uDE68\uD835\uDE68\uD835\uDE5E\uD835\uDE5B\uD835\uDE5E\uD835\uDE58\uD835\uDE56\uD835\uDE69\uD835\uDE5E\uD835\uDE64\uD835\uDE63</span> of the <span style='color:#fcc603;'>\uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE41\uD835\uDE67\uD835\uDE6A\uD835\uDE5E\uD835\uDE69<span/> whether " +
                    "it is Apple Mango, Carabao Mango, or Indian Mango." +
                    "<br><br> <span style='color:#ee941e;'> 1 = \uD835\uDE3E\uD835\uDE56\uD835\uDE67\uD835\uDE56\uD835\uDE57\uD835\uDE56\uD835\uDE64 \uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64</span>" +
                    "<br> <span style='color:#2da71b;'> 2 = \uD835\uDE44\uD835\uDE63\uD835\uDE59\uD835\uDE5E\uD835\uDE56\uD835\uDE63 \uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64</span>" +
                    "<br> <span style='color:#cf1a1c;'> 3 = \uD835\uDE3C\uD835\uDE65\uD835\uDE65\uD835\uDE61\uD835\uDE5A \uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64</span>"));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        highPercMI.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Analyzer Confidence Level");
            builder.setMessage(Html.fromHtml("The output tells on how <span style='color:#249023;'>\uD835\uDE56\uD835\uDE58\uD835\uDE58\uD835\uDE6A\uD835\uDE67\uD835\uDE56\uD835\uDE69\uD835\uDE5A</span> " +
                    "the analyzer of the results given after the image undergo in process." +
                    "<br><br> <span style='color:#ee941e;'> \uD835\uDE42\uD835\uDE67\uD835\uDE5A\uD835\uDE56\uD835\uDE69\uD835\uDE5A\uD835\uDE67 \uD835\uDE69\uD835\uDE5D\uD835\uDE56\uD835\uDE63 85% = \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>" +
                    "<br> <span style='color:#2da71b;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE69\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE63 30% \uD835\uDE69\uD835\uDE64 84% = \uD835\uDE42\uD835\uDE64\uD835\uDE64\uD835\uDE59 \uD835\uDE40\uD835\uDE63\uD835\uDE64\uD835\uDE6A\uD835\uDE5C\uD835\uDE5D </span>" +
                    "<br> <span style='color:#cf1a1c;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE61\uD835\uDE64\uD835\uDE6C 29% = \uD835\uDE49\uD835\uDE64\uD835\uDE69 \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>"));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        confidialogMI.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Confidence Level per Class");
            builder.setMessage(Html.fromHtml("The output tells on how <span style='color:#249023;'>\uD835\uDE56\uD835\uDE58\uD835\uDE58\uD835\uDE6A\uD835\uDE67\uD835\uDE56\uD835\uDE69\uD835\uDE5A</span> " +
                    "the analyzer of the results given after the image undergo in process." +
                    "<br><br> <span style='color:#ee941e;'> \uD835\uDE42\uD835\uDE67\uD835\uDE5A\uD835\uDE56\uD835\uDE69\uD835\uDE5A\uD835\uDE67 \uD835\uDE69\uD835\uDE5D\uD835\uDE56\uD835\uDE63 85% = \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>" +
                    "<br> <span style='color:#2da71b;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE69\uD835\uDE6C\uD835\uDE5A\uD835\uDE5A\uD835\uDE63 30% \uD835\uDE69\uD835\uDE64 84% = \uD835\uDE42\uD835\uDE64\uD835\uDE64\uD835\uDE59 \uD835\uDE40\uD835\uDE63\uD835\uDE64\uD835\uDE6A\uD835\uDE5C\uD835\uDE5D </span>" +
                    "<br> <span style='color:#cf1a1c;'> \uD835\uDE3D\uD835\uDE5A\uD835\uDE61\uD835\uDE64\uD835\uDE6C 29% = \uD835\uDE49\uD835\uDE64\uD835\uDE69 \uD835\uDE3C\uD835\uDE58\uD835\uDE58\uD835\uDE5A\uD835\uDE65\uD835\uDE69\uD835\uDE56\uD835\uDE57\uD835\uDE61\uD835\uDE5A </span>" +
                    "<br><br> <span style='color:#ee941e;'> \uD835\uDE3E\uD835\uDE48\uD835\uDE3E% = \uD835\uDE3E\uD835\uDE56\uD835\uDE67\uD835\uDE56\uD835\uDE57\uD835\uDE56\uD835\uDE64 \uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE3E\uD835\uDE64\uD835\uDE63\uD835\uDE5B\uD835\uDE5E\uD835\uDE59\uD835\uDE5A\uD835\uDE63\uD835\uDE58\uD835\uDE5A% </span>" +
                    "<br> <span style='color:#2da71b;'> \uD835\uDE44\uD835\uDE48\uD835\uDE3E% = \uD835\uDE44\uD835\uDE63\uD835\uDE59\uD835\uDE5E\uD835\uDE56\uD835\uDE63 \uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE3E\uD835\uDE64\uD835\uDE63\uD835\uDE5B\uD835\uDE5E\uD835\uDE59\uD835\uDE5A\uD835\uDE63\uD835\uDE58\uD835\uDE5A% </span>" +
                    "<br> <span style='color:#cf1a1c;'> \uD835\uDE3C\uD835\uDE48\uD835\uDE3E% = \uD835\uDE3C\uD835\uDE65\uD835\uDE65\uD835\uDE61\uD835\uDE5A \uD835\uDE48\uD835\uDE56\uD835\uDE63\uD835\uDE5C\uD835\uDE64 \uD835\uDE3E\uD835\uDE64\uD835\uDE63\uD835\uDE5B\uD835\uDE5E\uD835\uDE59\uD835\uDE5A\uD835\uDE63\uD835\uDE58\uD835\uDE5A% </span>"));
            builder.setIcon(getActivity().getDrawable(R.drawable.info2));
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        return rootview;

    }

    public void classifyImage(Bitmap image){
        try {

            MiIdentifier MiId = MiIdentifier.newInstance(getContext());
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

            // Runs Size Model inference and gets result.
            MiIdentifier.Outputs outputssize = MiId.process(inputFeature0);
            TensorBuffer outputFeature0size = outputssize.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0size.getFloatArray();
            // find the index of the class with the biggest confidence of ripeness.
            int maxPos= 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            CMpercNoMI.setText(df.format(confidences[0] * 100) +"%");
            IMpercNoMI.setText(df.format(confidences[1] * 100) +"%");
            AMpercNoMI.setText(df.format(confidences[2] * 100) +"%");

            CMpercProgBarMI.setProgress(Math.round(confidences[0] * 100));
            IMpercProgBarMI.setProgress(Math.round(confidences[1] * 100));
            AMpercProgBarMI.setProgress(Math.round(confidences[2] * 100));

            if (MangoIdentifier[maxPos].equals("Carabao Mango")){
                levelfruitMI.setCurrentStateDescriptionColor(getResources().getColor(R.color.CM));
                levelfruitMI.setForegroundColor(getResources().getColor(R.color.CM));
                levelfruitMI.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                highPercNameMI.setText("CMC%");
                highpercbarMI.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_3));
                highpercbarMI.setProgress(Math.round(confidences[maxPos] * 100));
                highPercNoMI.setText(df.format(confidences[maxPos] * 100) +"%");
            }
            if (MangoIdentifier[maxPos].equals("Indian Mango")){
                levelfruitMI.setCurrentStateDescriptionColor(getResources().getColor(R.color.IM));
                levelfruitMI.setForegroundColor(getResources().getColor(R.color.IM));
                levelfruitMI.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                highPercNameMI.setText("IMC%");
                highpercbarMI.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_2));
                highpercbarMI.setProgress(Math.round(confidences[maxPos] * 100));
                highPercNoMI.setText(df.format(confidences[maxPos] * 100) +"%");
            }
            if (MangoIdentifier[maxPos].equals("Apple Mango")){
                levelfruitMI.setCurrentStateDescriptionColor(getResources().getColor(R.color.AM));
                levelfruitMI.setForegroundColor(getResources().getColor(R.color.AM));
                levelfruitMI.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                highPercNameMI.setText("AMC%");
                highpercbarMI.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bg));
                highpercbarMI.setProgress(Math.round(confidences[maxPos] * 100));
                highPercNoMI.setText(df.format(confidences[maxPos] * 100) +"%");
            }


            // Releases model resources if no longer used.
            MiId.close();

        } catch (IOException e) {
            Log.d("Error: ","Error: "+e);
            Toast.makeText(getContext(), "Error Occured!. Please Try Again Later!" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            try {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageViewMI.setImageBitmap(image);
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
                    imageViewMI.setImageBitmap(bitmap);
                    bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);
                    classifyImage(bitmap);
                } else {
                    Uri selectedImage = data.getData();
                    ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, selectedImage);
                    bitmap = ImageDecoder.decodeBitmap(source);
                    int dimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension);
                    imageViewMI.setImageBitmap(bitmap);
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