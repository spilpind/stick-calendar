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

fun RBuilder.stickCalendarComponent(containerId: String, handler: RElementBuilder<ComponentProps>.() -> Unit) =
    document.getElementById(containerId)?.let { root ->
        child(StickCalendarComponent::class) {
            attrs.root = root

            handler()
        }
    }