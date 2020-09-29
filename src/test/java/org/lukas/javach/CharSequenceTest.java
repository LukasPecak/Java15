package org.lukas.javach;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CharSequenceTest {

    @Test
    void isEmpty_shouldReturnTrue_whenCharSequenceIsEmpty() {
        // given
        final CharSequence charSequence = "";

        // when
        final boolean isEmpty = charSequence.isEmpty();

        // then
        assertThat(isEmpty, is(true));
    }

    @Test
    void isEmpty_shouldReturnFalse_whenCharSequenceIsBlank() {
        // given
        final CharSequence charSequence = "    \t \n ";

        // when
        final boolean isEmpty = charSequence.isEmpty();

        // then
        assertThat(isEmpty, is(false));
    }

    @Test
    void isEmpty_shouldReturnFalse_whenCharSequenceIsNotEmpty() {
        // given
        final CharSequence charSequence = "Sample char sequence";

        // when
        final boolean isEmpty = charSequence.isEmpty();

        // then
        assertThat(isEmpty, is(false));
    }
}
