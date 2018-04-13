package app.familyexpensetracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import android.view.View;

import java.util.Calendar;

public class addExpense extends expenselist {
    public static final String EXTRA_MESSAGE = "";
    public static final String EXTRA_MESSAGE2 = "";
    String person;
    dbHelper myDb ;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
         et = (EditText) findViewById(R.id.amounttext);
        et.setOnClickListener(this);
        Intent intent = getIntent();
         person = intent.getStringExtra("NAME");

    }

    public void onClick(View v) {
        Calendar calender = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this,this,calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        monthOfYear= monthOfYear+1;
        et.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
    }


        public void displayExp(View view)
        {
            myDb = new dbHelper(this);

            Spinner sp = (Spinner) findViewById(R.id.spinner3);
            EditText editText = (EditText) findViewById(R.id.expensetext);
            EditText editText2 = (EditText) findViewById(R.id.amounttext);
            Double amount = Double.parseDouble(editText.getText().toString());
            String date = editText2.getText().toString();
            String category = sp.getSelectedItem().toString();

            myDb.insertData(person,date,category,amount);

            Intent intent = new Intent(this, expenselist.class);
            intent.putExtra("NAME",person);
            startActivity(intent);
        }


    }
