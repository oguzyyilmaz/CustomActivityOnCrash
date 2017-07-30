package sample.oguz.com.customactivityoncrash.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import sample.oguz.com.customactivityoncrash.R;


public class CustomErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_error);

        //**IMPORTANT**
        //The custom error activity in this sample is uglier than the default one and just
        //for demonstration purposes, please don't copy it to your project!
        //We recommend taking the original library's DefaultErrorActivity as a basis.
        //Of course, you are free to implement it as you wish in your application.

        //These four methods are available for you to use:
        //CustomActivityOnCrash.getStackTraceFromIntent(getIntent()): gets the stack trace as a string
        //CustomActivityOnCrash.getActivityLogFromIntent(getIntent()): gets the activity log as a string
        //CustomActivityOnCrash.getAllErrorDetailsFromIntent(context, getIntent()): returns all error details including stacktrace as a string
        //CustomActivityOnCrash.getConfigFromIntent(getIntent()): returns the config of the library when the error happened

        //Now, treat here the error as you wish. If you allow the user to restart or close the app,
        //don't forget to call the appropriate methods.
        //Otherwise, if you don't finish the activity, you will get the CustomErrorActivity on the activity stack and it will be visible again under some circumstances.
        //Also, you will get multiprocess problems in API<17.

        TextView errorDetailsText = (TextView) findViewById(R.id.error_details);
        errorDetailsText.setText(CustomActivityOnCrash.getStackTraceFromIntent(getIntent()));

        Button restartButton = (Button) findViewById(R.id.restart_button);

        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setText(R.string.restart_app);
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.restartApplication(CustomErrorActivity.this, config);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.closeApplication(CustomErrorActivity.this, config);
                }
            });
        }
    }
}
