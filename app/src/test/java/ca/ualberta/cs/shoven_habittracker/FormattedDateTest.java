package ca.ualberta.cs.shoven_habittracker;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.*;

/**
 * Created by shoven on 2016-09-28.
 */

public class FormattedDateTest {
    @Test
    public void testFormattedDate() {
        FormattedDate date1 = new FormattedDate();
        FormattedDate date2 = new FormattedDate(new Date());
        FormattedDate date3 = new FormattedDate(2016, 9, 28);
        assertEquals("2016-09-28", date1.toString());
        assertEquals("2016-09-28", date2.toString());
        assertEquals("2016-09-28", date3.toString());
    }
}
