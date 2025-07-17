package gabi.low.findmydog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextName;
    private EditText editTextBreed;
    private EditText editTextAge;
    private EditText editTextDescription;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button buttonAddDog;
    private Button addImageButton;
    private ImageView pictureDog;
    private Uri imageUri;
    private String DogKeyID;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add.
     */
    // TODO: Rename and change types and number of parameters
    public static add newInstance(String param1, String param2) {
        add fragment = new add();
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
        View view =inflater.inflate(R.layout.fragment_add, container, false);
        init(view);
        addImageButton.setOnClickListener(v -> {
            openGallery();

        });



        buttonAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDogV2();
            }
        });


        // Inflate the layout for this fragment
        return view;

    }

    private void uploadDogImage() {
        if (imageUri == null) {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }

        String keyID = FBref.refDogs.push().getKey();
        DogKeyID = keyID;
        String uid = FBref.refAuth.getCurrentUser().getUid();
        StorageReference ref = FBref.refStorage
                .child("dogs")
                .child(uid)
                .child(keyID + ".jpg");

        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    buttonAddDog.setEnabled(true);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    void  openGallery()
    {
        Intent intent =new  Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            pictureDog.setImageURI(imageUri);
            buttonAddDog.setEnabled(true);
            //uploadDogImage();
        }
    }

    public void init(View view)
    {
        editTextName = view.findViewById(R.id.editTextName);
        editTextBreed = view.findViewById(R.id.editTextBreed);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        radioButtonMale = view.findViewById(R.id.radioMale);
        radioButtonFemale = view.findViewById(R.id.radioFemale);
        buttonAddDog = view.findViewById(R.id.addDogButton);
        addImageButton = view.findViewById(R.id.buttonSelectImage);
        pictureDog = view.findViewById(R.id.dogPic);
        buttonAddDog.setEnabled(false);
    }
    void addDog()
    {
        String name = editTextName.getText().toString();
        String breed = editTextBreed.getText().toString();
        String age = editTextAge.getText().toString();
        boolean gender;
        String description = editTextDescription.getText().toString();
        int aintAge = Integer.parseInt(age);
        if(radioButtonMale.isChecked())
            gender = true;
        else
            gender= false;
        if(name.isEmpty() || breed.isEmpty() || age.isEmpty() || description.isEmpty())
        {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        DogsClass dog =new  DogsClass(name, breed,aintAge, description, gender);
        dog.setKeyID(DogKeyID);
        FBref.refDogs. child(FBref.refAuth.getCurrentUser().getUid()).child(dog.getKeyID()).setValue(dog)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "Dog added successfully", Toast.LENGTH_SHORT).show();
                        editTextName.setText("");
                        editTextBreed.setText("");
                        editTextAge.setText("");
                        editTextDescription.setText("");
                        radioButtonMale.setChecked(false);
                        radioButtonFemale.setChecked(false);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Failed to add dog", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //********************************************************
    private void uploadDogImageAndSaveDog(final DogsClass dog) {
        if (imageUri == null) {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = FBref.refAuth.getCurrentUser().getUid();
        String keyID = dog.getKeyID();
        StorageReference ref = FBref.refStorage
                .child("dogs")
                .child(uid)
                .child(keyID + ".jpg");

        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // ✅ Download the image URL
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        dog.setImageUrl(downloadUrl); // ✅ Save URL to dog object

                        // ✅ Now save the dog object in Realtime DB
                        FBref.refDogs.child(uid).child(keyID).setValue(dog)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Dog added successfully", Toast.LENGTH_SHORT).show();
                                        resetForm(); // optional clear form
                                    } else {
                                        Toast.makeText(getContext(), "Failed to save dog", Toast.LENGTH_SHORT).show();
                                    }
                                    buttonAddDog.setEnabled(true);
                                });

                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to get image URL", Toast.LENGTH_SHORT).show();
                        buttonAddDog.setEnabled(true);
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    buttonAddDog.setEnabled(true);
                });
    }
//****************************************************************************************************8

    void addDogV2() {
        String name = editTextName.getText().toString();
        String breed = editTextBreed.getText().toString();
        String age = editTextAge.getText().toString();
        String description = editTextDescription.getText().toString();
        boolean gender = radioButtonMale.isChecked();

        if (name.isEmpty() || breed.isEmpty() || age.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int aintAge = Integer.parseInt(age);
        DogsClass dog = new DogsClass(name, breed, aintAge, description, gender);

        String keyID = FBref.refDogs.push().getKey();
        dog.setKeyID(keyID);
        DogKeyID = keyID;

        buttonAddDog.setEnabled(false); // disable while uploading
        uploadDogImageAndSaveDog(dog);  // ✅ Upload and save in one go
    }

    //***********************************************8
    private void resetForm() {
        editTextName.setText("");
        editTextBreed.setText("");
        editTextAge.setText("");
        editTextDescription.setText("");
        radioButtonMale.setChecked(false);
        radioButtonFemale.setChecked(false);
        pictureDog.setImageDrawable(null); // remove preview image
        imageUri = null;                   // clear imageUri reference
        DogKeyID = null;                   // clear dog key ID
    }

}