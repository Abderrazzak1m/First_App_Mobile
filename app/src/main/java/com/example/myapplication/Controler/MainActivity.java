package com.example.myapplication.Controler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.User;

public class MainActivity extends AppCompatActivity {
    TextView mGreetingTextView;
    EditText mNameEditText;
    Button mPlayButton;
    SharedPreferences.Editor editor;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

   User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
        //mGreetingTextView.setText(getSharedPreferences("miski", MODE_PRIVATE).getString("name", null));
        mPlayButton.setEnabled(false);

        mUser = new User();

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPlayButton.setEnabled(!charSequence.toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = mNameEditText.getText().toString();
                mUser.setFirstName(firstname);
                SharedPreferences preferences = getSharedPreferences("miski", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("name", mUser.getFirstName().toString()).apply();
                Intent gamActivityIntent = new Intent(MainActivity.this, GameActivity.class );
                startActivityForResult(gamActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
        mUser.setFirstName(mNameEditText.getText().toString());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode)
        {
            int scor  = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            getSharedPreferences("miski", MODE_PRIVATE).edit()
                    .putInt("scor", scor)
                    .apply();
            greetUser();
        }
    }

    private void greetUser() {
        String firstName = getSharedPreferences("miski", MODE_PRIVATE).getString("name", null);
        int score = getSharedPreferences("miski", MODE_PRIVATE).getInt("scor", -1);

        if (firstName != null) {
            if (score != -1) {
                mGreetingTextView.setText(getString(R.string.welcome_back_with_score, firstName, score));
            } else {
                mGreetingTextView.setText(getString(R.string.welcome_back, firstName));
            }

            mNameEditText.setText(firstName);
        }
    }
}