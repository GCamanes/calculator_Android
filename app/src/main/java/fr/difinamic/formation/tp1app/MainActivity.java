package fr.difinamic.formation.tp1app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.sentry.Sentry;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Text view to show typing caracters and show resuslts of operation
    private TextView textResults;
    // Variable to save first member value of an operation
    private Double previousValue = 0.0;
    // Variable to save the operator specified by the user
    private String operator;

    // Keys to communicate between view portrait and landscape
    private final String textKey= "text";
    private final String previousValueKey = "previousValue";
    private final String operatorKey = "operator";

    private ArrayList<Equation> historic = new ArrayList<Equation>();

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

        historic.add(new Equation(a, b, result, op));
        return result;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        android.content.Context ctx = this.getApplicationContext();

        // Use the Sentry DSN (client key) from the Project Settings page on Sentry
        String sentryDsn = "https://9922bc1aa7394849a17ed40f223f850b@sentry.io/1365605";
        Sentry.init(sentryDsn, new AndroidSentryClientFactory(ctx));


        //
        textResults = (TextView) findViewById(R.id.text_results);

        if (savedInstanceState != null) {
            textResults.setText(savedInstanceState.getString("text"));
            previousValue = savedInstanceState.getDouble("previousValue");
            operator = savedInstanceState.getString("operator");
        }

        // Definition of a listener to set the behavior of digit buttons
        View.OnClickListener onDigitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sentry.getContext().recordBreadcrumb(
                    new BreadcrumbBuilder().setMessage("User tap on a digit").build()
                );

                String stringToAdd = ((Button) v).getText().toString();

                Sentry.capture("digit " + stringToAdd);

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
                intentInfo.putParcelableArrayListExtra("historic", MainActivity.this.historic);
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

    // Function save data (called when onCreate() is called)
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(textKey, textResults.getText().toString());
        outState.putDouble(previousValueKey, MainActivity.this.previousValue);
        outState.putString(operatorKey, MainActivity.this.operator);
    }
}
