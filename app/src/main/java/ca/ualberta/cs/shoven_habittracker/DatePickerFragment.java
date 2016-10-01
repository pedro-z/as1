package ca.ualberta.cs.shoven_habittracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;


/**
 * Created by shoven on 2016-10-01.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private LocalDateTime now = new LocalDateTime(DateTimeZone.forID("Canada/Mountain"));
    int year = now.getYear();
    // I need to subtract 1 because Joda time returns month where January is 1
    //   (DatePickerDialog expects month where January is 0)
    int month = now.getMonthOfYear()-1;
    int day = now.getDayOfMonth();

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        TextView textView = (TextView) getActivity().findViewById(R.id.pickDateTextView);
        String monthStr = "0"+(datePicker.getMonth()+1);
        String dayStr = "0"+datePicker.getDayOfMonth();
        textView.setText(datePicker.getYear() + "-"
                + monthStr.substring(monthStr.length() - 2) + "-"
                + dayStr.substring(dayStr.length() - 2));
    }
}
