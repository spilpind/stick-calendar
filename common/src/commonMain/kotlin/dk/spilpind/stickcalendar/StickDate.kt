package dk.spilpind.stickcalendar

/**
 * Representation of a date in the stick calendar. Note that [year] can be negative and thus represents a year prior to
 * the stick calendar epoch. There's no guarantee that this represents a valid year - see [StickCalendar.isValid]
 */
data class StickDate(val day: Int, val year: Int) {

    /**
     * Returns a simplified representation of the stick date, for instance "19\.311" or "35./125" in case the year is
     * prior to the stick calendar epoch
     */
    fun toSimplifiedString() =
        "${
            if (year >= 0) {
                "$year\\."
            } else {
                "${-year}./"
            }
        }$day"

    /**
     * Returns an extended representation of the stick date day in Danish. For instance "23. pindsedag"
     */
    fun toExtendedDayString() = "$day. pindsedag"

    /**
     * Returns an extended representation of the full stick date in Danish. For instance "277. pindsedag år 41" or "211.
     * pindsedag år 20 før pindens tid" in case the year is prior to the stick calendar epoch
     */
    fun toExtendedFullString() =
        "${toExtendedDayString()} år ${
            if (year >= 0) {
                year
            } else {
                "${-year} før pindens tid"
            }
        }"

}
