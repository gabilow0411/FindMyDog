package gabi.low.findmydog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class LikedDog {
    @PrimaryKey(autoGenerate = true)
    private int dogId;
    private String name;
    private String breed;

    //private String imageUrl;
    //private String ownerEmail;

    // Empty constructor (required by Room)
    public LikedDog() {
    }

    // Full constructor (optional convenience)
    public LikedDog(String name, String breed) {
        this.name = name;
        this.breed = breed;
        //this.imageUrl = imageUrl;
        //this.ownerEmail = ownerEmail;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

//    public String getOwnerEmail() {
//        return ownerEmail;
//    }
//
//    public void setOwnerEmail(String ownerEmail) {
//        this.ownerEmail = ownerEmail;
//    }
}
