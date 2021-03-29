import it.woar.stickcalendar.StickCalendar.toStickDate
import kotlinx.browser.document
import kotlinx.datetime.*
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

            val element = mutationRecord.target as? HTMLElement
            if (element?.style?.display == "none") {
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

    val text = node.nodeValue ?: return

    val newText = text.replaceExtendedDates()

    if (text != newText) {
        node.nodeValue = newText
    }
}

private fun String.replaceExtendedDates(): String {
    val regex = Regex(
        "([0-9]+)\\. (jan|januar|feb|februar|mar|marts|apr|april|maj|jun|juni|jul|juli|aug|august|sep|sept|september|okt|oktober|nov|november|dec|december|) ([0-9]+)?",
        RegexOption.IGNORE_CASE
    )

    return regex.replace(this) { matchResult ->
        val dayOfMonth = matchResult.groupValues.getOrNull(1)?.toIntOrNull() ?: return@replace matchResult.value

        val month = matchResult.groupValues.getOrNull(2).let { month ->
            when (month) {
                "jan", "januar" -> Month.JANUARY
                "feb", "februar" -> Month.FEBRUARY
                "mar", "marts" -> Month.MARCH
                "apr", "april" -> Month.APRIL
                "maj" -> Month.MAY
                "jun", "juni" -> Month.JUNE
                "jul", "juli" -> Month.JULY
                "aug", "august" -> Month.AUGUST
                "sep", "sept", "september" -> Month.SEPTEMBER
                "okt", "oktober" -> Month.OCTOBER
                "nov", "november" -> Month.NOVEMBER
                "dec", "december" -> Month.DECEMBER
                else -> {
                    return@replace matchResult.value
                }
            }
        }

        val year = matchResult.groupValues.getOrNull(3)?.toIntOrNull().let { year ->
            when {
                year == null -> Clock.System.todayAt(TimeZone.currentSystemDefault()).year
                year < 100 -> year + 2000
                else -> year
            }
        }

        try {
            val localDate = LocalDate(
                year = year,
                month = month,
                dayOfMonth = dayOfMonth
            )

            localDate.toStickDate().toExtendedFullString()
        } catch (exception: IllegalArgumentException) {
            matchResult.value
        }
    }
}

val Node.isChildOfMetaTag: Boolean
    get() = when (parentNode?.nodeName?.toLowerCase()) {
        "script" -> true
        "style" -> true
        else -> false
    }



