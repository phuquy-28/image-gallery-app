package com.example.imagegallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    public static int currentImageCount = 0;
    public static final int IMAGE_BATCH_SIZE = 10;
    public static boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        firstLoadImages();
        handleScroll();
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