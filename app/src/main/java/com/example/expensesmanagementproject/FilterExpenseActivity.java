package com.example.expensesmanagementproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FilterExpenseActivity extends AppCompatActivity {

    private Spinner expenseSpinner;
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private ArrayList<ExpenseModel> expenseList;
    private DatabaseHelper databaseHelper;

    private static final String TABLE_EXPENSES ="tbl_expenses";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_AMOUNT="amount";
    private static final String COLUMN_DATED="dated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        expenseSpinner =findViewById(R.id.expense_Spinner);
        recyclerView =findViewById(R.id.filtered_expense_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenseList=new ArrayList<>();
        new ArrayList<>();
        databaseHelper =new DatabaseHelper(this);
        adapter=new ExpenseAdapter(expenseList,databaseHelper,this);
        recyclerView.setAdapter(adapter);

        loadExpenseNames();

        expenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Load expenses data based on the expense name
                String selectedExpenseName=(String)parent.getItemAtPosition(position);
                loadFilteredExpensesData(selectedExpenseName);
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
            }


    });
}

    private void loadExpenseNames(){
        List<String> expenseNames=databaseHelper.getAllExpenseNames();
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,expenseNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseSpinner.setAdapter(spinnerAdapter);
    }

    private void loadFilteredExpensesData(String expenseName){
        expenseList.clear();
        expenseList.addAll(databaseHelper.getExpensesByName(expenseName));
        adapter.notifyDataSetChanged();
    }
}