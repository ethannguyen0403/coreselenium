package com.paltech.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 * @author isabella.huynh
 * created on Jan/03/2019.
 */

public class DateUtils {
    /**
     * Getting milliseconds of a current time
     *
     * @return a number of milliseconds
     */
    public static long getMilliSeconds() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    /**
     * Checking whether Date To >= Date >= Date From
     *
     * @param strDate     "01-11-2018"
     * @param strDateFrom "01-11-2018"
     * @param strDateTo   "01-11-2018"
     * @param format      "dd-MM-yyyy" or "dd/MM/yyyy" or "dd-MM-yyyy"
     * @return true or false
     * @throws ParseException
     */
    public static boolean isDateBetweenPeriod(String strDate, String strDateFrom, String strDateTo, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(strDate);
        Date dateFrom = simpleDateFormat.parse(strDateFrom);
        Date dateTo = simpleDateFormat.parse(strDateTo);
        return ((date.after(dateFrom) || date.equals(dateFrom)) && (date.before(dateTo) || date.equals(dateTo)));
    }

    /**
     * Checking whether a date list belongs to Date To >= dates >= Date From
     * @param lstDates
     * @param strDateFrom
     * @param strDateTo
     * @param format
     * @return true or false
     * @throws ParseException
     */
    public static boolean areDatesBetweenPeriod(List<String> lstDates, String strDateFrom, String strDateTo, String format) throws ParseException {
        for (String date : lstDates){
            if (!isDateBetweenPeriod(date, strDateFrom, strDateTo, format)) {
                System.out.println(String.format("DEBUG: This error occurs at %s <= %s < %s", strDateFrom, date, strDateFrom));
                return false;
            }
        }
        return true;
    }

