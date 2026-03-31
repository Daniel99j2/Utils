import com.daniel99j.djutil.TestCode;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    public static void main(String[] args) {
        if(TestCode.test()) System.out.println("Hello World");
    }

    @Test
    public void test() {
        assert TestCode.test();
    }
}
