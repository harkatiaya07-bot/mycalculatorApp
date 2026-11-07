package com.example.myfirstapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GraphicsCalculatorActivity extends AppCompatActivity {

    GraphView graphView;
    Spinner functionSpinner;
    EditText minInput, maxInput;
    Button drawButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_calculator);

        graphView = findViewById(R.id.graph_view);
        functionSpinner = findViewById(R.id.function_spinner);
        minInput = findViewById(R.id.min_input);
        maxInput = findViewById(R.id.max_input);
        drawButton = findViewById(R.id.draw_button);

        minInput.setText("-10");
        maxInput.setText("10");

        drawButton.setOnClickListener(v -> {

            try {
                float minimum = Float.parseFloat(
                        minInput.getText().toString());

                float maximum = Float.parseFloat(
                        maxInput.getText().toString());

                if (minimum >= maximum) {
                    Toast.makeText(this,
                            "Maximum must be greater than minimum",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int selected = functionSpinner.getSelectedItemPosition();

                graphView.setRange(minimum, maximum);
                graphView.setFunction(selected);

            } catch (Exception e) {
                Toast.makeText(this,
                        "Please enter valid numbers",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
