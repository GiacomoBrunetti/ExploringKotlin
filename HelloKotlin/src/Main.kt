
fun forStatement() {
    val names = arrayOf("Giacomo", "Giulia", null)
    for (name: String? in names) {
        greet(name)
    }
//    names.forEach { name -> greet(name) }
}

fun whenStatement(name: String?): String {
    return when (name) {
        "Giacomo" -> "Hi"
        "Giulia" -> "Hey"
        else -> "Error"
    }
}

fun ifStatement(name: String?): String {
//    return if (name != null) {
//        name
//    } else {
//        "Name was not specified."
//    }
    return name ?: "Name was not specified."
}

fun greet(name: String?) = println("${whenStatement(name)} ${ifStatement(name)}")

fun main() {
    val hello: String = "Hello World!"
    println(hello)
    forStatement()
}


