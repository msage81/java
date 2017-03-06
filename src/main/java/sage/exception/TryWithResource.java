package sage.exception;

import java.io.*;

public class TryWithResource {
    public String closeFileOnExceptionBecauseAutoCloseable() throws Exception {
        try(BufferedReader s = new BufferedReader(new FileReader(new File("c://ThisIsNotWindows.txt")));) {
            return s.readLine();
        }
    }

    public static void main(String[] args) throws Exception {
        TryWithResource t = new TryWithResource();
        t.closeFileOnExceptionBecauseAutoCloseable();
    }
}
