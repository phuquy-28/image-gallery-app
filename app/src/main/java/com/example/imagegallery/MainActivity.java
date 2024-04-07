package com.example.imagegallery;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    public static int currentImageCount = 0;
    public static final int IMAGE_BATCH_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        imageAdapter = new ImageAdapter(this);
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("images");
//
//// 3. Sử dụng phương thức addValueEventListener
//        databaseReference.orderByKey().limitToFirst(IMAGE_BATCH_SIZE).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // 4. Lặp qua tất cả các mục con của DataSnapshot
//                List<Image> imageList = new ArrayList<>();
//                for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
//                    Image image = imageSnapshot.getValue(Image.class);
//                    imageList.add(image);
//                }
//
//                // 5. Cập nhật ImageAdapter
//                imageAdapter.setImageList(imageList);
//                imageAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Xử lý lỗi
//            }
//        });

        recyclerViewImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    new LoadImagesTask(imageAdapter).execute();
                }
            }
        });
        new LoadImagesTask(imageAdapter).execute();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewImage.setLayoutManager(gridLayoutManager);
        recyclerViewImage.setAdapter(imageAdapter);
    }

}