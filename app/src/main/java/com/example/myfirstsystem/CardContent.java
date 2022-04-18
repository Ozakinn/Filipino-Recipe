package com.example.myfirstsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CardContent extends AppCompatActivity {
    private static final String TAG = "CardContent";

    TextView lblTitle, lblAuthor, lblContent;

    Button Modify, Delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_content);

        Log.d(TAG, "onCreate: CardContent na po");

        Intent intent = getIntent();
        String Title = intent.getStringExtra("Title");
        String Author = intent.getStringExtra("Author");
        String Content = intent.getStringExtra("FullContent");
        String TotalRecords = intent.getStringExtra("TotalRecords");
        String RecipeID = intent.getStringExtra("RecipeID");

        lblTitle = findViewById(R.id.Title_content);
        lblAuthor = findViewById(R.id.author_content);
        lblContent = findViewById(R.id.content_content);



        lblTitle.setText(Title);
        lblAuthor.setText(Author);
        lblContent.setText(Content);

        lblTitle.setSelected(true);
        lblAuthor.setSelected(true);

        Modify = findViewById(R.id.btnModifyRecipe);
        Delete = findViewById(R.id.btnDelete);

        Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ModifyRecipe.class);
                intent.putExtra("Title", Title);
                intent.putExtra("Author", Author);
                //intent.putExtra("Content", holder.Content.getText().toString());
                intent.putExtra("FullContent", Content);
                intent.putExtra("RecipeID", RecipeID);
                v.getContext().startActivity(intent);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteRecipe.class);
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
