package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class findProfile extends AppCompatActivity implements View.OnClickListener{
    private EditText txt;
    private Button btnFindProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);

        txt = (EditText)findViewById(R.id.editTextProfile) ;



        btnFindProfile = (Button) findViewById(R.id.FindProfile);
        btnFindProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FindProfile:
                Intent intent = new Intent(findProfile.this, ProfilJoueur.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(findProfile.this,  txt.getText().toString(), Toast.LENGTH_LONG).show();
                intent.putExtra("user",txt.getText().toString());
                startActivity(intent);
                break;
            default:
                // rien :)
                break;
        }
    }
}