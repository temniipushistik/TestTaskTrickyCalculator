package com.example.trickycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private void separation() {
        //clear the collection for escape hold previosly value
        operations = new ArrayList<>();


        String number = "";

        for (int i = 0; i < field.size(); i++) {

            char symbol = field.get(i);
            if (symbol != '+' && symbol != '-' && symbol != '*' && symbol != '/' && symbol != '(' && symbol != ')') {
                number += field.get(i);
            } else {
                if (!number.isEmpty())
                    operations.add(number);
                number = "";
                operations.add(symbol + "");
            }

        }
        if (!number.equals("")) {
            operations.add(number);
        }
        operations.add(0, "(");
        operations.add(")");

    }

    private String calculate() {
        if (operations.isEmpty()) {
            return "0";
        }
        countLeftBrackets = 0;
        countRightBrackets = 0;
        int leftBracketPosition = -1;
        int rightBracketPosition = -1;
        while (operations.size() > 1) {

            for (int i = 0; i < operations.size(); i++) {
                if (operations.get(i).equals("(")) {

                    if (operations.get(i + 1).equals("-")) {
                        operations.add(i + 1, "0");
                    }
                    leftBracketPosition = i;
                }
                //stop when we have found the first closing bracket
                if (operations.get(i).equals(")")) {
                    rightBracketPosition = i;

                    break;
                }
            }
            //create sub array without brackets

            ArrayList<String> subOperations = new ArrayList<>(operations.subList(leftBracketPosition + 1, rightBracketPosition));
            for (int j = 1; j < subOperations.size(); j += 2) {
                if (subOperations.get(j).equals("*")) {
                    BigDecimal a = new BigDecimal(subOperations.get(j - 1));
                    BigDecimal b = new BigDecimal(subOperations.get(j + 1));
                    BigDecimal c = a.multiply(b);
                    //correct to sailing math round if too much zero in the end
                    c.setScale(25, RoundingMode.HALF_UP).stripTrailingZeros();
                    //delete symbols, which have been deleted below
                    subOperations.remove(j - 1);
                    subOperations.remove(j - 1);
                    subOperations.set(j - 1, c.toString());
                    j -= 2;
                } else if (subOperations.get(j).equals("/")) {
                    BigDecimal a = new BigDecimal(subOperations.get(j - 1));
                    //delete all zero in the end
                    BigDecimal b = new BigDecimal(subOperations.get(j + 1)).stripTrailingZeros();

                    //crazy check divide by zero
                    if (b.compareTo(BigDecimal.ZERO) == 0) {
                        Toast.makeText(getApplicationContext(),
                                "you can't divide on zero",
                                Toast.LENGTH_SHORT).show();
                        return "error";

                    }
                    //round the value to sailing, killing zero in the end
                    BigDecimal c = a.divide(b, 50, RoundingMode.HALF_UP).stripTrailingZeros();
                    ;

                    subOperations.remove(j - 1);
                    subOperations.remove(j - 1);
                    subOperations.set(j - 1, c.toString());
                    j -= 2;
                }
            }
            for (int j = 1; j < subOperations.size(); j += 2) {
                if (subOperations.get(j).equals("+")) {
                    BigDecimal a = new BigDecimal(subOperations.get(j - 1));
                    BigDecimal b = new BigDecimal(subOperations.get(j + 1));


                    BigDecimal c = a.add(b);

                    subOperations.remove(j - 1);
                    subOperations.remove(j - 1);
                    subOperations.set(j - 1, c.toString());
                    j -= 2;
                } else if (subOperations.get(j).equals("-")) {
                    BigDecimal a = new BigDecimal(subOperations.get(j - 1));
                    BigDecimal b = new BigDecimal(subOperations.get(j + 1));


                    BigDecimal c = a.subtract(b);

                    subOperations.remove(j - 1);
                    subOperations.remove(j - 1);
                    subOperations.set(j - 1, c.toString());
                    j -= 2;
                }

            }
            //delete stuff between brackets
            for (int k = leftBracketPosition; k < rightBracketPosition; k++) {
                operations.remove(leftBracketPosition);
            }
            //put finish value to common collection
            operations.set(leftBracketPosition, subOperations.get(0));
        }


        return operations.get(0);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theFirstLine = findViewById(R.id.workingTextView);
        result = findViewById(R.id.resultTextView);
        attachButtonsAndField();
        getSupportActionBar().hide();

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
                        result.setText("");
                        break;

                    case R.id.doneMagickHoldButton:

                        if (!bracketsCounter(str)) {
                            toast.show();
                        } else
                            separation();
                        result.setText(calculate());

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

