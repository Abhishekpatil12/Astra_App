package com.example.astraapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageViewer extends AppCompatActivity {

    String imgPath;
    private ImageView imageView;
//    private ScaleGestureDetector scaleGestureDetector;

    // on below line we are defining our scale factor.
//    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        PhotoView fullImageView = findViewById(R.id.fullImageView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        ImageButton closeButton = findViewById(R.id.closeButton);

        imgPath = getIntent().getStringExtra("imageurl");


        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(imgPath)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(fullImageView);

        closeButton.setOnClickListener(v -> finish());



        // on below line we are initializing our scale gesture detector for zoom in and out for our image.
//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
//
//        // on below line we are getting our image file from its path.
//        //File imgFile = new File(imgPath);
//
//        // if the file exists then we are loading that image in our image view.
//
//            Picasso.get().load(imgPath).placeholder(R.drawable.ic_launcher_background).into(imageView);

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        scaleGestureDetector.onTouchEvent(event);
//        return true;
//    }
//
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
//
//            // inside on scale method we are setting scale
//            // for our image in our image view.
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
//
//            // on below line we are setting
//            // scale x and scale y to our image view.
//            imageView.setScaleX(mScaleFactor);
//            imageView.setScaleY(mScaleFactor);
//            return true;
//        }
//    }
}