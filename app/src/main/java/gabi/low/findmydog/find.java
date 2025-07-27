package gabi.low.findmydog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link find#newInstance} factory method to
 * create an instance of this fragment.
 */
public class find extends Fragment {
    private RecyclerView recyclerView;
    private DogsAdapter adapter;
    private ArrayList<DogsClass> favoriteDogsList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public find() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment find.
     */
    // TODO: Rename and change types and number of parameters
    public static find newInstance(String param1, String param2) {
        find fragment = new find();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        recyclerView = view.findViewById(R.id.findRecicler);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        favoriteDogsList = new ArrayList<>();
        loadFavoritesFromRoom();
        return  view;
    }

    private void loadFavoritesFromRoom() {
        AppDatabase db = DogsActivity.getDb();
        if(db== null) {
            return;
        }
        List<LikedDog> likedDogs  = db.likedDogDao().getAll();

        for(LikedDog liked : likedDogs)
        {
            DogsClass dog = new DogsClass();
            dog.setName(liked.getName());
            dog.setBreed(liked.getBreed());
            dog.setDescrition(liked.getDescription());
            dog.setAge(liked.getAge());
            dog.setGender(liked.isGender());
            dog.setImageUrl(liked.getImageUrl());
            dog.setFavorite(true);
            favoriteDogsList.add(dog);
        }
        adapter = new DogsAdapter(getContext(), favoriteDogsList, false);
        recyclerView.setAdapter(adapter);
    }
}