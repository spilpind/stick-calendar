# The Stick Calendar

Kotlin project that deals with The Stick Calendar (in Danish: Pindsekalenderen), which is a new calendar system based on
pentecost day developed by Danish Stick Association (in Danish: [Dansk Pind Union](https://spilpind.dk)). Since this was
originally made for a Danish audience, most user-facing texts will be in Danish for now.

## Modules

The project contains a common module which main purpose is to deal with core logic of the calendar system. Furthermore,
there is a few modules that in their own ways helps the user navigating in the new calendar.

The headers below here reflect the name of the module folder.

### common

[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) module that contains core logic and various
utilities that makes it easier to make implementations related to the calendar system. For now this is only used by
Kotlin/JS modules, but is still a multiplatform module such that it's ready for future projects and also to test out the
possibilities with such module type.

This module uses [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) in order to deal with the gregorian
calendar.

### website

[Kotlin/JS](https://kotlinlang.org/docs/js-project-setup.html) module that with help
from [Kotlin wrappers](https://github.com/JetBrains/kotlin-wrappers) and React
(including a custom wrapper around [React Datepicker](https://reactdatepicker.com)) sets up a website with various
converter tools such that the user can convert to/from stick dates.

In this website certain elements are replaced with both convert input and output elements. The implementation done like
this such that it's easier for the content creator to set up the page without needing to access this repository. To see
it in action, check out https://spilpind.dk/pindsekalenderen.

#### Building the module

During development, the module can be built, and an example website with all relevant elements can be started by simply
calling `./gradlew :website:browserRun` (In IntelliJ, look for the Gradle task called _browserRun_ in
_stick-calendar/website/kotlin browser_), or a similar task.

When the module needs to be deployed, call `./gradlew :website:browserDistribution` (In IntelliJ, look for the Gradle
task called _browserDistribution_ in _stick-calendar/website/kotlin browser_). After successful build, the compiled
JavaScript file can be found in _website/build/distributions_ and should just be executed in a webpage set up with the
relevant elements.

In case of any unexpected errors during build, feel free to contact us.

### chrome-extension

[Chrome extension](https://developer.chrome.com/docs/extensions) built
with [Kotlin/JS](https://kotlinlang.org/docs/js-project-setup.html). The extension simply replace all gregorian dates in
all websites to their counterpart in The Stick Calendar with help
from [Kotlin's DOM API](https://kotlinlang.org/docs/browser-api-dom.html).

The extension works best with Danish texts (since the pattern for instance searches for Danish months), but also works
with more generic dates like 20/3-1990. If this readme for example is viewed at GitHub in a Chrome browser with the
extension installed that date should for example be converted to 19\.311.

The extension is released to Chrome Webshop and can be
found [here](https://chrome.google.com/webstore/detail/stick-calendar/fadifphlmjkcaifcjejhfhehjajjapmb).

#### Build and distribute

In order to build/assemble everything needed to run or distribute the extension
call `./gradlew :website:browserDistribution` (In IntelliJ, look for the Gradle task called _browserDistribution_ in
_stick-calendar/chrome-extension/kotlin browser_). All resources and the compiled JavaScript file needed for the
distribution will after this be located in _chrome-extension/build/distributions_.

During development this output folder can also be used during development
by [loading it as an unpacked extension](https://developer.chrome.com/docs/extensions/mv3/getstarted/#manifest).

## About Stick Calendar

The Stick Calendar in short is a new calendar format with origin in pentecost day 1970. Each year starts by pentecost
day and ends right before pentecost day next year. For example year 0 start May 17th 1970 and ends May 29th 1971. Dates
from year 0 and onwards are denoted as YY\.DDD and dates before year 0 is denoted as YY./DDD.

The reason for the name and the association with pentecost is more clear when you look at it in Danish, where "stick"
translates to "pind". Pind is a game similar to some aspects of [tip-cat](https://en.wikipedia.org/wiki/Tip-cat) and the
sport is organized by Danish Stick Association. Furthermore, "pentecost" translates to "pinse" in Danish and because of
the similarity between the two words "pind" and "pinse", the calendar has its origin in pentecost. In Danish, the name
of the calendar system also mixes these two words into "pindsekalenderen". We have however not found a similar good mix
between "pentecost" and "stick" English yet, but let us know if you have a good suggestion.
