package uk.gov.hmcts.pdda.business.services.formatting;

import org.easymock.EasyMockExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * <p>
 * Title: MergeDateUtils Test.
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2024
 * </p>
 * <p>
 * Company: CGI
 * </p>
 * 
 * @author Luke Gittins
 */
@ExtendWith(EasyMockExtension.class)
class MergeDateUtilsTest {

    private static final String EQUAL = "Result is Equal";
    private static final String NULL = "Result is Null";
    private static final String NOT_NULL = "Result is Not Null";

    public static final String SUNDAY = "Sunday";
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";

    public static final String JANUARY = "January";
    public static final String FEBRUARY = "February";
    public static final String MARCH = "March";
    public static final String APRIL = "April";
    public static final String MAY = "May";
    public static final String JUNE = "June";
    public static final String JULY = "July";
    public static final String AUGUST = "August";
    public static final String SEPTEMBER = "September";
    public static final String OCTOBER = "October";
    public static final String NOVEMBER = "November";
    public static final String DECEMBER = "December";

    @BeforeAll
    public static void setUp() throws Exception {
        // Do nothing
    }

    @AfterAll
    public static void tearDown() throws Exception {
        // Do nothing
    }

    @Test
    void testSetupCalendarNull() {
        assertNull(MergeDateUtils.setupCalendar(null, null), NULL);
    }

    @Test
    void testSetupCalendarNotNull() {
        assertNotNull(MergeDateUtils.setupCalendar("10:00:00", "01/01/2024"), NOT_NULL);
    }

    @Test
    void testReturnDay() {
        Calendar day1 = MergeDateUtils.setupCalendar("10:00:00", "29/02/2024");
        assertEquals(THURSDAY, MergeDateUtils.returnDay(day1), EQUAL);
        Calendar day2 = addOneDay(day1);
        assertEquals(FRIDAY, MergeDateUtils.returnDay(day2), EQUAL);
        Calendar day3 = addOneDay(day2);
        assertEquals(SATURDAY, MergeDateUtils.returnDay(day3), EQUAL);
        Calendar day4 = addOneDay(day3);
        assertEquals(SUNDAY, MergeDateUtils.returnDay(day4), EQUAL);
        Calendar day5 = addOneDay(day4);
        assertEquals(MONDAY, MergeDateUtils.returnDay(day5), EQUAL);
        Calendar day6 = addOneDay(day5);
        assertEquals(TUESDAY, MergeDateUtils.returnDay(day6), EQUAL);
        Calendar day7 = addOneDay(day6);
        assertEquals(WEDNESDAY, MergeDateUtils.returnDay(day7), EQUAL);
    }

    @Test
    void testReturnMonth() {
        Calendar month1 = MergeDateUtils.setupCalendar("10:00:00", "01/01/2024");
        assertEquals(JANUARY, MergeDateUtils.returnMonth(month1), EQUAL);
        Calendar month2 = addOneMonth(month1);
        assertEquals(FEBRUARY, MergeDateUtils.returnMonth(month2), EQUAL);
        Calendar month3 = addOneMonth(month2);
        assertEquals(MARCH, MergeDateUtils.returnMonth(month3), EQUAL);
        Calendar month4 = addOneMonth(month3);
        assertEquals(APRIL, MergeDateUtils.returnMonth(month4), EQUAL);
        Calendar month5 = addOneMonth(month4);
        assertEquals(MAY, MergeDateUtils.returnMonth(month5), EQUAL);
        Calendar month6 = addOneMonth(month5);
        assertEquals(JUNE, MergeDateUtils.returnMonth(month6), EQUAL);
        Calendar month7 = addOneMonth(month6);
        assertEquals(JULY, MergeDateUtils.returnMonth(month7), EQUAL);
        Calendar month8 = addOneMonth(month7);
        assertEquals(AUGUST, MergeDateUtils.returnMonth(month8), EQUAL);
        Calendar month9 = addOneMonth(month8);
        assertEquals(SEPTEMBER, MergeDateUtils.returnMonth(month9), EQUAL);
        Calendar month10 = addOneMonth(month9);
        assertEquals(OCTOBER, MergeDateUtils.returnMonth(month10), EQUAL);
        Calendar month11 = addOneMonth(month10);
        assertEquals(NOVEMBER, MergeDateUtils.returnMonth(month11), EQUAL);
        Calendar month12 = addOneMonth(month11);
        assertEquals(DECEMBER, MergeDateUtils.returnMonth(month12), EQUAL);
    }

    private Calendar addOneDay(Calendar day) {
        Calendar result = (Calendar) day.clone();
        result.add(Calendar.DAY_OF_MONTH, 1);
        return result;
    }

    private Calendar addOneMonth(Calendar month) {
        Calendar result = (Calendar) month.clone();
        result.add(Calendar.MONTH, 1);
        return result;
    }
}
