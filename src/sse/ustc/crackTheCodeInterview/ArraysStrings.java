package sse.ustc.crackTheCodeInterview;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by SevenYoung on 15-6-12.
 */
public class ArraysStrings {
    public static void main(String[] args) {
        HashSet<Character> hs = new HashSet<>(16, (float) 0.5);
        String input = args[0];
        int inputLen = input.length();
        for(Character i:input.toCharArray())
            hs.add(i);

        boolean unique =  hs.size() == inputLen;
        System.out.println(" " + unique);
    }
}
