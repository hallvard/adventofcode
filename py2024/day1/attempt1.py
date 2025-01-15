def read_integers(file_path):
    with open(file_path, 'r') as file:
        # Read the entire file content
        content = file.read()
        # Split the content by whitespace and convert to integers
        integers = [int(x) for x in content.split()]
    return integers

def compute(file_path):
    integers = read_integers(file_path)
    left = integers[0::2]
    right = integers[1::2]

    left.sort()
    right.sort()

    sum = 0
    for num1, num2 in zip(left, right):
        sum = sum + abs(num2 - num1)

    return sum

print(compute("day1/sample.txt"))
print(compute("day1/data.txt"))
