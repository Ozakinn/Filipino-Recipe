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

public class ModifyRecipe extends AppCompatActivity {
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("DB");
    TextView editedTitle, editedAuthor, editedContent;

    Button Modify, Cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_layout);

        Intent intent = getIntent();
        String Title = intent.getStringExtra("Title");
        String Author = intent.getStringExtra("Author");
        String Content = intent.getStringExtra("FullContent");
        String TotalRecords = intent.getStringExtra("TotalRecords");
        String RecipeID = intent.getStringExtra("RecipeID");

        editedTitle = findViewById(R.id.editTitle);
        editedAuthor = findViewById(R.id.editAuthor);
        editedContent = findViewById(R.id.editContent);

        editedTitle.setText(Title);
        editedAuthor.setText(Author);
        editedContent.setText(Content);

        Modify = findViewById(R.id.btnModify);
        Cancel = findViewById(R.id.btnCancelModify);

        Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numRegex   = ".*[0-9].*";
                String alphaRegex = ".*[a-zA-Z].*";


                String TitleSS = editedTitle.getText().toString();
                String AuthorSS = editedAuthor.getText().toString();
                String ContentSS = editedContent.getText().toString();

                if(TitleSS.matches(numRegex) || TitleSS.matches(alphaRegex)){
                    //String stringR ="r"+StringTotal;
                    //Toast.makeText(getApplicationContext(),""+stringR,Toast.LENGTH_SHORT).show();



                    root.child("Recipes").child(""+RecipeID).child("Title").setValue(editedTitle.getText().toString());
                    root.child("Recipes").child(""+RecipeID).child("Author").setValue(editedAuthor.getText().toString());
                    root.child("Recipes").child(""+RecipeID).child("Content").setValue(editedContent.getText().toString());

                    editedTitle.setText("");
                    editedAuthor.setText("");
                    editedContent.setText("");


                    Toast.makeText(getApplicationContext(),"Modified Successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("Title", TitleSS);
                    intent.putExtra("Author", AuthorSS);
                    //intent.putExtra("Content", holder.Content.getText().toString());
                    intent.putExtra("FullContent", ContentSS);
                    intent.putExtra("RecipeID", RecipeID);
                    v.getContext().startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Title cannot be empty.",Toast.LENGTH_SHORT).show();
                }



            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

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
