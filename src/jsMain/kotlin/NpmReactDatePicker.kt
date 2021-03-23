@file:JsModule("react-datepicker")
@file:JsNonModule

import react.*
import kotlin.js.Date

@JsName("default")
external val npmReactDatePicker: RClass<NpmReactDatePickerProps>

external interface NpmReactDatePickerProps : RProps {
    var selected: Date
    var onChange: (Date) -> Unit
}