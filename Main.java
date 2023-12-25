import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result = calc(input);
        System.out.println(result);
    }

    public static String calc(String input) {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат выражения!");
            }

            String operand1 = parts[0];
            String operator = parts[1];
            String operand2 = parts[2];

            boolean isArabic = isArabicNumber(operand1) && isArabicNumber(operand2);
            boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);

            if (!isArabic && !isRoman) {
                throw new IllegalArgumentException("Операнды должны быть одного типа (целые арабские или римские числа)!");
            }

            if (isArabic) {
                int num1 = Integer.parseInt(operand1);
                int num2 = Integer.parseInt(operand2);
                int result = calculate(num1, operator, num2);
                return Integer.toString(result);
            } else {
                int num1 = romanToInt(operand1);
                int num2 = romanToInt(operand2);
                int result = calculate(num1, operator, num2);
                return intToRoman(result);
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    private static boolean isArabicNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isRomanNumber(String number) {
        String pattern = "^(I{1,3}|IV|V|VI|VII|VIII|IX|X+)$";
        return number.matches(pattern);
    }

    private static int calculate(int num1, String operator, int num2) {
        if (operator.equals("+")) {
            return num1 + num2;
        } else if (operator.equals("-")) {
            return num1 - num2;
        } else if (operator.equals("*")) {
            return num1 * num2;
        } else if (operator.equals("/")) {
            return num1 / num2;
        } else {
            throw new IllegalArgumentException("Недопустимый оператор!");
        }
    }

    private static int romanToInt(String roman) {
        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanDigitToInt(roman.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }

    private static int romanDigitToInt(char digit) {
        switch (digit) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            default:
                throw new IllegalArgumentException("Недопустимая римская цифра: " + digit);
        }
    }

    private static String intToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Результат выполнения операции с римскими цифрами не должен быть равным 0 или меньше");
        }
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicNumbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder romanNumber = new StringBuilder();

        for (int i = 0; i < arabicNumbers.length; i++) {
            while (number >= arabicNumbers[i]) {
                romanNumber.append(romanNumerals[i]);
                number -= arabicNumbers[i];
            }
        }

        return romanNumber.toString();
    }
}

