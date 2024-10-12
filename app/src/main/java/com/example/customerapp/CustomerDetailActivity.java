package com.example.customerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import com.example.customerapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerDetailActivity extends AppCompatActivity {

    private TextView nameTextView, codeTextView, modelTextView, serialTextView, batteryTextView,
            simTextView, slotTextView, doorTextView, memoryTextView, passwordTextView, patternTextView;
    private CustomerDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        dbHelper = new CustomerDatabaseHelper(this);

        nameTextView = findViewById(R.id.nameTextView);
        codeTextView = findViewById(R.id.codeTextView);
        modelTextView = findViewById(R.id.modelTextView);
        serialTextView = findViewById(R.id.serialTextView);
        batteryTextView = findViewById(R.id.batteryTextView);
        simTextView = findViewById(R.id.simTextView);
        slotTextView = findViewById(R.id.slotTextView);
        doorTextView = findViewById(R.id.doorTextView);
        memoryTextView = findViewById(R.id.memoryTextView);
        passwordTextView = findViewById(R.id.passwordTextView);
        patternTextView = findViewById(R.id.patternTextView);

        Intent intent = getIntent();
        long customerId = intent.getLongExtra("CUSTOMER_ID", -1);
        loadCustomerDetails(customerId);
    }

    private void loadCustomerDetails(long customerId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CustomerDatabaseHelper.TABLE_CUSTOMERS,
                null,
                CustomerDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(customerId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_NAME));
            String code = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_CODE));
            String model = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_MODEL));
            String serial = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_SERIAL));
            boolean battery = cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_BATTERY)) == 1;
            boolean sim = cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_SIM)) == 1;
            boolean slot = cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_SLOT)) == 1;
            boolean door = cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_DOOR)) == 1;
            boolean memory = cursor.getInt(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_MEMORY)) == 1;
            String password = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_PASSWORD));
            String pattern = cursor.getString(cursor.getColumnIndexOrThrow(CustomerDatabaseHelper.COLUMN_PATTERN));

            nameTextView.setText(name);
            codeTextView.setText(code);
            modelTextView.setText(model);
            serialTextView.setText(serial);
            batteryTextView.setText(battery ? "بله" : "خیر");
            simTextView.setText(sim ? "بله" : "خیر");
            slotTextView.setText(slot ? "بله" : "خیر");
            doorTextView.setText(door ? "بله" : "خیر");
            memoryTextView.setText(memory ? "بله" : "خیر");
            passwordTextView.setText(password);
            patternTextView.setText(pattern);
        }
        cursor.close();
    }
}

