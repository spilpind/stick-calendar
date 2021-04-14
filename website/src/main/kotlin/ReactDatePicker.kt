import react.*
import kotlin.js.Date

external interface ReactDatePickerProps : RProps {
    var selectedDate: Date
    var onDateSelected: (Date) -> Unit
}

@JsExport
class ReactDatePicker() : RComponent<ReactDatePickerProps, RState>() {

    override fun RBuilder.render() {
        kotlinext.js.require("react-datepicker/dist/react-datepicker.css")

        npmReactDatePicker {
            attrs {
                selected = props.selectedDate
                onChange = props.onDateSelected
                inline = true
            }

        }
    }
}

fun RBuilder.datePicker(handler: ReactDatePickerProps.() -> Unit) =
    child(ReactDatePicker::class) {
        this.attrs(handler)
    }
