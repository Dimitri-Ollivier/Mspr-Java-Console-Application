package dev;

import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

class AppTest {
    @Test
    public void testGetFile() throws IOException {
        String file = "https://raw.githubusercontent.com/Dimitri-Ollivier/Mspr-Java-Backup-Directory/main/staff.txt";
        assertEquals("Can't get the file", Objects.requireNonNull(App.GetGitFiles(file)).toString(), "App can get the file");
    }
}
