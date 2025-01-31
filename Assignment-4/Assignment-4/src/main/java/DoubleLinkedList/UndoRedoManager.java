package DoubleLinkedList;

/**
 * Implememt an application that support undo/redo functionality. Use a linked list to maintain a sequence of states.\
 * Each state change is stored as a node in the list, allowoing for easy navigation
 * 1<>2<>3<>4<>5
 */

public class UndoRedoManager<T> {
    private class Node {
        private T state;
        private Node prev;
        private Node next;

        private Node(T state) {
            this.state = state;
            this.prev = null;
            this.next = null;
        }
    }

    private Node currentState;

    public UndoRedoManager() {
        this.currentState = null;
    }

    public T undo() {
        if (currentState == null || currentState.prev == null) {
            return null;
        }
        currentState = currentState.prev;
        return currentState.state;
    }

    public void addState(T newState) {
        Node newNode = new Node(newState);

        if (currentState == null) {
            currentState = newNode;
            return;
        }

        currentState.next = newNode;
        newNode.prev = currentState;
        currentState = newNode;
    }

    public T redo() {
        if (currentState == null || currentState.next == null) {
            return null;
        }
        currentState = currentState.next;
        return currentState.state;
    }

    public T getCurrentState() {
        return currentState != null ? currentState.state : null;
    }

    public static void main(String[] args) {
        UndoRedoManager<String> textEditor = new UndoRedoManager<>();

        textEditor.addState("Hello");
        textEditor.addState("Hello World");
        textEditor.addState("Hello World!");

        System.out.println("Initial state after three edits: " + textEditor.getCurrentState());
        System.out.println("Reverting last edit operation: " + textEditor.undo());
        System.out.println("Reverting to first edit: " + textEditor.undo());
        System.out.println("Restoring second edit: " + textEditor.redo());
        textEditor.addState("New Branch");
        System.out.println("State after creating new edit branch: " + textEditor.getCurrentState());
        System.out.println("Attempting redo on newest branch (should be null): " + textEditor.redo());
    }
}