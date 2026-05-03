package basicjava.java15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Java15Test {
    @Test
    void textBlocks() {
        var oldHtml = "    <html>\n" + //
                      "        <body>\n" + //
                      "            <p> '\"Hello world\"'' </p>\n" + //
                      "        </body>\n" + //
                      "    </html>\n";
        var html = """
                    <html>
                        <body>
                            <p> '"Hello world"'' </p>
                        </body>
                    </html>
                """;
        assertEquals(oldHtml, html);
    }
}
