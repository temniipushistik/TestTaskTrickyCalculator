package com.example.trickycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //the field, which will be accumulate all input information
    private ArrayList<Character> field = new ArrayList<>();
    //collection for work with numbers
    private ArrayList<String> operations = new ArrayList<>();


    private String str;

    TextView theFirstLine, result;
    Button one, two, three, four, five, six, seven, eight, nine, zero, plus, minus, equally, split, multiply, point, closeBracket, openBracket, delete, cancel;
    int countLeftBrackets, countRightBrackets;

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
        openBracket = findViewById(R.id.openBracket);
        closeBracket = findViewById(R.id.closeBracket);
        delete = findViewById(R.id.delete);
        cancel = findViewById(R.id.cancel);
        //field.add(' ');

    }

    //work with dublication
    private boolean repeatSigns() {

        return (field.get(field.size() - 1) == '-' ||
                field.get(field.size() - 1) == '+' ||
                field.get(field.size() - 1) == '/' ||
                field.get(field.size() - 1) == '*' ||
                field.get(field.size() - 1) == '.');


    }

    private boolean findBrackets() {
        return (field.get(field.size() - 1) == ')' ||
                field.get(field.size() - 1) == '(');
    }

    private boolean bracketsCounter(String a) {
        int open = 0;
        int close = 0;
        for (int i = 0; i < a.length(); ++i) {

            if (a.charAt(i) == '(') {
                open++;
            } else if (a.charAt(i) == ')') {
                close++;
            }

        }
        if (open != close) {

            return false;
        } else return true;

    }

    //каждое число в отдельную ячейку
    public void separation() {

        //countLeftBrackets = 0;
        // countRightBrackets = 0;
        String number = "";

        for (int i = 0; i < field.size(); i++) {

            char symbol = field.get(i);
            if (symbol != '+' && symbol != '-' && symbol != '*' && symbol != '/' && symbol != '(' && symbol != ')') {
                number += field.get(i);
            } else {
                operations.add(number);
                number = "";
                operations.add(symbol + "");
            }

        }
        if (!number.equals("")) {
            operations.add(number);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theFirstLine = findViewById(R.id.workingTextView);
        result = findViewById(R.id.resultTextView);
        attachButtonsAndField();

        final Toast toast = Toast.makeText(getApplicationContext(),
                "something went wrong",
                Toast.LENGTH_SHORT);


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
                    case R.id.delete:
                        if (!field.isEmpty())
                            field.remove(field.size() - 1);
                        else {

                        }
                        break;
                    case R.id.plusButton:

                        //if the field empty of there is no repeat signs
                        if (field.isEmpty() || repeatSigns()) {
                            toast.show();
                        } else {
                            field.add('+');


                        }
                        break;

                    case R.id.minusButton:
                        if (field.isEmpty() || !repeatSigns()) {
                            field.add('-');
                        } else {

                            toast.show();

                        }

                        break;

                    case R.id.splitButton:
                        if (field.isEmpty() || repeatSigns()) {

                            toast.show();

                        } else
                            field.add('/');
                        break;
                    case R.id.multiplyButton:
                        if (field.isEmpty() || repeatSigns()) {

                            toast.show();

                        } else
                            field.add('*');
                        break;
                    case R.id.point:
                        if (field.isEmpty() || repeatSigns() || findBrackets()) {

                            toast.show();

                        } else
                            field.add('.');
                        break;
                    case R.id.openBracket:
                        if (field.isEmpty() || repeatSigns()) {
                            field.add('(');

                        } else
                            toast.show();

                        break;
                    case R.id.closeBracket:
                        if (field.isEmpty() || repeatSigns() || !field.contains('(') || field.get(field.size() - 1) == '(') {

                            toast.show();
                        } else
                            field.add(')');
                        break;
                    case R.id.cancel:
                        field.clear();

                    case R.id.doneMagickHoldButton:

                        if (!bracketsCounter(str)) {
                            toast.show();
                        } else
                            separation();
                        result.setText(operations.toString());


                        //do some magic
                        break;


                }


                str = "";
                for (char c : field) {
                    str += c;
                }


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
        point.setOnClickListener(onClickListener);
        delete.setOnClickListener(onClickListener);
        openBracket.setOnClickListener(onClickListener);
        closeBracket.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        equally.setOnClickListener(onClickListener);

    }
}