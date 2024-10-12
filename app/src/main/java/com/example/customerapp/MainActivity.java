package com.example.customerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.customerapp.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CustomerDatabaseHelper dbHelper;
    private ListView customerListView;
    private ArrayAdapter<String> customerAdapter;
    private List<String> customerList;
    private List<Long> customerIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new CustomerDatabaseHelper(this);
        customerListView = findViewById(R.id.customerListView);

        loadCustomers();

        findViewById(R.id.nav_home).setOnClickListener(v -> {
            // Home is already active
        });

        findViewById(R.id.nav_add).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_about).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.nav_search).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        customerListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, EditCustomerActivity.class);
            intent.putExtra("customerId", customerIds.get(position));
            startActivity(intent);
        });
    }

    private void loadCustomers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CustomerDatabaseHelper.TABLE_CUSTOMERS, null, null, null, null, null, null);

        customerList = new ArrayList<>();
        customerIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            customerList.add(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_NAME)));
            customerIds.add(cursor.getLong(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_ID)));
        }
        cursor.close();

        customerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerList);
        customerListView.setAdapter(customerAdapter);
    }
}


