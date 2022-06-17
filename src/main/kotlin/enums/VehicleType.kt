package enums

enum class VehicleType(var type: String, var fee: Int) {
    CAR("Car", 20),
    MOTORCYCLE("Car", 15),
    MINIBUS("Minibus", 25),
    BUS("Bus", 30)
}