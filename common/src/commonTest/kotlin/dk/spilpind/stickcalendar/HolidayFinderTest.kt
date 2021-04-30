package dk.spilpind.stickcalendar

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class HolidayFinderTest {

    companion object {
        private const val EXPECTED_EARLIEST_SUPPORTED_YEAR = 1583
    }

    @Test
    fun easterCheckWeekDay() {
        (EXPECTED_EARLIEST_SUPPORTED_YEAR..10000).forEach { year ->
            assertEquals(expected = DayOfWeek.SUNDAY, HolidayFinder.findEaster(year).dayOfWeek)
        }
    }

    @Test
    fun easterCheckDateStatic() {
        // Sources: https://assa.org.au/edm and https://commons.wikimedia.org/wiki/File:DiagrammePaques_Flammarion.jpg
        // It's some "random" picks, especially focusing on leap years and the years before/after
        val expectedEasterDates = listOf(
            LocalDate(dayOfMonth = 10, month = Month.APRIL, year = 1583),
            LocalDate(dayOfMonth = 1, month = Month.APRIL, year = 1584),
            LocalDate(dayOfMonth = 11, month = Month.APRIL, year = 1599),
            LocalDate(dayOfMonth = 2, month = Month.APRIL, year = 1600),
            LocalDate(dayOfMonth = 22, month = Month.APRIL, year = 1601),
            LocalDate(dayOfMonth = 25, month = Month.MARCH, year = 1663),
            LocalDate(dayOfMonth = 13, month = Month.APRIL, year = 1664),
            LocalDate(dayOfMonth = 5, month = Month.APRIL, year = 1665),
            LocalDate(dayOfMonth = 13, month = Month.APRIL, year = 1727),
            LocalDate(dayOfMonth = 28, month = Month.MARCH, year = 1728),
            LocalDate(dayOfMonth = 17, month = Month.APRIL, year = 1729),
            LocalDate(dayOfMonth = 20, month = Month.APRIL, year = 1919),
            LocalDate(dayOfMonth = 4, month = Month.APRIL, year = 1920),
            LocalDate(dayOfMonth = 27, month = Month.MARCH, year = 1921),
            LocalDate(dayOfMonth = 4, month = Month.APRIL, year = 1999),
            LocalDate(dayOfMonth = 23, month = Month.APRIL, year = 2000),
            LocalDate(dayOfMonth = 15, month = Month.APRIL, year = 2001),
            LocalDate(dayOfMonth = 2, month = Month.APRIL, year = 2051),
            LocalDate(dayOfMonth = 14, month = Month.APRIL, year = 2199),
            LocalDate(dayOfMonth = 6, month = Month.APRIL, year = 2200),
        )

        expectedEasterDates.forEach { expectedDate ->
            assertEquals(expected = expectedDate, HolidayFinder.findEaster(expectedDate.year))
        }
    }

    @Test
    fun easterCheckDateDynamic() {
        (EXPECTED_EARLIEST_SUPPORTED_YEAR..4099).forEach { year ->
            assertEquals(expected = calculateDateWithAssa(year), HolidayFinder.findEaster(year))
        }
    }

    @Test
    fun easterCheckMinimumSupportedYear() {
        assertFails {
            HolidayFinder.findEaster(EXPECTED_EARLIEST_SUPPORTED_YEAR - 1)
        }

        // We just don't expect it to fail
        HolidayFinder.findEaster(EXPECTED_EARLIEST_SUPPORTED_YEAR)
    }

    @Test
    fun pentecostCheckWeekDay() {
        (EXPECTED_EARLIEST_SUPPORTED_YEAR..10000).forEach { year ->
            assertEquals(expected = DayOfWeek.SUNDAY, HolidayFinder.findPentecost(year).dayOfWeek)
        }
    }

    @Test
    fun pentecostCheckDateStatic() {
        // Source: https://assa.org.au/edm (+49 days)
        // Confirmed by (expect 2400) (+49 days): https://commons.wikimedia.org/wiki/File:DiagrammePaques_Flammarion.jpg
        val expectedPentecostDates = listOf(
            LocalDate(dayOfMonth = 29, month = Month.MAY, year = 1583),
            LocalDate(dayOfMonth = 21, month = Month.MAY, year = 1600),
            LocalDate(dayOfMonth = 3, month = Month.JUNE, year = 1900),
            LocalDate(dayOfMonth = 31, month = Month.MAY, year = 2020),
            LocalDate(dayOfMonth = 23, month = Month.MAY, year = 2021),
            LocalDate(dayOfMonth = 5, month = Month.JUNE, year = 2022),
            LocalDate(dayOfMonth = 4, month = Month.JUNE, year = 2400),
        )

        expectedPentecostDates.forEach { expectedDate ->
            assertEquals(expected = expectedDate, HolidayFinder.findPentecost(expectedDate.year))
        }
    }

    /**
     * Logic from https://assa.org.au/edm that are here to help with the remaining dates that we don't have statically
     */
    private fun calculateDateWithAssa(year: Int): LocalDate {
        if (year > 4099) {
            throw IllegalArgumentException("Years later than 4099 not supported by the ASSA calculation")
        }

        val firstDigits = year / 100
        val lastDigits = year % 100
        val remainder19 = year % 19

        // Calculate PFM (Paschal Full Moon) date
        var temp = (firstDigits - 15) / 2 + 202 - (11 * remainder19)

        when (firstDigits) {
            21, 24, 25, 27, 28, 29, 30, 31, 32, 34, 35, 38 ->
                temp -= 1
            33, 36, 37, 39, 40 ->
                temp -= 2
        }
        temp %= 30

        var tableA = temp + 21
        if (temp == 29) {
            tableA -= 1
        }
        if (temp == 28 && remainder19 > 10) {
            tableA -= 1
        }

        // Find the next Sunday
        val tableB = (tableA - 19) % 7

        var tableC = (40 - firstDigits) % 4
        if (tableC == 3) {
            tableC += 1
        }
        if (tableC > 1) {
            tableC += 1
        }

        val tableD = (lastDigits + (lastDigits / 4)) % 7

        val tableE = ((20 - tableB - tableC - tableD) % 7) + 1
        val dayOfMarch = tableA + tableE

        return if (dayOfMarch > 31) {
            LocalDate(dayOfMonth = dayOfMarch - 31, month = Month.APRIL, year = year)
        } else {
            LocalDate(dayOfMonth = dayOfMarch, month = Month.MARCH, year = year)
        }
    }
}
