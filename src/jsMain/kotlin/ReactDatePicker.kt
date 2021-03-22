import react.*
import react.dom.div
import kotlin.js.Date

@JsExport
class ReactDatePicker() : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        kotlinext.js.require("react-datepicker/dist/react-datepicker.css")

        npmReactDatePicker {
            attrs.selected = Date(2020, 10, 20)
        }
    }
}

fun RBuilder.datePicker(handler: RProps.() -> Unit) =
    child(ReactDatePicker::class) {
        this.attrs(handler)
    }