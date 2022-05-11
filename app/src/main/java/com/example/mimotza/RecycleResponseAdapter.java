package com.example.mimotza;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecycleResponseAdapter extends RecyclerView.Adapter<RecycleResponseAdapter.ResponseHolder> {

    private ArrayList<JSONObject> responseList;
    private RecycleResponseAdapter recycleResponseAdapter;
    private Context context;
    private String threadId;

    @NonNull
    @Override
    public ResponseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_response, parent, false);

        return new ResponseHolder(view);
    }

    public RecycleResponseAdapter(Context context, ArrayList<JSONObject> json, String threadId) {

        this.context = context;
        this.responseList = json;
        this.threadId = threadId;
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseHolder holder, int position) {
        //final int pos = position;
        try {

            ArrayList<JSONObject> reponses = new ArrayList<JSONObject>();

            if (this.responseList.get(position).get("Reponses").getClass().getSimpleName().contentEquals("JSONObject")) {
                JSONObject reponsesJSON = this.responseList.get(position).getJSONObject("Reponses");
                JSONArray keys = reponsesJSON.names();

                for (int i = 0; i < keys.length(); i++) {
                    reponses.add(reponsesJSON.getJSONObject(keys.getString(i)));
                }
            }

            recycleResponseAdapter = new RecycleResponseAdapter(context, reponses, this.threadId);


            holder.auteurReponse.setText(this.context.getResources().getString(R.string.auteur, this.responseList.get(position).getString("Auteur")));
            holder.messReponse.setText(this.responseList.get(position).getString("Message"));
            holder.messReponse.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    try {
                        Intent intent = new Intent(context, MessReponseActivity.class);
                        intent.putExtra("IDThread", threadId);
                        intent.putExtra("IDMessage", responseList.get(position).getInt("ID"));
                        intent.putExtra("Message", responseList.get(position).getString("Message"));
                        intent.putExtra("Auteur", responseList.get(position).getString("Auteur"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    catch(Exception e) {
                        System.out.println(e);
                    }
                }
            });

            holder.inceptionReponse.setLayoutManager(new LinearLayoutManager(context));
            holder.inceptionReponse.setAdapter(recycleResponseAdapter);

            //holder.inceptionReponse.setLayoutParams(new LinearLayout.LayoutParams(500, 500));

            //System.out.println("Position " + position + " fait avec succès.");
        }
        catch (Exception e) {
            System.out.println("Position " + position + " échoué...");

            System.out.println("Erreur: " + e.toString());

            for (int i = 0; i < e.getStackTrace().length; i++) {
                System.out.println("Erreur" + i + ": " + e.getStackTrace()[i]);
            }
        }
    }

    public void setThreadList(ArrayList<JSONObject> jsonList) {

        this.responseList = jsonList;
    }

    @Override
    public int getItemCount() {

        return this.responseList.size();
    }

    public class ResponseHolder extends RecyclerView.ViewHolder {

        TextView auteurReponse;
        TextView messReponse;
        RecyclerView inceptionReponse;

        public ResponseHolder(@NonNull View itemView) {
            super(itemView);
            auteurReponse = (TextView)itemView.findViewById(R.id.auteur);
            messReponse = (TextView)itemView.findViewById(R.id.reponse);
            inceptionReponse = (RecyclerView)itemView.findViewById(R.id.responseRecycler);
        }
    }
}
