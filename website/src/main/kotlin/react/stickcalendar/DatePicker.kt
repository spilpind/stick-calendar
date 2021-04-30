package react.stickcalendar

import kotlinx.datetime.LocalDate
import react.*
import kotlin.js.Date

/**
 * Props of [DatePicker]
 */
external interface DatePickerProps : RProps {
    var selectedDate: LocalDate
    var onDateSelected: (LocalDate) -> Unit
}

/**
 * React component that uses an inline version of [react.datepicker.datePicker] to show a date picker. Note that this
 * deals with [LocalDate] instead of [Date] (that is used by the React date picker)
 */
@JsExport
class DatePicker : RComponent<DatePickerProps, RState>() {

    override fun RBuilder.render() {
        kotlinext.js.require("react-datepicker/dist/react-datepicker.css")

        react.datepicker.datePicker {
            attrs {
                selected = Date(
                    props.selectedDate.year,
                    props.selectedDate.monthNumber - 1,
                    props.selectedDate.dayOfMonth
                )
                onChange = { date ->
                    props.onDateSelected(
                        LocalDate(
                            dayOfMonth = date.getDate(),
                            monthNumber = date.getMonth() + 1,
                            year = date.getFullYear()
                        )
                    )
                }
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
