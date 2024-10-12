package com.example.customerapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.customerapp.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CustomersActivity extends AppCompatActivity {

    private CustomerDatabaseHelper dbHelper;
    private ListView customersListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> customersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        dbHelper = new CustomerDatabaseHelper(this);
        customersListView = findViewById(R.id.customersListView);
        customersList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customersList);
        customersListView.setAdapter(adapter);

        loadCustomers();
    }

    private void loadCustomers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CustomerDatabaseHelper.TABLE_CUSTOMERS,
                null, null, null, null, null, null);

        customersList.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_NAME));
            String code = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_CODE));
            customersList.add(name + " - " + code);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
