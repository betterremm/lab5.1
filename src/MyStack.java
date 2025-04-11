import java.util.EmptyStackException;

class StackNode {
    int value;
    StackNode next;
    public StackNode(int value) {
        this.value = value;
        next = null;
    }
}

public class MyStack implements StackToDeque.SaveAndOpenable {
    StackNode top;
    public MyStack() {
        top = null;
    }

    public void push(int value) {
        StackNode newNode = new StackNode(value);
        if (top != null) {
            newNode.next = top;
        }
        top = newNode;
    }

    public int pop()  throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException();
        }
        int value = top.value;
        top = top.next;
        return value;
    }

    public int peek() throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException();
        }
        return top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public String toString(){
        StackNode current = top;
        StringBuilder sb = new StringBuilder();
        sb.append(current.value);
        while(current.next != null){
            current = current.next;
            sb.append(", ").append(current.value);
        }
        return sb.toString();
    }

    public void fromString(String str) throws NumberFormatException, IndexOutOfBoundsException {
        String[] parts = str.substring(10, str.length() - 1).split(", ");
        top = new StackNode(Integer.parseInt(parts[0]));
        StackNode current = top;
        for (int i = 1; i < parts.length; i++) {
            current.next = new StackNode(Integer.parseInt(parts[i]));
            current = current.next;
        }
    }

    public void clearStack() {
        top = null;
    }

}
