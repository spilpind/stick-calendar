import react.dom.*
import kotlinx.browser.document

fun main() {

    render(document.getElementById("stick-calendar-root")) {
        h1 {
            +"Hello, React+Kotlin/JS!"
        }

        datePicker {
        }
    }
}
