package app.familyexpensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    dbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean done = pref.getBoolean("setupdone",false);
        int nokids = pref.getInt("kids",1);


        if(done==false){
            Intent intent = new Intent(this,setup.class);
            startActivity(intent);
        }else {

            switch(nokids){
                case 0: setContentView(R.layout.kid_1_layout);
                    setNames(); break;
                case 1: setContentView(R.layout.activity_main);
                    setNames2();break;
                case 2: setContentView(R.layout.kid_3_layout);
                    setNames3(); break;
                case 3: setContentView(R.layout.kid_4_layout);
                    setNames4();break;
            }

        }

    }

    private void setNames4() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        TextView g1 = (TextView) findViewById(R.id.tg_4_1);
        g1.setText(pref.getString("g1","Parent 1"));

        TextView g2 = (TextView) findViewById(R.id.tg_4_2);
        g2.setText(pref.getString("g2","Parent 2"));

        TextView c1 = (TextView) findViewById(R.id.tc_4_1);
        c1.setText(pref.getString("c1","Child 1"));

        TextView c2 = (TextView) findViewById(R.id.tc_4_2);
        c2.setText(pref.getString("c2","Child 2"));

        TextView c3 = (TextView) findViewById(R.id.tc_4_3);
        c3.setText(pref.getString("c3","Child 3"));

        TextView c4 = (TextView) findViewById(R.id.tc_4_4);
        c4.setText(pref.getString("c4","Child 4"));
    }

    private void setNames3() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        TextView g1 = (TextView) findViewById(R.id.tg_3_1);
        g1.setText(pref.getString("g1","Parent 1"));

        TextView g2 = (TextView) findViewById(R.id.tg_3_2);
        g2.setText(pref.getString("g2","Parent 2"));

        TextView c1 = (TextView) findViewById(R.id.tc_3_1);
        c1.setText(pref.getString("c1","Child 1"));

        TextView c2 = (TextView) findViewById(R.id.tc_3_2);
        c2.setText(pref.getString("c2","Child 2"));

        TextView c3 = (TextView) findViewById(R.id.tc_3_3);
        c3.setText(pref.getString("c3","Child 3"));

    }

    private void setNames2() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        TextView g1 = (TextView) findViewById(R.id.tg_2_1);
        g1.setText(pref.getString("g1","Parent 1"));

        TextView g2 = (TextView) findViewById(R.id.tg_2_2);
        g2.setText(pref.getString("g2","Parent 2"));

        TextView c1 = (TextView) findViewById(R.id.tc_2_1);
        c1.setText(pref.getString("c1","Child 1"));

        TextView c2 = (TextView) findViewById(R.id.tc_2_2);
        c2.setText(pref.getString("c2","Child 2"));




    }

    private void setNames() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        TextView g1 = (TextView) findViewById(R.id.tg_1_1);
        g1.setText(pref.getString("g1","Parent 1"));

        TextView g2 = (TextView) findViewById(R.id.tg_1_2);
        g2.setText(pref.getString("g2","Parent 2"));

        TextView c1 = (TextView) findViewById(R.id.tc_1_1);
        c1.setText(pref.getString("c1","Child 1"));


    }

    public void chooseButton(View v){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String EXTRA_MESSAGE = "";

        switch(v.getId()){
            case R.id.button1: EXTRA_MESSAGE = pref.getString("g1",null); break;
            case R.id.button2: EXTRA_MESSAGE = pref.getString("g2",null); break;
            case R.id.button3: EXTRA_MESSAGE = pref.getString("c1",null); break;
            case R.id.button4: EXTRA_MESSAGE = pref.getString("c2",null); break;
            case R.id.button5: EXTRA_MESSAGE = pref.getString("c3",null); break;
            case R.id.button6: EXTRA_MESSAGE = pref.getString("c4",null); break;



        }


        Intent intent = new Intent(this,expenselist.class);
        intent.putExtra("NAME",EXTRA_MESSAGE);
        startActivity(intent);

    }
}
