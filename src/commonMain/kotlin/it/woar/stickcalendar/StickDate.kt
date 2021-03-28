package it.woar.stickcalendar

/**
 * Representation of a date in the Stick calendar
 */
data class StickDate(val day: Int, val year: Int) {

    fun toSimplifiedString() =
        "${
            if (year >= 0) {
                "$year\\."
            } else {
                "${-year}./"
            }
        }$day"

    fun toExtendedDayString() = "$day. pindsedag"

    fun toExtendedFullString() =
        "${toExtendedDayString()} år ${
            if (year >= 0) {
                year
            } else {
                "${-year} før pindens tid"
            }
        }"

}
