@file:JsModule("react-datepicker")
@file:JsNonModule

package react.datepicker

import react.*
import kotlin.js.Date

@JsName("default")
external val datePicker: RClass<DatePickerProps>

external interface DatePickerProps : RProps {
    var selected: Date
    var onChange: (Date) -> Unit
    var inline: Boolean
}