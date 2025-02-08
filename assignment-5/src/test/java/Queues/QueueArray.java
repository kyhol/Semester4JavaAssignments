package Queues;
import java.util.Queue;
import java.util.LinkedList;

// First implementation: Array-based Queue
public class QueueArray {
    private int[] arr;
    private int backOfQueue;
    private int nItems;
    private int beginingOfQueue;

    public QueueArray(int size) {
        this.arr = new int[size];
        this.backOfQueue = -1;
        this.beginingOfQueue = -1;
        this.nItems = 0;
        System.out.println("The queue is successfully created with size of: " + size);
    }

    //isFull
    public boolean isFull() {
        if (backOfQueue == arr.length - 1) {
            return true;
        } else {
            return false;
        }
    }

    //isEmpty
    public boolean isEmpty() {
        return (nItems == 0);
    }

    public void enQueue(int value) {
        if (isFull()) {
            System.out.println("The Queue is full");
        } else if (isEmpty()) {
            beginingOfQueue = 0;
            backOfQueue++;
            arr[backOfQueue] = value;
            System.out.println("Successfully inserted " + value + "in the queue");
        } else {
            backOfQueue++;
            nItems++;
            arr[backOfQueue] = value;
            System.out.println("Successfully inserted " + value + "in the queue");
        }
    }

    public int deQueue() {
        int result = 0;
        if (isEmpty()) {
            System.out.println("The Queue is empty");
        } else {
            result = arr[beginingOfQueue];
            beginingOfQueue++;
            if (beginingOfQueue > backOfQueue) {
                beginingOfQueue = backOfQueue = -1;
            }
            nItems--;
        }
        return result;
    }

    public int peek() {
        if (!isEmpty()) {
            System.out.println("The stack is empty");
            return arr[beginingOfQueue];
        } else {
            System.out.println("The queue is empty");
            return -1;
        }
    }

    //delete
    public void deleteQueue() {
        arr = null;
        System.out.println("The Queue is successfully delete");
    }
}

class Animal {
    private int order;
    private final String name;

    public Animal(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }
}

class AnimalShelter {
    private Queue<Dog> dogs;
    private Queue<Cat> cats;
    private int order;

    public AnimalShelter() {
        dogs = new LinkedList<>();
        cats = new LinkedList<>();
        order = 0;
    }

    public void enqueue(Animal animal) {
        animal.setOrder(order);
        order++;

        if (animal instanceof Dog) {
            dogs.add((Dog) animal);
        } else if (animal instanceof Cat) {
            cats.add((Cat) animal);
        }
    }

    public Animal dequeueAny() {
        if (dogs.isEmpty()) {
            return dequeueCat();
        } else if (cats.isEmpty()) {
            return dequeueDog();
        }

        Dog oldestDog = dogs.peek();
        Cat oldestCat = cats.peek();

        if (oldestDog.getOrder() < oldestCat.getOrder()) {
            return dequeueDog();
        } else {
            return dequeueCat();
        }
    }

    public Dog dequeueDog() {
        if (dogs.isEmpty()) {
            throw new IllegalStateException("No dogs available");
        }
        return dogs.poll();
    }

    public Cat dequeueCat() {
        if (cats.isEmpty()) {
            throw new IllegalStateException("No cats available");
        }
        return cats.poll();
    }

    public boolean isEmpty() {
        return dogs.isEmpty() && cats.isEmpty();
    }


    public static void main(String[] args) {
        AnimalShelter shelter = new AnimalShelter();

        shelter.enqueue(new Dog("Dog1 sarah"));
        shelter.enqueue(new Cat("Cat1 steve"));
        shelter.enqueue(new Dog("Dog2 max"));
        shelter.enqueue(new Cat("cat2 Luna"));

        System.out.println("Dequeuing any: " + shelter.dequeueAny().getName());
        System.out.println("Dequeuing dog: " + shelter.dequeueDog().getName());
        System.out.println("Dequeuing cat: " + shelter.dequeueCat().getName());
    }
}
/**
 * An Animal shelter, which holds only dogs and cats, operates on a strictly "first in, first out" bases.
 * People must adopt either the "oldest" (based on arrival time) of  all animals at the shelter, or they can select
 *  whether they would prefer a dog or cat (and will receive the oldest animal of that type).
 *  They cannot select which specific animal they would like.
 *  Create a datastucture to maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog and DequeueCat.
 */