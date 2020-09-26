package com.example.trickycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //the field, which will be accumulate all input information
    ArrayList<Character> field = new ArrayList<>();
    private String str = "";
    // String field = "";
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
                        field.add('1');

                        break;

                    case R.id.twoButton:
                        field.add('2');

                        break;
                    case R.id.threeButton:
                        field.add('3');

                        break;
                    case R.id.fourButton:
                        field.add('4');

                        break;
                    case R.id.fiveButton:
                        field.add('5');

                        break;
                    case R.id.sixButton:
                        field.add('6');
                        break;
                    case R.id.sevenButton:
                        field.add('7');
                        break;
                    case R.id.eightButton:
                        field.add('8');
                        break;
                    case R.id.nineButton:
                        field.add('9');
                        break;
                    case R.id.zeroButton:
                        field.add('0');
                        break;
                    case R.id.plusButton:
                        // if (field.length()-1
                        field.add('+');
                        break;
                    case R.id.minusButton:
                        if (field.get(field.size() - 1) == '-' ||
                                field.get(field.size() - 1) == '+' ||
                                field.get(field.size() - 1) == '/' ||
                                field.get(field.size() - 1) == '*') {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "You can't add more then one arithmetic symbols together",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                            field.remove(field.size() - 1);
                            break;



                        } else {


                            field.add('-');
                            break;
                        }



                    case R.id.doneMagickHoldButton:
                        //do some magic
                        break;
                    case R.id.splitButton:
                        field.add('/');
                        break;
                    case R.id.multiplyButton:
                        field.add('*');
                        break;

                }

                str += field.get(field.size() - 1);
                theFirstLine.setText(str);
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