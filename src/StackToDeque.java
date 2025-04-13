import java.io.*;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StackToDeque {

    public static final Scanner scan = new Scanner(System.in);
    public static final int MIN_CHOICE = 0;
    public static final int MAX_STACK_CHOICE = 8;
    public static final int MAX_DEQUE_CHOICE = 10;
    public static final int MIN_NUM = -100000;
    public static final int MAX_NUM = 100000;
    public enum Status
    {
        GOOD,
        WRONG_EXTENSION,
        BAD_FILE,
        NOT_AN_INT,
        OUT_OF_RANGE,
        EMPTY_STACK,
        EMPTY_DEQUE
    }

    public interface SaveAndOpenable {
        @Override
        String toString();
    }

    static final String[] ERR_TEXT = {"",
            "Заданный файл имеет неверное расширение.\n",
            "Данный файл невозможно прочитать.\n",
            "Введенные данные некорректны.\n",
            "Введенное значение вне диапазона.\n",
            "Стек пустой!\n",
            "Очередь пустая!\n"
    };











    public static void writeTask() {
        System.out.println("Данная программа позволяет работать с записями, хранящими данные о поезде.");
        System.out.println("Ввод/вывод производится через файлы .dat.");
    }

    public static void writeStackOptions() {
        System.out.println("""
                Выберите, что вы хотите сделать со стеком.
                0 - Выход из программы;
                1 - Добавить значение наверх стека (Push);
                2 - Получить значение с вершины стека (Pop);
                3 - Получить значение с вершины стека не удаляя (Peek);
                4 - Очистить стек (Clear);
                5 - Просмотреть все элементы стека (Show);
                6 - Сохранить все элементы стека в файл (Save);
                7 - Открыть элементы стека из файла (Open);
                8 - Перейти к работе над двунаправленной очередью;
                """);
    }
    public static void writeDequeOptions() {
        System.out.println("""
                Выберите, что вы хотите сделать с очередью.
                0 - Вернуться к работе над стеком;
                1 - Добавить значение в начало очереди (PushFront);
                2 - Добавить значение в конец очереди (PushBack)
                3 - Удалить значение из начала очереди (PopFront);
                4 - Удалить значение из конца очереди (PopBack)
                5 - Получить значение из начала очереди не удаляя (PeekFront);
                6 - Получить значение из конца очереди не удаляя (PeekBack)
                7 - Очистить очередь (Clear);
                8 - Просмотреть все элементы очереди (Show);
                9 - Сохранить все элементы очереди в файл (Save);
                10 - Открыть элементы очереди из файла (Open);
                """);
    }

    public static int getNum(int MIN_NUM, int MAX_NUM) {
        Status stat;
        int input = 0;
        do{
            System.out.printf("Введите целое число [%d, %d].%n", MIN_NUM, MAX_NUM);
            stat = Status.GOOD;
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                stat = Status.NOT_AN_INT;
            }
            if (stat == Status.GOOD && (input < MIN_NUM || input > MAX_NUM)) {
                stat = Status.OUT_OF_RANGE;
            }
            System.err.print(ERR_TEXT[stat.ordinal()]);
        } while (stat != Status.GOOD);
        return input;
    }

    public static void saveToFile(SaveAndOpenable obj) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()));
        writer.write("Элементы: " + obj.toString() + '.');
        writer.close();
    }

    public static String getFilePath(){
        String pathToFile;
        Status err;
        System.out.println("Введите название файла (путь к файлу *.txt).");
        do {
            pathToFile = scan.nextLine().trim();
            if (pathToFile.endsWith(".txt") && !pathToFile.equals(".txt")) {
                err = Status.GOOD;
            }
            else {
                err = Status.WRONG_EXTENSION;
            }
            System.err.print(ERR_TEXT[err.ordinal()]);
        }while(err != Status.GOOD);
        return  pathToFile;
    }

    public static String getStringFromFile(){
        Status status;
        StringBuilder str = new StringBuilder();
        do {
            status = Status.GOOD;
            try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } catch (IOException e) {
                status = Status.BAD_FILE;
            }
            System.err.println(ERR_TEXT[status.ordinal()]);
        } while (status != Status.GOOD);
        return str.toString();

    }



    public static MyDeque stackToDeque(MyStack stack){
        MyDeque deque = new MyDeque();
        while (!stack.isEmpty()) {
            deque.pushFront(stack.pop());
        }
        return deque;
    }

    public static MyStack dequeToStack(MyDeque deque){
        MyStack stack = new MyStack();
        while (!deque.isEmpty()) {
            stack.push(deque.popFront());
        }
        return stack;
    }

    public static MyStack workWithDeque(MyDeque mainDeque){
        boolean dequeWorkNotDone = true;
        Status status;
        while(dequeWorkNotDone){
            writeDequeOptions();
            status = switch(getNum(MIN_CHOICE, MAX_DEQUE_CHOICE)){

                case 1 -> {
                    mainDeque.pushFront(getNum(MIN_NUM, MAX_NUM));
                    yield Status.GOOD;
                }
                case 2 -> {
                    mainDeque.pushBack(getNum(MIN_NUM, MAX_NUM));
                    yield Status.GOOD;
                }
                case 3 -> {
                    try {
                        System.out.println("Было извлечено число " + mainDeque.popFront());
                    }
                    catch (NoSuchElementException e) {
                        yield Status.EMPTY_DEQUE;
                    }
                    yield Status.GOOD;
                }
                case 4 -> {
                    try {
                        System.out.println("Было извлечено число " + mainDeque.popBack());
                    }
                    catch (NoSuchElementException e) {
                        yield Status.EMPTY_DEQUE;
                    }
                    yield Status.GOOD;
                }
                case 5 -> {
                    try {
                        System.out.println("Было извлечено число " + mainDeque.peekFront());
                    }
                    catch (NoSuchElementException e) {
                        yield Status.EMPTY_DEQUE;
                    }
                    yield Status.GOOD;
                }
                case 6 -> {
                    try {
                        System.out.println("Было извлечено число " + mainDeque.peekBack());
                    }
                    catch (NoSuchElementException e) {
                        yield Status.EMPTY_DEQUE;
                    }
                    yield Status.GOOD;
                }
                case 7 -> {
                    mainDeque.clearDeque();
                    System.out.println("Очередь очищена успешно!");
                    yield Status.GOOD;
                }
                case 8 -> {
                    if (mainDeque.isEmpty()) {
                        yield Status.EMPTY_DEQUE;
                    }
                    System.out.println("Элементы очереди: " + mainDeque + '.');
                    yield Status.GOOD;
                }
                case 9 -> {
                    try {
                        saveToFile(mainDeque);
                    }catch (IOException e) {
                        yield Status.BAD_FILE;
                    }
                    System.out.println("Файл был успешно сохранен.");
                    yield Status.GOOD;
                }
                case 10 -> {
                    try {
                        mainDeque.fromString(getStringFromFile());
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        yield Status.BAD_FILE;
                    }
                    System.out.println("Стек был успешно сформирован.");
                    yield Status.GOOD;
                }
                default -> {
                    dequeWorkNotDone = false;
                    yield Status.GOOD;
                }
            };
            System.err.println(ERR_TEXT[status.ordinal()]);

        }
        return dequeToStack(mainDeque);
    }

    public static void workWithStack(MyStack mainStack){
        if (mainStack == null) {
            mainStack = new MyStack();
        }
        boolean cantLeave = true;
        Status status;
        while (cantLeave) {
            writeStackOptions();
            status = switch (getNum(MIN_CHOICE, MAX_STACK_CHOICE)) {
                case 1 -> {
                    mainStack.push(getNum(MIN_NUM, MAX_NUM));
                    yield Status.GOOD;
                }
                case 2 -> {
                    try {
                        System.out.println("Было извлечено число " + mainStack.pop());
                    }
                    catch (EmptyStackException e) {
                        yield Status.EMPTY_STACK;
                    }
                    yield Status.GOOD;
                }
                case 3 -> {
                    try {
                        System.out.println("На вершине стека лежит число " + mainStack.peek());
                    }
                    catch (EmptyStackException e) {
                        yield Status.EMPTY_STACK;
                    }
                    yield Status.GOOD;
                }
                case 4 -> {
                    mainStack.clearStack();
                    System.out.println("Стек очищен успешно!");
                    yield Status.GOOD;
                }
                case 5 -> {
                    if (mainStack.isEmpty()) {
                        yield Status.EMPTY_STACK;
                    }
                    System.out.println("Элементы стека: " + mainStack + '.');
                    yield Status.GOOD;
                }
                case 6 -> {
                    try {
                        saveToFile(mainStack);
                    }catch (IOException e) {
                        yield Status.BAD_FILE;
                    }
                    System.out.println("Файл был успешно сохранен.");
                    yield Status.GOOD;
                }
                case 7 -> {
                    try {
                        mainStack.fromString(getStringFromFile());
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        yield Status.NOT_AN_INT;
                    }
                    System.out.println("Стек был успешно сформирован.");
                    yield Status.GOOD;
                }
                case 8 -> {
                    mainStack = workWithDeque(stackToDeque(mainStack));
                    yield Status.GOOD;
                }
                default -> {
                    cantLeave = false;
                    scan.close();
                    yield Status.GOOD;
                }
            };
            System.err.println(ERR_TEXT[status.ordinal()]);
        }

    }


    public static void main(String[] args) {
        writeTask();
        System.setErr(System.out); //IntelliJ IDEA bad output fix
        workWithStack(null);
    }
}