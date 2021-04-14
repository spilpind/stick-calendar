import kotlinx.browser.document
import org.w3c.dom.Element
import react.*
import react.dom.createPortal

external interface ComponentProps : RProps {
    var root: Element?
}

@JsExport
class StickCalendarComponent : Component<ComponentProps, RState>() {

    override fun render(): dynamic = createPortal(props.children, props.root)
}

fun RBuilder.stickCalendarComponentById(containerId: String, handler: RElementBuilder<ComponentProps>.() -> Unit) =
    document.getElementById(containerId)?.let { element ->
        stickCalendarComponent(element, handler)
    }

fun RBuilder.stickCalendarComponentsByClassName(
    className: String,
    handler: RElementBuilder<ComponentProps>.() -> Unit
) = document.getElementsByClassName(className).let { elements ->
    for (index in 0.until(elements.length)) {
        val element = elements.item(index) ?: continue

        stickCalendarComponent(element, handler)
    }
}

fun RBuilder.stickCalendarComponent(elementRoot: Element, handler: RElementBuilder<ComponentProps>.() -> Unit) =
    child(StickCalendarComponent::class) {
        attrs.root = elementRoot

        handler()
    }
