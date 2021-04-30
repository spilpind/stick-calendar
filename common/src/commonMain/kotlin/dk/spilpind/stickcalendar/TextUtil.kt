package dk.spilpind.stickcalendar

import dk.spilpind.stickcalendar.StickCalendar.toStickDate
import kotlinx.datetime.*

/**
 * Various methods that helps dealing with the stick calendar in text. For now, they work best with Danish texts
 */
object TextUtil {

    /**
     * Replaces all extended gregorian dates (like "20. marts 1990") in the string with their corresponding extended
     * stick date. If the gregorian date only consist of day and month (without year) we assume it's the current year
     * and only add the stick year to the output if it isn't the same as the current stick year
     */
    fun String.replaceExtendedDates(today: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault())): String {
        val regex = Regex(
            "([0-9]+)\\. (januar|jan\\.?|februar|feb\\.?|marts|mar\\.?|april|apr\\.?|maj|juni|jun\\.?|juli|jul\\.?|august|aug\\.?|september|sept\\.?|sep\\.?|oktober|okt\\.?|november|nov\\.?|december|dec\\.?)(?: ([0-9]+))?",
            RegexOption.IGNORE_CASE
        )

        return regex.replace(this) { matchResult ->
            val dayOfMonth = matchResult.groupValues.getOrNull(1)?.toIntOrNull() ?: return@replace matchResult.value

            val month = matchResult.groupValues.getOrNull(2).let { month ->
                when (month?.removeSuffix(".")) {
                    "jan", "januar" -> Month.JANUARY
                    "feb", "februar" -> Month.FEBRUARY
                    "mar", "marts" -> Month.MARCH
                    "apr", "april" -> Month.APRIL
                    "maj" -> Month.MAY
                    "jun", "juni" -> Month.JUNE
                    "jul", "juli" -> Month.JULY
                    "aug", "august" -> Month.AUGUST
                    "sep", "sept", "september" -> Month.SEPTEMBER
                    "okt", "oktober" -> Month.OCTOBER
                    "nov", "november" -> Month.NOVEMBER
                    "dec", "december" -> Month.DECEMBER
                    else -> {
                        return@replace matchResult.value
                    }
                }
            }

            val year = matchResult.groupValues.getOrNull(3)?.toIntOrNull().let { year ->
                when {
                    year == null -> null
                    year < 100 -> year + 2000
                    else -> year
                }
            }

            try {
                val stickDate = LocalDate(
                    year = year ?: today.year,
                    month = month,
                    dayOfMonth = dayOfMonth
                ).toStickDate()

                if (year == null && stickDate.year == today.toStickDate().year) {
                    // We just assume a date without a year is for the current year and if that's the same for the stick
                    // date there's no reason to include the stick date year
                    stickDate.toExtendedDayString()
                } else {
                    stickDate.toExtendedFullString()
                }
            } catch (exception: IllegalArgumentException) {
                matchResult.value
            }
        }
    }

    /**
     * Replaces all simple gregorian dates (like "20/3-1990") in the string with their corresponding simple stick date.
     * The stick date will always contain the year, even if the corresponding gregorian date doesn't. A lot of various
     * date formats are supported with a mix of dividers like "/", "." and "-"
     */
    fun String.replaceSimpleDates(today: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault())): String {
        val regex = Regex(
            "(?:(d\\.|den) )?([0-9]+)[.\\-/]([0-9]+)(?:[.\\-/ ]([0-9]+))?",
            RegexOption.IGNORE_CASE
        )

        return regex.replace(this) { matchResult ->
            val prefix = matchResult.groupValues.getOrNull(1)

            val datesParts = matchResult.groupValues.subList(2, matchResult.groupValues.size)
                .mapIndexedNotNull { index, datePart ->
                    if (index > 2) {
                        return@replace matchResult.value
                    }

                    val datePartNumber = datePart.toIntOrNull()
                    if (datePartNumber != null) {
                        return@mapIndexedNotNull datePartNumber
                    }

                    if (index == 2) {
                        // Non-existing date part is allowed as it's then just a simple date
                        null
                    } else {
                        return@replace matchResult.value
                    }
                }

            val monthNumber = datesParts[1]
            val (dayOfMonth, year) = when {
                datesParts.size < 3 -> Pair(datesParts[0], today.year)
                datesParts.size > 3 -> return@replace matchResult.value
                datesParts[0] > 31 -> Pair(datesParts[2], datesParts[0])
                else -> Pair(datesParts[0], datesParts[2])
            }.let { (dayOfMonth, year) ->
                if (year < 100) {
                    Pair(dayOfMonth, year + 2000)
                } else {
                    Pair(dayOfMonth, year)
                }
            }

            try {
                val localDate = LocalDate(
                    year = year,
                    monthNumber = monthNumber,
                    dayOfMonth = dayOfMonth
                )

                "${
                    if (prefix != null) {
                        "$prefix "
                    } else {
                        ""
                    }
                }${localDate.toStickDate().toSimplifiedString()}"
            } catch (exception: IllegalArgumentException) {
                matchResult.value
            }
        }
    }
}