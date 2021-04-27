import dk.spilpind.stickcalendar.StickCalendar.lengthOfYear
import dk.spilpind.stickcalendar.StickCalendar.toStickDate
import kotlinx.browser.document
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import react.RBuilder
import react.dom.render
import react.stickcalendar.GregToStickApp
import react.stickcalendar.StickToGregApp
import react.stickcalendar.stickCalendarComponentsByClassName

fun main() {
    render(document.getElementById("stick-calendar-root")) {
        child(GregToStickApp::class) { }
        child(StickToGregApp::class) { }

        renderTodayInfo()
    }
}

private fun RBuilder.renderTodayInfo() {
    val today = Clock.System.todayAt(TimeZone.currentSystemDefault()).toStickDate()

    stickCalendarComponentsByClassName("stick-calendar-today") {
        +today.toExtendedFullString()
    }

    stickCalendarComponentsByClassName("stick-calendar-days-left-of-year") {
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
