import java.util.*;

/*
Парсер реквестов
Парсер реквестов
Считать с консоли URL-ссылку.
Вывести на экран через пробел список всех параметров (Параметры идут после ? и разделяются &, например, lvl=15).
URL содержит минимум 1 параметр.
Если присутствует параметр obj, то передать его значение в нужный метод alert.
alert(double value) — для чисел (дробные числа разделяются точкой)
alert(String value) — для строк
Обрати внимание на то, что метод alert необходимо вызывать ПОСЛЕ вывода списка всех параметров на экран.

Пример 1

Ввод:
http://javarush.ru/alpha/index.html?lvl=15&view&name=Amigo

Вывод:
lvl view name

Пример 2

Ввод:
http://javarush.ru/alpha/index.html?obj=3.14&name=Amigo

Вывод:
obj name
double 3.14


Требования:
1. Программа должна считывать с клавиатуры только одну строку.
2. Программа должна выводить данные на экран в соответствии с условием.
3. Программа должна вызывать метод alert с параметром double в случае, если значение параметра obj может быть корректно преобразовано в число типа double.
4. Программа должна вызывать метод alert с параметром String в случае, если значение параметра obj НЕ может быть корректно преобразовано в число типа double.



*/

public class Solution {
    static public class ParametrObject
        /*Класс хранения параметров и ключей*/ {
        String key;
        String value;

        public ParametrObject(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ParametrObject> parametresList = new ArrayList<>();
        StringBuilder adress = new StringBuilder(scanner.nextLine());
        //   StringBuilder adress=new StringBuilder("http://javarush.ru/alpha/index.html?obj=1s23.12&lvl=15&view&name=Amigo");
        //   StringBuilder adress=new StringBuilder("http://javarush.ru/alpha/index.html?obj=123.12&lvl=15&view&name=Amigo");

        // StringBuilder adress=new StringBuilder("http://javarush.ru/alpha/index.html?lvl=15&view&name=Amigo");
        String key = "";
        String value = "";
        int indexStartDecompose = 0; //начало участка разбора
        int indexEndDecompose = 0;  //начало участка разбора
        indexStartDecompose = adress.indexOf("?") + 1; //Начинаем разбор строки, после управляющего символа "?"

        while ((indexEndDecompose < (adress.length()))) {  // Делаем в цикле пока конец участка разбора меньше конца разбираемой
            // строки
            indexEndDecompose = adress.indexOf("&", indexStartDecompose);//находим начало следующего блока по
            // символу разделения "&". резульатат приравниваем к концу участка разбора
            if (indexEndDecompose == -1) indexEndDecompose = adress.length(); //Если следующего блока нет, то концу
            // разбора приравниваем адресс конца разбираемой строки

            //Если у нас есть параметр со значением, то есть есть его приравнивание "=" значению, то
            if (adress.indexOf("=", indexStartDecompose) < indexEndDecompose) {
                //находим параметр :
                key = getKeyParameter(adress.toString(), indexStartDecompose, "=");
                //находим зачение параметра
                value = getValueParametr(adress.toString(), indexStartDecompose, indexEndDecompose, key);
            } else {
                //находим параметр :
                key = getKeyParameter(adress.toString(), indexStartDecompose, "&");
                //Значени параметра равны null
                value = null;
            }
            indexStartDecompose = indexEndDecompose + 1;//приравниваем конец блока разбора к началу следующего блока разбора
            //Добавляем в коллекцию объект хранения параметров и значений
            parametresList.add(new ParametrObject(key, value));
        }
/* Вывод всех параметров входящей строки*/
        for (ParametrObject parametrObject : parametresList) {
            System.out.print(parametrObject + " ");
        }
// вывод ключей пармметров "obj",если они есть.
        for (ParametrObject parametrObject : parametresList) {
            if (parametrObject.key.equals("obj")) {
                System.out.println("");
                try {  //Пытаемся расшифровать значение как double
                    double doubleKey = Double.parseDouble(parametrObject.value);
                    alert(doubleKey);//передаем значение как double
                } catch (NumberFormatException e) { //если выскакивет исключение передаем значение как String
                    String doubleKey = parametrObject.value;
                    alert(doubleKey);
                }
            }
        }
    }

    // Функция разбора параметра в строке adress, с позиции indexStartDecompose до символа separator
    private static String getKeyParameter(String adress, int indexStartDecompose, String separator) {
        int parametrObjEnd = adress.indexOf(separator, indexStartDecompose); //находим позицию символа разделителя
        return adress.substring(indexStartDecompose, parametrObjEnd); //отдаем подстроку
    }

    // Функция разбора ключа с позиции параметра до конца декомпозиции
    public static String getValueParametr(String adress, int indexStartDecompose, int indexEndDecopmpose, String key) {
        //Находим начальную позицию ключа по позиции параметра (key+"=") + длина параметра.
        int startValueParameter = adress.indexOf(key + "=", indexStartDecompose) + key.length() + 1;
        return adress.substring(startValueParameter, indexEndDecopmpose);//отдаем подстроку
    }

    public static void alert(double value) {
        System.out.println("double " + value);
    }

    public static void alert(String value) {
        System.out.println("String " + value);
    }
}
