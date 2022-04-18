package com.example.myfirstsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class NewRecipe extends AppCompatActivity {
    private static final String TAG = "New REcipe";
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("DB");
    Button Cancel, Save;
    EditText txtTitle, txtAuthor, txtContent;
    String[] Recipe_Recorded;
    String randomString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recipe_layout);

        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtContent = findViewById(R.id.txtContent);

        Intent intent = getIntent();
        int TotalRecords = Integer.parseInt(intent.getStringExtra("TotalRecords"));
        String Recorded_Ids = intent.getStringExtra("Recorded_IDs");

        Recipe_Recorded = TextUtils.split(Recorded_Ids," | ");


            // create a string of uppercase and lowercase characters and numbers
            String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
            String numbers = "0123456789";

            // combine all strings
            String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

            // create random string builder
            StringBuilder sb = new StringBuilder();

            // create an object of Random class
            Random random = new Random();

            // specify length of random string
            int length = 20;

        for(String ss: Recipe_Recorded){



            for(int i = 0; i < length; i++) {

                // generate random index number
                int index = random.nextInt(alphaNumeric.length());

                // get character specified by index
                // from the string
                char randomChar = alphaNumeric.charAt(index);

                // append the character to string builder
                sb.append(randomChar);
            }

            randomString = sb.toString();

            if(!ss.equals(randomString)){
                Log.v(TAG, "newID: "+randomString);
                break;

            }

        }






        //TotalRecords = TotalRecords + 1;





        //BTN CANCEL
        Cancel = (Button) findViewById(R.id.btnCancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);}
        });

        //BTN SAVE
        Save = (Button) findViewById(R.id.btnSave);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numRegex   = ".*[0-9].*";
                String alphaRegex = ".*[a-zA-Z].*";

                if(txtTitle.getText().toString().matches(numRegex) || txtTitle.getText().toString().matches(alphaRegex)){
                    String stringR ="r"+randomString;
                    //Toast.makeText(getApplicationContext(),""+stringR,Toast.LENGTH_SHORT).show();
                    root.child("Recipes").child(""+stringR).child("Title").setValue(txtTitle.getText().toString());
                    root.child("Recipes").child(""+stringR).child("Author").setValue(txtAuthor.getText().toString());
                    root.child("Recipes").child(""+stringR).child("Content").setValue(txtContent.getText().toString());

                    txtTitle.setText("");
                    txtAuthor.setText("");
                    txtContent.setText("");

                    Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Title cannot be empty.",Toast.LENGTH_SHORT).show();
                }





            };

        });

    }

}
