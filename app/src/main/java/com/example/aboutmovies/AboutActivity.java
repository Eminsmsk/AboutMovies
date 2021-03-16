package com.example.aboutmovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbarAbout;
    private ImageView imageViewBackAbout;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbarAbout = findViewById(R.id.toolbarAbout);
        imageViewBackAbout = findViewById(R.id.imageViewBackAbout);
        toolbarAbout.setTitle("About Developer");
        toolbarAbout.setTitleMargin(200, 0, 0, 0);
        toolbarAbout.setTitleTextColor(getResources().getColor(R.color.myPurple));

        imageViewBackAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}
