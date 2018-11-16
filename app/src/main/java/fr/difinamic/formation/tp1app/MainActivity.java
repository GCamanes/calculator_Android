package fr.difinamic.formation.tp1app;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonDigit0, buttonDigit1, buttonDigit2, buttonDigit3, buttonDigit4,
        buttonDigit5, buttonDigit6, buttonDigit7, buttonDigit8, buttonDigit9,
        buttonDigitPlus, buttonDigitSous, buttonDigitMult, buttonDigitDiv,
        buttonDigitEqual, buttonDigitC, buttonDigitDot;

    private TextView textResults;

    private Double previousValue = 0.0;

    private String operator;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResults = (TextView) findViewById(R.id.text_results);

        buttonDigit0 = (Button) findViewById(R.id.button_digit0);
        buttonDigit1 = (Button) findViewById(R.id.button_digit1);
        buttonDigit2 = (Button) findViewById(R.id.button_digit2);
        buttonDigit3 = (Button) findViewById(R.id.button_digit3);
        buttonDigit4 = (Button) findViewById(R.id.button_digit4);
        buttonDigit5 = (Button) findViewById(R.id.button_digit5);
        buttonDigit6 = (Button) findViewById(R.id.button_digit6);
        buttonDigit7 = (Button) findViewById(R.id.button_digit7);
        buttonDigit8 = (Button) findViewById(R.id.button_digit8);
        buttonDigit9 = (Button) findViewById(R.id.button_digit9);
        buttonDigitPlus = (Button) findViewById(R.id.button_digitPlus);
        buttonDigitSous = (Button) findViewById(R.id.button_digitSous);
        buttonDigitMult = (Button) findViewById(R.id.button_digitMult);
        buttonDigitDiv = (Button) findViewById(R.id.button_digitDiv);
        buttonDigitEqual = (Button) findViewById(R.id.button_digitEqual);
        buttonDigitC = (Button) findViewById(R.id.button_digitC);
        buttonDigitDot = (Button) findViewById(R.id.button_digitDot);

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

        buttonDigit0.setOnClickListener(onDigitListener);
        buttonDigit1.setOnClickListener(onDigitListener);
        buttonDigit2.setOnClickListener(onDigitListener);
        buttonDigit3.setOnClickListener(onDigitListener);
        buttonDigit4.setOnClickListener(onDigitListener);
        buttonDigit5.setOnClickListener(onDigitListener);
        buttonDigit6.setOnClickListener(onDigitListener);
        buttonDigit7.setOnClickListener(onDigitListener);
        buttonDigit8.setOnClickListener(onDigitListener);
        buttonDigit9.setOnClickListener(onDigitListener);


        View.OnClickListener onOperatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textResults.getText().toString().isEmpty()) {
                    previousValue = Double.parseDouble(textResults.getText().toString());
                    operator = ((Button) v).getText().toString();
                    textResults.setText("0");
                }
            }
        };

        buttonDigitPlus.setOnClickListener(onOperatorListener);
        buttonDigitSous.setOnClickListener(onOperatorListener);
        buttonDigitMult.setOnClickListener(onOperatorListener);
        buttonDigitDiv.setOnClickListener(onOperatorListener);

        buttonDigitDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textResults.getText().toString().contains(".")) {
                    textResults.setText(textResults.getText()+".");
                }
            }
        });

        buttonDigitC.setOnClickListener(v -> {textResults.setText(""); previousValue = 0.0; operator = null;});


        buttonDigitEqual.setOnClickListener(new View.OnClickListener() {
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
