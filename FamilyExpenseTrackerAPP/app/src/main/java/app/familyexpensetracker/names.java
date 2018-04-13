package app.familyexpensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user on 01/03/2018.
 */

public class names extends AppCompatActivity {

    String g1,g2,c1,c2,c3,c4;


    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        super.onCreate(savedInstanceState);

        int nokids = pref.getInt("kids",0);
        switch (nokids) {

            case 0:
                setContentView(R.layout.names);
                getInfo();
                break;
            case 1:
                setContentView(R.layout.names2);
                getInfo2();

                break;
            case 2:
                setContentView(R.layout.names3);
                getInfo3();
                break;
            case 3:
                setContentView(R.layout.names4);
                getInfo4();
                break;
        }




    }

    private void getInfo() {



      final   EditText et1 = (EditText) findViewById(R.id.g1);
       final EditText et2 = (EditText) findViewById(R.id.g2);
        final EditText et3 = (EditText) findViewById(R.id.k1);
        Button nxt = (Button) findViewById(R.id.nxt1);

        nxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                g1 =  et1.getText().toString();
                g2 = et2.getText().toString();
                c1 = et3.getText().toString();

                editor.putString("g1",g1);
                editor.putString("g2",g2);
                editor.putString("c1",c1);
                editor.putBoolean("setupdone",true);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }


    private void getInfo2() {

        final   EditText et1 = (EditText) findViewById(R.id.g_2_1);
        final EditText et2 = (EditText) findViewById(R.id.g_2_2);
        final EditText et3 = (EditText) findViewById(R.id.k_2_1);
        final EditText et4 = (EditText) findViewById(R.id.k_2_2);
        Button nxt = (Button) findViewById(R.id.nxt2);

        nxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                g1 =  et1.getText().toString();
                g2 = et2.getText().toString();
                c1 = et3.getText().toString();
                c2 = et4.getText().toString();

                editor.putString("g1",g1);
                editor.putString("g2",g2);
                editor.putString("c1",c1);
                editor.putString("c2",c2);
                editor.putBoolean("setupdone",true);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getInfo3() {

        final   EditText et1 = (EditText) findViewById(R.id.g_3_1);
        final EditText et2 = (EditText) findViewById(R.id.g_3_2);
        final EditText et3 = (EditText) findViewById(R.id.k_3_1);
        final EditText et4 = (EditText) findViewById(R.id.k_3_2);
        final EditText et5 = (EditText) findViewById(R.id.k_3_3);
        Button nxt = (Button) findViewById(R.id.nxt3);

        nxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                g1 =  et1.getText().toString();
                g2 = et2.getText().toString();
                c1 = et3.getText().toString();
                c2 = et4.getText().toString();
                c3 = et4.getText().toString();


                editor.putString("g1",g1);
                editor.putString("g2",g2);
                editor.putString("c1",c1);
                editor.putBoolean("setupdone",true);
                editor.putString("c2",c2);
                editor.putString("c3",c3);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getInfo4() {
        final   EditText et1 = (EditText) findViewById(R.id.g_4_1);
        final EditText et2 = (EditText) findViewById(R.id.g_4_2);
        final EditText et3 = (EditText) findViewById(R.id.k_4_1);
        final EditText et4 = (EditText) findViewById(R.id.k_4_2);
        final EditText et5 = (EditText) findViewById(R.id.k_4_3);
        final EditText et6 = (EditText) findViewById(R.id.k_4_4);
        Button nxt = (Button) findViewById(R.id.nxt4);

        nxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                g1 =  et1.getText().toString();
                g2 = et2.getText().toString();
                c1 = et3.getText().toString();
                c2 = et4.getText().toString();
                editor.putBoolean("setupdone",true);
                c3 = et5.getText().toString();
                c4 = et6.getText().toString();


                editor.putString("g1",g1);
                editor.putString("g2",g2);
                editor.putString("c1",c1);
                editor.putString("c2",c2);
                editor.putString("c3",c3);
                editor.putString("c4",c4);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
