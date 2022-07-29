package stat;
import utils.Tag;

public interface Stat extends Tag {
    static Stat getRandom() {
        return () -> "UNDEFINED";
    };
}
