package dk.spilpind.stickcalendar

import dk.spilpind.stickcalendar.TextUtil.replaceExtendedDates
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
}
