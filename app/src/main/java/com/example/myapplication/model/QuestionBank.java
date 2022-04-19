package com.example.myapplication.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class QuestionBank implements Serializable {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex = 0;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList );
    }
    public Question getCurrentQuestion(){
        return mQuestionList.get(mNextQuestionIndex);
    }

    public Question getNextQuestion() {
        mNextQuestionIndex++;
        return getCurrentQuestion();
    }

    public Question getQuestion() {
        if(mNextQuestionIndex == mQuestionList.size())
        {
            mNextQuestionIndex = 0;
        }
        return mQuestionList.get(mNextQuestionIndex++);
    }
}
