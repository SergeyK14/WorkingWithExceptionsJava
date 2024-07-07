package org.example;

import java.io.*;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            //String fileName;
            //r_data();
            System.out.println("\n");
            workingWithData();
        }catch (FileSystemException e){
         System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }

    }

    public static void workingWithData() throws Exception{
        System.out.println("Введите в одну строку через пробел Фамилию_Имя_Отчество_Дату рождения(дд.мм.гггг)_Номер телефона_Пол человека(m or f)");
        String resStr;
        try(Scanner sc = new Scanner (System.in)) {
            resStr = sc.nextLine();
        }catch (Exception e){
            throw new Exception("Произошла ошибка при чтении данных");
        }


        String [] res = resStr.split(" ");
        System.out.println(Arrays.toString(res));
        if (res.length != 6){
            throw new IncorrectAmountOfDataException ("Произошла ошибка при чтении данных");
        }


        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String surname;
        String name;
        String patronymic;
        Date birthdate;
        int phone;
        String gender;

        surname = res[0];
        name = res[1];
        patronymic = res[2];
        gender = res[5];

        try {
            birthdate = format.parse(res[3]);
        }catch (ParseException e){
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }


        try {
            phone = Integer.parseInt(res[4]);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Формат телефона введен неверно");
        }


        if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")){
            throw new RuntimeException("Пол человека введен неверно");
        }


        String fileName = surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            fileWriter.write('\n');
        }catch (FileDoesNotWriteException e){
            System.out.println(e.getMessage());
        }

    }


    public static void r_data(String fileName) throws IOException{
        try(FileReader fileReader = new FileReader(fileName + ".txt")){
            while (fileReader.ready()) {
                System.out.print((char) fileReader.read());
            }
        } catch (FileDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }
}


class FileDoesNotExistException extends FileNotFoundException {
    public FileDoesNotExistException(Exception e) {
        super("По указанному пути файл:   не найден");
        e.printStackTrace();
    }

}

class FileDoesNotWriteException extends IOException {
    public FileDoesNotWriteException(Exception e) {
        super("При записи файла по указанному пути:   возникла ошибка");
        e.printStackTrace();
    }

}

class IncorrectAmountOfDataException extends IOException {
    public IncorrectAmountOfDataException(String e) {
        super("Веедено неверное количество данных. Вы ввели большее или меньшее количество параметров. Проверьте ввод");

    }
}
