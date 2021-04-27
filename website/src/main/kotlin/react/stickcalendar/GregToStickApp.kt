package react.stickcalendar

import RenderUtil.renderFunFacts
import RenderUtil.renderGregDateOutput
import RenderUtil.renderStickDateOutput
import dk.spilpind.stickcalendar.StickCalendar.toStickDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import react.*

/**
 * State of [GregToStickApp]
 */
external interface GregToStickAppState : RState {
    var selectedDate: LocalDate
}

/**
 * An application that enables the user to convert gregorian dates to stick dates
 */
@JsExport
class GregToStickApp : RComponent<RProps, GregToStickAppState>() {

    override fun GregToStickAppState.init() {
        selectedDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
    }

    override fun RBuilder.render() {
        val elementPrefix = "stick-calendar-greg-to-stick"

        stickCalendarComponentById("$elementPrefix-input") {
            localDateInput {
                selectedDate = state.selectedDate
                onDateSelected = { date ->
                    setState {
                        selectedDate = date
                    }
                }
            }
        }

        stickCalendarComponentById("$elementPrefix-calendar") {
            datePicker {
                selectedDate = state.selectedDate
                onDateSelected = { date ->
                    setState {
                        selectedDate = date
                    }
                }
            }
        }

        val localDate = state.selectedDate.toStickDate()

        renderStickDateOutput(elementPrefix, localDate)

        renderGregDateOutput(elementPrefix, state.selectedDate)

        renderFunFacts(elementPrefix, localDate)
    }
}
