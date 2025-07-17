package gabi.low.findmydog;



public class DogsClass {
    private String name;
    private String breed;
    private int age;
    private  boolean gender;
    private int imageResource;
    String keyID;
    String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private boolean isFavorite;
    private  String description;




    public DogsClass(String name, String breed, int age, String description, boolean gender) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
        if(gender)
                    this.imageResource = R.drawable.male;
        else
                    this.imageResource =R.drawable.female;
            // Assuming you have a drawable
        //this.imageResource = imageResource;
        this.isFavorite = false;
        this.description = description;
    }
    public DogsClass()
    {

    }



    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDescrition(String descrition) {
        this.description = descrition;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
