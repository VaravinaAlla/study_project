package io.qameta.junit5;

import com.codeborne.selenide.Selenide;
import dto.RandomTestData;
import helpers.DbManager;
import helpers.SetupFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.OrderPage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.codeborne.selenide.Selenide.*;

public class IntegrationTest {
    static Connection connection;
    static SetupFunctions setupFunctions;

    @Test
    public void createOrderApiAndCheckNumber() {
        RandomTestData randomOrder = new RandomTestData();
        RandomTestData order = randomOrder.createOrderApiAndReturnReceiveOrder();
        int orderID = order.getId();
        setupFunctions = new SetupFunctions();
        open(setupFunctions.getBaseUrlWeb());
        Selenide.localStorage().setItem("jwt", setupFunctions.getToken());
        Selenide.refresh();
        OrderPage orderPage = new OrderPage();
        orderPage.clickSearchOrderButton();
        orderPage.insertOrderNumber(String.valueOf(orderID));
        orderPage.clickSubmitSearchButton();
        orderPage.checkStatusOrderPageIsOpened();
        closeWebDriver();
    }

    @Test
    public void createOrderApiAndCheckItInDb() {
        RandomTestData randomOrder = new RandomTestData();
        RandomTestData order = randomOrder.createOrderApiAndReturnReceiveOrder();
        int orderID = order.getId();
        String customerRandomName = order.getCustomerName();
        DbManager dbManager = new DbManager();
        connection = dbManager.connectToDb();
        RandomTestData orderFromDb = executeSearchAndCompare(orderID);
        Assertions.assertEquals(orderID, orderFromDb.getId());
        Assertions.assertEquals(customerRandomName, orderFromDb.getCustomerName());
        dbManager.close(connection);
    }

    public RandomTestData executeSearchAndCompare(int orderId) {
        String sql = String.format("select * from orders where id = %d ;", orderId);
        System.out.println();
        RandomTestData orderFromBd = null;
        try {
            System.out.println("Executing sql ... ");
            System.out.println("SQL is : " + sql);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    int customerIdFromDb = resultSet.getInt(1);
                    String customerNameFromDb = resultSet.getString(4);
                    String customerPhoneFromDb = resultSet.getString(5);
                    String customerCommentFromDb = resultSet.getString(6);
                    System.out.println(customerIdFromDb + ","
                            + resultSet.getString(3) + ","
                            + customerNameFromDb + ","
                            + customerPhoneFromDb);
                    orderFromBd = new RandomTestData(0, customerNameFromDb,customerPhoneFromDb,customerCommentFromDb,customerIdFromDb);
                }

            } else {
                Assertions.fail("Result set is null");
            }
        } catch (SQLException e) {
            System.out.println("Error while executing sql ");
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            e.printStackTrace();
            Assertions.fail("SQLException");
        }
        return orderFromBd;
    }
}