    /**
     * Getting a date following GMT
     *
     * @param gmt    GMT-4 if you want to get GMT-4
     * @param format "dd-MM-yyyy HH:mm:ss" or "dd-MM-yyyy" or "dd/MM/yyyy HH:mm:ss"
     * @return string date following format
     */
    public static String getDateFollowingGMT(String gmt, String format) {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(gmt));
        return sdf.format(currentTime);
    }

    /**
     * Getting a date before the current date with GMT-4
     *
     * @param numberBeforeCurrentDate it is a integer
     * @param format                  "dd-MM-yyyy HH:mm:ss" or "dd-MM-yyyy" or "dd/MM/yyyy HH:mm:ss"
     * @return string date
     */
    public static String getDateBeforeCurrentDate(int numberBeforeCurrentDate, String format) {
        return DateUtils.getDateBeforeCurrentDate(numberBeforeCurrentDate, "GMT-4", format);
    }

    /**
     * Getting a date before the current date
     *
     * @param numberBeforeCurrentDate it is a integer
     * @param format                  "dd-MM-yyyy HH:mm:ss" or "dd-MM-yyyy" or "dd/MM/yyyy HH:mm:ss"
     * @return string date
     */
    public static String getDateBeforeCurrentDate(int numberBeforeCurrentDate, String gmt, String format) {
        if (numberBeforeCurrentDate > 0){
            numberBeforeCurrentDate = numberBeforeCurrentDate * -1;
        }

        Calendar c = Calendar.getInstance();
        c = (Calendar) c.clone();
        c.add(Calendar.DATE, numberBeforeCurrentDate);
        Date date = c.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(gmt));
        return simpleDateFormat.format(date);
    }

    public static String getPreviousDate(String dateString, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date myDate = simpleDateFormat.parse(dateString);

            Calendar c = Calendar.getInstance();
            c = (Calendar) c.clone();
            c.setTime(myDate);
            c.add(Calendar.DAY_OF_YEAR, -1);

            Date previousDate = c.getTime();
            return simpleDateFormat.format(previousDate);
        } catch (ParseException ex) {
            System.out.println("ERROR: ParseException occurs by " + ex.getMessage());
            return "00/00/0000";
        }
    }


    /**
     * Getting a date after the current date with GMT-4
     *
     * @param numberAfterCurrentDate it is a integer
     * @param format                 "dd-MM-yyyy HH:mm:ss" or "dd-MM-yyyy" or "dd/MM/yyyy HH:mm:ss"
     * @return string date
     */
    public static String getDateAfterCurrentDate(int numberAfterCurrentDate, String format) {
        return DateUtils.getDateAfterCurrentDate(numberAfterCurrentDate, "GMT-4", format);
    }

    /**
     * Getting a date after the current date
     *
     * @param numberAfterCurrentDate it is a integer
     * @param format                 "dd-MM-yyyy HH:mm:ss" or "dd-MM-yyyy" or "dd/MM/yyyy HH:mm:ss"
     * @param gmt                    GMT-4
     * @return string date
     */
    public static String getDateAfterCurrentDate(int numberAfterCurrentDate, String gmt, String format) {
        if (numberAfterCurrentDate < 0){
            numberAfterCurrentDate = numberAfterCurrentDate * -1;
        }
        Calendar c = Calendar.getInstance();
        c = (Calendar) c.clone();
        c.add(Calendar.DATE, numberAfterCurrentDate);
        Date date = c.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(gmt));
        return simpleDateFormat.format(date);
    }

    /**
     * Getting the Monday of the last week is Monday with default GMT-4
     * @param format "dd-MM-yyyy HH:mm:ss" or "dd-MM-yyyy" or "dd/MM/yyyy HH:mm:ss"
     * @return string date following date format
     */
    public static String getFirstDayOfLastWeek(String format) {
        return getFirstDayOfLastWeek(format, "GMT-04", true);
    }

    // TODO: check Sunday
    public static String getFirstDayOfLastWeek(String format, String gmt, boolean isSunday) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(gmt));
        c = (Calendar) c.clone();

        Date date = c.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(gmt));
        String currentDateGMT = simpleDateFormat.format(date);
        Date dateGMT4 = DateUtils.convertToDate(currentDateGMT, format);
        String currentDateGMT1 = dateGMT4.toString();
        // Notice: The 1st day of a week is Sunday but in PS3838 Agent is Monday and the last day of a week is Sunday
        if(currentDateGMT1.contains("Sun") && isSunday) {
            c.add(Calendar.WEEK_OF_YEAR, -2);
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 1);
        } else {
            // last week
            c.add(Calendar.WEEK_OF_YEAR, -1);
            // first day is Monday
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 1); // +0: Sunday; +1 is Monday
        }
        Date monDay2WeeksAgo = c.getTime();
        return simpleDateFormat.format(monDay2WeeksAgo);
    }

    public static String getLastDayOfLastWeek(String format) {
        return getLastDayOfLastWeek(format, "GMT-04", true);
    }

    public static String getLastDayOfLastWeek(String format, String gmt, boolean isSunday) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(gmt));
        c = (Calendar) c.clone();

        Date date = c.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(gmt));
        String currentDateGMT = simpleDateFormat.format(date);
        Date dateGMT4 = DateUtils.convertToDate(currentDateGMT, format);
        String currentDateGMT1 = dateGMT4.toString();
        // Notice: The 1st day of a week is Sunday but in PS3838 Agent is Monday and the last day of a week is Sunday
        if(currentDateGMT1.contains("Sun") && isSunday) {
            c.add(Calendar.WEEK_OF_YEAR, -1);
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        } else {
            // first day of this week
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
            // last day of previous week
            c.add(Calendar.DAY_OF_MONTH, 0);
        }

        Date sunday2WeeksAgo = c.getTime();
        return simpleDateFormat.format(sunday2WeeksAgo);
    }

    /**
     * Returning current year
     * @param gmt GMT-4
     * @return
     */
    public static int getYear(String gmt){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone(gmt));

        LocalDateTime now = LocalDateTime.now();
        return now.getYear();
    }

    public static int getMonth(String gmt){
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone(gmt));

        LocalDateTime now = LocalDateTime.now();
        return now.getMonthValue();
    }

    /**
     * Getting MM/yyyy
     * @param gmt GMT-04
     * @param previousMonth If previousMonth is negative it will return the previous month.
     *                      If previousMonth is 0, it returns the current month
     *                      If previousMonth is positive it will return the next month.
     * @param format MM/yyyy
     * @return
     */
    public static String getMonthYear(String gmt, int previousMonth, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(gmt));

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, previousMonth);// then one month
        return sdf.format(c.getTime());
    }

    /**
     * getting a date before the current date following GMT-4
     * @param previousDate how many date is before the current date
     * @return e.g 1, 2, 3, ... and 31
     * @throws ParseException
     */
    public static String getOnlyDateBeforeCurrentDate(int previousDate, String gmt) throws ParseException {
        String date = DateUtils.getDateBeforeCurrentDate(previousDate, gmt, "dd");
        if (date.startsWith("0")){
            date = date.replace("0", "");
        }
        return date;
    }

    /**
     * Format a string from a format to another format
     * @param date 26/01/2019
     * @param oldFormat e.g dd/MM/yyyy
     * @param newFormat e.g yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String formatDate(String date, String oldFormat, String newFormat){
        if (date.isEmpty() || oldFormat.isEmpty() || newFormat.isEmpty()){
            System.out.println("Debug: date or oldFormat or newFormat is empty");
            return "";
        }
        Date d = convertToDate(date, oldFormat);
        SimpleDateFormat formatter = new SimpleDateFormat(newFormat);
        return formatter.format(d);
    }

    /**
     * Convert a date in any Timzone and any format to another format and timezone
     * @param date  date input match with @param convertFromFormat
     * @param convertFromFormat date format (e.g yyyy-MM-dd'T'HH:mm:ss.SSSXXX)
     * @param convertFromTimeZone timezone, if not input script will get default timezone
     * @param convertToFormat date format (e.g yyyy-MM-dd HH:mm)
     * @param convertTimeZone timezone (eg, GMT+8, America/New_York)
     * @return
     * @throws ParseException
     */
    public static String convertDateToNewTimeZone(String date,String convertFromFormat,String convertFromTimeZone,String convertToFormat, String convertTimeZone) throws ParseException {
        DateFormat df = new SimpleDateFormat(convertFromFormat);
        TimeZone timeZone;

        if(convertFromTimeZone.isEmpty()){
            timeZone = TimeZone.getDefault();
        }else
            timeZone = TimeZone.getTimeZone(convertTimeZone);

        df.setTimeZone(timeZone);
        Date dateConvertoldFormat = df.parse(date);
        System.out.println(String.format("Date with format %s as input Timezone: %s ",convertFromFormat,df.format(dateConvertoldFormat)));

        df = new SimpleDateFormat(convertToFormat);
        System.out.println(String.format("Date with format %s as input Timezone: %s",convertToFormat,df.format(dateConvertoldFormat)));

        TimeZone convertToTimezone = TimeZone.getTimeZone(convertTimeZone);
        df.setTimeZone(convertToTimezone);
        System.out.println(String.format("Date with format %s in Timezone %s: %s ",convertToFormat ,convertTimeZone,df.format(dateConvertoldFormat)));
        return df.format(dateConvertoldFormat);

    }
    /**
     * Converting a string to a date
     * @param date 26/01/2019
     * @param format yyyy-MM-dd
     * @return 26/01/2019
     */
    public static Date convertToDate(String date, String format){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get the expected date with correct format and time zone
     * @param date: int, ...-1 or 0, 1, 5,... -1 is yesterday, 0 is current date, 1 is tommorrow
     * @param format the format of date will print "MMM dd, hh:mn"
     *  https://docs.microsoft.com/en-us/dotnet/standard/base-types/custom-date-and-time-format-strings
     * @param  timeZone the timezone
     * @return  date as format Apr 29, 20:30
     */
    public static String getDate (int date,String format, String timeZone){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,date);
        return sdf.format(cal.getTime()).toString();
    }

    /**
     *
     * @param timeMillis 1557721905598 length is 13 digits
     * @param format dd/MM/yyyy HH:mm:ss, dd/MM/yyyy and so on
     * @return 12/05/2019 23:40:12
     */
    public static String convertMillisToDateTime(String timeMillis, String format){
        Date date = new Date(Long.parseLong(timeMillis));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        return sdf.format(date);
    }


    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime()-date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
///////////////////

    ////////////////
    public static void main(String[] args) throws ParseException {
      //  System.out.println(DateUtils.getPreviousDate("1/1/2019","dd/MM/yyyy"));
//        System.out.println(DateUtils.getDateBeforeCurrentDate(0, "GMT-7", "dd/MM/yyyy"));
//        System.out.println(DateUtils.getMonthYear("GMT-4", 0, "MM-yyyy"));
       // System.out.println(DateUtils.formatDate("1/1/2019","YYYY-MM-dd","");
        //System.out.println(DateUtils.convertToDate("2022-09-26T04:35:02.000+00:00","dd/MM/yyyy HH:mm"));
        System.out.println(DateUtils.convertDateToNewTimeZone("2022-09-30T09:00:00.000+00:00","yyyy-MM-dd'T'HH:mm:ss.SSSXXX","","yyyy-MM-dd HH:mm","GMT+8"));

    }

}
