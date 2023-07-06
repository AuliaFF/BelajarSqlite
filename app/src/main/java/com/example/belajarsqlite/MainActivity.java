package com.example.belajarsqlite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<MhsModel> mhsList;
    MhsModel mm;
    DbHelper db;

    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edNama = (EditText) findViewById(R.id.edNama);
        EditText edNim = (EditText) findViewById(R.id.edNim);
        EditText edNoHp = (EditText) findViewById(R.id.edNoHp);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        mhsList = new ArrayList<>();

        isEdit = false;

        Intent intent_main = getIntent();
        if (intent_main.hasExtra("mhsData")){
            mm = intent_main.getExtras().getParcelable("mhsData");
            edNama.setText(mm.getNama());
            edNim.setText(mm.getNim());
            edNoHp.setText(mm.getNoHp());

            isEdit = true;

            btnSave.setBackgroundColor(Color.DKGRAY);
            btnSave.setText("EDIT");
        }

        db = new DbHelper(getApplicationContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Isian_Nama = edNama.getText().toString();
                String Isian_Nim = edNim.getText().toString();
                String Isian_NoHp = edNoHp.getText().toString();


                if (Isian_Nama.isEmpty() || Isian_Nim.isEmpty()|| Isian_NoHp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ISIAN MASIH KOSONG", Toast.LENGTH_SHORT).show();
                } else {
                    // mhsList.add(new MhsModel(-1, Isian_Nama, Isian_Nim, Isian_NoHp));


                    boolean stts;

                    if (!isEdit){
                        mm = new MhsModel(-1, Isian_Nama, Isian_Nim, Isian_NoHp);
                        stts = db.simpan(mm);

                        edNama.setText("");
                        edNim.setText("");
                        edNoHp.setText("");

                    }else {
                        mm = new MhsModel(mm.getId(), Isian_Nama, Isian_Nim, Isian_NoHp);
                        stts = db.ubah(mm);
                    }

                    if(stts){

                        Toast.makeText(getApplicationContext(), "DATA BERHASIL DISIMPAN", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "DATA GAGAL DISIMPAN", Toast.LENGTH_SHORT).show();
                    }

                    // intent_list.putParcelableArrayListExtra("mhsList", mhsList);
                    // startActivity(intent_list);
                }
            }
        });

        Button btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mhsList = db.list();

                if (mhsList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "BELOM ADA DATA", Toast.LENGTH_SHORT).show();

                }else {
                    Intent intent_list = new Intent(MainActivity.this, ListMhsActivity.class);
                    intent_list.putParcelableArrayListExtra("mhsList", mhsList);
                    startActivity(intent_list);
                }
            }
        });
    }
}