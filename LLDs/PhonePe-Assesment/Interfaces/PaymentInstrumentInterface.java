package Interfaces;

import java.util.List;

import Model.Pair;

/**
 * PaymentInstrument
 */
public interface PaymentInstrumentInterface {
    public boolean validator(List<Pair<String, String>> inputFields);
}