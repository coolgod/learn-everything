Exercise 1

> digits :: Int -> [Int]
> digits n
>   | n <= 0    = []
>   | n <= 9    = [n]
>   | otherwise = digits(quot n 10) ++ ((mod n 10) : [])

> main = do
>   print(digits(-1))
>   print(digits(0))
>   print(digits(1))
>   print(digits(12))
>   print(digits(123))