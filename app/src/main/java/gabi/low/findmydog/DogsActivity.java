package gabi.low.findmydog;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import gabi.low.findmydog.databinding.ActivityMainBinding;
import gabi.low.findmydog.databinding.DogsBinding;

public class DogsActivity extends AppCompatActivity {
private DogsBinding binding;
    private static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.deleteDatabase("dog-database");
         db = Room.databaseBuilder(this, AppDatabase.class, "dog-database")
                .fallbackToDestructiveMigration()  // ← this prevents the crash
                .allowMainThreadQueries()
                .build();

        binding = DogsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        replaceFragment(new home());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new home());
            } else if (item.getItemId() == R.id.add) {
                replaceFragment(new add());
            } else if (item.getItemId() == R.id.search) {
                replaceFragment(new find());
            }
            else if(item.getItemId()==R.id.upload)
            {
                replaceFragment(new MyDogsFragment());
            }
            return true;
        });
    }

    private  void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
    public static AppDatabase getDb() {
        return db;
    }
}