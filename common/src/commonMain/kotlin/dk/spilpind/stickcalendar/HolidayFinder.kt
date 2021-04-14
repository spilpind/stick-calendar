package dk.spilpind.stickcalendar

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

/**
 * Utility class that with various methods helps figuring out dates of certain holiday-days.
 *
 * We have for now limited the earliest year to be 1583 since the Gregorian calendar was introduced in late 1582.
 * The issue of dealing with dates before that is mainly related to leap years (since the calculation was much more
 * simple up until 1582) and usage of Julian vs. Gregorian calendar. The implementations of [LocalDate] seems to ignore
 * the fact that Gregorian calendar wasn't used before 1582 and uses the new leap year calculation at all times (for
 * instance, see JavaScript: https://tc39.es/ecma262/#sec-year-number) which might make sense in some cases (see
 * https://en.wikipedia.org/wiki/Proleptic_Gregorian_calendar), but some sites (like Wikipedia and timeanddate.com)
 * looks like they kept the the original leap days (for instance for the year 1500: timeanddate.com/calendar/?year=1500)
 * and weekdays (for instance 1582 started on a monday according to timeanddate.com and the Julian calendar, but
 * according to [LocalDate] it was a sunday). Therefore [LocalDate] and what the week days actually were back then will
 * come out of sync when using dates prior to 1583
 */
object HolidayFinder {

    /**
     * There's a lot of different algorithms that could be used here, but we ended up using this found here:
     * https://en.wikipedia.org/wiki/Computus#Anonymous_Gregorian_algorithm
     *
     * The Gauss algorithm also mentioned in Wikipedia seems to result in some wrong dates. This might be solved by the
     * corrections made by Gauss, they are however tricky to figure out and just makes the calculations even more
     * complex.
     *
     * Another algorithm could be the one at assa.org.au/edm, but the computer program algorithm "only" works until
     * 4099, so why not just pick the one from Wikipedia that also works further on (even though - who knows if there'll
     * even be easter at that point?). It's however used as expected list in the tests instead
     */
    fun findEaster(year: Int): LocalDate {
        if (year < 1583) {
            throw IllegalArgumentException("Years prior to 1583 not supported")
        }

        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val g = ((8 * b) + 13) / 25
        val h = ((19 * a) + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val l = (32 + (2 * e) + (2 * i) - h - k) % 7
        val m = (a + (11 * h) + (19 * l)) / 433
        val month = (h + l - (7 * m) + 90) / 25
        val day = (h + l - (7 * m) + (33 * month) + 19) % 32

        return LocalDate(
            year = year,
            monthNumber = month,
            dayOfMonth = day
        )
    }

    /**
     * Finds date of pentecost for the given [year]. Uses [findEaster], so same thoughts and limitations applies
     */
    fun findPentecost(year: Int): LocalDate {
        return findEaster(year) + DatePeriod(days = 49)
    }
}