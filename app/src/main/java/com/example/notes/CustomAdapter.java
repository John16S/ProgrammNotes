package com.example.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;          // for refresh after Update and Delete
    private ArrayList note_id, note_title, note_text;

    CustomAdapter(Activity _activity, Context _context, ArrayList _note_id, ArrayList _note_title, ArrayList _note_text){
        this.activity = _activity;
        this.context = _context;
        this.note_id = _note_id;
        this.note_title = _note_title;
        this.note_text = _note_text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =  inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*--------------Setting the texts--------------*/
        holder.note_id_txt.setText(String.valueOf(note_id.get(position)));
        holder.note_title_txt.setText(String.valueOf(note_title.get(position)));
        holder.note_Text_txt.setText(String.valueOf(note_text.get(position)));

        /*-----------Storing data's into the UpdateActivity---------------------*/
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);

                intent.putExtra("id", String.valueOf(note_id.get(position)));
                intent.putExtra("title", String.valueOf(note_title.get(position)));
                intent.putExtra("text", String.valueOf(note_text.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView note_id_txt, note_title_txt, note_Text_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_id_txt = itemView.findViewById(R.id.Note_id);
            note_title_txt = itemView.findViewById(R.id.Note_title);
            note_Text_txt = itemView.findViewById(R.id.Note_text);

            mainLayout =itemView.findViewById(R.id.mainLayout);
        }
    }
}
