fun main() {

    println("=== CALCULADORA BÁSICA ===")

    print("Ingresa el primer número: ")
    val num1 = readLine()!!.toDouble()

    print("Ingresa el segundo número: ")
    val num2 = readLine()!!.toDouble()

    println("Selecciona una operación:")
    println("1. Sumar")
    println("2. Restar")
    println("3. Multiplicar")
    println("4. Dividir")

    print("Opción: ")
    val opcion = readLine()!!.toInt()

    when (opcion) {
        1 -> println("Resultado: ${num1 + num2}")
        2 -> println("Resultado: ${num1 - num2}")
        3 -> println("Resultado: ${num1 * num2}")
        4 -> {
            if (num2 != 0.0) {
                println("Resultado: ${num1 / num2}")
            } else {
                println("Error: No se puede dividir por cero")
            }
        }
        else -> println("Opción inválida")
    }
}
