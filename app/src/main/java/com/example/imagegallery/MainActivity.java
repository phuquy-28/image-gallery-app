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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        recyclerViewImage = findViewById(R.id.recyclerViewImage);
        imageAdapter = new ImageAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewImage.setLayoutManager(gridLayoutManager);

        imageAdapter.setImageList(getListImage());
        recyclerViewImage.setAdapter(imageAdapter);
    }

    private List<Image> getListImage() {
        List<Image> imageList = new ArrayList<>();
        imageList.add(new Image(R.drawable.image1, "Image 1"));
        imageList.add(new Image(R.drawable.image2, "Image 2"));
        imageList.add(new Image(R.drawable.image3, "Image 3"));
        imageList.add(new Image(R.drawable.image4, "Image 4"));

        imageList.add(new Image(R.drawable.image1, "Image 1"));
        imageList.add(new Image(R.drawable.image2, "Image 2"));
        imageList.add(new Image(R.drawable.image3, "Image 3"));
        imageList.add(new Image(R.drawable.image4, "Image 4"));

        imageList.add(new Image(R.drawable.image1, "Image 1"));
        imageList.add(new Image(R.drawable.image2, "Image 2"));
        imageList.add(new Image(R.drawable.image3, "Image 3"));
        imageList.add(new Image(R.drawable.image4, "Image 4"));

        imageList.add(new Image(R.drawable.image1, "Image 1"));
        imageList.add(new Image(R.drawable.image2, "Image 2"));
        imageList.add(new Image(R.drawable.image3, "Image 3"));
        imageList.add(new Image(R.drawable.image4, "Image 4"));
        return imageList;
    }
}