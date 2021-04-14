package dk.spilpind.stickcalendar

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus

/**
 * Utility class that helps dealing with the Stick calendar and dates of it. Uses [HolidayFinder], so same limits as
 * described in its documentation applies
 */
object StickCalendar {

    private const val ORIGIN_YEAR = 1970

    /**
     * Converts the [LocalDate] (in Gregorian calendar) to its counterpart in the stick calendar
     */
    fun LocalDate.toStickDate(): StickDate {
        var pentecostDate = HolidayFinder.findPentecost(year)
        if (this < pentecostDate) {
            pentecostDate = HolidayFinder.findPentecost(year - 1)
        }

        return StickDate(
            day = pentecostDate.daysUntil(this) + 1,
            year = pentecostDate.year - ORIGIN_YEAR
        )
    }

    val StickDate.isValid: Boolean
        get() = try {
            toLocalDate()
            true
        } catch (exception: IllegalArgumentException) {
            false
        }

    fun StickDate.toLocalDate(): LocalDate {
        val yearLength = lengthOfYear(year)
        if (day > yearLength) {
            throw IllegalArgumentException("Could not convert $this to LocalDate as year $year only has $yearLength days")
        }

        val pentecost = HolidayFinder.findPentecost(ORIGIN_YEAR + year)
        return pentecost + DatePeriod(days = day - 1)
    }

    fun lengthOfYear(year: Int): Int {
        val thisYearPentecost = HolidayFinder.findPentecost(ORIGIN_YEAR + year)
        val nextYearPentecost = HolidayFinder.findPentecost(ORIGIN_YEAR + year + 1)

        return thisYearPentecost.daysUntil(nextYearPentecost)
    }
}