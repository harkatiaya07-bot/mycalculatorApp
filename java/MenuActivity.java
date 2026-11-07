package com.example.myfirstapplication;



import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(MenuActivity.this, menuButton);

            popup.getMenuInflater().inflate(
                    R.menu.calculator, popup.getMenu());


            popup.setOnMenuItemClickListener(item -> {

                int id = item.getItemId();

                if (id == R.id.standard) {
                    Intent intent = new Intent(
                            MenuActivity.this,
                            StandardCalculatorActivity.class
                    );

                    startActivity(intent);

                } else if (id == R.id.scientific) {
                    Intent intent = new Intent(
                            MenuActivity.this,
                            ScientificCalculatorActivity.class
                    );

                    startActivity(intent);


                } else if (id == R.id.graphics) {
                    Intent intent = new Intent(
                            MenuActivity.this,
                            GraphicsCalculatorActivity.class
                    );

                    startActivity(intent);
                    return true;
                }

                return true;
            });

            popup.show();
        });
    }
}
