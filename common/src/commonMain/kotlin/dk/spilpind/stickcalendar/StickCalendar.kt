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
        val pentecostDate = try {
            val pentecostDate = HolidayFinder.findPentecost(year)

            if (this < pentecostDate) {
                // The date is before this years pentecost date so origin is last year's instead
                HolidayFinder.findPentecost(year - 1)
            } else {
                pentecostDate
            }

        } catch (exception: IllegalArgumentException) {
            throw IllegalArgumentException("Could not convert $this to StickDate", exception)
        }

        return StickDate(
            day = pentecostDate.daysUntil(this) + 1,
            year = pentecostDate.year - ORIGIN_YEAR
        )
    }

    /**
     * Returns true if the [StickDate] is a valid combination of [StickDate.day] and [StickDate.year] (a date that has
     * happened or will happen)
     */
    val StickDate.isValid: Boolean
        get() = try {
            toLocalDate()
            true
        } catch (exception: IllegalArgumentException) {
            false
        }

    /**
     * Converts the [StickDate] to its counterpart in the Gregorian calendar (represented by [LocalDate]). Note that
     * this will throw an exception if the date is invalid
     */
    fun StickDate.toLocalDate(): LocalDate {
        val yearLength = lengthOfYear(year)
        if (day > yearLength) {
            throw IllegalArgumentException(
                "Could not convert $this to LocalDate as year $year only has $yearLength days"
            )
        }

        val pentecost = HolidayFinder.findPentecost(ORIGIN_YEAR + year)

        return pentecost + DatePeriod(days = day - 1)
    }

    /**
     * Returns number of days for the specified [stickYear]
     */
    fun lengthOfYear(stickYear: Int): Int {
        val thisYearPentecost = HolidayFinder.findPentecost(ORIGIN_YEAR + stickYear)
        val nextYearPentecost = HolidayFinder.findPentecost(ORIGIN_YEAR + stickYear + 1)

        return thisYearPentecost.daysUntil(nextYearPentecost)
    }
}
