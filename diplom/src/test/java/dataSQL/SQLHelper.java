package dataSQL;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class StatusCheck {
        String status = getStatusCard();
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var conn = getConn()) {
            QUERY_RUNNER.execute(conn, "DELETE FROM credit_request_entity");
            QUERY_RUNNER.execute(conn, "DELETE FROM payment_entity");
            QUERY_RUNNER.execute(conn, "DELETE FROM order_entity");
        }
    }

    @SneakyThrows
    public static String getStatusCard() {
        var statusCardPay = "SELECT status FROM payment_entity";
        try (var conn = getConn()) {
            return QUERY_RUNNER.query(conn, statusCardPay, new ScalarHandler<>());

        }
    }

    @SneakyThrows
    public static String getStatusCredit() {
        var statusCreditPay = "SELECT status FROM credit_request_entity";
        try (var conn = getConn()) {
            return QUERY_RUNNER.query(conn, statusCreditPay, new ScalarHandler<>());
        }
    }


}