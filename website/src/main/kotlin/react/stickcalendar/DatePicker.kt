package react.stickcalendar

import react.*
import kotlin.js.Date

external interface DatePickerProps : RProps {
    var selectedDate: Date
    var onDateSelected: (Date) -> Unit
}

@JsExport
class DatePicker : RComponent<DatePickerProps, RState>() {

    override fun RBuilder.render() {
        kotlinext.js.require("react-datepicker/dist/react-datepicker.css")

        react.datepicker.datePicker {
            attrs {
                selected = props.selectedDate
                onChange = props.onDateSelected
                inline = true
            }

        }
    }
}

fun RBuilder.datePicker(handler: DatePickerProps.() -> Unit) =
    child(DatePicker::class) {
        this.attrs(handler)
    }
