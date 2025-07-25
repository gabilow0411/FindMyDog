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

    private String imageUrl;
    private  int age;
    private  boolean gender;
    private  String description;
    private String imagePath;
    //private String ownerEmail;
    public LikedDog(String name, String breed, String imageUrl, int age, boolean gender,String description) {
        this.name = name;
        this.breed = breed;
        this.imageUrl = imageUrl;
        this.age = age;
        this.description=description;
        this.gender=gender;
    }

    // Empty constructor (required by Room)
    public LikedDog() {
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public boolean isGender() {
        return gender;
    }

    public String getDescription() {
        return description;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

//    public String getOwnerEmail() {
//        return ownerEmail;
//    }
//
//    public void setOwnerEmail(String ownerEmail) {
//        this.ownerEmail = ownerEmail;
//    }
}
