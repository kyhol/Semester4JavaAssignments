import java.util.Scanner;

public class TheaterSeatingSystem {
    private static final char SEAT_AVAILABLE = 'O';
    private static final char SEAT_RESERVED = 'X';
    private static final int NEARBY_SEAT_SEARCH_RADIUS = 2;

    private final char[][] seatingLayout;
    private final int numberOfRows;
    private final int numberOfColumns;

    public TheaterSeatingSystem(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.seatingLayout = new char[numberOfRows][numberOfColumns];
        this.initializeEmptyTheater();
    }

    private void initializeEmptyTheater() {
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
                seatingLayout[rowIndex][columnIndex] = SEAT_AVAILABLE;
            }
        }
    }

    public void displayTheaterLayout() {
        System.out.println("\nCurrent Theater Seating Layout:");
        printColumnHeaders();
        printSeatingRows();
        printLegend();
    }

    private void printColumnHeaders() {
        StringBuilder headerRow = new StringBuilder("   ");
        for (int columnIndex = 1; columnIndex <= numberOfColumns; columnIndex++) {
            headerRow.append(columnIndex).append(" ");
        }
        System.out.println(headerRow.toString());
    }

    private void printSeatingRows() {
        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            System.out.printf("%2d ", rowIndex + 1);
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
                System.out.print(seatingLayout[rowIndex][columnIndex] + " ");
            }
            System.out.println();
        }
    }

    private void printLegend() {
        System.out.println("\n" + SEAT_AVAILABLE + ": Available   " +
                SEAT_RESERVED + ": Reserved");
    }

    public String reserveSeatAtPosition(int requestedRow, int requestedColumn) {
        SeatPosition seatPosition = convertToZeroBasedIndices(requestedRow, requestedColumn);

        if (!isValidSeatPosition(seatPosition)) {
            return "Invalid seat position! Please check row and column numbers.";
        }

        if (isSeatReserved(seatPosition)) {
            return findAndSuggestAlternativeSeats(seatPosition);
        }

        markSeatAsReserved(seatPosition);
        return "Seat successfully reserved at Row " + requestedRow +
                ", Column " + requestedColumn + "!";
    }

    public String cancelSeatReservation(int requestedRow, int requestedColumn) {
        SeatPosition seatPosition = convertToZeroBasedIndices(requestedRow, requestedColumn);

        if (!isValidSeatPosition(seatPosition)) {
            return "Invalid seat position! Please check row and column numbers.";
        }

        if (!isSeatReserved(seatPosition)) {
            return "This seat is already available!";
        }

        markSeatAsAvailable(seatPosition);
        return "Reservation successfully cancelled for Row " + requestedRow +
                ", Column " + requestedColumn + "!";
    }

    private SeatPosition convertToZeroBasedIndices(int oneBasedRow, int oneBasedColumn) {
        return new SeatPosition(oneBasedRow - 1, oneBasedColumn - 1);
    }

    private boolean isValidSeatPosition(SeatPosition position) {
        return position.row >= 0 && position.row < numberOfRows &&
                position.column >= 0 && position.column < numberOfColumns;
    }

    private boolean isSeatReserved(SeatPosition position) {
        return seatingLayout[position.row][position.column] == SEAT_RESERVED;
    }

    private void markSeatAsReserved(SeatPosition position) {
        seatingLayout[position.row][position.column] = SEAT_RESERVED;
    }

    private void markSeatAsAvailable(SeatPosition position) {
        seatingLayout[position.row][position.column] = SEAT_AVAILABLE;
    }

    private String findAndSuggestAlternativeSeats(SeatPosition takenSeat) {
        StringBuilder suggestions = new StringBuilder(
                "This seat is already taken. Available seats nearby:\n");
        boolean alternativesFound = false;

        for (int rowOffset = -NEARBY_SEAT_SEARCH_RADIUS;
             rowOffset <= NEARBY_SEAT_SEARCH_RADIUS; rowOffset++) {

            int nearbyRow = takenSeat.row + rowOffset;

            for (int columnOffset = -NEARBY_SEAT_SEARCH_RADIUS;
                 columnOffset <= NEARBY_SEAT_SEARCH_RADIUS; columnOffset++) {

                int nearbyColumn = takenSeat.column + columnOffset;
                SeatPosition nearbySeat = new SeatPosition(nearbyRow, nearbyColumn);

                if (isValidSeatPosition(nearbySeat) && !isSeatReserved(nearbySeat)) {
                    suggestions.append("Row ").append(nearbyRow + 1)
                            .append(", Column ").append(nearbyColumn + 1)
                            .append("\n");
                    alternativesFound = true;
                }
            }
        }

        if (!alternativesFound) {
            return "This seat is taken and no nearby seats are available.";
        }
        return suggestions.toString();
    }

    private static class SeatPosition {
        final int row;
        final int column;

        SeatPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Theater Seating System ===");
        System.out.println("1. View Seating Layout");
        System.out.println("2. Reserve a Seat");
        System.out.println("3. Cancel Reservation");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    private static int getValidIntInput(Scanner scanner, String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TheaterSeatingSystem theater = new TheaterSeatingSystem(5, 5);

        while (true) {
            displayMenu();
            int choice = getValidIntInput(scanner, "", 1, 4);

            switch (choice) {
                case 1:
                    theater.displayTheaterLayout();
                    break;

                case 2:
                    theater.displayTheaterLayout();
                    System.out.println("\nReserve a Seat:");
                    int row = getValidIntInput(scanner, "Enter row number: ", 1, theater.numberOfRows);
                    int col = getValidIntInput(scanner, "Enter column number: ", 1, theater.numberOfColumns);
                    System.out.println(theater.reserveSeatAtPosition(row, col));
                    break;

                case 3:
                    theater.displayTheaterLayout();
                    System.out.println("\nCancel Reservation:");
                    row = getValidIntInput(scanner, "Enter row number: ", 1, theater.numberOfRows);
                    col = getValidIntInput(scanner, "Enter column number: ", 1, theater.numberOfColumns);
                    System.out.println(theater.cancelSeatReservation(row, col));
                    break;

                case 4:
                    System.out.println("Thank you for using Theater Seating System!");
                    scanner.close();
                    return;
            }
        }
    }
}