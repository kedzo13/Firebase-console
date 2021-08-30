package com.karlo.zavrsni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG  =MojFBCMservis.class.getSimpleName();
    private TextView tToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tToken=findViewById(R.id.printToken);
    }

    public void getToken(View view) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if(!task.isSuccessful()){
                    Log.e(TAG, "Neuspješno dohvaćanje tokena");
                    return;
                }
                String token = task.getResult();
                tToken.setText(token);
                Log.d(TAG, "Token"+token);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Neuspješno dohvaćanje tokena"+e.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "On new Intent");
    }

    public void pretplata(View view) {
        FirebaseMessaging.getInstance().subscribeToTopic("akcije").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Prijava na obavijesti", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Neuspješna prijava", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void otkazi(View view) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("akcije").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Obavijesti uspješno otkazane", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Neuspješno otkazivanje pretplate", Toast.LENGTH_SHORT).show();
            }
        });
    }
}