package model

import ANSI_GREEN
import ANSI_RED
import ANSI_RESET
import ANSI_YELLOW

data class Parking(val vehicles: MutableSet<Vehicle>) {
    private val MAX_VEHICLES = 20
    var totalEarns = Pair<Int, Int>(0, 0)

    constructor() : this(mutableSetOf()) {

    }

    private fun addVehicle(vehicle: Vehicle): Boolean {
        if (this.vehicles.size == this.MAX_VEHICLES) {
            return false
        }
        return this.vehicles.add(vehicle)
    }

    fun checkIn(vehicle: Vehicle): String {
        if (this.addVehicle(vehicle)) {
            return ANSI_GREEN + "Welcome to the AlkeParking!" + ANSI_RESET
        }
        return ANSI_RED + "Sorry! the check-in failed..." + ANSI_RESET
    }

    fun info(): String {
        val (vehicles, earns) = this.totalEarns

        return "$ANSI_YELLOW$vehicles$ANSI_RESET vehicles have checked out and have earnings of $ANSI_GREEN$ $earns.00$ANSI_RESET"
    }

    fun listVehicles() {
        this.vehicles.forEach {
            println(it.plate)
        }
    }

}