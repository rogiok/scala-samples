package share;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * User: Rogerio
 * Date: 6/18/13
 * Time: 10:57 PM
 */
public class ShareBetweenScalaAndJavaTest {

    @Test
    public void shareTest() {
        ShareBetweenScalaAndJava share = new ShareBetweenScalaAndJava(1);

        System.out.println(share.getA());

        assertTrue(true);
    }
}
