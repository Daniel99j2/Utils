import com.daniel99j.djutil.Either;
import com.daniel99j.djutil.NumberUtils;
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
        Either<Integer, String> either = Either.right("Hello World");

        assert either.equals("Hello World");
        assert !either.equals("Hello World!!!");

        Either<Integer, String> either1 = Either.left(1);
        assert either1.equals(1);

        assert !either1.equals(either);

        Either<Integer, String> either2 = Either.left(5);
        Either<Integer, String> either3 = Either.left(5);
        assert either3.equals(either2);
        for (int i = 0; i < 100; i++) {
            assert NumberUtils.getRandomInt(0, 10) > -1;
            assert NumberUtils.getRandomInt(0, 10) < 11;
        }
    }
}
