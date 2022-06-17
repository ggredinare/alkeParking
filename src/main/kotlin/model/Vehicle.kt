package model

import enums.VehicleType
import java.util.*

data class Vehicle(
    val plate: String,
    val type: VehicleType,
    val discountCard: String? = null,
) {
    val checkInTime: Calendar = Calendar.getInstance()

    val parkedTime: Long
        //get() = 120
        get() = (Calendar.getInstance().timeInMillis - checkInTime.timeInMillis) / 60000

    override fun equals(other: Any?): Boolean {
        if (other is Vehicle) {
            return this.plate == other.plate
        }
        return super.equals(other)
    }
    override fun hashCode(): Int = this.plate.hashCode()
}