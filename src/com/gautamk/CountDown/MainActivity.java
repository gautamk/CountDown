package com.gautamk.CountDown;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements DatePicker.OnDateChangedListener {


    /**
     * Called when the activity is first created.
     */
    TextView daysLeft, hoursLeft;
    EditText hoursPerDay;
    DatePicker targetDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        daysLeft = (TextView) findViewById(R.id.daysLeft);
        hoursLeft = (TextView) findViewById(R.id.hoursLeft);
        targetDate = (DatePicker) findViewById(R.id.targetDate);
        hoursPerDay = (EditText) findViewById(R.id.hoursPerDay);
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker(new LocalDateTime());
            }
        });
        initDatePicker();
    }

    private void initDatePicker() {
        LocalDateTime dateTime = new LocalDateTime();
        targetDate.init(dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth(), this);
    }
    private void initDatePicker(LocalDateTime dateTime) {
        targetDate.init(dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth(), this);
    }

    private void setDaysLeft(int year, int month, int day) {
        LocalDate now = new LocalDate();
        LocalDate then = new LocalDate(year, month, day);
        int days = Days.daysBetween(now, then).getDays();
        daysLeft.setText(days < 0 ? (days * -1) + " Day Since" : days + " Days Left");
    }

    private void setHoursLeft(int year, int month, int day) {
        LocalDateTime now = new LocalDateTime();
        LocalDateTime then = new LocalDateTime(year, month, day, now.getHourOfDay(), now.getMinuteOfHour());
        double hoursPerDay = Double.parseDouble(this.hoursPerDay.getText().toString());
        int days = Days.daysBetween(now, then).getDays(),
                hours = hoursPerDay < 24.0 && hoursPerDay > 0.0 ?
                        (int) (days * hoursPerDay) :
                        Hours.hoursBetween(now, then).getHours();
        hoursLeft.setText(hours < 0 ? (hours * -1) + " Hours Since" : hours + " Hours Left");
    }


    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        month += 1;  // Convert months (0-11) to (1-12)
        setDaysLeft(year, month, day);
        setHoursLeft(year, month, day);
    }
}
