import dk.spilpind.stickcalendar.TextUtil.replaceExtendedDates
import dk.spilpind.stickcalendar.TextUtil.replaceSimpleDates
import org.w3c.dom.*

/**
 * Replaces all gregorian dates of [document] by with their corresponding stick date. Any dates added later to the
 * document are also replaced, this is done by the observing relevant mutations
 */
class DomDateReplacer(private val document: Document) {

    private val mutationObserverOptions = MutationObserverInit(
        subtree = true,
        childList = true,
        attributes = true,
        attributeFilter = arrayOf("style"),
        attributeOldValue = true,
        characterData = true,
    )

    private val mutationObserver = MutationObserver { mutations, _ ->
        mutations.forEach { mutationRecord ->
            handleMutation(mutationRecord)
        }
    }

    init {
        mutationObserver.observe(document, mutationObserverOptions)

        replaceDatesInTree(document)
    }

    private fun handleMutation(mutationRecord: MutationRecord) {
        if (mutationRecord.type == "childlist") {
            val nodes = mutationRecord.addedNodes
            for (index in 0.until(nodes.length)) {
                nodes[index]?.let { node ->
                    replaceDatesInTree(node)
                }
            }

            return
        }

        val element = mutationRecord.target as? HTMLElement
        if (element?.style?.display == "none") {
            return
        }

        replaceDatesInTree(mutationRecord.target)
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

    private fun replaceDates(node: Node) {
        if (node.nodeType != Node.TEXT_NODE || node.isChildOfMetaTag) {
            return
        }

        val text = node.nodeValue ?: return

        val newText = text.replaceExtendedDates().replaceSimpleDates()

        if (text != newText) {
            node.nodeValue = newText
        }
    }

    private val Node.isChildOfMetaTag: Boolean
        get() = when (parentNode?.nodeName?.toLowerCase()) {
            "script" -> true
            "style" -> true
            else -> false
        }
}
