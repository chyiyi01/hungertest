package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class receiverAdapter extends RecyclerView.Adapter<receiverAdapter.myViewHolder>
{
    ArrayList<model> dataList;
    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    public String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
    public String uid;

    public receiverAdapter(ArrayList<model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.tName.setText(dataList.get(position).getName());
        holder.tType.setText(dataList.get(position).getType());
        holder.tContact.setText(dataList.get(position).getContact());
    }

    public void deleteItem(int position){
        //getSnapshots().getSnapshot(position).getReference().delete();
    }

    @Override
    public int getItemCount() {return dataList.size();}

    class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tName,tType,tContact;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tName = itemView.findViewById(R.id.name);
            tType = itemView.findViewById(R.id.type);
            tContact = itemView.findViewById(R.id.contact);
        }
    }
}

