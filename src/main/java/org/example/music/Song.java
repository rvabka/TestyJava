package org.example.music;

public record Song(String artist, String title, int durationInSeconds) {

    public int getDurationInSeconds() {
        return durationInSeconds;
    }
}
