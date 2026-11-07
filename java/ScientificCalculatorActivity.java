package com.example.myfirstapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScientificCalculatorActivity extends AppCompatActivity {

    TextView solutionTV, resultTV;
    String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific_calculator);

        solutionTV = findViewById(R.id.solution_tv);
        resultTV = findViewById(R.id.result_tv);

        int[] numberIds = {
                R.id.button_0, R.id.button_1, R.id.button_2,
                R.id.button_3, R.id.button_4, R.id.button_5,
                R.id.button_6, R.id.button_7, R.id.button_8,
                R.id.button_9
        };

        for (int id : numberIds) {
            Button button = findViewById(id);

            button.setOnClickListener(v -> addText(
                    button.getText().toString()));
        }

        int[] operationIds = {
                R.id.button_plus,
                R.id.button_minus,
                R.id.button_multiply,
                R.id.button_divide,
                R.id.button_open,
                R.id.button_close,
                R.id.button_dot,
                R.id.button_pi
        };

        for (int id : operationIds) {
            Button button = findViewById(id);

            button.setOnClickListener(v -> {
                String value = button.getText().toString();

                if (value.equals("π")) {
                    addText(String.valueOf(Math.PI));
                } else {
                    addText(value);
                }
            });
        }

        findViewById(R.id.button_sin).setOnClickListener(v ->
                addText("sin("));

        findViewById(R.id.button_cos).setOnClickListener(v ->
                addText("cos("));

        findViewById(R.id.button_tan).setOnClickListener(v ->
                addText("tan("));

        findViewById(R.id.button_sqrt).setOnClickListener(v ->
                addText("sqrt("));

        findViewById(R.id.button_log).setOnClickListener(v ->
                addText("log("));

        findViewById(R.id.button_ln).setOnClickListener(v ->
                addText("ln("));

        findViewById(R.id.button_square).setOnClickListener(v ->
                addText("^2"));

        findViewById(R.id.button_power).setOnClickListener(v ->
                addText("^"));

        findViewById(R.id.button_ac).setOnClickListener(v -> {
            expression = "";
            solutionTV.setText("");
            resultTV.setText("0");
        });


        findViewById(R.id.button_equals).setOnClickListener(v ->
                calculate());
    }

    private void addText(String value) {
        expression += value;
        solutionTV.setText(expression);
    }

    private void calculate() {
        try {
            double result = evaluate(expression);

            if (!Double.isFinite(result)) {
                throw new ArithmeticException();
            }

            String answer;

            if (result == (long) result) {
                answer = String.valueOf((long) result);
            } else {
                answer = String.valueOf(result);
            }

            resultTV.setText(answer);
            expression = answer;

        } catch (Exception e) {
            Toast.makeText(this, "Invalid expression",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private double evaluate(String input) {

        input = input.replace(" ", "");

        if (input.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return parseExpression(input);
    }

    private double parseExpression(String input) {

        if (input.startsWith("sin(") && input.endsWith(")")) {
            double value = parseExpression(
                    input.substring(4, input.length() - 1));
            return Math.sin(Math.toRadians(value));
        }

        if (input.startsWith("cos(") && input.endsWith(")")) {
            double value = parseExpression(
                    input.substring(4, input.length() - 1));
            return Math.cos(Math.toRadians(value));
        }

        if (input.startsWith("tan(") && input.endsWith(")")) {
            double value = parseExpression(
                    input.substring(4, input.length() - 1));
            return Math.tan(Math.toRadians(value));
        }

        if (input.startsWith("sqrt(") && input.endsWith(")")) {
            double value = parseExpression(
                    input.substring(5, input.length() - 1));
            return Math.sqrt(value);
        }

        if (input.startsWith("log(") && input.endsWith(")")) {
            double value = parseExpression(
                    input.substring(4, input.length() - 1));
            return Math.log10(value);
        }

        if (input.startsWith("ln(") && input.endsWith(")")) {
            double value = parseExpression(
                    input.substring(3, input.length() - 1));
            return Math.log(value);
        }

        if (input.startsWith("(") && input.endsWith(")")) {
            return parseExpression(
                    input.substring(1, input.length() - 1));
        }

        int powerPosition = input.lastIndexOf("^");

        if (powerPosition > 0) {
            double first = parseExpression(
                    input.substring(0, powerPosition));
            double second = parseExpression(
                    input.substring(powerPosition + 1));

            return Math.pow(first, second);
        }

        for (int i = input.length() - 1; i > 0; i--) {
            char operator = input.charAt(i);

            if (operator == '+' || operator == '-') {
                double first = parseExpression(input.substring(0, i));
                double second = parseExpression(input.substring(i + 1));

                if (operator == '+') return first + second;
                return first - second;
            }
        }

        for (int i = input.length() - 1; i > 0; i--) {
            char operator = input.charAt(i);

            if (operator == '*' || operator == '/') {
                double first = parseExpression(input.substring(0, i));
                double second = parseExpression(input.substring(i + 1));

                if (operator == '*') return first * second;

                if (second == 0) {
                    throw new ArithmeticException();
                }

                return first / second;
            }
        }

        return Double.parseDouble(input);
    }
}
