import com.daniel99j.djutil.Either;
import com.daniel99j.djutil.NumberUtils;
import com.daniel99j.djutil.TestCode;
import com.daniel99j.djutil.maths.MathsContext;
import com.daniel99j.djutil.maths.MathsInterpreter;
import com.daniel99j.djutil.pathfinder.PathfindPos;
import com.daniel99j.djutil.pathfinder.Pathfinder;
import com.daniel99j.djutil.pathfinder.PathfinderOptions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LibraryTest {
    public static void main(String[] args) {
        if(TestCode.test()) System.out.println("Hello World");

        Either<Integer, String> either;
        if(TestCode.test()) either = Either.right("Hello World");
        else either = Either.left(1);
        System.out.println(either);
    }

    @Test
    public void testMaths() {
        double v = MathsInterpreter.eval("10x+8+${test}", MathsContext.create().withGlobalVariable("x", "5").withVariable("test", "5"));
        System.out.println(v);
        assert v == 63;

        double v3 = MathsInterpreter.eval("50+(5*(8+(3-2)))");
        System.out.println(v3);
        assert v3 == 50+(5*(8+(3-2)));

        double v2 = MathsInterpreter.eval("sin(12345.6)");
        System.out.println(v2);
        assert v2 == Math.sin(12345.6);

        double v4 = MathsInterpreter.eval("-5*-2");
        System.out.println(v4);
        assert v4 == 10;


        for (int i = 0; i < 10000000; i++) {
            //no cache: 1m 51s
            //cache: 8s
            //fast cache: 2s
            MathsInterpreter.eval("tttttttttttttttttttttttt", MathsContext.create().withFastCache().withGlobalVariable("t", "(1)(2)(3)(4)(5)(6)"));
        }
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


        //the pathfinder finds a path correctly
        PathfindPos start = new PathfindPos(0, 0);
        PathfindPos end = new PathfindPos(50, 60);
        List<PathfindPos> visited = new ArrayList<>();

        PathfinderOptions options = PathfinderOptions.builder()
                .diagonalNeighbourProvider()
                .maxIterations(1000)
                .onVisitConsumer(visited::add)
                .build();

        List<PathfindPos> path = Pathfinder.findPath(start, end, options);
        assert !path.isEmpty();
        assert path.getFirst().equals(start);
        assert path.getLast().equals(end);
        assert visited.contains(start);




        Integer i = TestEnumRecord.C.create(2).value();
        assert i == 2;

        assert TestEnumRecord.C.create(2).equals(TestEnumRecord.C.create(2));
        assert !TestEnumRecord.B.equals(TestEnumRecord.C);
    }
}
