package com.example.myfirstsystem;

import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_my extends RecyclerView.Adapter<Adapter_my.MyViewHolder>{
    private Context context;
    private ArrayList button_list;
    private ArrayList author_list;
    private ArrayList content_list;
    private ArrayList fullcontent_list;
    private ArrayList RecipeID_list;
    private String TotalRecords;
    private OnCardListener monCardListener;

    public Adapter_my(Context context_parameter, ArrayList button_parameter, ArrayList author_param, ArrayList content_param, ArrayList fullcontent_list_param, String totalRecords_param, ArrayList RecipeID_list_param, OnCardListener onCardListener){
        this.context = context_parameter;
        this.button_list= button_parameter;
        this.monCardListener = onCardListener;
        this.author_list = author_param;
        this.content_list = content_param;
        this.TotalRecords = totalRecords_param;
        this.fullcontent_list = fullcontent_list_param;
        this.RecipeID_list = RecipeID_list_param;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_layout,parent,false);
        return new Adapter_my.MyViewHolder(v, monCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Title.setText(""+button_list.get(position));
        holder.Author.setText(""+author_list.get(position));
        holder.Content.setText(""+content_list.get(position));
        holder.FullContent.setText(""+fullcontent_list.get(position));
        holder.RecipeID.setText(""+RecipeID_list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CardContent.class);
                intent.putExtra("Title", holder.Title.getText().toString());
                intent.putExtra("Author", holder.Author.getText().toString());
                //intent.putExtra("Content", holder.Content.getText().toString());
                intent.putExtra("FullContent", holder.FullContent.getText().toString());
                intent.putExtra("TotalRecords", holder.TotalRecords.getText().toString());
                intent.putExtra("RecipeID", holder.RecipeID.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return button_list.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cards;
        TextView Title, Author, Content, FullContent, TotalRecords, RecipeID;
        OnCardListener onCardListener;

        public MyViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);

            cards = itemView.findViewById(R.id.view_card);
            Title = itemView.findViewById(R.id.lblTitle);
            Author = itemView.findViewById(R.id.lblAuthor);
            Content = itemView.findViewById(R.id.lblContent);
            FullContent = itemView.findViewById(R.id.lblFullContent);
            TotalRecords = itemView.findViewById(R.id.lblTotalRecords);
            RecipeID = itemView.findViewById(R.id.lblRecipeID);
            this.onCardListener = onCardListener;

            Title.setSelected(true);
            Author.setSelected(true);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());

        }
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }
}
