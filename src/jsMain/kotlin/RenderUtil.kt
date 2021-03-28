import it.woar.stickcalendar.FunFacts
import it.woar.stickcalendar.StickDate
import kotlinx.css.Display
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
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
        val occurrences = FunFacts.occurrencesSince(date)

        val display = if (occurrences == null) {
            Display.none
        } else {
            stickCalendarComponentsByClassName("$elementPrefix-fun-fact-count-match-since") {
                +"${occurrences.countSince}"
            }

            stickCalendarComponentsByClassName("$elementPrefix-fun-fact-count-greg-match-since") {
                +"${occurrences.countGregorianMatchSince}"
            }

            Display.block
        }

        stickCalendarComponentById("$elementPrefix-fun-fact-count-container") {
            (attrs.root as? HTMLElement)?.style?.display = display.name
        }
    }
}