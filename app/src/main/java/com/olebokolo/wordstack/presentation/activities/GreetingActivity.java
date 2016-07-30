package com.olebokolo.wordstack.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.olebokolo.wordstack.R;
import com.olebokolo.wordstack.core.app.WordStack;
import com.olebokolo.wordstack.presentation.navigation.ActivityNavigator;
import com.olebokolo.wordstack.presentation.navigation.NavigationDirection;

public class GreetingActivity extends AppCompatActivity {

    private ActivityNavigator navigator;

    public GreetingActivity() {
        WordStack application = WordStack.getInstance();
        navigator = application.getActivityNavigator();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        setUpGoNextButton();
    }

    @SuppressWarnings("ConstantConditions")
    private void setUpGoNextButton() {
        View goNextButton = findViewById(R.id.go_next_button);
        goNextButton.setOnClickListener(goNextClickListener);
    }

    private View.OnClickListener goNextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            navigator.goForwardWithSlideAnimationAndFurtherActivity(
                    GreetingActivity.this,
                    LanguagesActivity.class,
                    MainMenuActivity.class,
                    NavigationDirection.FORWARD);
        }
    };

}
