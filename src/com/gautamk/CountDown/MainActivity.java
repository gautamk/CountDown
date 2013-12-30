package com.gautamk.CountDown;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Calendar;

public class MainActivity extends Activity implements DatePicker.OnDateChangedListener {

    /**
     * Called when the activity is first created.
     */
    TextView daysLeft,hoursLeft;
    DatePicker targetDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        daysLeft   = (TextView) findViewById(R.id.daysLeft);
        hoursLeft  = (TextView) findViewById(R.id.hoursLeft);
        targetDate = (DatePicker) findViewById(R.id.targetDate);
        LocalDate now = new LocalDate().plusDays(1);
        targetDate.init(now.getYear(),now.getMonthOfYear()-1,now.getDayOfMonth(),this);
        this.onDateChanged(targetDate,now.getYear(),now.getMonthOfYear()-1,now.getDayOfMonth());
    }


    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        LocalDateTime now = new LocalDateTime();
        LocalDateTime then = new LocalDateTime(year, month + 1, day, now.getHourOfDay(), now.getMinuteOfHour());
        Days days = Days.daysBetween(now,then);
        Hours hours = Hours.hoursBetween(now, then);
        int noOfDaysLeft  = days.getDays(),
            noOfHoursLeft = hours.getHours();

        daysLeft.setText(noOfDaysLeft<0 ? (noOfDaysLeft * -1 )+" Days Since" : noOfDaysLeft + " Days Left" );
        hoursLeft.setText(noOfHoursLeft<0 ? (noOfHoursLeft* -1 )+" Hours Since" : noOfHoursLeft + " Hours Left" );
    }
}
