package dk.spilpind.stickcalendar

import dk.spilpind.stickcalendar.TextUtil.replaceExtendedDates
import dk.spilpind.stickcalendar.TextUtil.replaceSimpleDates
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlin.test.Test
import kotlin.test.assertEquals

class TextUtilTest {

    private companion object {
        val TODAY = LocalDate(year = 2012, month = Month.MARCH, dayOfMonth = 14)
    }

    @Test
    fun replaceExtendedDatesShortMonths() {
        assertEquals(expected = "216. pindsedag år 50", "1. jan 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "248. pindsedag år 50", "2. feb 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "277. pindsedag år 50", "3. mar 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "309. pindsedag år 50", "4. apr 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "14. pindsedag år 51", "5. jun 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "45. pindsedag år 51", "6. jul 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "77. pindsedag år 51", "7. aug 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "109. pindsedag år 51", "8. sep 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "110. pindsedag år 51", "9. sept 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "141. pindsedag år 51", "10. okt 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "173. pindsedag år 51", "11. nov 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "204. pindsedag år 51", "12. dec 2021".replaceExtendedDates(TODAY))
    }

    @Test
    fun replaceExtendedDatesShortMonthsDots() {
        assertEquals(expected = "228. pindsedag år 50", "13. jan. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "260. pindsedag år 50", "14. feb. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "289. pindsedag år 50", "15. mar. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "321. pindsedag år 50", "16. apr. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "26. pindsedag år 51", "17. jun. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "57. pindsedag år 51", "18. jul. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "89. pindsedag år 51", "19. aug. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "121. pindsedag år 51", "20. sep. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "122. pindsedag år 51", "21. sept. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "153. pindsedag år 51", "22. okt. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "185. pindsedag år 51", "23. nov. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "216. pindsedag år 51", "24. dec. 2021".replaceExtendedDates(TODAY))
    }

    @Test
    fun replaceExtendedDatesLongMonths() {
        assertEquals(expected = "240. pindsedag år 50", "25. januar 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "272. pindsedag år 50", "26. februar 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "301. pindsedag år 50", "27. marts 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "333. pindsedag år 50", "28. april 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "7. pindsedag år 51", "29. maj 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "39. pindsedag år 51", "30. juni 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "70. pindsedag år 51", "31. juli 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "71. pindsedag år 51", "1. august 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "103. pindsedag år 51", "2. september 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "134. pindsedag år 51", "3. oktober 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "166. pindsedag år 51", "4. november 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "197. pindsedag år 51", "5. december 2021".replaceExtendedDates(TODAY))
    }

    @Test
    fun replaceExtendedDatesLongMonthsDots() {

        // In this case we don't expect year to be used because of the dot
        // This at the same time tests the functionality that handles dates without a year

        assertEquals(expected = "209. pindsedag. 2021", "6. januar. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "241. pindsedag. 2021", "7. februar. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "271. pindsedag. 2021", "8. marts. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "303. pindsedag. 2021", "9. april. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "334. pindsedag. 2021", "10. maj. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "16. pindsedag år 42. 2021", "11. juni. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "47. pindsedag år 42. 2021", "12. juli. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "79. pindsedag år 42. 2021", "13. august. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "111. pindsedag år 42. 2021", "14. september. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "142. pindsedag år 42. 2021", "15. oktober. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "174. pindsedag år 42. 2021", "16. november. 2021".replaceExtendedDates(TODAY))
        assertEquals(expected = "205. pindsedag år 42. 2021", "17. december. 2021".replaceExtendedDates(TODAY))
    }

    @Test
    fun replaceExtendedDatesNonDates() {
        // Day too large
        assertEquals(
            expected = "33. marts 2021",
            actual = "33. marts 2021".replaceSimpleDates(TODAY)
        )
        // Unknown month
        assertEquals(
            expected = "21. bent 2012",
            actual = "21. bent 2012".replaceSimpleDates(TODAY)
        )
        // Invalid date
        assertEquals(
            expected = "29. februar 2009",
            actual = "29. februar 2009".replaceSimpleDates(TODAY)
        )
    }

    @Test
    fun replaceSimpleDatesFullDatesHumanReadable() {
        val dividers = listOf(
            Pair("-", "-"),
            Pair("-", "/"),
            Pair("-", "."),
            Pair("-", " "),
            Pair(".", "."),
            Pair(".", "-"),
            Pair(".", "/"),
            Pair(".", " "),
            Pair("/", "/"),
            Pair("/", "-"),
            Pair("/", "."),
            Pair("/", " "),
        )

        dividers.forEach { (firstDivider, secondDivider) ->
            // Single digit day, single digit month, full year
            assertEquals(
                expected = "49\\.207",
                actual = "1${firstDivider}1${secondDivider}2020".replaceSimpleDates(TODAY)
            )
            // Double digit day, single digit month, full year
            assertEquals(
                expected = "51\\.111",
                actual = "10${firstDivider}9${secondDivider}2021".replaceSimpleDates(TODAY)
            )
            // Single digit day, double digit month, full year
            assertEquals(
                expected = "52\\.126",
                actual = "8${firstDivider}10${secondDivider}2022".replaceSimpleDates(TODAY)
            )
            // Double digit day, double digit month, full year
            assertEquals(
                expected = "53\\.168",
                actual = "11${firstDivider}11${secondDivider}2023".replaceSimpleDates(TODAY)
            )
            // Single digit day, single digit month, short year
            assertEquals(
                expected = "39\\.276",
                actual = "2${firstDivider}3${secondDivider}10".replaceSimpleDates(TODAY)
            )
            // Double digit day, single digit month, short year
            assertEquals(
                expected = "40\\.374",
                actual = "31${firstDivider}5${secondDivider}11".replaceSimpleDates(TODAY)
            )
            // Single digit day, double digit month, short year
            assertEquals(
                expected = "42\\.164",
                actual = "6${firstDivider}11${secondDivider}12".replaceSimpleDates(TODAY)
            )
            // Double digit day, double digit month, short year
            assertEquals(
                expected = "43\\.220",
                actual = "24${firstDivider}12${secondDivider}13".replaceSimpleDates(TODAY)
            )
        }
    }

    @Test
    fun replaceSimpleDatesFullDatesSortfriendlyOrdering() {
        val dividers = listOf(
            Pair("-", "-"),
            Pair("-", "/"),
            Pair("-", "."),
            Pair("-", " "),
            Pair(".", "."),
            Pair(".", "-"),
            Pair(".", "/"),
            Pair(".", " "),
            Pair("/", "/"),
            Pair("/", "-"),
            Pair("/", "."),
            Pair("/", " "),
        )

        dividers.forEach { (firstDivider, secondDivider) ->
            // Full year, single digit month, single digit day
            assertEquals(
                expected = "44\\.302",
                actual = "2015${firstDivider}4${secondDivider}5".replaceSimpleDates(TODAY)
            )
            // Full year, double digit month, single digit day
            assertEquals(
                expected = "46\\.121",
                actual = "2016${firstDivider}9${secondDivider}12".replaceSimpleDates(TODAY)
            )
            // Full year, single digit month, double digit day
            assertEquals(
                expected = "47\\.121",
                actual = "2017${firstDivider}10${secondDivider}2".replaceSimpleDates(TODAY)
            )
            // Full year, double digit month, double digit day
            assertEquals(
                expected = "48\\.205",
                actual = "2018${firstDivider}12${secondDivider}10".replaceSimpleDates(TODAY)
            )
            // Short year, single digit month, single digit day - this has to be with year > 31
            assertEquals(
                expected = "82\\.85",
                actual = "52${firstDivider}9${secondDivider}1".replaceSimpleDates(TODAY)
            )
            // Short year, double digit month, single digit day - this has to be with year > 31
            assertEquals(
                expected = "72\\.163",
                actual = "42${firstDivider}11${secondDivider}3".replaceSimpleDates(TODAY)
            )
            // Short year, single digit month, double digit day - this has to be with year > 31
            assertEquals(
                expected = "61\\.305",
                actual = "32${firstDivider}3${secondDivider}31".replaceSimpleDates(TODAY)
            )
            // Short year, double digit month, double digit day - this has to be with year > 31
            assertEquals(
                expected = "129\\.196",
                actual = "99${firstDivider}12${secondDivider}12".replaceSimpleDates(TODAY)
            )
        }
    }

    @Test
    fun replaceSimpleDatesFullDatesNonDates() {
        val invalidDividerCombinations = listOf(
            Pair(" ", "-"),
            Pair(" ", "/"),
            Pair(" ", "."),
            Pair(" ", " "),
        )

        invalidDividerCombinations.forEach { (firstDivider, secondDivider) ->
            // Human readable
            assertEquals(
                expected = "10${firstDivider}2${secondDivider}2012",
                actual = "10${firstDivider}2${secondDivider}2012".replaceSimpleDates(TODAY)
            )

            // Sortfriendly ordering
            assertEquals(
                expected = "1970${firstDivider}12${secondDivider}24",
                actual = "1970${firstDivider}12${secondDivider}24".replaceSimpleDates(TODAY)
            )
        }

        // Day/year too large
        assertEquals(
            expected = "32-7-32",
            actual = "32-7-32".replaceSimpleDates(TODAY)
        )
        // Month too large
        assertEquals(
            expected = "5-13-2012",
            actual = "5-13-2012".replaceSimpleDates(TODAY)
        )

        // Invalid date
        assertEquals(
            expected = "29-2-2018",
            actual = "29-2-2018".replaceSimpleDates(TODAY)
        )
    }

    @Test
    fun replaceSimpleDatesShortDates() {
        val dividers = listOf("-", ".", "/")

        dividers.forEach { divider ->
            // Single digit day, single digit month
            assertEquals(
                expected = "42\\.105",
                actual = "8${divider}9".replaceSimpleDates(TODAY)
            )
            // Single digit day, double digit month
            assertEquals(
                expected = "42\\.136",
                actual = "9${divider}10".replaceSimpleDates(TODAY)
            )
            // Double digit day, single digit month
            assertEquals(
                expected = "41\\.213",
                actual = "10${divider}1".replaceSimpleDates(TODAY)
            )
            // Double digit day, double digit month
            assertEquals(
                expected = "42\\.199",
                actual = "11${divider}12".replaceSimpleDates(TODAY)
            )
        }
    }

    @Test
    fun replaceSimpleDatesShortDatesNonDates() {
        val dividers = listOf("-", ".", "/")
        val todayWithLeapYear = LocalDate(year = 2021, month = Month.JANUARY, dayOfMonth = 1)

        dividers.forEach { divider ->
            // Day too large
            assertEquals(
                expected = "32${divider}7",
                actual = "32${divider}7".replaceSimpleDates(TODAY)
            )
            // Month too large
            assertEquals(
                expected = "6${divider}13",
                actual = "6${divider}13".replaceSimpleDates(TODAY)
            )
            // Invalid date
            assertEquals(
                expected = "29${divider}2",
                actual = "29${divider}2".replaceSimpleDates(today = todayWithLeapYear)
            )
        }

        // Using unexpected divider
        assertEquals(
            expected = "24 12",
            actual = "24 12".replaceSimpleDates(TODAY)
        )
    }

}
