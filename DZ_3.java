import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:
// Фамилия Имя Отчество номертелефона

// Форматы данных:
// фамилия, имя, отчество - строки
// номертелефона - целое беззнаковое число без форматирования

// Ввод всех элементов через пробел

// Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

// Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

// Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида

// <Фамилия><Имя><Отчество><номер_телефона>

// Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

// Не забудьте закрыть соединение с файлом.

// При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.


public class DZ_3 {
    public static void main(String[] args) throws Exception {
        String userDate = getUserDate("Введите Фамилиию, Имя, Отчетство и номер телефона(1234567890) через пробел: ");
        if (checkAmount(userDate)) {
            System.out.println(stringValidate(userDate));
        } else {
            System.out.println("Проверьте ввод!");
        }

    }

    private static Boolean checkAmount(String userDate) {
        String[] words = userDate.split(" ");
        if (words.length < 6) {
            System.out.println("Введено мало данных!");
            return false;
        }
        if (words.length > 6) {
            System.out.println("Введено слишком много данных!");
            return false;
        }
        return true;
    }

    public static String stringValidate(String userDate) throws Exception{
        char[] chars = userDate.toCharArray();
        StringBuilder surName = new StringBuilder();
        StringBuilder name = new StringBuilder();
        StringBuilder secondName = new StringBuilder();
        StringBuilder phone = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < chars.length; i++) {
            char cur = chars[i];
            if (cur == ' ') {
                flag ++;
                continue;
            }
            switch (flag) {
                case 0:
                    if (Character.isLetter(cur)){
                        surName.append(cur);
                    }else {
                        return "Фамилия должна состоять только из букв!";
                    }
                    break;
                case 1:
                    if (Character.isLetter(cur)){
                        name.append(cur);
                    }else {
                        return "Имя должно состоять только из букв!";
                    }
                    break;
                case 2:
                    if (Character.isLetter(cur)){
                        secondName.append(cur);
                    }else {
                        return "Отчество должно состоять только из букв!";
                    }
                    break;
                case 3:
                    if (Character.isDigit(cur)){
                        phone.append(cur);
                    }else {
                        return "Номер телефона должен состоять только из цифр!";
                    }
                    break;
            }
        }
        
        
        if (surName.length() < 2){
            return "Фамилия не может состоять из одного символа!";
        }
        if (name.length()<2){
            return "Имя не может состоять из одного символа!";
        }
        if (secondName.length() < 2){
            return "Отчество не может состоять из одного символа!";
        }
        if (phone.length() > 11 || phone.length() < 3){
            return "Длина номера телефона не может быть меньше 3 и превышать 10 цифр!";
        }
        String sendData = String.format("<%s><%s><%s><%s><%s><%s>", surName, name, secondName, phone);
        String fileName = String.format("%s", surName);
        saveData(sendData, fileName);
        return  sendData;
    }

    public static String getUserDate(String title) {
        Scanner line = new Scanner(System.in, "CP866");
        System.out.println(title);
        String str = line.nextLine();
        line.close();
        return str;
    }

    public static void saveData(String string, String surName) throws Exception {
        try (FileWriter fw = new FileWriter(surName, true)) {
            fw.write(string+"\n");
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}