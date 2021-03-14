package it.woar.stickcalendar

import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil

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
}