package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMain {
    private String movieRandom, movieCode, badSolve, correctSolve;
    private int badTrying;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    GameMain() {
        movieRandom = "";
        movieCode = "";
        badSolve = "";
        correctSolve = "";
    }

    String[] getListFilms() throws FileNotFoundException {
        StringBuilder listReaderFilms;
        try (Scanner databaseScanner = new Scanner(new File("src\\com\\company\\movies.txt"))) {
            listReaderFilms = new StringBuilder();
            while (databaseScanner.hasNextLine()) {
                listReaderFilms.append(databaseScanner.nextLine());
                listReaderFilms.append("\n");
            }
        }
        return listReaderFilms.toString().trim().split("\n");
    }
    String generateMoviesRandom(String @NotNull [] films) {
        int indexMovie = (int) (Math.random() * films.length);
        return films[indexMovie].replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
    }
    void setRandomlyFilms(String movieRandom) {
        this.movieRandom = movieRandom;
    }
    private String getMovieRandom() {
        return movieRandom;
    }
    String codeMovie(@NotNull String randomMovie) { return randomMovie.replaceAll("[a-zA-Z]", "_"); }
    void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }
    String getMovieCode() { return movieCode; }
    void setBadTrying(int badTrying) {
        this.badTrying = badTrying;
    }
    int getBadTrying() {
        return badTrying;
    }
    int getAttemptsLeft() {
        return 10 - badTrying;
    }
    private void setBadSolve(String badSolve) { this.badSolve = badSolve; }
    String getBadSolve() { return badSolve; }
    private void setCorrectSolve(String correctSolve) {
        this.correctSolve = correctSolve;
    }
    private String getCorrectSolve() {
        return correctSolve;
    }
    boolean isValidGuess(char solves) {
        return solves >= 'a' && solves <= 'z';
    }
    boolean hasAlreadyGuessed(char guess) {
        return getBadSolve().indexOf(guess) >= 0 || getCorrectSolve().indexOf(guess) >= 0;
    }
    void checkSolve(char guess) {
        if (getMovieRandom().indexOf(guess) >= 0) {
            StringBuilder codeTrackerMovie = new StringBuilder(getMovieCode());
            for (int i = 0; i < movieCode.length(); i++) {
                if (guess == movieRandom.charAt(i)) {
                    codeTrackerMovie.setCharAt(i, guess);
                }
            }
            setMovieCode(codeTrackerMovie.toString());
            correctSolve += guess + " ";
            setCorrectSolve(correctSolve);
        } else {
            badSolve += guess + " ";
            setBadSolve(badSolve);
            badTrying++;
            setBadTrying(badTrying);
        }
    }
    @SuppressWarnings("RedundantIfStatement")
    boolean winGame() {
        if (getMovieCode().indexOf('_') < 0) return true;
        else return false;
    }
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    boolean lostGame() {
        return switch (getBadTrying()) {
            case 10 -> true;
            default -> false;
        };
    }
    @SuppressWarnings("RedundantIfStatement")
    boolean gameOver() {
        if (!(!winGame() && !lostGame())) return true;
        else return false;
    }
}
