package com.example.customerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.customerapp.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private CustomerDatabaseHelper dbHelper;
    private ListView searchListView;
    private ArrayAdapter<String> searchAdapter;
    private List<String> searchResults;
    private List<Long> customerIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbHelper = new CustomerDatabaseHelper(this);
        searchListView = findViewById(R.id.searchListView);

        EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchCustomers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchResults = new ArrayList<>();
        customerIds = new ArrayList<>();
        searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        searchListView.setAdapter(searchAdapter);

        searchListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(SearchActivity.this, EditCustomerActivity.class);
            intent.putExtra("customerId", customerIds.get(position));
            startActivity(intent);
        });
    }

    private void searchCustomers(String query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CustomerDatabaseHelper.TABLE_CUSTOMERS,
                new String[]{CustomerDatabaseHelper.COLUMN_ID, CustomerDatabaseHelper.COLUMN_NAME},
                CustomerDatabaseHelper.COLUMN_NAME + " LIKE ?",
                new String[]{"%" + query + "%"},
                null,
                null,
                null
        );

        searchResults.clear();
        customerIds.clear();
        while (cursor.moveToNext()) {
            searchResults.add(cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_NAME)));
            customerIds.add(cursor.getLong(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_ID)));
        }
        cursor.close();
        searchAdapter.notifyDataSetChanged();
    }
}

