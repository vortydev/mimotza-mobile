package com.example.mimotza;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

public class RecycleThreadAdapter extends RecyclerView.Adapter<RecycleThreadAdapter.ThreadHolder> {

    private ArrayList<JSONObject> threadList;
    private Context context;

    @NonNull
    @Override
    public ThreadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_thread, parent, false);

        //System.out.println("View: " + view);
        //System.out.println("ViewType: " + viewType);

        return new ThreadHolder(view);
    }

    public RecycleThreadAdapter(Context context, ArrayList<JSONObject> json) {

        this.context = context;
        this.threadList = json;
        //System.out.println("InitThreadList: " + threadList);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadHolder holder, @SuppressLint("RecyclerView") int position) {
        //final int pos = position;
        try {
            holder.auteurThread.setText(this.context.getResources().getString(R.string.auteur, this.threadList.get(position).getString("Auteur")));
            holder.titreThread.setText(this.threadList.get(position).getString("Titre"));
            holder.titreThread.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    try {
                        Intent intent = new Intent(context, ThreadActivity.class);
                        intent.putExtra("ID", threadList.get(position).getString("ID"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
            holder.messThread.setText(this.threadList.get(position).getString("Message"));
            holder.nbReponsesThread.setText(this.threadList.get(position).getString("NbReponses"));
            //System.out.println("Position " + position + " fait avec succès.");
        }
        catch (Exception e) {
            System.out.println("Position " + position + " échoué...");
            System.out.println("Erreur: " + e.toString());
        }
    }

    public void setThreadList(ArrayList<JSONObject> jsonList) {

        this.threadList = jsonList;
    }

    @Override
    public int getItemCount() {

        //System.out.println("ThreadListSize: " + this.threadList.size());
        return this.threadList.size();
    }

    public class ThreadHolder extends RecyclerView.ViewHolder {

        TextView auteurThread;
        TextView titreThread;
        TextView messThread;
        TextView nbReponsesThread;

        public ThreadHolder(@NonNull View itemView) {
            super(itemView);
            auteurThread = (TextView)itemView.findViewById(R.id.auteur);
            titreThread = (TextView)itemView.findViewById(R.id.titre);
            messThread = (TextView)itemView.findViewById(R.id.message);
            nbReponsesThread = (TextView)itemView.findViewById(R.id.nbReponses);
        }
    }
}
