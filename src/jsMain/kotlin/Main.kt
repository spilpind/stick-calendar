import kotlinx.browser.document
import react.dom.render

fun main() {
    render(document.getElementById("stick-calendar-root")) {
        child(ReactGregToStickApp::class) { }
        child(ReactStickToGregApp::class) { }
    }
}
