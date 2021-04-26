package react.stickcalendar

import dk.spilpind.stickcalendar.StickCalendar
import dk.spilpind.stickcalendar.StickCalendar.isValid
import dk.spilpind.stickcalendar.StickDate
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

/**
 * Props of [StickDateInput]. [selectedDate] is the selected date and [onDateSelected] will be called whenever a valid
 * date has been entered
 */
external interface StickDateInputProps : RProps {
    var selectedDate: StickDate
    var onDateSelected: (StickDate) -> Unit
}

/**
 * State of [StickDateInput]
 */
external interface StickDateInputState : RState {
    var error: String?
    var lastPropDate: StickDate?
    var dateString: String
}

/**
 * React component that with an input field enables the user to enter a [StickDate]. In case of an invalid input
 * (including dates that are too old for the current implementation of the stick calendar), an error message will be
 * shown the the user. If a valid date is entered, [StickDateInputProps.onDateSelected] will be called
 */
@JsExport
class StickDateInput : RComponent<StickDateInputProps, StickDateInputState>() {

    companion object : RStatics<StickDateInputProps, StickDateInputState, StickDateInput, Nothing>(
        StickDateInput::class
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
                        state.dateString = props.selectedDate.toSimplifiedString()
                    }

                    state.error = null
                }

                state
            }
        }
    }

    override fun StickDateInputState.init() {
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

/**
 * Creates a [LocalDateInput] child element with the with the provided props
 */
fun RBuilder.stickDateInput(handler: StickDateInputProps.() -> Unit) =
    child(StickDateInput::class) {
        this.attrs(handler)
    }

private fun String.toDate(): StickDate {
    val isPositive = contains("\\.")
    val dateParts = if (isPositive) {
        split("\\.")
    } else {
        split("./")
    }.map { datePart ->
        datePart.trim()
    }

    if (dateParts.size < 2 || dateParts.any { it.isEmpty() }) {
        throw IllegalArgumentException("Forventet format: ÅÅÅÅ\\.DD eller ÅÅÅÅ./DD")
    }

    var year = dateParts[0].toIntOrNull()
    val day = dateParts[1].toIntOrNull()

    if (year == null || day == null) {
        throw IllegalArgumentException("Forventet format: ÅÅÅÅ\\.DD eller ÅÅÅÅ./DD. Nogle af elementerne var ikke tal")
    } else if (year < 0 || day < 1) {
        throw IllegalArgumentException("Året skal være mindst 0 og dag mindst 1")
    }

    if (!isPositive) {
        year = -year
    }

    val yearLength = StickCalendar.lengthOfYear(year)
    if (day > yearLength) {
        throw IllegalArgumentException("Dagen må være maks $yearLength for det specifikke år")
    } else if (year < -387) {
        throw IllegalArgumentException("Året må ikke være ældre end 386 før pindens tid")
    }

    return StickDate(
        year = year,
        day = day
    ).also { date ->
        if (!date.isValid) {
            throw IllegalArgumentException(
                "Datoen er ugyldig - muligvis fordi den ikke eksisterer. Forventet format: ÅÅÅÅ\\.DD eller ÅÅÅÅ./DD"
            )
        }
    }
}
