package com.example.myapplication.Controler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.QuestionBank;
import com.example.myapplication.model.User;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextViewQuestion;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    private QuestionBank mQuestionBank;
    private Question mCQuestion;

    private int mScor;
    private int nbrQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mQuestionBank = this.generatQustionBank();

        mTextViewQuestion = (TextView) findViewById(R.id.id0);
        mAnswerButton1 = (Button) findViewById(R.id.id1);
        mAnswerButton2 = (Button) findViewById(R.id.id2);
        mAnswerButton3 = (Button) findViewById(R.id.id3);
        mAnswerButton4 = (Button) findViewById(R.id.id4);

        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mCQuestion =   mQuestionBank.getQuestion();
        this.displayQuestion(mCQuestion);
        mScor = 0;
        nbrQuestion = 4;

    }
    public void onClick(View v)
    {
        int index = (int) v.getTag();
        if (index ==  mCQuestion.getAnswerIndex())
        {
            mScor++;
            Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "incorrect!", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(--nbrQuestion == 0)
                {
                    endGame();
                }
                else
                {
                    mCQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCQuestion);
                }
            }
        }, 2000);


    }
    private void endGame() {
        // No question left, end the game
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done !")
                .setMessage("your score is " + mScor)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScor);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        return mEnableTouchEvents &&  super.dispatchKeyEvent(event);
//    }

    private void displayQuestion(final Question question) {
        mTextViewQuestion.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generatQustionBank(){
        Question qst1 = new Question(
          "Où se trouve le Maroc ?",
        Arrays.asList(
            "En Europe",
            "En Afrique",
            " En Asie",
            "En Australie"

        ),
        1
        );
        Question qst2 = new Question(
                "Quelle est la langue officielle du Maroc ?",
                Arrays.asList(
                        "Français",
                        "Arabe",
                        "Anglais",
                        "Espagnol"

                ),
                1
        );
        Question qst3 = new Question(
                "Quelle est la capitale du Maroc ?",
                Arrays.asList(
                        "Casablanca",
                        "Salé",
                        "Rabat",
                        "marrakech"
                ),
                2
        );

        Question qst4 = new Question(
                "Quelle est la monnaie du Maroc ?",
                Arrays.asList(
                        "Dirham",
                        "Dinar",
                        "Dollar",
                        "Livre"
                ),
                0
        );



        return new QuestionBank(Arrays.asList(qst1,qst2,qst3,qst4));
        }
    }