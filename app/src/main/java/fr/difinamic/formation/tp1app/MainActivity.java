package fr.difinamic.formation.tp1app;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Text view to show typing caracters and show resuslts of operation
    private TextView textResults;
    // Variable to save first member value of an operation
    private Double previousValue = 0.0;
    // Variable to save the operator specified by the user
    private String operator;

    // Function to resolve equation
    private Double resolve(Double a, Double b, String op) {
        double result = 0.0;
        switch (op) {
            case "+":
                result = a + b; break;
            case "-":
                result = a - b; break;
            case "*":
                result = a * b; break;
            case "/":
                result = a / b; break;
        }
        return result;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        textResults = (TextView) findViewById(R.id.text_results);

        // Definition of a listener to set the behavior of digit buttons
        View.OnClickListener onDigitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringToAdd = ((Button) v).getText().toString();

                if (textResults.getText().equals("0")) {
                    textResults.setText(stringToAdd);
                } else {
                    textResults.setText(textResults.getText() + stringToAdd);
                }

            }
        };

        // Definition of a listener to set the behavior of operator buttons
        View.OnClickListener onOperatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textResults.getText().toString().isEmpty()) {
                    if (operator != null) {
                        previousValue = resolve(previousValue, Double.parseDouble(textResults.getText().toString()), operator);
                    } else {
                        previousValue = Double.parseDouble(textResults.getText().toString());
                    }
                    operator = ((Button) v).getText().toString();
                    textResults.setText("0");
                }
            }
        };

        // Get all digit and operator button and add corresponding listener
        findViewById(R.id.button_digit0).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit1).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit2).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit3).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit4).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit5).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit6).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit7).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit8).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digit9).setOnClickListener(onDigitListener);
        findViewById(R.id.button_digitPlus).setOnClickListener(onOperatorListener);
        findViewById(R.id.button_digitSous).setOnClickListener(onOperatorListener);
        findViewById(R.id.button_digitMult).setOnClickListener(onOperatorListener);
        findViewById(R.id.button_digitDiv).setOnClickListener(onOperatorListener);

        findViewById(R.id.button_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent(MainActivity.this, InfoActivity.class);
                intentInfo.putExtra("infos", "infos");
                startActivity(intentInfo);
            }
        });

        // Get dot button and a listener
        findViewById(R.id.button_digitDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textResults.getText().toString().contains(".")) {
                    textResults.setText(textResults.getText()+".");
                }
            }
        });

        // Get cancel button and a listener
        findViewById(R.id.button_digitC).setOnClickListener(v -> {
            textResults.setText("");
            previousValue = 0.0; operator = null;
        });

        // Get equal button and a listener
        findViewById(R.id.button_digitEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (operator != null) {
                    Double currentValue = Double.parseDouble(textResults.getText().toString());
                    previousValue = resolve(previousValue, currentValue, operator);
                    textResults.setText(previousValue.toString());
                    operator = null;
                }
            }
        });


    }
}
