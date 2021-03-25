import react.dom.*
import kotlinx.browser.document

fun main() {
    render(document.getElementById("stick-calendar-root")) {
        child(ReactApp::class) { }
    }
}
