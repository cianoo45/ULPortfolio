package app.familyexpensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by user on 01/03/2018.
 */

public class setup extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);



     final   Spinner kids = (Spinner) findViewById(R.id.spinner);
        final Spinner money = (Spinner) findViewById(R.id.spinner2);
        Button nxt = (Button) findViewById(R.id.nxt);


        nxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                int nokids = kids.getSelectedItemPosition();
                String currency = money.getSelectedItem().toString();


                editor.putInt("kids",nokids);
                editor.putString("currency",currency);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(),names.class);

               startActivity(intent);




            }
        });







    }






}
