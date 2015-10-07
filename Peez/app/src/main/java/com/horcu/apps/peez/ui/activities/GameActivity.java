package com.horcu.apps.peez.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.util.Log;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.SingleInputFormActivity;
import com.horcu.apps.peez.steps.CheckBoxStep;
import com.horcu.apps.peez.steps.DateStep;
import com.horcu.apps.peez.steps.SeekBarStep;
import com.horcu.apps.peez.steps.Step;
import com.horcu.apps.peez.steps.TextStep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class GameActivity extends SingleInputFormActivity {

    private static final String DATA_KEY_HEIGHT = "height";
    private static final String DATA_KEY_EULA = "eula";
    private static final String DATA_KEY_EMAIL = "email";
    private static final String DATA_KEY_PASSWORD = "password";
    private static final String DATA_KEY_BIRTHDAY = "birthday";
    private static final String DATA_KEY_CITY = "city";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//       // setContentView(R.layout.activity_game);
//     //   getActionBar().setDisplayHomeAsUpEnabled(true);
//    }

    @Override
    protected List<Step> getSteps(Context context) {
        List<Step> steps = new ArrayList<Step>();
        steps.add(
                new CheckBoxStep(context, DATA_KEY_EULA, R.string.eula, R.string.eula_title, R.string.eula_error, R.string.eula_details, new CheckBoxStep.StepChecker() {
                    @Override
                    public boolean check(boolean input) {
                        return input;
                    }
                })
        );
        steps.add(
                new TextStep(context, DATA_KEY_EMAIL, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, R.string.email, R.string.email_error, R.string.email_details, new TextStep.StepChecker() {
                    @Override
                    public boolean check(String input) {
                        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();
                    }
                })
        );
        steps.add(
                new TextStep(context, DATA_KEY_PASSWORD, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, R.string.password, R.string.password_error, R.string.password_details, new TextStep.StepChecker() {
                    @Override
                    public boolean check(String input) {
                        return input.length() >= 5;
                    }
                })
        );
        steps.add(
                new DateStep(context, DATA_KEY_BIRTHDAY, R.string.birthday, R.string.birthday_error, R.string.birthday_details, new DateStep.StepChecker() {
                    @Override
                    public boolean check(int year, int month, int day) {
                        Calendar today = new GregorianCalendar();
                        Calendar birthday = new GregorianCalendar(year, month, day);
                        today.add(Calendar.YEAR, -14);
                        return today.after(birthday);
                    }
                })
        );
        steps.add(
                new SeekBarStep(context, DATA_KEY_HEIGHT, 150, 180, R.string.height, R.string.height_error, R.string.height_details, new SeekBarStep.StepChecker() {
                    @Override
                    public boolean check(int progress) {
                        return progress >= 160;
                    }
                })
        );
        steps.add(
                new TextStep(context, DATA_KEY_CITY, InputType.TYPE_CLASS_TEXT, R.string.city, R.string.city_error, R.string.city_details)
        );


        return steps;
    }


    @Override
    protected void onFormFinished(Bundle data) {
        Snackbar.make(findViewById(R.id.game_main), "bet finished: " +
                        CheckBoxStep.checked(data, DATA_KEY_EULA) + ", " +
                        TextStep.text(data, DATA_KEY_EMAIL) + ", " +
                        TextStep.text(data, DATA_KEY_PASSWORD) + ", " +
                        DateStep.day(data, DATA_KEY_BIRTHDAY) + "." + DateStep.month(data, DATA_KEY_BIRTHDAY) + "." + DateStep.year(data, DATA_KEY_BIRTHDAY) + ", " +
                        SeekBarStep.progress(data, DATA_KEY_HEIGHT) + ", " +
                        TextStep.text(data, DATA_KEY_CITY),
                Snackbar.LENGTH_LONG).show();
        Log.d("GameActivity", "data: " + data.toString());
    }

}
