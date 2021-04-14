import dk.spilpind.stickcalendar.FunFacts
import dk.spilpind.stickcalendar.StickCalendar.isValid
import dk.spilpind.stickcalendar.StickCalendar.toLocalDate
import dk.spilpind.stickcalendar.StickCalendar.toStickDate
import dk.spilpind.stickcalendar.StickDate
import kotlinx.css.Display
import kotlinx.datetime.*
import org.w3c.dom.HTMLElement
import react.RBuilder

object RenderUtil {

    fun RBuilder.renderGregDateOutput(elementPrefix: String, date: LocalDate) {
        val dayMonth = "${date.dayOfMonth}. ${
            when (date.month) {
                Month.JANUARY -> "januar"
                Month.FEBRUARY -> "februar"
                Month.MARCH -> "marts"
                Month.APRIL -> "april"
                Month.MAY -> "maj"
                Month.JUNE -> "juni"
                Month.JULY -> "juli"
                Month.AUGUST -> "august"
                Month.SEPTEMBER -> "september"
                Month.OCTOBER -> "oktober"
                Month.NOVEMBER -> "november"
                Month.DECEMBER -> "december"
            }
        }"

        stickCalendarComponentsByClassName("$elementPrefix-output-greg-extended") {
            +"$dayMonth ${date.year}"
        }

        stickCalendarComponentsByClassName("$elementPrefix-output-greg-day-month") {
            +dayMonth
        }
    }

    fun RBuilder.renderStickDateOutput(elementPrefix: String, date: StickDate) {
        stickCalendarComponentsByClassName("$elementPrefix-output-stick-extended") {
            +date.toExtendedFullString()
        }

        stickCalendarComponentsByClassName("$elementPrefix-output-stick-simplified") {
            +date.toSimplifiedString()
        }

        stickCalendarComponentsByClassName("$elementPrefix-output-stick-day") {
            +date.toExtendedDayString()
        }
    }

    fun RBuilder.renderFunFacts(elementPrefix: String, date: StickDate) {
        val todayLocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
        val todayStickDate = todayLocalDate.toStickDate()

        var nextTime = StickDate(
            day = date.day,
            year = todayStickDate.year
        )

        while (!nextTime.isValid || nextTime.toLocalDate() <= todayLocalDate) {
            nextTime = StickDate(
                day = nextTime.day,
                year = nextTime.year + 1
            )
        }

        stickCalendarComponentsByClassName("$elementPrefix-fun-fact-until-next-count") {
            val days = todayStickDate.toLocalDate().daysUntil(nextTime.toLocalDate())
            +"$days ${
                if (days == 1) {
                    "dag"
                } else {
                    "dage"
                }
            }"
        }

        stickCalendarComponentsByClassName("$elementPrefix-fun-fact-until-next-date") {
            +nextTime.toSimplifiedString()
        }

        stickCalendarComponentById("$elementPrefix-fun-fact-day-is-today") {
            val element = attrs.root as? HTMLElement ?: return@stickCalendarComponentById

            val display = if (date.day == todayStickDate.day) {
                Display.block
            } else {
                Display.none
            }

            element.style.display = display.name
        }

        val occurrences = FunFacts.occurrencesSince(date)
        val displayCountContainer = if (occurrences == null) {
            Display.none
        } else {
            stickCalendarComponentsByClassName("$elementPrefix-fun-fact-count-match-since-years") {
                +"${occurrences.countSince} år"
            }

            stickCalendarComponentsByClassName("$elementPrefix-fun-fact-count-match-since-times") {
                +"${occurrences.countSince} ${
                    if (occurrences.countSince == 1) {
                        "gang"
                    } else {
                        "gange"
                    }
                }"
            }

            stickCalendarComponentsByClassName("$elementPrefix-fun-fact-count-greg-match-since") {
                +"${occurrences.countGregorianMatchSince} ${
                    if (occurrences.countGregorianMatchSince == 1) {
                        "gang"
                    } else {
                        "gange"
                    }
                }"
            }

            Display.block
        }

        stickCalendarComponentById("$elementPrefix-fun-fact-count-container") {
            (attrs.root as? HTMLElement)?.style?.display = displayCountContainer.name
        }
    }
}
