import it.woar.stickcalendar.StickCalendar.toStickDate
import kotlinx.datetime.LocalDate
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.input
import react.dom.p
import kotlin.js.Date

external interface AppState : RState {
    var selectedDate: Date
}

@JsExport
class ReactApp : RComponent<RProps, AppState>() {

    override fun AppState.init() {
        selectedDate = Date(Date.now())
    }

    override fun RBuilder.render() {
        stickCalendarComponent("stick-calendar-greg-to-stick-input") {
            input {
                attrs.value =
                    "${state.selectedDate.getDate()}/${state.selectedDate.getUTCMonth() + 1}/${state.selectedDate.getFullYear()}"
                attrs.onChangeFunction = {
                    val target = it.target as HTMLInputElement
                    setState {
                        val dateParts = target.value.split("/")
                        selectedDate = Date(dateParts[2].toInt(), dateParts[1].toInt(), dateParts[0].toInt())
                    }
                }
            }
        }

        stickCalendarComponent("stick-calendar-greg-to-stick-calendar") {
            datePicker {
                selectedDate = state.selectedDate
                onDateSelected = { date ->
                    setState {
                        selectedDate = date
                    }
                }
            }
        }

        stickCalendarComponent("stick-calendar-greg-to-stick-result") {
            p {
                +"Stick Date: ${
                    LocalDate(
                        year = state.selectedDate.getFullYear(),
                        monthNumber = state.selectedDate.getUTCMonth() + 1,
                        dayOfMonth = state.selectedDate.getDate()
                    ).toStickDate()
                }"
            }
        }
    }
}