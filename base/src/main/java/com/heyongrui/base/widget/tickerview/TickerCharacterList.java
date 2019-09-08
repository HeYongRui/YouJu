package com.heyongrui.base.widget.tickerview;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lambert on 2018/10/11.
 */

public class TickerCharacterList {
    private final int numOriginalCharacters;
    // The saved character list will always be of the format: EMPTY, list, list
    private final char[] characterList;
    // A minor optimization so that we can cache the indices of each character.
    private final Map<Character, Integer> characterIndicesMap;

    TickerCharacterList(String characterList) {
        if (characterList.contains(Character.toString(TickerUtils.EMPTY_CHAR))) {
            throw new IllegalArgumentException(
                    "You cannot include TickerUtils.EMPTY_CHAR in the character list.");
        }

        final char[] charsArray = characterList.toCharArray();
        final int length = charsArray.length;
        this.numOriginalCharacters = length;

        characterIndicesMap = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            characterIndicesMap.put(charsArray[i], i);
        }

        this.characterList = new char[length * 2 + 1];
        this.characterList[0] = TickerUtils.EMPTY_CHAR;
        for (int i = 0; i < length; i++) {
            this.characterList[1 + i] = charsArray[i];
            this.characterList[1 + length + i] = charsArray[i];
        }
    }

    /**
     * @param start the character that we want to animate from
     * @param end the character that we want to animate to
     * @return a valid pair of start and end indices, or null if the inputs are not supported.
     */
    CharacterIndices getCharacterIndices(char start, char end) {
        int startIndex = getIndexOfChar(start);
        int endIndex = getIndexOfChar(end);
        if (startIndex < 0 || endIndex < 0) {
            return null;
        }

        // see if the wrap-around animation is shorter distance than the original animation
        if (start != TickerUtils.EMPTY_CHAR && end != TickerUtils.EMPTY_CHAR &&
                endIndex < startIndex) {
            final int nonWrapDistance = startIndex - endIndex;
            final int wrapDistance = numOriginalCharacters - startIndex + endIndex;
            if (wrapDistance < nonWrapDistance) {
                endIndex = endIndex + numOriginalCharacters;
            }
        }
        return new CharacterIndices(startIndex, endIndex);
    }

    Set<Character> getSupportedCharacters() {
        return characterIndicesMap.keySet();
    }

    char[] getCharacterList() {
        return characterList;
    }

    private int getIndexOfChar(char c) {
        if (c == TickerUtils.EMPTY_CHAR) {
            return 0;
        } else if (characterIndicesMap.containsKey(c)) {
            return characterIndicesMap.get(c) + 1;
        } else {
            return -1;
        }
    }

    class CharacterIndices {
        final int startIndex;
        final int endIndex;

        public CharacterIndices(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
    }
}
