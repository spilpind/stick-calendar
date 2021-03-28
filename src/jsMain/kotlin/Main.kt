import kotlinx.browser.document
import org.w3c.dom.*

fun main() {
    /* render(document.getElementById("stick-calendar-root")) {
         child(ReactGregToStickApp::class) { }
         child(ReactStickToGregApp::class) { }
     }*/

    val observer = MutationObserver { mutations, _ ->
        mutations.forEach { mutationRecord ->
            if (mutationRecord.type == "childlist") {
                val nodes = mutationRecord.addedNodes
                for (index in 0.until(nodes.length)) {
                    nodes[index]?.let { node ->
                        replaceDatesInTree(node)
                    }
                }

                return@forEach
            }

            val element = mutationRecord.target as? HTMLElement ?: return@forEach

            if (element.style.display == "none") {
                return@forEach
            }

            replaceDatesInTree(mutationRecord.target)
        }
    }

    observer.observe(
        document, MutationObserverInit(
            subtree = true,
            childList = true,
            attributes = true,
            attributeFilter = arrayOf("style"),
            attributeOldValue = true,
            characterData = true,
        )
    )

    replaceDatesInTree(document)
}

private fun replaceDatesInTree(root: Node) {
    val treeWalker = document.createTreeWalker(root, whatToShow = NodeFilter.SHOW_TEXT) { node ->
        if (node.isChildOfMetaTag) {
            NodeFilter.FILTER_REJECT
        } else {
            NodeFilter.FILTER_ACCEPT
        }
    }

    var currentNode: Node? = treeWalker.currentNode
    while (currentNode != null) {
        replaceDates(currentNode)
        currentNode = treeWalker.nextNode()
    }
}

fun replaceDates(node: Node) {
    if (node.nodeType != Node.TEXT_NODE || node.isChildOfMetaTag) {
        return
    }

    val upperCased = node.nodeValue?.toUpperCase()
    if (node.nodeValue != upperCased) {
        node.nodeValue = upperCased
    }
}

val Node.isChildOfMetaTag: Boolean
    get() = when (parentNode?.nodeName?.toLowerCase()) {
        "script" -> true
        "style" -> true
        else -> false
    }



