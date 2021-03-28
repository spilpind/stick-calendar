import it.woar.stickcalendar.StickCalendar.toLocalDate
import it.woar.stickcalendar.StickCalendar.toStickDate
import it.woar.stickcalendar.StickDate
import kotlinx.datetime.*
import react.*

external interface ReactStickToGregAppState : RState {
    var selectedDate: StickDate
}

@JsExport
class ReactStickToGregApp : RComponent<RProps, ReactStickToGregAppState>() {

    override fun ReactStickToGregAppState.init() {
        selectedDate = Clock.System.todayAt(TimeZone.currentSystemDefault()).toStickDate()
    }

    override fun RBuilder.render() {
        stickCalendarComponentById("stick-calendar-stick-to-greg-input") {
            stickDateInput {
                selectedDate = state.selectedDate
                onDateSelected = { date ->
                    setState {
                        selectedDate = date
                    }
                }
            }
        }

        stickCalendarComponentsByClassName("stick-calendar-stick-to-greg-result-extended") {
            val localDate = state.selectedDate.toLocalDate()
            +"${localDate.dayOfMonth}. ${
                when (localDate.month) {
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
            } ${localDate.year}"
        }
    }
}