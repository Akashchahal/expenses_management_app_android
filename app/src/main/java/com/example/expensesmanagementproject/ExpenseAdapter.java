package com.example.expensesmanagementproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private List<ExpenseModel> expenseList;
    private DatabaseHelper databaseHelper;

    private Context context;
    public ExpenseAdapter(List<ExpenseModel> expenseList,DatabaseHelper databaseHelper,Context context) {

        this.context=context;
        this.expenseList = expenseList;
        this.databaseHelper=databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExpenseModel expense = expenseList.get(position);
        holder.nameTextView.setText(expense.getName());
        holder.amountTextView.setText(String.valueOf(expense.getAmount()));
        holder.dateTextView.setText(expense.getDated());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj=new Intent(context,EditExpenseActivity.class);
                obj.putExtra("name",expense.getName());
                obj.putExtra("id",expense.getId());
                obj.putExtra("amount",expense.getAmount());

                context.startActivity(obj);
            }
        });

        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=expense.getId();
                databaseHelper.deleteExpense(id);
                expenseList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView amountTextView;
        TextView dateTextView;

        Button delButton;

        Button editButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.expense_name_text);
            amountTextView = itemView.findViewById(R.id.expense_amount_text);
            dateTextView = itemView.findViewById(R.id.expense_date_text);

            delButton=itemView.findViewById(R.id.delete_button);

            editButton=itemView.findViewById(R.id.edit_button);
        }
    }
}
