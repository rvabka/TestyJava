package org.example.music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int seconds) {
        int currentSecond = 0;
        for(Song song : this) {
            int songDuration = song.getDurationInSeconds();
            if(currentSecond <= seconds && seconds < currentSecond + songDuration) {
                return song;
            } else if(seconds < 0 && seconds > songDuration) {
                throw new IndexOutOfBoundsException("Time is out of range");
            }
            currentSecond += songDuration;
        }
        throw new IndexOutOfBoundsException("The specified time is beyond the playlist duration.");
    }
}
