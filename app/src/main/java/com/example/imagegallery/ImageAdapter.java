package com.example.imagegallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<Image> imageList;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
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
        if (image == null)
            return;
        holder.imageView.setImageResource(image.getResourceImage());
        holder.tvName.setText(image.getName());
    }

    @Override
    public int getItemCount() {
        if (imageList != null)
            return imageList.size();
        else
            return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvName;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewItem);
            tvName = itemView.findViewById(R.id.textViewNameItem);
        }
    }
}
