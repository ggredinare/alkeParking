import view.ConsoleView

const val ANSI_RESET = "\u001B[0m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"

fun main() {
    val program = ConsoleView()
    program.addVehicles(19, false)
    program.start()
}