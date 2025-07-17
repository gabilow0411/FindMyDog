package gabi.low.findmydog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDogsFragment extends Fragment {
    private  DogsAdapter myAdapter;
    private ArrayList<DogsClass> dogsList;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyDogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDogsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDogsFragment newInstance(String param1, String param2) {
        MyDogsFragment fragment = new MyDogsFragment();
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
        {
            View view = inflater.inflate(R.layout.fragment_my_dogs, container, false);
            recyclerView = view.findViewById(R.id.myDogRecicle);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            //creat a arryList for the dogs
            ArrayList<DogsClass> dogsList = new ArrayList<>();
            myAdapter = new DogsAdapter(getContext(), dogsList);
            recyclerView.setAdapter(myAdapter);
            String uid = FBref.refAuth.getCurrentUser().getUid();
            FBref.refDogs.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dogsList.clear();
                    for (DataSnapshot dogSnapshot : snapshot.getChildren()) {
                        DogsClass dog = dogSnapshot.getValue(DogsClass.class);
                        if (dog != null) {
                            dogsList.add(dog);
                        }
                    }
                    myAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return view;
        }
    }
}
//
//package gabi.low.findmydog;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link home#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class home extends Fragment {
//    private  DogsAdapter myAdapter;
//    private ArrayList<DogsClass> dogsList;
//    private RecyclerView recyclerView;
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public home() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment home.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static home newInstance(String param1, String param2) {
//        home fragment = new home();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        recyclerView = view.findViewById(R.id.dogRecicle);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        //creat a arryList for the dogs
//        ArrayList<DogsClass> dogsList = new ArrayList<>();
//        myAdapter = new DogsAdapter(dogsList);
//        recyclerView.setAdapter(myAdapter);
//        FBref.refDogs.child(FBref.refAuth.getCurrentUser().getUid()).
//                addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        dogsList.clear();
//                        for( DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            DogsClass dog = dataSnapshot.getValue(DogsClass.class);
//                            if (dog != null) {
//                                dogsList.add(dog);
//                            }
//                        }
//                        myAdapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
////        dogsList.add(new DogsClass("nina", "mix", 12, "loyal and reserve", false));
////        dogsList.add(new DogsClass("jaky", "dalmetion", 10, "energetyc and playful",true));
////        dogsList.add(new DogsClass("rexy", "boxer", 8,  "happy and friendly",true));
//        // Inflate the layout for this fragment
//
//
//
//        return view;
//    }
//}