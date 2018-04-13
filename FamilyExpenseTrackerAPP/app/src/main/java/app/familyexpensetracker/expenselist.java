package app.familyexpensetracker;

import android.content.Intent;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by user on 13/03/2018.
 */

public class expenselist extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText editText;
    dbHelper myDb;
    TextView tvAmount;
    String person;
   public static  ArrayList<String> result1;
   Spinner sp;
   String currency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenselist);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        currency = pref.getString("currency","Euro");

        Intent intent = getIntent();
        sp = (Spinner) findViewById(R.id.sp) ;
        sp.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        getExpenses();
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        person = intent.getStringExtra("NAME");
        editText = (EditText)findViewById(R.id.Birthday);
        getExpenses();
        editText.setOnClickListener(this);





    }
    public void adExpense(View view)
    {
        Intent intent = new Intent(this,addExpense.class);
        intent.putExtra("NAME",person);
        startActivity(intent);
    }


    public void onCategoryChanged(View v){
        getExpenses();
    }

    @Override
    public void onClick(View v) {
        Calendar calender = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this,this,calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        monthOfYear= monthOfYear+1;
        editText.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        getExpenses();
    }

   public void getExpenses() {



        myDb = new dbHelper(this);


        TextView amount = (TextView) findViewById(R.id.number);
        Intent intent = getIntent();
        String person = intent.getStringExtra("NAME");
        Cursor res;

        String date = editText.getText().toString();

        if(sp.getSelectedItemPosition()==0) {
             res = myDb.getData(person, date);
        }else {
             res = myDb.getData(person,date,sp.getSelectedItem().toString());
        }


        String result ="";
         result1 = new ArrayList<>();
        int tamount =0;
        String s = "   ";
        String uCurrency="\u20AC";

       if (currency.equals("Sterling"))
            uCurrency = "\u00A3";
        else if (currency.equals("Dollar"))
            uCurrency = "\u0024";



        while(res.moveToNext()){
            result =s;
            result+=res.getString(3)+s;
            result+=res.getString(2)+s;
            result+=uCurrency+res.getString(4);
            result1.add(result);
            tamount+=Integer.parseInt(res.getString(4));
        }


       customListAdapter adapter=new customListAdapter(this);
       ListView lv = (ListView) findViewById(R.id.listview1);
       lv.setAdapter(adapter);


        String amount1 = String.valueOf(tamount);
        amount.setText(uCurrency+amount1);


    }
}
