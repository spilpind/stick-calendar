import react.*
import react.dom.div
import kotlin.js.Date

external interface ReactDatePickerState : RState {
    var selectedDate: Date
}

@JsExport
class ReactDatePicker() : RComponent<RProps, ReactDatePickerState>() {
    override fun RBuilder.render() {
        kotlinext.js.require("react-datepicker/dist/react-datepicker.css")

        npmReactDatePicker {
            attrs {
                selected = state.selectedDate
                onChange = { date ->
                    setState {
                        selectedDate = date
                    }
                }
            }

        }
    }
}

fun RBuilder.datePicker(handler: RProps.() -> Unit) =
    child(ReactDatePicker::class) {
        this.attrs(handler)
    }