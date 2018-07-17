package io.github.swarajsaaj.otpblogdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static String[] separated;
    DBHelper db;
    ArrayList<String> array_list;
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text",messageText);
                Toast.makeText(MainActivity.this,"Message : "+messageText,Toast.LENGTH_LONG).show();

                String CurrentString = messageText;
                separated = CurrentString.split(",");

                if(separated[0].equals("#0#"))
                {
                    db.insertContact(separated[2], separated[1
                            ], separated[3], separated[4], separated[5], separated[6]);
                    Intent i= new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });


        db = new DBHelper(this);

        listView = (ListView) findViewById(R.id.list);

        array_list = new ArrayList<String>();
        array_list= db.getAllData();

        ArrayList<String> Titles = new ArrayList<String>();
        for(int i=0; i<array_list.size()/6; i++) {
            Titles.add("LHW-Code : "+array_list.get((i*6)+1)+ " \nUC : "+array_list.get((i*6)+0)+" \nHead-Name : " + array_list.get((i*6)+2)+ "\nHead-Number: "+ array_list.get((i*6)+3) + "\nSex : "+ array_list.get((i*6)+4)+ "\nUn-Reg Children : "+ array_list.get((i*6)+5));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Titles);


        // Assign adapter to ListView
        listView.setAdapter(adapter);



    }
}
