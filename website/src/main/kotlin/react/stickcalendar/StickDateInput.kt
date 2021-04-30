package react.stickcalendar

import dk.spilpind.stickcalendar.StickCalendar
import dk.spilpind.stickcalendar.StickCalendar.isValid
import dk.spilpind.stickcalendar.StickDate
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.color
import kotlinx.css.display
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
                    // format it. Can happen if the user types 50\.001 => 50\.00 => 50\.002. In that case we would have
                    // formatted it as 50\.2, but that would seem strange to the user as it wasn't how they entered it
                    if (currentDate != props.selectedDate) {
                        state.dateString = props.selectedDate.toSimplifiedString()
                    }

                    // A new date has been set (or was like the valid one) so there's no more errors
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
 * Creates a [StickDateInput] child element with the with the provided props
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

    if (dateParts.size != 2 || dateParts.any { it.isEmpty() }) {
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

    if (year < -387) {
        throw IllegalArgumentException("År ældre end 387 før pindens tid er ikke supporteret")
    }

    val yearLength = StickCalendar.lengthOfYear(year)
    if (day > yearLength) {
        throw IllegalArgumentException("Dagen må være maks $yearLength for det specifikke år")
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
