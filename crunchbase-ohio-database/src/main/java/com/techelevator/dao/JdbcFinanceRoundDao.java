package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcFinanceRoundDao implements FinanceRoundDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcFinanceRoundDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createFinanceRound(List<String> financeRoundInfo) {
        String sql = "INSERT INTO finance_rounds_raw (transaction_name, organization_name, funding_type, money_raised, announced_date, funding_stage, pre_money_valuation, " +
                "equity_only_funding, organization_description, organization_industries, diversity_spotlight, organization_location, " +
                "organization_website, organization_revenue, total_funding_amount, funding_status, number_of_funding_rounds, " +
                "lead_investors, investors_names, number_of_investors, number_of_partner_investors) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING finance_round_id";
        jdbcTemplate.queryForObject(sql, Integer.class, financeRoundInfo.get(2), financeRoundInfo.get(4), financeRoundInfo.get(5), financeRoundInfo.get(6), financeRoundInfo.get(7), financeRoundInfo.get(8), financeRoundInfo.get(9), financeRoundInfo.get(10), financeRoundInfo.get(11), financeRoundInfo.get(12), financeRoundInfo.get(13), financeRoundInfo.get(14), financeRoundInfo.get(15), financeRoundInfo.get(16), financeRoundInfo.get(17), financeRoundInfo.get(18), financeRoundInfo.get(19), financeRoundInfo.get(20), financeRoundInfo.get(21), financeRoundInfo.get(22), financeRoundInfo.get(23));
    }

    @Override
    public void createInvestor(String investorName) {
        String sql = "INSERT INTO investors_raw (investor_name) " +
                "VALUES (?) RETURNING investor_id";
        jdbcTemplate.queryForObject(sql, Integer.class, investorName);
    }
}
