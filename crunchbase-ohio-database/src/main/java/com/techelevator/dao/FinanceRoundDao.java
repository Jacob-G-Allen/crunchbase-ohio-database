package com.techelevator.dao;

import java.util.List;

public interface FinanceRoundDao {

    void createFinanceRound(List<String> financeRoundInfo);

    void createInvestor(String investorName);
}
