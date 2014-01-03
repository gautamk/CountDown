package com.gautamk.CountDown;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class MainActivity extends Activity implements DatePicker.OnDateChangedListener {


    /**
     * Called when the activity is first created.
     */
    TextView daysLeft, hoursLeft;
    EditText hoursPerDay;
    DatePicker targetDate;
    LocalDateTime then = new LocalDateTime();

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
                hoursPerDay.setText("24");
            }
        });
        initDatePicker();
        hoursPerDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                setDaysLeft(then.getYear(),then.getMonthOfYear(),then.getDayOfMonth());
                setHoursLeft(then.getYear(),then.getMonthOfYear(),then.getDayOfMonth());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }


    private void initDatePicker() {
        LocalDateTime dateTime = new LocalDateTime();
        targetDate.init(dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth(), this);
    }

    private void initDatePicker(LocalDateTime dateTime) {
        targetDate.init(dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth(), this);
    }

    private void setDaysLeft(int year, int month, int day) {
        LocalDateTime now = new LocalDateTime();
        LocalDateTime then = new LocalDateTime(year, month, day, now.getHourOfDay(), now.getMinuteOfHour());
        int days = Days.daysBetween(now, then).getDays();
        daysLeft.setText(days < 0 ? (days * -1) + " Day Since" : days + " Days Left");
        this.then = then;
    }

    private double getHoursPerDay(){
        try {
            double hoursPerDay = Double.parseDouble(this.hoursPerDay.getText().toString());
            if(!(hoursPerDay > 0.0 && hoursPerDay <= 24.0)) throw new Exception();
            return hoursPerDay;
        } catch (Exception e){
            this.hoursPerDay.setText("24");
            return 24.0;
        }
    }

    private void setHoursLeft(int year, int month, int day) {
        LocalDateTime now = new LocalDateTime();
        LocalDateTime then = new LocalDateTime(year, month, day, now.getHourOfDay(), now.getMinuteOfHour());
        double hoursPerDay = getHoursPerDay();

        int days = Days.daysBetween(now, then).getDays();
        if(days == 0 && new LocalDate().plusDays(1).equals(new LocalDate(then.getYear(),then.getMonthOfYear(),then.getDayOfMonth()))){
            days = 1;
        }
        int hours = hoursPerDay < 24.0 && hoursPerDay > 0.0 ?
                        (int) (days * hoursPerDay) :
                        Hours.hoursBetween(now, then).getHours();

        hoursLeft.setText(hours < 0 ? (hours * -1) + " Hours Since" : hours + " Hours Left");
        this.then = then;
    }


    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        month += 1;  // Convert months (0-11) to (1-12)
        setDaysLeft(year, month, day);
        setHoursLeft(year, month, day);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_calendar:
                return true;
            default:
                return super.onMenuItemSelected(featureId, item);
        }

    }
}
