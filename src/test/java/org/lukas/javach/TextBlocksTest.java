package org.lukas.javach;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextBlocksTest {

    @Test
    void testTextBlocks() {
        final String textBlock = """
            This is the first line of a text block.
            This is the second line of a text block.
            This is the third line of a text block.

            Above there is an empty line.
            """;


        final String[] lines = textBlock.split("\n");
        assertThat(lines[0], is(equalTo("This is the first line of a text block.")));
        assertThat(lines[1], is(equalTo("This is the second line of a text block.")));
        assertThat(lines[2], is(equalTo("This is the third line of a text block.")));
        assertThat(lines[3], is(equalTo("")));
        assertThat(lines[4], is(equalTo("Above there is an empty line.")));
    }
}
