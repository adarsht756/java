package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Model.Bank;
import Model.PaymentInstrument;

public class Router {
    private Map<PaymentInstrument, List<Bank>> methodBankMap = new HashMap<>();
    private Map<Bank, Integer> distributionMap = new HashMap<>();

    public void addBank(PaymentInstrument method, Bank bank, int percentage) {
        methodBankMap.computeIfAbsent(method, k -> new ArrayList<>()).add(bank);
        distributionMap.put(bank, percentage);
    }

    public Bank route(PaymentInstrument method) {
        List<Bank> banks = methodBankMap.get(method);
        if (banks == null || banks.isEmpty())
            return null;
        int totalPercentage = banks.stream().mapToInt(distributionMap::get).sum();
        int randomValue = new Random().nextInt(totalPercentage);
        int cumulativePercentage = 0;
        for (Bank bank : banks) {
            cumulativePercentage += distributionMap.get(bank);
            if (randomValue < cumulativePercentage) {
                return bank;
            }
        }
        return null;
    }

    public Map<Bank, Integer> getDistribution() {
        return distributionMap;
    }
}
