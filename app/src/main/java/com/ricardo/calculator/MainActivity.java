package com.ricardo.calculator;


import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardo.test.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String operator = "";   //运算符
    private String firstNum = "";
    private String secondNum = "";
    private String showText = "";
    private TextView ev_return, ev_input;

    private RecyclerView mRecyclerView;
    private CalculatorAaptor mAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ev_return = (TextView)findViewById(R.id.ev_return);
        ev_input = (TextView)findViewById(R.id.ev_input);


        ev_input.setSingleLine(false);
        ev_input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        ev_input.setMaxLines(3);
        ev_input.setHorizontallyScrolling(false);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdaptor = new CalculatorAaptor(createButtons());
        mRecyclerView.setAdapter(mAdaptor);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));

        mAdaptor.setOnItemOnClickListener(new CalculatorAaptor.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {
                String inputText = mAdaptor.getText(pos);

                if (inputText == "+" || inputText == "-" || inputText == "*" || inputText == "/") {
                    operator = inputText;                               //记录当前输入的操作符

                    if (firstNum != "") {                               //考虑连乘的情况,本程序在计算完后会将结果赋值给firstNum,以支持连乘.
                        showText = firstNum;
                    }
                    refreshText(showText + operator);                   //将记录的操作符显示到ev_input中
                } else if (inputText == "Del") {
                    clear();    //清空ev_input以及ev_return
                } else if (inputText == "=") {
                    if (secondNum != "") {                              //等号只有在secondNum不为空时才会执行计算
                        double ev_result = calculate();                 //进行计算

                        refreshOperate(String.valueOf(ev_result));      //刷新运算结果
                        refreshText(String.valueOf(ev_result));          //将结果显示到ev_input中
                        ev_return.setText(String.valueOf(ev_result));    //将结果显示到ev_return中
                        showText = "";
                    }
                } else {
                    if (operator.equals("")) {                          //在未输入操作符前,输入的数字都属于第一个数
                        if (firstNum == "" && inputText.equals("0")) {  //输入数字的第一位不能是0
                        } else {
                            firstNum += inputText;
                        }
                    } else {
                        if (secondNum == "" && inputText.equals("0")) {
                        } else {
                            secondNum += inputText;
                        }
                    }

                    if (showText.equals("0")) {
                        refreshText(inputText);
                    } else {
                        if ((firstNum == "" || secondNum == "") && inputText.equals("0")) {
                        } else {
                            refreshText(showText + inputText);
                        }
                    }

                }
            }
        });
    }

    private void clear() {
        refreshOperate("");
        firstNum = "";
        showText = "";
        refreshText("0");            //清空ev_input
        ev_return.setText("0");      //清空ev_return

    }

    //刷新运算结果
    private void refreshOperate(String new_result) {
        firstNum = new_result;
        secondNum = "";
        operator = "";
    }

    //刷新文本显示
    private void refreshText(String text) {
        showText = text;                //记录显示的文本
        ev_input.setText(showText);     //将记录的数设置到ev_input中

    }

    //四则运算
    private double calculate() {
        double ev_result = 0;
        if (operator.equals("+")) {
            ev_result = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
        } else if (operator.equals("-")) {
            ev_result = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
        } else if (operator.equals("*")) {
            ev_result = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
        } else if (operator.equals("/")) {
            ev_result = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
        return ev_result;
    }

    private List<Button> createButtons() {
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < 16; ++i) {
            buttons.add(new Button(this));
        }

        return buttons;
    }

}