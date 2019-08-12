class Compiler {
    def compile(name) { println name }
}

Compiler c = new Compiler();

def dependencies = { closure ->
    closure.delegate = c
    closure()
}

// calling the method of dependencies, and pass the closure as argument
// equivalent to dependencies({ ... })
dependencies {
    compile "a"
    compile "b"
    compile "c"
}
