package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.company.GameMain.ANSI_RED;
import static com.company.GameMain.ANSI_RESET;

public class Main {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) throws NullPointerException {
        try {
            GameMain project = new GameMain();
            String movieRandom = project.generateMoviesRandom(project.getListFilms());
            project.setRandomlyFilms(movieRandom);
            String movieCode = project.codeMovie(movieRandom);
            project.setMovieCode(movieCode);
            try (Scanner scanner = new Scanner(System.in)) {
                char solve;
                int badTrying = 0;
                project.setBadTrying(badTrying);
                extracted();
                while (!project.gameOver()) {
                    extracted(project);
                    solve = scanner.next().toLowerCase().charAt(0);
                    if (project.isValidGuess(solve)) {
                        if (!project.hasAlreadyGuessed(solve)) project.checkSolve(solve);
                        else System.out.println("You have already guessed '" + solve  + "'.");
                    } else System.out.println(ANSI_BLUE + "Please enter a letter only." + ANSI_RESET + "\n");
                }
                if (project.winGame()) System.out.println("\n" + ANSI_GREEN + "WIN !" + ANSI_RESET + "\n" +
                        "You have guessed '" + ANSI_CYAN + movieRandom + ANSI_RESET + "' correctly.");
                else System.out.println("\n" + ANSI_RED + "GAME OVER ..." + ANSI_RESET + "\n" + "The movie was '" +
                        ANSI_CYAN + movieRandom + ANSI_RESET + "'.");
            }
        } catch (FileNotFoundException evt) {
            System.err.println("File is not found.");
        }
    }
    @SuppressWarnings({"StringBufferReplaceableByString", "UnnecessaryToStringCall"})
    private static void extracted() {
        System.out.println(new StringBuilder()
                .append("\nWelcome to \"Guess the movie\"! ")
                .append("Your goal is to guess the title of the movie by entering one letter. \n")
                .append("If there is a letter in the title, the computer will show its position in the word, \n")
                .append("if not, you lose a point. If you lose 10 points, game over! \n")
                .append("Let's play!\n").toString());
    }

    private static void extracted(@NotNull GameMain project) {
        System.out.print("You are guessing: ");
        System.out.println(ANSI_GREEN + project.getMovieCode() + ANSI_RESET);
        System.out.print("Incorrect letters : (" + ANSI_RED + project.getBadTrying() + ANSI_RESET + ") ");
        System.out.println(ANSI_PURPLE + project.getBadSolve() + ANSI_RESET);
        System.out.println("Wrong guesses left : (" + ANSI_YELLOW + project.getAttemptsLeft() + ANSI_RESET + ") ");
        System.out.print("Guess a letter : ");
    }
}
