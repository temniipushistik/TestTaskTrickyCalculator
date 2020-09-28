/*
 * Copyright (C) 2020 Gennadii Koshelev
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at KoshelevGennadii1989@gmail.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.trickycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
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
    private boolean codeTimerStarts = false;


    private String str;

    TextView firstLine, result;
    Button one, two, three, four, five, six, seven, eight, nine, zero, plus, minus, equally, split, multiply, point, closeBracket, openBracket, delete, cancel;
    int countLeftBrackets, countRightBrackets;

    private void attachButtonsAndField() {
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


    //work with dublication and last symbol
    private boolean repeatSignsAndLastSymbol() {

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
        firstLine = findViewById(R.id.workingTextView);
        result = findViewById(R.id.resultTextView);
        attachButtonsAndField();
        getSupportActionBar().hide();

        final Handler btnStartHandler = new Handler();
        final Handler waitingHandler = new Handler();


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
                        if (field.isEmpty() || repeatSignsAndLastSymbol()) {
                            toast.show();
                        } else {
                            field.add('+');


                        }
                        break;

                    case R.id.minusButton:
                        if (field.isEmpty() || !repeatSignsAndLastSymbol()) {
                            field.add('-');
                        } else {

                            toast.show();

                        }

                        break;

                    case R.id.splitButton:
                        if (field.isEmpty() || repeatSignsAndLastSymbol()) {

                            toast.show();

                        } else
                            field.add('/');
                        break;
                    case R.id.multiplyButton:
                        if (field.isEmpty() || repeatSignsAndLastSymbol()) {

                            toast.show();

                        } else
                            field.add('*');
                        break;
                    case R.id.point:
                        if (field.isEmpty() || repeatSignsAndLastSymbol() || findBrackets()) {

                            toast.show();

                        } else
                            field.add('.');
                        break;
                    case R.id.openBracket:
                        if (field.isEmpty() || repeatSignsAndLastSymbol()) {
                            field.add('(');

                        } else
                            toast.show();

                        break;
                    case R.id.closeBracket:
                        if (field.isEmpty() || repeatSignsAndLastSymbol() || !field.contains('(') || field.get(field.size() - 1) == '(') {

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
                        } else if (repeatSignsAndLastSymbol()) {
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


                firstLine.setText(str);
            }

        };
        // listen long push
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //get id  which action was happened
                int action = motionEvent.getAction();
                //if we push the button
                if (action == MotionEvent.ACTION_DOWN) {
                    //show that button was happened
                    view.setPressed(true);
                    btnStartHandler.postDelayed(new Runnable() {
                        //как только прошло 4 сек кодтаймер становится тру
                        @Override
                        public void run() {
                            codeTimerStarts = true;

                            Toast.makeText(getApplicationContext(),
                                    "enter secret code",
                                    Toast.LENGTH_SHORT).show();

                            waitingHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "no secret code",
                                            Toast.LENGTH_SHORT).show();
                                    codeTimerStarts = false;
                                }
                            }, 5000);
                        }
                    }, 4000);

                    return false;
                }
                if (action == MotionEvent.ACTION_UP) {
                    view.setPressed(false);
                    //приоритет онтач выше чем у клик листенера
                    //поэтому нужно это все дублировать
                    result.setText(calculate());
                    if (!codeTimerStarts) {
                        btnStartHandler.removeCallbacksAndMessages(null);
                    }
                    return false;
                }
                return false;
            }
        };
//обращает внимание на текст
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("123") && codeTimerStarts) {
                    codeTimerStarts = false;
                    //switch off the timer
                    waitingHandler.removeCallbacksAndMessages(null);
                    //change activity
                    Intent intent = new Intent(MainActivity.this, HiddenActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        equally.setOnTouchListener(onTouchListener);
        firstLine.addTextChangedListener(textWatcher);


    }
}

