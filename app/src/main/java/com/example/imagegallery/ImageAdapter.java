package com.example.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<Image> imageList;

    public ImageAdapter(Context context) {
        this.context = context;
        this.imageList = new ArrayList<>();
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = imageList.get(position);
        if (image == null) return;
        Glide.with(context).load(image.getUrlImage()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.imageView);
        holder.tvName.setText(image.getName());
    }

    @Override
    public int getItemCount() {
        if (imageList != null) return imageList.size();
        else return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvName;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewItem);
            tvName = itemView.findViewById(R.id.textViewNameItem);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Image clickedDataItem = imageList.get(position);
                        String imageUrl = clickedDataItem.getUrlImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Intent intent = new Intent(context, FullScreenActivity.class);
                            intent.putExtra("URL", imageUrl);
                            context.startActivity(intent);
                        } else {
                            Log.e("ImageViewHolder", "Image URL is null or empty");
                        }
                    }
                }
            });
        }

    }
}
