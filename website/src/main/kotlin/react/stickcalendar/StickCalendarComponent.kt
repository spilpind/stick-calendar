package react.stickcalendar

import kotlinx.browser.document
import org.w3c.dom.Element
import react.*
import react.dom.createPortal

/**
 * Props of [StickCalendarComponent]. [root] should be the element in which the component will be rendered
 */
external interface ComponentProps : RProps {
    var root: Element?
}

/**
 * A generic react component that portals children of [ComponentProps] into the [ComponentProps.root]. This is useful to
 * fill out existing elements that are not children of the root app component
 */
@JsExport
class StickCalendarComponent : Component<ComponentProps, RState>() {

    override fun render(): dynamic = createPortal(props.children, props.root)
}

/**
 * Adds the result of [handler] to the container associated with id of [containerId]
 */
fun RBuilder.stickCalendarComponentById(containerId: String, handler: RElementBuilder<ComponentProps>.() -> Unit) =
    document.getElementById(containerId)?.let { element ->
        stickCalendarComponent(element, handler)
    }

/**
 * Adds the result of [handler] to all containers that has the class [className]
 */
fun RBuilder.stickCalendarComponentsByClassName(
    className: String,
    handler: RElementBuilder<ComponentProps>.() -> Unit
) = document.getElementsByClassName(className).let { elements ->
    for (index in 0.until(elements.length)) {
        val element = elements.item(index) ?: continue

        stickCalendarComponent(element, handler)
    }
}

private fun RBuilder.stickCalendarComponent(elementRoot: Element, handler: RElementBuilder<ComponentProps>.() -> Unit) =
    child(StickCalendarComponent::class) {
        attrs.root = elementRoot

        handler()
    }
