package com.example.myfirstapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StandardCalculatorActivity extends AppCompatActivity {

    TextView solutionTV, resultTV;

    String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_calculator);

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

            button.setOnClickListener(v -> {
                expression += button.getText().toString();
                solutionTV.setText(expression);
            });
        }

        int[] operationIds = {
                R.id.button_plus,
                R.id.button_minus,
                R.id.button_multiply,
                R.id.button_divide,
                R.id.button_open_bracket,
                R.id.button_close_bracket,
                R.id.button_dot
        };

        for (int id : operationIds) {
            Button button = findViewById(id);

            button.setOnClickListener(v -> {
                expression += button.getText().toString();
                solutionTV.setText(expression);
            });
        }

        Button buttonC = findViewById(R.id.button_c);

        buttonC.setOnClickListener(v -> {
            if (expression.length() > 0) {
                expression = expression.substring(0, expression.length() - 1);
                solutionTV.setText(expression);
            }
        });

        Button buttonAC = findViewById(R.id.button_ac);

        buttonAC.setOnClickListener(v -> {
            expression = "";
            solutionTV.setText("");
            resultTV.setText("0");
        });

        Button buttonEquals = findViewById(R.id.button_equals);

        buttonEquals.setOnClickListener(v -> calculate());
    }

    private void calculate() {

        try {
            ExpressionParser parser = new ExpressionParser(expression);

            double result = parser.parse();

            solutionTV.setText(expression);

            if (result == (long) result) {
                resultTV.setText(String.valueOf((long) result));
                expression = String.valueOf((long) result);
            } else {
                resultTV.setText(String.valueOf(result));
                expression = String.valueOf(result);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Invalid expression", Toast.LENGTH_SHORT).show();
        }
    }

    private static class ExpressionParser {

        private final String input;
        private int position = 0;

        ExpressionParser(String input) {
            this.input = input;
        }

        double parse() {
            double result = parseExpression();

            if (position != input.length()) {
                throw new IllegalArgumentException();
            }

            return result;
        }

        double parseExpression() {
            double result = parseTerm();

            while (position < input.length()) {
                char operator = input.charAt(position);

                if (operator == '+') {
                    position++;
                    result += parseTerm();
                } else if (operator == '-') {
                    position++;
                    result -= parseTerm();
                } else {
                    break;
                }
            }

            return result;
        }

        double parseTerm() {
            double result = parseFactor();

            while (position < input.length()) {
                char operator = input.charAt(position);

                if (operator == '*') {
                    position++;
                    result *= parseFactor();
                } else if (operator == '/') {
                    position++;

                    double divisor = parseFactor();

                    if (divisor == 0) {
                        throw new ArithmeticException();
                    }

                    result /= divisor;
                } else {
                    break;
                }
            }

            return result;
        }

        double parseFactor() {

            if (position < input.length()
                    && input.charAt(position) == '-') {
                position++;
                return -parseFactor();
            }

            if (position < input.length()
                    && input.charAt(position) == '+') {
                position++;
                return parseFactor();
            }

            if (position < input.length()
                    && input.charAt(position) == '(') {

                position++;

                double result = parseExpression();

                if (position >= input.length()
                        || input.charAt(position) != ')') {
                    throw new IllegalArgumentException();
                }

                position++;
                return result;
            }

            int start = position;

            while (position < input.length()
                    && (Character.isDigit(input.charAt(position))
                    || input.charAt(position) == '.')) {
                position++;
            }

            if (start == position) {
                throw new IllegalArgumentException();
            }

            return Double.parseDouble(input.substring(start, position));
        }
    }
}