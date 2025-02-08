package Queues;

public class QueueDriver {
    public static void main(String[] args) {
        AnimalShelter shelter = new AnimalShelter();

        System.out.println("Welcome to the Animal Shelter!");
        System.out.println("-----------------------------");

        // Add initial animals
        System.out.println("\nReceiving new animals:");
        shelter.enqueue(new Dog("Rex"));
        shelter.enqueue(new Cat("Whiskers"));
        shelter.enqueue(new Dog("Max"));
        shelter.enqueue(new Cat("Luna"));

        // Show adoption options
        System.out.println("\nAdoption Options:");
        System.out.println("1. Adopt any animal (oldest first)");
        System.out.println("2. Adopt a dog (oldest dog)");
        System.out.println("3. Adopt a cat (oldest cat)");

        // Demonstrate each type of adoption
        System.out.println("\nProcessing adoptions:");
        System.out.println("\nOption 1 - Adopting any animal:");
        Animal adopted = shelter.dequeueAny();
        System.out.println("Congratulations! You've adopted " + adopted.getName() +
                " (" + (adopted instanceof Dog ? "Dog" : "Cat") + ")");

        System.out.println("\nOption 2 - Adopting a dog:");
        Dog adoptedDog = shelter.dequeueDog();
        System.out.println("Congratulations! You've adopted " + adoptedDog.getName() + " (Dog)");

        System.out.println("\nOption 3 - Adopting a cat:");
        Cat adoptedCat = shelter.dequeueCat();
        System.out.println("Congratulations! You've adopted " + adoptedCat.getName() + " (Cat)");

        System.out.println("\nRemaining animals for adoption:");
        while (!shelter.isEmpty()) {
            Animal remaining = shelter.dequeueAny();
            System.out.println("- " + remaining.getName() +
                    " (" + (remaining instanceof Dog ? "Dog" : "Cat") + ")");
        }
    }
}