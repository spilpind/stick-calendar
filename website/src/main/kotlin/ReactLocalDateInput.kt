import it.woar.stickcalendar.StickCalendar.toStickDate
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.datetime.LocalDate
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.input
import styled.css
import styled.styledP

external interface ReactLocalDateInputProps : RProps {
    var selectedDate: LocalDate
    var onDateSelected: (LocalDate) -> Unit
}

external interface ReactLocalDateInputState : RState {
    var error: String?
    var lastPropDate: LocalDate?
    var dateString: String
}

@JsExport
class ReactLocalDateInput : RComponent<ReactLocalDateInputProps, ReactLocalDateInputState>() {

    companion object : RStatics<ReactLocalDateInputProps, ReactLocalDateInputState, ReactLocalDateInput, Nothing>(
        ReactLocalDateInput::class
    ) {
        init {
            getDerivedStateFromProps = { props, state ->
                if (state.lastPropDate != props.selectedDate) {
                    state.lastPropDate = props.selectedDate

                    val currentDate = try {
                        state.dateString.toDate()
                    } catch (exception: IllegalArgumentException) {
                        null
                    }

                    // Checking this ensures we won't change the input text if the input is valid but not as we would
                    // insert. Can happen if the user types: 01-01-2021 => 01-0-2021 => 01-02-2021
                    if (currentDate != props.selectedDate) {
                        state.dateString = props.selectedDate.let { date ->
                            "${date.dayOfMonth}-${date.monthNumber}-${date.year}"
                        }
                    }

                    state.error = null
                }

                state
            }
        }
    }

    override fun ReactLocalDateInputState.init() {
        dateString = ""
    }

    override fun RBuilder.render() {
        input {
            attrs.value = state.dateString

            attrs.onChangeFunction = {
                val target = it.target as HTMLInputElement
                setState {
                    dateString = target.value
                    error = try {
                        val date = target.value.toDate()

                        // Note: It seems like props update as a reaction to calling this will be executed after
                        // updating the local state
                        props.onDateSelected(date)

                        null
                    } catch (exception: IllegalArgumentException) {
                        exception.message
                    }
                }
            }
        }
        styledP {
            css {
                color = Color.darkRed
                display = if (state.error == null) {
                    Display.none
                } else {
                    Display.block
                }
            }

            +(state.error ?: "")
        }
    }
}

fun RBuilder.localDateInput(handler: ReactLocalDateInputProps.() -> Unit) =
    child(ReactLocalDateInput::class) {
        this.attrs(handler)
    }

private fun String.toDate(): LocalDate {
    val dateParts = split("-").map { datePart ->
        datePart.trim()
    }

    if (dateParts.size < 3 || dateParts.any { it.isEmpty() }) {
        throw IllegalArgumentException("Forventet format: DD-MM-ÅÅÅÅ")
    }

    val dayOfMonth = dateParts[0].toIntOrNull()
    val monthNumber = dateParts[1].toIntOrNull()
    val year = dateParts[2].toIntOrNull()

    if (dayOfMonth == null || monthNumber == null || year == null) {
        throw IllegalArgumentException("Forventet format: DD-MM-ÅÅÅÅ. Nogle af elementerne var ikke tal")
    } else if (dayOfMonth < 1 || dayOfMonth > 31) {
        throw IllegalArgumentException("Dagen skal være mellem 1-31")
    } else if (monthNumber < 1 || monthNumber > 12) {
        throw IllegalArgumentException("Måneden skal være mellem 1-12")
    } else if (year < 1583) {
        throw IllegalArgumentException("År mindre end 1583 er desværre ikke supporteret")
    }

    val localDate = try {
        LocalDate(
            dayOfMonth = dayOfMonth,
            monthNumber = monthNumber,
            year = year
        )
    } catch (exception: IllegalArgumentException) {
        throw IllegalArgumentException(
            "Datoen er ugyldig - muligvis fordi den ikke eksisterer. Forventet format: DD-MM-ÅÅÅÅ"
        )
    }

    try {
        // Checks if it's possible at all
        localDate.toStickDate()

        return localDate
    } catch (exception: IllegalArgumentException) {
        throw IllegalArgumentException(
            "Datoen er ugyldig - muligvis fordi vi ikke kan konvertere alt for gamle datoer"
        )
    }
}
