import it.woar.stickcalendar.StickCalendar.toStickDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import react.*
import kotlin.js.Date

external interface ReactGregToStickAppState : RState {
    var selectedDate: LocalDate
}

@JsExport
class ReactGregToStickApp : RComponent<RProps, ReactGregToStickAppState>() {

    override fun ReactGregToStickAppState.init() {
        selectedDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
    }

    override fun RBuilder.render() {
        stickCalendarComponent("stick-calendar-greg-to-stick-input") {
            localDateInput {
                selectedDate = state.selectedDate
                onDateSelected = { date ->
                    setState {
                        selectedDate = date
                    }
                }
            }
        }

        stickCalendarComponent("stick-calendar-greg-to-stick-calendar") {
            datePicker {
                selectedDate = Date(
                    state.selectedDate.year,
                    state.selectedDate.monthNumber - 1,
                    state.selectedDate.dayOfMonth
                )
                onDateSelected = { date ->
                    setState {
                        selectedDate = LocalDate(
                            dayOfMonth = date.getDate(),
                            monthNumber = date.getMonth() + 1,
                            year = date.getFullYear()
                        )
                    }
                }
            }
        }

        stickCalendarComponent("stick-calendar-greg-to-stick-result-extended") {
            val stickDate = state.selectedDate.toStickDate()
            +stickDate.toExtendedString()
        }

        stickCalendarComponent("stick-calendar-greg-to-stick-result-simplified") {
            val stickDate = state.selectedDate.toStickDate()
            +stickDate.toSimplifiedString()
        }
    }
}