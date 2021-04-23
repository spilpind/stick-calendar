package dk.spilpind.stickcalendar

import dk.spilpind.stickcalendar.StickCalendar.isValid
import dk.spilpind.stickcalendar.StickCalendar.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

/**
 * Helps finding fun facts related to [StickDate]
 */
object FunFacts {

    /**
     * Representing number of times the stick date day was found ([stickDateMatchesSince]) and number of times it also
     * matched its representation in the gregorian calendar for a specific stick date ([gregorianMatchesSince]).
     *
     * For instance between 9\.270 and 50\.330 (the date of writing this documentation), the day 270 has occurred 41
     * times (which of course matches the difference in years since the day is less than 350), but the gregorian
     * day/month of 9\.270 (27th of february) has only occurred twice on the same day as 270 since 9\.270
     */
    data class Occurrences(val stickDateMatchesSince: Int, val gregorianMatchesSince: Int)

    /**
     * Calculates the number of times the day of [date] has happened until today. If the date is today or in the future,
     * this calculation doesn't make sense and null is returned. For further info to the result, check documentation of
     * [Occurrences]
     */
    fun occurrencesSince(date: StickDate): Occurrences? {
        val today = Clock.System.todayAt(TimeZone.currentSystemDefault())

        if (date.toLocalDate() >= today) {
            return null
        }

        val originLocalDate = date.toLocalDate()

        var currentDate = date

        var countSince = 0
        var countGregorianMatchSince = 0
        while (true) {
            currentDate = StickDate(
                day = currentDate.day,
                year = currentDate.year + 1
            )

            if (!currentDate.isValid) {
                continue
            }

            val localDate = currentDate.toLocalDate()
            if (localDate > today) {
                break
            }

            ++countSince

            if (originLocalDate.dayOfMonth == localDate.dayOfMonth && originLocalDate.month == localDate.month) {
                ++countGregorianMatchSince
            }
        }

        return Occurrences(
            stickDateMatchesSince = countSince,
            gregorianMatchesSince = countGregorianMatchSince
        )
    }

}
