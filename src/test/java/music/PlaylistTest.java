package music;
import org.example.music.Playlist;
import org.example.music.Song;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    @Test
    public void testNewPlaylistIsEmpty() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty(), "Newly created playlist should be empty");
    }

    @Test public void testPlaylistSizeAfterAddingOneSong() {
        Playlist playlist = new Playlist();
        Song song = new Song("xyz", "123", 180);
        playlist.add(song);
        assertEquals(1, playlist.size());
    }

    @Test public void testPlayListContainsAddedSong() {
        Playlist playlist = new Playlist();
        Song song = new Song("xyz", "123", 180);
        playlist.add(song);
        assertTrue(playlist.contains(song));
    }

    @Test public void testAtSecond() {
        Playlist playlist = new Playlist();
        Song song = new Song("xyz", "123", 180);
        Song song2 = new Song("bnm", "456", 240);
        Song song3 = new Song("jkl", "789", 150);
        playlist.add(song);
        playlist.add(song2);
        playlist.add(song3);

        assertEquals(song, playlist. atSecond(0));
        assertEquals(song, playlist. atSecond(120));
        assertEquals(song2, playlist.atSecond(180));
        assertEquals(song3, playlist.atSecond(420));
    }

    @Test public void testAtSecondThrowsException() {
        Playlist playlist = new Playlist();
        Song song = new Song("xyz", "123", 180);
        Song song2 = new Song("bnm", "456", 240);
        Song song3 = new Song("jkl", "789", 150);
        playlist.add(song);
        playlist.add(song2);
        playlist.add(song3);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            playlist.atSecond(570);
        }, "Index out of bounds");
    }

    @Test public void testAtSecondOutOfBoundsThrowsException() {
        Playlist playlist = new Playlist();
        Song song = new Song("xyz", "123", 180);
        Song song2 = new Song("bnm", "456", 240);
        Song song3 = new Song("jkl", "789", 150);
        playlist.add(song);
        playlist.add(song2);
        playlist.add(song3);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            playlist.atSecond(-10);
        }, "Negative time");
    }



}
