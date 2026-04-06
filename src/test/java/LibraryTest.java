import com.daniel99j.djutil.Either;
import com.daniel99j.djutil.TestCode;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    public static void main(String[] args) {
        if(TestCode.test()) System.out.println("Hello World");

        Either<Integer, String> either;
        if(TestCode.test()) either = Either.right("Hello World");
        else either = Either.left(1);
        System.out.println(either);
    }

    @Test
    public void test() {
        assert TestCode.test();
    }
}
