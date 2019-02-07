Exercise 1

> toDigits :: Int -> [Int]
> toDigits n
>   | n <= 0    = []
>   | n <= 9    = [n]
>   | otherwise = toDigits (quot n 10) ++ ((mod n 10) : [])

> digitsRev :: Int -> [Int]
> digitsRev n
>   | n <= 0    = []
>   | n <= 9    = [n]
>   | otherwise = ((mod n 10) : []) ++ digitsRev (quot n 10)

Exercise 2

> doubleFromLeft :: [Int] -> [Int]
> doubleFromLeft []         = []
> doubleFromLeft (x:[])     = [x]
> doubleFromLeft (x:(y:[])) = [x, 2 * y]
> doubleFromLeft (x:y:zs)   = [x, 2 * y] ++ doubleFromLeft zs
> doubleEveryOther :: [Int] -> [Int]
> doubleEveryOther xs = reverse ( doubleFromLeft (reverse xs) )

Exercise 3

> sumDigits :: [Int] -> Int
> sumDigits (x:[])     = sum (toDigits x)
> sumDigits (x:(y:[])) = sumDigits [x] + sumDigits [y]
> sumDigits (x:y:zs)   = sumDigits [x, y] + sumDigits zs

Exercise 4

> validate :: Int -> Bool
> validate x
>   | mod (sumDigits (doubleEveryOther (toDigits x))) 10 == 0  = True
>   | otherwise                                                = False

Exercise 5

> type Peg = String
> type Move = (Peg, Peg)
> hanoi :: Integer -> Peg -> Peg -> Peg -> [Move]
> hanoi 1 a b c = [(a, b)]
> hanoi n a b c = (hanoi (n - 1) a c b) ++ [(a, b)] ++ (hanoi (n - 1) c b a)

Exercise 6 -  attemp 1: 5466 for 15 stacks

> {-
> hanoiFour :: Integer -> Peg -> Peg -> Peg -> Peg -> [Move]
> hanoiFour 1 a b c d = [(a, b)]
> hanoiFour 2 a b c d = [(a, d), (a, b), (d, c), (c, b)]
> hanoiFour n a b c d =
>   (hanoiFour (n-2) a d c b) ++
>   (hanoi 2 a b c) ++
>   (hanoiFour (n-2) d a b c) ++
>   (hanoiFour (n-2) a b c d)
> -}

Exercise 6 attempt 2: 847 for 15 stacks

> hanoiFour :: Integer -> Peg -> Peg -> Peg -> Peg -> [Move]
> hanoiFour 1 a b c d = [(a, b)]
> hanoiFour 2 a b c d = [(a, d), (a, b), (d, c), (c, b)]
> hanoiFour 3 a b c d = [(a, d), (a, b), (b, c), (a, b), (c, b), (d, c), (c, b)]
> hanoiFour n a b c d =
>   (hanoiFour (n-3) a d c b) ++
>   (hanoiFour 3 a b c d) ++
>   (hanoiFour (n-3) d a c b) ++
>   (hanoiFour (n-3) a b c d)

> main = do
>   print(toDigits(-1))
>   print(toDigits(0))
>   print(toDigits(1))
>   print(toDigits(12))
>   print(toDigits(123))
>   print(digitsRev(123))
>   print(doubleEveryOther([1,2,3]))
>   print(doubleEveryOther([8,7,6,5]))
>   print(sumDigits([16, 7, 12, 5]))
>   print(validate(4012888888881881))
>   print(validate(4012888888881882))
>   print(hanoi 1 "a" "b" "c")
>   print(hanoi 2 "a" "b" "c")
>   print(hanoi 3 "a" "b" "c")
>   print("------------------")
>   print(length (hanoi 4 "a" "b" "c"))
>   print(length (hanoiFour 4 "a" "b" "c" "d"))
>   print(length (hanoi 8 "a" "b" "c"))
>   print(length (hanoiFour 8 "a" "b" "c" "d"))
>   print(length (hanoi 15 "a" "b" "c"))
>   print(length (hanoiFour 15 "a" "b" "c" "d"))