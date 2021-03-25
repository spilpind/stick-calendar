import it.woar.stickcalendar.StickCalendar.toStickDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.input
import kotlin.js.Date

external interface AppState : RState {
    var selectedDate: LocalDate
}

@JsExport
class ReactApp : RComponent<RProps, AppState>() {

    override fun AppState.init() {
        selectedDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
    }

    override fun RBuilder.render() {
        stickCalendarComponent("stick-calendar-greg-to-stick-input") {
            input {
                attrs.value =
                    "${state.selectedDate.dayOfMonth}/${state.selectedDate.monthNumber}/${state.selectedDate.year}"
                attrs.onChangeFunction = {
                    val target = it.target as HTMLInputElement
                    setState {
                        val dateParts = target.value.split("/")
                        selectedDate = LocalDate(
                            dayOfMonth = dateParts[0].toInt(),
                            monthNumber = dateParts[1].toInt(),
                            year = dateParts[2].toInt()
                        )
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

        stickCalendarComponent("stick-calendar-greg-to-stick-result") {
            val stickDate = state.selectedDate.toStickDate()
            +"Stick Date: ${stickDate.toHumanString()} (${stickDate.toShortString()})"
        }
    }
}