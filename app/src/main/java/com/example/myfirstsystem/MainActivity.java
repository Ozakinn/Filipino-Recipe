package com.example.myfirstsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Adapter_my.OnCardListener {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("DB");
    private static final String TAG = "Main";
    ArrayList button_list, author_list, content_list, fullcontent_list, RecipeID_list;
    ArrayList<String> Title = new ArrayList<String>();
    ArrayList<String> Author = new ArrayList<String>();
    ArrayList<String> Content = new ArrayList<String>();
    ArrayList<String> RecipeID = new ArrayList<String>();
    String TotalRecords, RecipeID_Recorded;
    RecyclerView rec;
    Adapter_my adapter_my;
    EditText editTXT, editNUM;
    Button New;
    TextView TotalR, RecordedIDs;
    int count, r, May_Record_ba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //New = (Button) findViewById(R.id.btnNew);
        //New.setOnClickListener(this);

        //root.child("Recipes").child("r2").setValue("0");
        //root.child("Recipes").child("r1").child("Content").setValue("Combine chicken, garlic, peppercorn, vinegar, MAGGI® Oyster Sauce, soy sauce and water in a pot. Simmer for 15 minutes." +
        //        "Heat oil in a separate pan, strain chicken and sauté until golden brown. Pour cooking liquid and simmer for 5 minutes. Transfer into a serving plate and serve.");
        //root.child("Recipes").child("r2").child("Content").setValue("Filipino soup cooked with pork. Serve with rice and for additional sauce, use soy or fish sauce. If you want to, you can add what Filipinos call gabi gabi, which is a small taro root. When peeled they look like potatoes. You can add 5 to 6 of them when you add the water and make sure they are cooked through. Take them out when they are cooked because they can get too soft.");
        //root.child("Status").child("TotalCount").setValue("2");








        //getRecipes();
        Menu();

        New = (Button) findViewById(R.id.btnNew);
        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewRecipe.class);
                intent.putExtra("TotalRecords", TotalR.getText().toString());
                intent.putExtra("Recorded_IDs", RecordedIDs.getText().toString());
                view.getContext().startActivity(intent);}

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Menu();
    }

    public void Menu(){

        rec = findViewById(R.id.rec_view);
        button_list = new ArrayList();
        author_list = new ArrayList();
        content_list = new ArrayList();
        fullcontent_list = new ArrayList();
        RecipeID_list = new ArrayList();
        adapter_my = new Adapter_my(this, button_list, author_list, content_list, fullcontent_list, TotalRecords, RecipeID_list, this);



        rec.setHasFixedSize(true);
        GridLayoutManager manager_cat = new GridLayoutManager(this, 2);
        manager_cat.setOrientation(RecyclerView.VERTICAL);
        rec.setLayoutManager(manager_cat);
        rec.setAdapter(adapter_my);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                
                int totalRecipes = (int)snapshot.child("Recipes").getChildrenCount();
                count = 0;
                r = 0;


                if(totalRecipes == 0){
                    May_Record_ba = 0;
                }else{
                    May_Record_ba = 1;
                }

                if(May_Record_ba == 1){
                    try {
                        for (DataSnapshot childDataSnapshot : snapshot.child("Recipes").getChildren()) {
                            //Log.v(TAG, "" + childDataSnapshot.getKey()); //displays the key for the node
                            //Log.v(TAG, "" + childDataSnapshot.child("Title").getValue());   //gives the value for given keyname


                            String RecipeNUM = childDataSnapshot.getKey();

                            Title.add(snapshot.child("Recipes").child("" + RecipeNUM).child("Title").getValue().toString());
                            Author.add(snapshot.child("Recipes").child("" + RecipeNUM).child("Author").getValue().toString());
                            Content.add(snapshot.child("Recipes").child("" + RecipeNUM).child("Content").getValue().toString());
                            RecipeID.add(RecipeNUM);
                        }
                    }catch (Exception ex){

                    }

                    TotalR = findViewById(R.id.lblTotalR);
                    RecordedIDs = findViewById(R.id.lblRecordedIds);

                    TotalR.setText(String.valueOf(totalRecipes));
                    RecordedIDs.setText(TextUtils.join(" | ",RecipeID));


                    //CLEAR MUNA KUNG MAY LAMAN
                    button_list.clear();
                    author_list.clear();
                    content_list.clear();
                    fullcontent_list.clear();
                    RecipeID_list.clear();


                    //FOR SOME REASON ITO YUNG NAGRERENDER SA BUTTON TO RESET THE VALUE
                    rec.setAdapter(adapter_my);

                    //CONVERT SA NUM SINCE DETECTED NA NUM NA ITO
                    //int num = Integer.parseInt(TotalCount)// ;
                    //RecipeID_Recorded = TextUtils.join(" | ",RecipeID);
                    //TRYING TO LOOP THE BUTTON BASED ON USER INPUT
                    try {

                        for(int x=0; x < totalRecipes;x++){
                            button_list.add(Title.get(x));
                            author_list.add(Author.get(x));

                            TotalRecords = String.valueOf(totalRecipes);


                            fullcontent_list.add(Content.get(x));
                            RecipeID_list.add(RecipeID.get(x));


                            if(Content.get(x).length() >= 12){
                                content_list.add(Content.get(x).substring(0,12)+"...");
                            }
                            else{
                                content_list.add(Content.get(x));
                            }
                        }
                    }catch (Exception ex){


                    }

                }else{
                    RecipeID.add("wala");
                    TotalR = findViewById(R.id.lblTotalR);
                    RecordedIDs = findViewById(R.id.lblRecordedIds);
                    TotalR.setText(String.valueOf(totalRecipes));
                    RecordedIDs.setText(TextUtils.join(" | ",RecipeID));
                    Toast.makeText(getApplicationContext(),"No Records Found",Toast.LENGTH_SHORT).show();
                }


                /*
                try {
                    do {
                        String stringY = String.valueOf(count);
                        String childpath = "r" + stringY;

                        if (!snapshot.child("Recipes").child("" + childpath).exists()) {
                            count++;

                        } else if (snapshot.child("Recipes").child("" + childpath).exists()) {
                            Title.add(snapshot.child("Recipes").child("" + childpath).child("Title").getValue().toString());
                            Author.add(snapshot.child("Recipes").child("" + childpath).child("Author").getValue().toString());
                            Content.add(snapshot.child("Recipes").child("" + childpath).child("Content").getValue().toString());
                            RecipeID.add(childpath);
                            count++;
                            r++;
                        } else {
                            count++;
                        }


                    } while (r != totalRecipes);
                }catch (Exception ex){

                }

                 */
                
                //String Title = snapshot.child("Recipes").child("r1").child("Title").getValue().toString();
                //String TotalCount = snapshot.child("Status").child("TotalCount").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onCardClick(int position) {
        Log.d(TAG, "onCardClick: clicked");
        //button_list.get(position);
        //Intent intent = new Intent(this, CardContent.class);
       // intent.putExtra("Title",Title.toString());
        //startActivity(intent);
    }
}

