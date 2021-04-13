import it.woar.stickcalendar.StickCalendar.lengthOfYear
import it.woar.stickcalendar.StickCalendar.toStickDate
import kotlinx.browser.document
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import react.dom.render

fun main() {
    render(document.getElementById("stick-calendar-root")) {
        child(ReactGregToStickApp::class) { }
        child(ReactStickToGregApp::class) { }

        stickCalendarComponentsByClassName("stick-calendar-today") {
            val today = Clock.System.todayAt(TimeZone.currentSystemDefault()).toStickDate()
            +today.toExtendedFullString()
        }

        stickCalendarComponentsByClassName("stick-calendar-days-left-of-year") {
            val today = Clock.System.todayAt(TimeZone.currentSystemDefault()).toStickDate()
            val daysLeft = lengthOfYear(today.year) - today.day
            +"$daysLeft ${
                if (daysLeft == 1) {
                    "dag"
                } else {
                    "dage"
                }
            }"
        }
    }
}
