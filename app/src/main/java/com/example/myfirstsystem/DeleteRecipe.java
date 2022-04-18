package com.example.myfirstsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteRecipe extends AppCompatActivity {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("DB");

    TextView txtSure;
    Button Yes, No;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_layout);

        Intent intent = getIntent();
        String Title = intent.getStringExtra("Title");
        String Author = intent.getStringExtra("Author");
        String Content = intent.getStringExtra("FullContent");
        String TotalRecords = intent.getStringExtra("TotalRecords");
        String RecipeID = intent.getStringExtra("RecipeID");

        txtSure = findViewById(R.id.txtSure);
        txtSure.setText("Are you sure to delete "+"'"+Title+"'"+" by " +Author+"?");

        Yes = findViewById(R.id.btnYes);
        No = findViewById(R.id.btnNo);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.child("Recipes").child(RecipeID).removeValue();
                Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("Title", Title);
                intent.putExtra("Author", Author);
                //intent.putExtra("Content", holder.Content.getText().toString());
                intent.putExtra("FullContent", Content);
                intent.putExtra("RecipeID", RecipeID);
                v.getContext().startActivity(intent);

            }
        });
    }
}
