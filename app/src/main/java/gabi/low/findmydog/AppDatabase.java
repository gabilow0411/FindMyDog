package gabi.low.findmydog;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LikedDog.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DogDao likedDogDao();
}
