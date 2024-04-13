package com.example.imagegallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    public static int currentImageCount = 1;
    public static final int IMAGE_BATCH_SIZE = 50;
    public static boolean isLoading = false;
    ImageButton btnGoUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        firstLoadImages();
        handleScroll();

        btnGoUpload = findViewById(R.id.btnGoUpload);
        btnGoUpload.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UploadActivity.class);
            startActivity(intent);
        });
    }

    private void firstLoadImages() {
        new LoadImagesTask(imageAdapter).execute();
    }

    private void initRecyclerView() {
        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        imageAdapter = new ImageAdapter(this);
        recyclerViewImage.setAdapter(imageAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewImage.setLayoutManager(gridLayoutManager);
    }

    private void handleScroll() {
        recyclerViewImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && lastVisibleItem + 6 > totalItemCount) {
                    isLoading = true;
                    new LoadImagesTask(imageAdapter).execute();
                }
            }
        });
    }
}