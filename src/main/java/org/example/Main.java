package org.example;

import org.threads.ThreadManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new ThreadManager().cleanup();
        System.out.println("ciao");
    }
}