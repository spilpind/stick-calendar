package it.woar.stickcalendar

/**
 * Representation of a date in the Stick calendar
 */
data class StickDate(val day: Int, val year: Int) {

    fun toShortString() =
        "${
            if (year >= 0) {
                "$year\\."
            } else {
                "${-year}./"
            }
        }$day"

    fun toHumanString() =
        "$day. pindsedag år ${
            if (year >= 0) {
                year
            } else {
                "${-year} før pindens tid"
            }
        }"

}
