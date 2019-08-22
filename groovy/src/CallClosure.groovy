def test1(arg1, arg2) {
    println arg1
    println arg2
}

def test = { name -> println name }

//test1("aaa")
test1 "aaa", "bbb"

test "abc"