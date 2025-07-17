package gabi.low.findmydog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FBref {
    public static FirebaseAuth refAuth = FirebaseAuth.getInstance();
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();


    public static DatabaseReference refDogs = FBDB.getReference("Dogs");
    public static DatabaseReference refSubscibe = FBDB.getReference("Subscribers");

    public  static FirebaseStorage storage = FirebaseStorage.getInstance();
    public  static StorageReference refStorage = storage.getReference();


}
