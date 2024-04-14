package com.example.imagegallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.imagegallery.MainActivity.isLoading;

import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity {
    ImageView imageView;
    Button buttonBack;
    Button buttonChoose;
    Button buttonUpload;
    private Uri imageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    ProgressBar progressBar;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = findViewById(R.id.imageView);
        buttonBack = findViewById(R.id.buttonBack);
        buttonChoose = findViewById(R.id.buttonChooseImage);
        buttonUpload = findViewById(R.id.buttonUploadImage);
        progressBar = findViewById(R.id.progressBar);

        buttonBack.setOnClickListener(v -> {
            finish();
        });

        buttonChoose.setOnClickListener(v -> {
            chooseImage();
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("images");

        buttonUpload.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            uploadImage();
        });
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadImage() {
        if (imageView.getDrawable() != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String urlImage = uri.toString();
                                    String imageName = fileReference.getName();
                                    Image upload = new Image(imageName, urlImage);

                                    DatabaseReference indexRef = FirebaseDatabase.getInstance().getReference("images");
                                    indexRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            ArrayList list;
                                            long index;
                                            if (dataSnapshot.getValue() != null) {
                                                list = (ArrayList) dataSnapshot.getValue();
                                                index = list.size()-1;
                                            } else {
                                                index = 0;
                                            }
                                            index++;
                                            mDatabaseRef.child(String.valueOf(index)).setValue(upload);
                                            isLoading = false;
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Log the error
                                            Log.e("Upload error", databaseError.getMessage(), databaseError.toException());
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Log the error
                            Log.e("Upload error", e.getMessage(), e);
                        }
                    });
        }
    }
}