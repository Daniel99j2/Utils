import com.daniel99j.djutil.enumrecord.*;

public class TestEnumRecord extends EnumRecord {
    public static final SimpleEnumRecordType B = new SimpleEnumRecordType();
    public static final ComplexEnumRecordType<Integer> C = new ComplexEnumRecordType<>();
    static {
        EnumRecord.init(TestEnumRecord.class);
    }
}
