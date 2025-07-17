package gabi.low.findmydog;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import java.util.ArrayList;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogsViewHolder> {
    private ArrayList<DogsClass> dataList;
    private Context context;
    public DogsAdapter(Context context, ArrayList<DogsClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    private void showImageDialog(DogsClass dog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_dog_image, null);
        ImageView imageView = dialogView.findViewById(R.id.dogImageView);

        if (dog.getImageUrl() != null && !dog.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(dog.getImageUrl())
                    .placeholder(R.drawable.female)
                    .into(imageView);
        } else {
            imageView.setImageResource(dog.getImageResource());
        }

        builder.setView(dialogView);
        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
        builder.show();
    }



    @NonNull
    @Override
    public DogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new DogsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsViewHolder holder, int position) {
        DogsClass currentItem = dataList.get(position);
        holder.ivDogImage.setImageResource(currentItem.getImageResource());
        holder.tvName.setText(currentItem.getName());
        holder.tvBreed.setText(currentItem.getBreed());
        if (currentItem.isFavorite()) {
            holder.ivFavorite.setImageResource(R.drawable.full);}
        else
            holder.ivFavorite.setImageResource(R.drawable.empty); // empty heart

        holder.ivFavorite.setOnClickListener(v -> {
            boolean currentState = currentItem.isFavorite();
            currentItem.setFavorite(!currentState);
            notifyItemChanged(position); // refresh the item to show new image
        });
        holder.expandButton.setOnClickListener(v -> {
            showImageDialog(currentItem);
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public  class  DogsViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvName;
        public final TextView tvBreed;
        public final ImageView ivDogImage;
        public final ImageView ivFavorite;
        public final com.google.android.material.floatingactionbutton.FloatingActionButton expandButton;

        public DogsViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.textViewTitle);
            tvBreed = view.findViewById(R.id.textViewGender);
            ivDogImage = view.findViewById(R.id.imageViewItem);
            ivFavorite = view.findViewById(R.id.hart);
            expandButton = view.findViewById(R.id.expand);
        }

    }

}
