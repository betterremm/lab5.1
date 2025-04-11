import java.util.NoSuchElementException;

class MyDequeNode{
    int value;
    MyDequeNode next;
    MyDequeNode prev;
    public MyDequeNode(int value){
        this.value = value;
        this.next = this.prev = null;
    }
}


public class MyDeque implements StackToDeque.SaveAndOpenable {
    MyDequeNode head;
    MyDequeNode tail;
    public MyDeque(){
        this.head = null;
        this.tail = null;
    }

    public void pushFront(int value) {
        MyDequeNode newNode = new MyDequeNode(value);
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
    }

    public void pushBack(int value) {
        MyDequeNode newNode = new MyDequeNode(value);
        newNode.prev = tail;
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
    }


    public int peekFront() throws NoSuchElementException{
        if(head == null){
            throw new NoSuchElementException();
        }
        return head.value;
    }

    public int popFront() throws NoSuchElementException{
        if (head == null) {
            throw new NoSuchElementException();
        }
        int value = head.value;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null; // deque is now empty
        }
        return value;
    }


    public int popBack() throws NoSuchElementException{
        if (tail == null) {
            throw new NoSuchElementException();
        }
        int value = tail.value;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null; // deque is now empty
        }
        return value;
    }


    public int peekBack() throws NoSuchElementException{
        if(head == null){
            throw new NoSuchElementException();
        }
        return head.value;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public void clearDeque(){
        head = null;
        tail = null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        MyDequeNode current = head;
        sb.append(current.value);
        current = current.next;
        while(current != null){
            sb.append(", ").append(current.value);
            current = current.next;
        }
        return sb.toString();
    }

    public void fromString(String str) throws NumberFormatException, IndexOutOfBoundsException {
        if(str.endsWith(".") && str.startsWith("Элементы: ")) {
            String[] parts = str.substring(10).split(", ");
            head = new MyDequeNode(Integer.parseInt(parts[0]));
            MyDequeNode current = head;
            MyDequeNode tempPrev = null;
            for (int i = 1; i < parts.length; i++) {
                current.next = new MyDequeNode(Integer.parseInt(parts[i]));
                tempPrev = current;
                current = current.next;
                current.prev = tempPrev;
            }
            tail = tempPrev;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

}
