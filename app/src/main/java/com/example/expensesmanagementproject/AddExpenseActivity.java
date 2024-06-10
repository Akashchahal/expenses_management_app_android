package com.example.expensesmanagementproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText;
    private EditText expenseAmountEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        expenseNameEditText = findViewById(R.id.expense_name_edit_text);
        expenseAmountEditText = findViewById(R.id.expense_amount_edit_text);
        saveButton = findViewById(R.id.save_button);
        databaseHelper = new DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = expenseNameEditText.getText().toString().trim();
                String expenseAmountStr = expenseAmountEditText.getText().toString().trim();
                if (!expenseName.isEmpty() && !expenseAmountStr.isEmpty()) {
                    int expenseAmount = Integer.parseInt(expenseAmountStr);
                    long newRowId = databaseHelper.addExpense(expenseName, expenseAmount);
                    if (newRowId != -1) {

                        showToast("Expense added successfully!");
                        finish();
                    } else {

                        showToast("Failed to add expense. Please try again.");
                    }
                } else {
                    // Error message for empty fields
                    showToast("Please fill in both fields.");
                }
            }
        });


    }

    private void showToast(String message) {
        Toast.makeText(AddExpenseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
