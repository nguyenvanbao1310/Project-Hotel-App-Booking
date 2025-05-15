package com.example.hotel_project.dialog;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.datepicker.CalendarConstraints;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DateValidatorExclude implements CalendarConstraints.DateValidator {

    private final Set<Long> disabledDates;

    public DateValidatorExclude(Set<Long> rawDisabledDates) {
        this.disabledDates = new HashSet<>();
        Calendar calendar = Calendar.getInstance();
        for (Long date : rawDisabledDates) {
            calendar.setTimeInMillis(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            this.disabledDates.add(calendar.getTimeInMillis());
        }
    }

    protected DateValidatorExclude(Parcel in) {
        disabledDates = new HashSet<>();
        List<Long> tempList = new ArrayList<>();
        in.readList(tempList, Long.class.getClassLoader());
        for (Long date : tempList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            disabledDates.add(calendar.getTimeInMillis());
        }
    }



    @Override
    public boolean isValid(long date) {
        // Kiểm tra xem ngày có nằm trong danh sách bị disable không
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return !disabledDates.contains(calendar.getTimeInMillis());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(new ArrayList<>(disabledDates));

    }

    public static final Parcelable.Creator<DateValidatorExclude> CREATOR =
            new Parcelable.Creator<DateValidatorExclude>() {
                @Override
                public DateValidatorExclude createFromParcel(Parcel in) {
                    return new DateValidatorExclude(in);
                }

                @Override
                public DateValidatorExclude[] newArray(int size) {
                    return new DateValidatorExclude[size];
                }
            };
}
