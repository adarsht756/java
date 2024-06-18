import java.util.List;

/**
 * PaymentInstrument
 */
public interface PaymentInstrumentInterface {
    public boolean validator(List<Pair<String, String>> inputFields);
}