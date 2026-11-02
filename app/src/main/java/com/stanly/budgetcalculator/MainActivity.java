package com.stanly.budgetcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import android.text.InputType;
import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText salary, business, otherIncome;
    ArrayList<EditText> expenses = new ArrayList<>();
    EditText savings;
    TextView result;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.WHITE);

        scroll.addView(layout);
        setContentView(scroll);

        addTitle("MY BUDGET CALCULATOR");
        addSubtitle("Manage your money wisely");

        addTitle("INCOME");

        salary = addInput("Monthly salary (MWK)");
        business = addInput("Business income (MWK)");
        otherIncome = addInput("Other income (MWK)");

        addTitle("EXPENSES");

        String[] categories = {
            "Food",
            "Transport",
            "House rent",
            "Electricity",
            "Water",
            "School fees",
            "Airtime and Internet",
            "Medical expenses",
            "Clothing",
            "Other expenses"
        };

        for (String category : categories) {
            expenses.add(addInput(category + " (MWK)"));
        }

        addTitle("SAVINGS");

        savings = addInput("Total savings (MWK)");

        Button calculate = new Button(this);
        calculate.setText("CALCULATE MY BUDGET");
        layout.addView(calculate);

        result = new TextView(this);
        result.setTextSize(20);
        result.setPadding(10, 30, 10, 30);
        result.setTextColor(Color.BLACK);
        layout.addView(result);

        calculate.setOnClickListener(v -> calculateBudget());
    }

    private void addTitle(String text) {
        TextView title = new TextView(this);
        title.setText(text);
        title.setTextSize(21);
        title.setTextColor(Color.rgb(20, 90, 60));
        title.setPadding(0, 25, 0, 15);
        layout.addView(title);
    }

    private void addSubtitle(String text) {
        TextView subtitle = new TextView(this);
        subtitle.setText(text);
        subtitle.setTextSize(16);
        subtitle.setTextColor(Color.DKGRAY);
        layout.addView(subtitle);
    }

    private EditText addInput(String hint) {
        EditText input = new EditText(this);
        input.setHint(hint);
        input.setInputType(
            InputType.TYPE_CLASS_NUMBER |
            InputType.TYPE_NUMBER_FLAG_DECIMAL
        );

        layout.addView(input);
        return input;
    }

    private double getValue(EditText input) {
        String value = input.getText().toString().trim();

        if (value.isEmpty()) {
            return 0;
        }

        try {
            double number = Double.parseDouble(value);

            if (Double.isNaN(number) ||
                Double.isInfinite(number) ||
                number < 0) {
                throw new NumberFormatException();
            }

            return number;

        } catch (NumberFormatException e) {
            input.setError("Enter a valid amount");
            throw e;
        }
    }

    private void calculateBudget() {

        try {
            double totalIncome =
                getValue(salary) +
                getValue(business) +
                getValue(otherIncome);

            double totalExpenses = 0;

            for (EditText expense : expenses) {
                totalExpenses += getValue(expense);
            }

            double totalSavings = getValue(savings);

            double balance =
                totalIncome - totalExpenses - totalSavings;

            double spentPercentage = totalIncome > 0
                ? (totalExpenses / totalIncome) * 100 : 0;

            String status = balance < 0
                ? "WARNING: You are over budget!"
                : "Your budget is balanced.";

            result.setText(String.format(
                java.util.Locale.US,
                "BUDGET SUMMARY\n\n" +
                "Total Income: MWK %,.2f\n" +
                "Total Expenses: MWK %,.2f\n" +
                "Total Savings: MWK %,.2f\n" +
                "Remaining Balance: MWK %,.2f\n" +
                "Income Spent: %.1f%%\n\n%s",
                totalIncome,
                totalExpenses,
                totalSavings,
                balance,
                spentPercentage,
                status
            ));

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                "Please correct the invalid amount.",
                Toast.LENGTH_SHORT).show();
        }
    }
}