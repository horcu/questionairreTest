package misc;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Horatio on 3/28/2016.
 */
public class KeyGenerator {

    public static String GenerateGameboardKey()
    {
        String first = new Date().toString();
        String second = UUID.randomUUID().toString();
        String third = String.valueOf(new Random(1000009).nextInt(9999999)).substring(2,5);

        return first + "__" + second + "_-_" + third;
    }
}
