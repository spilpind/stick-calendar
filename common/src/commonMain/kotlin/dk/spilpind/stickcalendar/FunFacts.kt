package dk.spilpind.stickcalendar

import dk.spilpind.stickcalendar.StickCalendar.isValid
import dk.spilpind.stickcalendar.StickCalendar.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

object FunFacts {

    data class Occurrences(val countSince: Int, val countGregorianMatchSince: Int)

    fun occurrencesSince(date: StickDate): Occurrences? {
        val today = Clock.System.todayAt(TimeZone.currentSystemDefault())

        if (date.toLocalDate() >= today) {
            return null
        }

        val originLocalDate = date.toLocalDate()

        var currentDate = date

        var countSince = 0
        var countGregorianMatchSince = 0
        while (currentDate.toLocalDate() <= today) {
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
            countSince = countSince,
            countGregorianMatchSince = countGregorianMatchSince
        )
    }

}
