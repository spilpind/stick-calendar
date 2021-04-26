@file:JsModule("react-datepicker")
@file:JsNonModule

package react.datepicker

import react.*
import kotlin.js.Date

/**
 * React date picker component. See https://npmjs.com/package/react-datepicker
 */
@JsName("default")
external val datePicker: RClass<DatePickerProps>

/**
 * Props of [datePicker]
 */
external interface DatePickerProps : RProps {
    var selected: Date
    var onChange: (Date) -> Unit
    var inline: Boolean
}
