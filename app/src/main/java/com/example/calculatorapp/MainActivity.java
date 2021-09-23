package com.example.calculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";


    // variables to hold the operands and the type of calculations
    private Double operand1 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);

        Button buttonDot = (Button) findViewById(R.id.buttonDot);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonSubtract = (Button) findViewById(R.id.buttonSubstract);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);

//        button0.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Button b = (Button) v;
//                newNumber.append(b.getText().toString());
//            }
//        });

        //waiting for a click on the view
        View.OnClickListener ourOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };
        //once something is clicked on the view, it is transferred to this button
        button0.setOnClickListener(ourOnClickListener);
        button1.setOnClickListener(ourOnClickListener);
        button2.setOnClickListener(ourOnClickListener);
        button3.setOnClickListener(ourOnClickListener);
        button4.setOnClickListener(ourOnClickListener);
        button5.setOnClickListener(ourOnClickListener);
        button6.setOnClickListener(ourOnClickListener);
        button7.setOnClickListener(ourOnClickListener);
        button8.setOnClickListener(ourOnClickListener);
        button9.setOnClickListener(ourOnClickListener);
        buttonDot.setOnClickListener(ourOnClickListener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view; //we're using this bc there are different operation buttons
                String op = b.getText().toString();
                String value = newNumber.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                }   catch(NumberFormatException e){
                        newNumber.setText("");
                }

                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonDivide.setOnClickListener(opListener);
        buttonSubtract.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonEquals.setOnClickListener(opListener);

        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);

        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = newNumber.getText().toString();
                if(value.length() == 0){
                    newNumber.setText("-");
                } else{
                    try{
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e){
                        //newNumber was "-" or "."
                        newNumber.setText("");
                    }
                }
            }
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if(operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(Double value, String operation){

        if( null == operand1){
            operand1 = (value);
        } else{

            if(pendingOperation.equals("=")){
                pendingOperation = operation;
            }

            switch (pendingOperation){
                case "=" :
                    operand1 = value;
                    break;
                case "/" :
                    if(value ==0){
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*" :
                    operand1 *= value;
                    break;
                case "-" :
                    operand1 -= value;
                    break;

                case "+" :
                    operand1 += value;
                    break;
            }

        }

        result.setText(operand1.toString());
        newNumber.setText("");

    }

}