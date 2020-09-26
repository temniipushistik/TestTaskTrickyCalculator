package com.example.trickycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //the field, which will be accumulate all input information
    String field = "";
    TextView theFirstLine;
    Button one, two, three, four, five, six, seven, eight, nine, zero, plus, minus, equally, split, multiply, point, rightBracket, leftBreaket;

    public void attachButtonsAndField() {
        one = findViewById(R.id.oneButton);
        two = findViewById(R.id.twoButton);
        three = findViewById(R.id.threeButton);
        four = findViewById(R.id.fourButton);
        five = findViewById(R.id.fiveButton);
        six = findViewById(R.id.sixButton);
        seven = findViewById(R.id.sevenButton);
        eight = findViewById(R.id.eightButton);
        nine = findViewById(R.id.nineButton);
        zero = findViewById(R.id.zeroButton);
        plus = findViewById(R.id.plusButton);
        minus = findViewById(R.id.minusButton);
        equally = findViewById(R.id.doneMagickHoldButton);
        split = findViewById(R.id.splitButton);
        multiply = findViewById(R.id.multiplyButton);
        point = findViewById(R.id.point);
        leftBreaket = findViewById(R.id.leftBracket);
        rightBracket = findViewById(R.id.rightBracket);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theFirstLine = findViewById(R.id.workingTextView);
        attachButtonsAndField();

        //listener
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getId shows which button was pushed
                switch (view.getId()) {
                    case R.id.oneButton:
                        field += String.valueOf(1);

                        break;

                    case R.id.twoButton:
                        field += String.valueOf(2);

                        break;
                    case R.id.threeButton:
                        field += String.valueOf(3);

                        break;
                    case R.id.fourButton:
                        field += String.valueOf(4);

                        break;
                    case R.id.fiveButton:
                        field += String.valueOf(5);

                        break;
                    case R.id.sixButton:
                        field += String.valueOf(6);
                        break;
                    case R.id.sevenButton:
                        field += String.valueOf(7);
                        break;
                    case R.id.eightButton:
                        field += String.valueOf(8);
                        break;
                    case R.id.nineButton:
                        field += String.valueOf(9);
                        break;
                    case R.id.zeroButton:
                        field += String.valueOf(0);
                        break;
                    case R.id.plusButton:
                        field += "+";
                        break;
                    case R.id.minusButton:
                        field += "-";
                        break;
                    case R.id.doneMagickHoldButton:
                        //do some magix
                    case R.id.splitButton:
                        field += "/";
                        break;
                    case R.id.multiplyButton:
                        field += "×";
                        break;

                }

                theFirstLine.setText(field);
            }
        };
        one.setOnClickListener(onClickListener);
        two.setOnClickListener(onClickListener);
        three.setOnClickListener(onClickListener);
        four.setOnClickListener(onClickListener);
        five.setOnClickListener(onClickListener);
        six.setOnClickListener(onClickListener);
        seven.setOnClickListener(onClickListener);
        eight.setOnClickListener(onClickListener);
        nine.setOnClickListener(onClickListener);
        zero.setOnClickListener(onClickListener);
        plus.setOnClickListener(onClickListener);
        minus.setOnClickListener(onClickListener);
        split.setOnClickListener(onClickListener);
        multiply.setOnClickListener(onClickListener);


    }
}