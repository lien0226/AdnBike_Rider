package com.arife.adnbikerider.Utilitarios.Image;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arife.adnbikerider.AppData.Adapters.OnImageResponse;
import com.arife.adnbikerider.R;
import com.arife.adnbikerider.mvc.m.GroupModel;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProcessImg {
    private final int CODE_IMG_GALLERY = 1;
    private final String SAMPLE_CROPPED_IMG_NAME = "SampleCropImg";
    private Context context;
    private Activity activity;
    private RequestQueue request;
    private  OnImageResponse onImageResponse;

    public ProcessImg(Context context, Activity activity,OnImageResponse onImageResponse) {
        this.context = context;
        this.activity = activity;
        request = Volley.newRequestQueue(this.context);
        this.onImageResponse = onImageResponse;
    }

    public void starCrop(@NonNull Uri uri){
        String destinationFileName = SAMPLE_CROPPED_IMG_NAME;
        destinationFileName += ".jpg";

        UCrop ucrop = UCrop.of(uri, Uri.fromFile(new File(context.getCacheDir(),destinationFileName)));
        ucrop.withAspectRatio(1,1);
        //ucrop.withAspectRatio(3,4);
        //ucrop.useSourceImageAspectRatio();
        //ucrop.withAspectRatio(2,3);
        //ucrop.withAspectRatio(19,9);
        ucrop.withMaxResultSize(500, 500);
        ucrop.withOptions(this.getCropOptions());
        ucrop.start(this.activity);
    }

    private UCrop.Options getCropOptions(){
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(70);

        //Compress Type
        //options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        //options.setCompressionFormat(Bitmap.CompressFormat.JPEG);

        //UI
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);

        //Color
        options.setStatusBarColor(context.getResources().getColor(R.color.purple_700));
        options.setToolbarColor(context.getResources().getColor(R.color.purple_500));
        options.setToolbarTitle("Recortar Imagen");
        return options;
    }

    public void ObtenerImg(String rutaImg){
        ImageRequest imageRequest = new ImageRequest(rutaImg, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ProcessImg.this.onImageResponse.OnImage(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProcessImg.this.onImageResponse.OnErrorImage("Error al cargar la imagen");
            }
        });
        request.add(imageRequest);
    }

    public String ImgToString(Bitmap bitmap){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imageByte = array.toByteArray();
        String imageString = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return imageString;

    }
}
