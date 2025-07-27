package gabi.low.findmydog;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogsViewHolder> {
    private ArrayList<DogsClass> dataList;
    private Context context;
    private boolean isFavoriteMode;
    private AppDatabase db;
    public DogsAdapter(Context context, ArrayList<DogsClass> dataList, boolean isFavoriteMode) {
        this.context = context;
        this.dataList = dataList;
        this.isFavoriteMode = isFavoriteMode;
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
        //LikedDog likedDog = db.likedDogDao().getDogByNameAndBreed(currentItem.getName(), currentItem.getBreed());
        if (!isFavoriteMode) {
            holder.ivFavorite.setImageResource(R.drawable.baseline_delete_24); // delete icon
            holder.ivFavorite.setOnClickListener(v -> {
                db = DogsActivity.getDb();
                LikedDog likedDog = db.likedDogDao().getDogByNameAndBreed(currentItem.getName(), currentItem.getBreed());
                db.likedDogDao().deleteById(likedDog.getDogId());
                dataList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
            });
        }
            else{
                if (currentItem.isFavorite()) {
                    holder.ivFavorite.setImageResource(R.drawable.full);
                } else
                    holder.ivFavorite.setImageResource(R.drawable.empty); // empty heart

                holder.ivFavorite.setOnClickListener(v -> {
                    boolean currentState = currentItem.isFavorite();
                    if (currentState == false) {
                        wirtetoRooms(currentItem);
                    }
                    currentItem.setFavorite(!currentState);
                    notifyItemChanged(position); // refresh the item to show new image

                });
            }
        holder.expandButton.setOnClickListener(v -> {
            showImageDialog(currentItem);
        });

    }

    private void wirtetoRooms(DogsClass currentItem) {
        LikedDog likedDog = new LikedDog();
        //likedDog.setDogId(currentItem.getKeyID());  // ensure your DogsClass has this
        likedDog.setName(currentItem.getName());
        likedDog.setBreed(currentItem.getBreed());
        likedDog.setImageUrl(currentItem.getImageUrl());
        likedDog.setAge(currentItem.getAge());
        likedDog.setGender(currentItem.isGender());
        likedDog.setDescription(currentItem.getDescription());
        // Check if already in DB before continuing
         db = DogsActivity.getDb();
        LikedDog existing = db.likedDogDao().getDogByNameAndBreed(currentItem.getName(), currentItem.getBreed());
        if (existing != null) {
            Toast.makeText(context, "Already in favorites", Toast.LENGTH_SHORT).show();
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(currentItem.getImageUrl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        String filename = "dog_" + System.currentTimeMillis() + ".png";
                        File file = new File(context.getFilesDir(), filename);
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            likedDog.setImagePath(file.getAbsolutePath());

                            AppDatabase db = DogsActivity.getDb();
                            db.likedDogDao().insert(likedDog);
                            Log.d("ROOM_TEST", "Inserted with local image path: " + file.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("ROOM_TEST", "Failed to save image", e);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Not needed for now
                    }
                });






        //AppDatabase db = DogsActivity.getDb();
        if (db == null) {
            Log.e("ROOM_TEST", "Dog key is null or empty! Skipping insert.");
            return;
        }
        else {

            Log.d("ROOM_TEST", "Trying to insert LikedDog: " +
                    "\nID: " + likedDog.getDogId() +
                    "\nName: " + likedDog.getName() +
                    "\nBreed: " + likedDog.getBreed());
            //db.likedDogDao().insert(likedDog);
            Log.d("ROOM_TEST", "Inserted likedDog: " + likedDog.getName());
            List<LikedDog> allDogs = db.likedDogDao().getAll();
            Log.d("ROOM_TE" +
                    "ST", "Number of liked dogs in DB: " + allDogs.size());
            for (LikedDog dog : allDogs) {
                Log.d("ROOM_TEST", "Dog in DB: " + dog.getName() + ", " + dog.getBreed() +
                        ", ID: " + dog.getDogId() +
                        ", Image URL: " + dog.getImageUrl()+ "Age: " + dog.getAge() + "gender: "
                        + dog.isGender() + "Description: " + dog.getDescription());
            }

        }
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
