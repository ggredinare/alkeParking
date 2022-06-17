package model

import ANSI_GREEN
import ANSI_RED
import ANSI_RESET
import ANSI_YELLOW
import enums.VehicleType

data class ParkingSpace(var parking: Parking) {

    fun checkOutVehicle(plate: String): String {

        for (vehicle in parking.vehicles) {
            if (plate.equals(vehicle.plate)) {
                val type = vehicle.type
                val parkedTime = vehicle.parkedTime.toInt() ?: 0
                val discountCard = vehicle.discountCard != null

                if (parking.vehicles.remove(vehicle)) {
                    val str =
                        "Type: $ANSI_YELLOW${type.type}$ANSI_RESET\nTime in park: $ANSI_YELLOW$parkedTime minutes$ANSI_RESET\nDiscount Card: $ANSI_YELLOW${if (discountCard) "Yes" else "No"}$ANSI_RESET\n\n"
                    return str + onSuccess(calculateFee(type, parkedTime, discountCard))
                }
            }
        }
        return onError()
    }

    private fun calculateFee(type: VehicleType, parkedTime: Int, hasDiscountCard: Boolean): Int {
        var fee = type.fee
        var timeout = parkedTime - 120

        while (timeout > 0) {
            fee += 5
            timeout -= 15
        }

        if (hasDiscountCard) {
            fee = (fee * 0.85).toInt()
        }

        return fee
    }

    private fun onSuccess(fee: Int): String {
        val (vehicles, earns) = this.parking.totalEarns
        this.parking.totalEarns = Pair<Int, Int>(vehicles + 1, earns + fee)
        return "Your fee is:$ANSI_GREEN $$fee.00$ANSI_RESET\nCome back soon!\n"
    }

    private fun onError(): String {
        return ANSI_RED + "Sorry, the check-out failed" + ANSI_RESET
    }
}

