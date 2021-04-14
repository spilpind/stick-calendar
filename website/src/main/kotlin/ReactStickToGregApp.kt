import RenderUtil.renderFunFacts
import RenderUtil.renderGregDateOutput
import RenderUtil.renderStickDateOutput
import dk.spilpind.stickcalendar.StickCalendar.toLocalDate
import dk.spilpind.stickcalendar.StickCalendar.toStickDate
import dk.spilpind.stickcalendar.StickDate
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
        val elementPrefix = "stick-calendar-stick-to-greg"

        stickCalendarComponentById("$elementPrefix-input") {
            stickDateInput {
                selectedDate = state.selectedDate
                onDateSelected = { date ->
                    setState {
                        selectedDate = date
                    }
                }
            }
        }

        renderGregDateOutput(elementPrefix, state.selectedDate.toLocalDate())

        renderStickDateOutput(elementPrefix, state.selectedDate)

        renderFunFacts(elementPrefix, state.selectedDate)
    }
}