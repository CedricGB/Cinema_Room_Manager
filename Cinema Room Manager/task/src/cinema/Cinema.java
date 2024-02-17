package cinema;

import java.util.Scanner;
public class Cinema {

    private String[][] cinemaBuilding;
    private int rows;
    private int seats;
    private int freeSeat;
    private int income;
    public Cinema(int row, int seats){
        // +1 because [0][1] is empty
        this.rows = row ;
        this.seats = seats ;
        this.freeSeat = this.rows * this.seats;
        this.cinemaBuilding = populateArray(this.rows, this.seats);
        this.income = 0;
    }

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        int rows = setRowCinema(scanner);
        int seats = setSeatCinema(scanner);

        Cinema cinema = new Cinema(rows, seats);
        cinema.menu();
    }
    public void menu(){
        Scanner scanner = new Scanner(System.in);
        String text = """
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """;
        while(true){
            System.out.println(text);
            String input = scanner.nextLine();
            if(input.equals("1")){
                showCinema();
            } else if(input.equals("2")){
                buySeat();
            } else if(input.equals("3")){
                stats();
            }else if(input.equals("0")){
                break;
            }

        }
    }
    public void stats(){
        double percentage = (1.0 *this.rows * this.seats - this.freeSeat) * 100 / (this.rows * this.seats);
        String formatedPercentage = String.format("Percentage :  %.2f", percentage);
        System.out.println("Number of purchased tickets: " + (this.rows * this.seats - this.freeSeat));
        System.out.println(formatedPercentage + "%");
        System.out.println("Current income: $" + this.income );
        totalIncome();
    }
    public String[][] populateArray(int rows, int seats){

        String[][] cinema = new String[rows + 1 ][seats + 1];
        for(int i = 0; i < cinema.length;i++){
            for(int j = 0; j < cinema[i].length;j++){
                if(j == 0 && i == 0){
                    cinema[i][j] = " ";
                } else if(i == 0){
                    // populate 12345678 first row
                    cinema[i][j] = String.valueOf(j);
                }else if(j == 0 ){
                    // populate first column
                    cinema[i][j] = String.valueOf(i);
                }else {
                    // populate the row
                    cinema[i][j] = "S";
                }
            }

        }

        return cinema;
    }
    public void showCinema(){
        System.out.println("Cinema: ");
        for(int i = 0; i < this.cinemaBuilding.length;i++){
            for(int j = 0; j < this.cinemaBuilding[i].length;j++){
                System.out.print(this.cinemaBuilding[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    // Using nextLine() because nextInt() let a white space and it was causing bug in the menu
    public static int setRowCinema(Scanner scanner){
        System.out.println("Enter the number of rows:");
        return Integer.parseInt(scanner.nextLine());
    }

    public static int setSeatCinema(Scanner scanner){

        System.out.println("Enter the number of seats in each row:\n");

        return Integer.parseInt(scanner.nextLine());
    }
    public void totalIncome(){
        int totalSeats = this.rows  * this.seats;
        int totalIncome = 0;
        if(totalSeats <= 60){
            totalIncome = totalSeats * 10;
        } else {
            int frontRows = rows / 2;
            int backRows = rows - (rows / 2);
            totalIncome = frontRows * seats *  10 + backRows * 8 * seats;
        }
        System.out.println("Total income: " +"$" + totalIncome);

    }

    public int seatPrice(int row){

            int totalSeats = (this.cinemaBuilding.length - 1 )  * (this.cinemaBuilding[0].length - 1);
            if( totalSeats <= 60 || row <= this.cinemaBuilding.length / 2 - 1){
                return 10;
            } else {
                return 8;
            }

    }
    private void buySeat(){
        // choice of seat
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();
            if(row > this.rows || seat > this.seats){
                System.out.println("Wrong input!");
                continue;
            }
            // Test if the seat is free
            if(this.cinemaBuilding[row][seat].equals("B")){
                System.out.println("That ticket has already been purchased!");

            } else{
                this.income += seatPrice(row);
                System.out.println("Ticket price: $" + seatPrice(row));
                replaceSeatByB(row, seat);
                this.freeSeat--;
                break;
            }
        }




    }

    private void replaceSeatByB(int row, int seat){

        this.cinemaBuilding[row][seat] = "B";

    }

    public String[][] getCinema(){
        return this.cinemaBuilding;
    }

}