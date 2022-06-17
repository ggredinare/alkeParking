package view

import ANSI_RED
import ANSI_RESET
import ANSI_YELLOW
import enums.VehicleType
import model.Parking
import model.ParkingSpace
import model.Vehicle
import kotlin.random.Random

class ConsoleView {
    private val spaces = "-".repeat(70)
    private val parkingSpace = ParkingSpace(Parking())
    private val validDiscounts = arrayOf("BIGDAY15", "ALKE15")

    fun start() {
        var input = 0

        while (input != 6) {

            println(spaces)
            println("AlkeParking")
            println(spaces)
            println("[1] - CheckIn vehicle")
            println("[2] - CheckOut vehicle")
            println("[3] - List plates")
            println("[4] - Search vehicle")
            println("[5] - Show earnings and vehicles checks")
            println("[6] - Exit")
            println(spaces)

            input = inputInt(6)
            println()
            when (input) {
                1 -> checkInVehicle()
                2 -> checkOut()
                3 -> listPlates()
                4 -> searchVehicle()
                5 -> showEarnings()
            }
            loading(3)
        }

    }


    private fun checkInVehicle() {
        println(spaces)
        println("CheckIn Vehicle")
        println(spaces)

        val plate = inputPlate()
        println(spaces)
        println("[1] - Car")
        println("[2] - Motorcycle")
        println("[3] - Minibus")
        println("[4] - Bus")
        val type = makeVehicleType(inputInt(4))
        println(spaces)
        val discountCard = inputDiscount()
        println(spaces)

        val vehicle = Vehicle(plate, type, discountCard)
        println(parkingSpace.parking.checkIn(vehicle))
        println()
    }

    private fun checkOut() {
        println(spaces)
        println("CheckOut")
        println(spaces)
        val plate = inputPlate()
        println(parkingSpace.checkOutVehicle(plate))
    }

    private fun listPlates() {
        println(spaces)
        println("List plates")
        println(spaces)
        parkingSpace.parking.listVehicles()
        println()
    }

    private fun searchVehicle() {
        println(spaces)
        println("Search vehicle")
        println(spaces)

        val plate = inputPlate()

        for (i in parkingSpace.parking.vehicles) {
            if (plate == i.plate) {
                printVehicle(i)
                return
            }
        }
        println("Vehicle not found!")
        println()
    }

    private fun showEarnings() {
        println(spaces)
        println("Earnings and vehicles checks")
        println(spaces)
        println(parkingSpace.parking.info())
        println()
    }


    private fun inputInt(max: Int): Int {
        var done: Boolean = true
        var input = 0

        while (done) {
            print("Type an option: ")
            try {
                input = readLine()!!.trim().toInt()
                if (input > max || input < 1) {
                    println(ANSI_RED + "Please type a valid command number!" + ANSI_RESET)
                } else {
                    done = false
                }
            } catch (e: java.lang.NumberFormatException) {
                println(ANSI_RED + "Please type a number!" + ANSI_RESET)
            }
        }
        return input
    }

    private fun inputPlate(): String {
        var done = true
        var input = ""
        val regex = Regex("([A-Z][A-Z][A-Z][0-9][A-Z0-9][0-9][0-9])")

        while (done) {
            print("Enter vehicle plate: ")
            input = readLine()!!.trim().uppercase()
            if (regex.matches(input)) {
                done = false
            } else {
                println(ANSI_RED + "Acceptable plate formats: AAA0000 / AAA0A00" + ANSI_RESET)
            }
        }
        return input
    }

    private fun inputDiscount(): String? {
        var done = true
        var input: String?
        var inputInt = 0

        println("Have a discount card?")
        println("[1] - Yes")
        println("[2] - No")
        inputInt = inputInt(2)
        println()

        if (inputInt == 2) {
            done = false
        }

        while (done) {
            print("Type the discount card: ")
            input = readLine()!!.trim().uppercase()

            for (i in validDiscounts) {
                if (input == i) {
                    return i
                }
            }

            println("$ANSI_YELLOW$input$ANSI_RESET is not a valid discount card, want to try again?")
            println("[1] - Yes")
            println("[2] - No")
            inputInt = inputInt(2)

            if (inputInt == 2) {
                done = false
            }
            println()
        }

        return null
    }


    private fun makeVehicleType(n: Int): VehicleType {
        lateinit var type: VehicleType

        when (n) {
            1 -> type = VehicleType.CAR
            2 -> type = VehicleType.MOTORCYCLE
            3 -> type = VehicleType.MINIBUS
            4 -> type = VehicleType.BUS
        }

        return type
    }

    private fun loading(n: Int) {
        for (i in 1..n) {
            print("-")
            Thread.sleep(500)
        }
        println()
    }

    private fun printVehicle(vehicle: Vehicle) {
        val cardDis = vehicle.discountCard ?: "N/A"

        println(spaces)
        println("Plate : ${vehicle.plate}")
        println("Type : ${vehicle.type.type}")
        println("CheckIn Time : ${vehicle.checkInTime.time}")
        println("Parked Time: ${vehicle.parkedTime} minutes")
        println("DiscountCard : ${cardDis.uppercase()}")
        println()
    }


    fun addVehicles(n: Int, bind: Boolean) {
        for (i in 1..n) {
            val vehicle = Vehicle(randomPlate(), makeVehicleType(Random.nextInt(1, 5)), randomDiscount())
            val result = parkingSpace.parking.checkIn(vehicle)

            val str = String.format("%-10s : %-10s : %-10s : %-10s : %s", vehicle.plate, vehicle.type.type, vehicle.discountCard,vehicle.parkedTime, result)
            println(str)

            if (bind) {
                println(parkingSpace.checkOutVehicle(vehicle.plate))
                println(spaces)
            }
        }
        loading(5)
    }

    private fun randomPlate(): String {
        val charPoolChar: CharRange = 'A'..'Z'
        val charPoolNum: CharRange = '0'..'9'
        var str = ""

        for (i in 1..3) {
            str += charPoolChar.random()
        }
        str += charPoolNum.random()
        str += (charPoolChar + charPoolNum).random()

        for (i in 1..2) {
            str += charPoolNum.random()
        }

        return str
    }

    private fun randomDiscount(): String? {
        val haveDiscount = Random.nextInt(1, 3)
        var cardDiscount: String? = null

        if (haveDiscount == 1) {
            cardDiscount = validDiscounts.get(Random.nextInt(0, 2))
        }

        return cardDiscount
    }
}