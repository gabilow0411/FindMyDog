package gabi.low.findmydog;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LikedDog likedDog);

    @Query("SELECT * FROM LikedDog")
    List<LikedDog> getAll();


    @Delete
    void delete(LikedDog dog);

    @Query("DELETE FROM LikedDog WHERE dogId = :id")
    void deleteById(int id);

    @Query("SELECT * FROM LikedDog WHERE name = :name AND breed = :breed LIMIT 1")
    LikedDog getDogByNameAndBreed(String name, String breed);


    @Query("SELECT * FROM LikedDog WHERE dogId = :dogId")
    LikedDog getByDogId(String dogId);
}

