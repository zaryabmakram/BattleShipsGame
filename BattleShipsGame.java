/**
 * Microsoft: DEV277x - Object Oriented Programming in Java
 * Module 1 Project
 ***************
 * BATTLE SHIPS
 ***************
 * Author: Zaryab Muhammad Akram
 * 2/1/2018
 **/

import java.util.Scanner;
import java.util.Random;

public class BattleShipsGame {
    public static final Scanner input = new Scanner(System.in); //creating a global Scanner object
    public static final Random rand = new Random(); //creating global random object

    public static void main(String[] args) {
     /*representing ocean maps by 10 by 10 grids*/
        String[][] playerMap = new String[10][10];
        String[][] computerMap = new String[10][10];
        /*initializing the map elements to empty*/
        for (int i = 0; i < playerMap.length; ++i) {
            for (int j = 0; j < playerMap[i].length; ++j) {
                playerMap[i][j] = " ";
                computerMap[i][j] = " ";
            }
        }
        /*Welcome message*/
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println("\nRight now, the sea is empty.");
        printMap(playerMap);

        placePlayerShips(playerMap);
        printMap(playerMap);

        placeComputerShips(computerMap, playerMap);
        battle(computerMap, playerMap);
    }

    /*
    * This method prints the map showing the ships placed.
     */
    public static void printMap(String[][] oceanMap) {
        System.out.println(""); //new line
        System.out.println("   0123456789");
        for (int i = 0; i < oceanMap.length; ++i) {
            System.out.print(i + " |");
            for (int j = 0; j < oceanMap[i].length; ++j) {
                String tmp = oceanMap[i][j];
                System.out.print(tmp);
            }
            System.out.println("| " + i);
        }
        System.out.println("   0123456789");
        System.out.println(""); //new line
    } //end method

    /*
    * This method asks the user where they would like to place their ships. The player deploys 5 ships.
     */
    public static void placePlayerShips(String[][] playerMap) {
        int x, y; //coordinates of player's ship in the map
        System.out.println("Deploy your ships:");
        int i = 1;
        while(i <= 5){
            System.out.print("Enter X coordinate for your " + i + ". ship: ");
            x = input.nextInt();
            while (x < 0 || x > 9) { /*If the input is not within range*/
                System.out.println("Invalid Input!");
                System.out.print("Enter X coordinate for your " + i + ". ship: ");
                x = input.nextInt();
            }

            System.out.print("Enter Y coordinate for your " + i + ". ship: ");
            y = input.nextInt();
            while (y < 0 || y > 9) {
                System.out.println("Invalid Input!");
                System.out.print("Enter Y coordinate for your " + i + ". ship: ");
                y = input.nextInt();
            }

            if(playerMap[y][x].equals("@")) {
                System.out.println("Invalid Input! This position is already occupied!");
                continue;
            }
            playerMap[y][x] = "@"; /*Placing player's ship on the map*/
            ++i;
        } //end while
    }

    /*
     * This method deploys 5 of computer ships. The ships are kept hidden and are not displayed on map printed.
     */
    public static void placeComputerShips(String[][] computerMap, String[][] playerMap){
        int x, y; //coordinates of computer's ships
        System.out.println("Computer is deploying ships:");
        int i = 1;
        while (i <= 5) {
            x = rand.nextInt(10); /*generates a random number from 0 to 9 inclusively*/
            y = rand.nextInt(10);
            if (playerMap[y][x].equals("@") || computerMap[y][x].equals("@")) {
                continue;
            }
            computerMap[y][x] = "@";
            System.out.println(i + ". ship DEPLOYED");
            ++i;
        } //end while
        System.out.println("-----------------------------");
    }

    /*
    * This method prompts the player and computer to take turns guessing X and Y coordinates of the opponent’s ships.
    * This battle ends when either of the opponent runs out of ships
    */

    public static void battle(String[][] computerMap, String[][] playerMap){
        int playerShipCount = 5, computerShipCount = 5;
        int x1, y1; /*coordinates of player's guess*/
        while(playerShipCount > 0 && computerShipCount > 0) {
            System.out.println("YOUR TURN"); /*Player's turn*/
            System.out.print("Enter X coordinate: ");
            x1 = input.nextInt();
            while (x1 < 0 || x1 > 9) {
                System.out.println("Invalid Input!");
                System.out.print("Enter X coordinate: ");
                x1 = input.nextInt();
            }

            System.out.print("Enter Y coordinate: ");
            y1 = input.nextInt();
            while (y1 < 0 || y1 > 9) {
                System.out.println("Invalid Input!");
                System.out.print("Enter Y coordinate: ");
                y1 = input.nextInt();
            }
            if(computerMap[y1][x1].equals("@")){
                System.out.println("BOOM! You sunk the ship!");
                playerMap[y1][x1] = "!";
                --computerShipCount; //decrementing computer's ship count
            }else if(playerMap[y1][x1].equals("@")){
                System.out.println("Oh no, you sunk your own ship :(");
                playerMap[y1][x1] = "x";
                --playerShipCount; //decrementing player's ship count
            }else if(playerMap[y1][x1].equals(" ")){
                System.out.println("Sorry, you missed");
                playerMap[y1][x1] = "-";
            }else{
                System.out.println("This position is already occupied!");
                continue;
            }
            printMap(playerMap); /*printing map after player's turn*/
            System.out.println("Your ships: " + playerShipCount + " | Computer ships: " + computerShipCount);
            System.out.println("-----------------------------");

            System.out.println("Computer's turn");
            int x2, y2; /*coordinates of computer's guess*/

            x2=rand.nextInt(10);
            y2=rand.nextInt(10);
            while(playerMap[y2][x2].equals("x") || playerMap[y2][x2].equals("!") || computerMap[y2][x2].equals("-")){
                x2=rand.nextInt(10);
                y2=rand.nextInt(10);
            }
            if(playerMap[y2][x2].equals("@")){
                System.out.println("The Computer sunk one of your ships!");
                playerMap[y2][x2] = "x";
                --playerShipCount; //decrementing player's ship count
            }else if(computerMap[y2][x2].equals("@")){
                System.out.println("The computer sunk one of its own ships");
                playerMap[y2][x2] = "!";
                --computerShipCount; //decrementing computer's ship count
            }
            else if(computerMap[y2][x2].equals(" ")){
                System.out.println("Computer missed");
                computerMap[y2][x2] = "-";
            }
            printMap(playerMap); /*printing map after computer's turn*/
            System.out.println("Your ships: " + playerShipCount + " | Computer ships: " + computerShipCount);
            System.out.println("-----------------------------");

        }

        /*Game Over*/
        if(computerShipCount == 0){
            System.out.println("Hooray! You win the battle :)");
        }else{ /*If playerShipCount == 0*/
            System.out.println("Sorry! You lose :(");
        }
    }
}
