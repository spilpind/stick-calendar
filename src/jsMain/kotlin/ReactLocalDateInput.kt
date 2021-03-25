import kotlinx.datetime.LocalDate
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.input

external interface ReactLocalDateInputProps : RProps {
    var selectedDate: LocalDate
    var onDateSelected: (LocalDate) -> Unit
}

external interface ReactLocalDateInputState : RState {
    var error: String?
}

@JsExport
class ReactLocalDateInput : RComponent<ReactLocalDateInputProps, ReactLocalDateInputState>() {

    override fun RBuilder.render() {
        input {
            attrs.value =
                "${props.selectedDate.dayOfMonth}/${props.selectedDate.monthNumber}/${props.selectedDate.year}"

            attrs.onChangeFunction = {
                val target = it.target as HTMLInputElement
                val dateParts = target.value.split("/")
                props.onDateSelected(
                    LocalDate(
                        dayOfMonth = dateParts[0].toInt(),
                        monthNumber = dateParts[1].toInt(),
                        year = dateParts[2].toInt()
                    )
                )
            }
        }
    }
}

fun RBuilder.localDateInput(handler: ReactLocalDateInputProps.() -> Unit) =
    child(ReactLocalDateInput::class) {
        this.attrs(handler)
    }
