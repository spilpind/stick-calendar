package react.stickcalendar

import react.*
import kotlin.js.Date

/**
 * Props of [DatePicker]
 */
external interface DatePickerProps : RProps {
    var selectedDate: Date
    var onDateSelected: (Date) -> Unit
}

/**
 * React component that uses an inline version of [react.datepicker.datePicker] to show a date picker
 */
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

/**
 * Creates a [DatePicker] child element with the provided props
 */
fun RBuilder.datePicker(handler: DatePickerProps.() -> Unit) =
    child(DatePicker::class) {
        this.attrs(handler)
    }
