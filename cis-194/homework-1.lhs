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

> main = do
>   print(toDigits(-1))
>   print(toDigits(0))
>   print(toDigits(1))
>   print(toDigits(12))
>   print(toDigits(123))
>   print(digitsRev(123))
>   print(doubleEveryOther([1,2,3]))
>   print(doubleEveryOther([8,7,6,5]))
>   