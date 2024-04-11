package com.example.imagegallery;

import static com.example.imagegallery.MainActivity.IMAGE_BATCH_SIZE;
import static com.example.imagegallery.MainActivity.currentImageCount;
import static com.example.imagegallery.MainActivity.isLoading;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LoadImagesTask extends AsyncTask<Void, Void, List<Image>> {
    private ImageAdapter imageAdapter;

    public LoadImagesTask(ImageAdapter imageAdapter) {
        this.imageAdapter = imageAdapter;
    }

    @Override
    protected List<Image> doInBackground(Void... voids) {
        List<Image> imageList = new ArrayList<>();
        imageList = loadImages();
        return imageList;
    }

    private List<Image> loadImages() {
        List<Image> imageList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
        CountDownLatch latch = new CountDownLatch(1);

        databaseReference.orderByKey().startAt(String.valueOf(currentImageCount)).limitToFirst(IMAGE_BATCH_SIZE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
                            Image image = imageSnapshot.getValue(Image.class);
                            imageList.add(image);
                        }
                        latch.countDown();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi
                        latch.countDown();
                    }
                });

        try {
            latch.await();  // Chờ cho đến khi dữ liệu từ Firebase được lấy xong
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return imageList;
    }

    @Override
    protected void onPostExecute(List<Image> imageList) {
        updateImageAdapter(imageList);

        if (imageList.size() == IMAGE_BATCH_SIZE) isLoading = false;  // Đã hết hình ảnh để tải

        notificationForUser(imageList);
        // Log thông báo
        Log.d("LoadImagesTask", "Loaded " + imageList.size() + " images, total: " + currentImageCount);
    }

    private void updateImageAdapter(List<Image> imageList) {
        imageAdapter.getImageList().addAll(imageList);
        imageAdapter.notifyDataSetChanged();
        currentImageCount += imageList.size();
    }

    private void notificationForUser(List<Image> imageList) {
        Toast.makeText(imageAdapter.getContext(), "Loaded " + imageList.size() + " images, total: " + (currentImageCount-1), Toast.LENGTH_SHORT).show();
    }
}