import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TransactionTestRunner extends Setup {
    @Test(priority = 1,description = "Agent successfully received DEPOSIT from SYSTEM")
    public void systemToAgent () throws IOException, ParseException {
        UserController userController = new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        UserModel userModel = new UserModel();
        userModel.setFrom_account("SYSTEM");
        String to_account = Utils.getAgentFromJsonArray().get("phone_number").toString();
        userModel.setTo_account(to_account);
        userModel.setAmount(2000);
        Response res = userController.systemToAgent(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath= res.jsonPath();
        String message=jsonPath.get("message").toString();
        Assert.assertTrue(message.contains("Deposit successful"));
    }
    @Test(priority = 2,description = "Agent successfully DEPOSIT to Customer")
    public void agentToFirstCustomer () throws IOException, ParseException {
        UserController userController = new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        UserModel userModel = new UserModel();
        String agentPhoneNumber = Utils.getAgentFromJsonArray().get("phone_number").toString();
        userModel.setFrom_account(agentPhoneNumber);
        String firstCustomerPhoneNumber = Utils.getFirstCustomerFromJsonArray().get("phone_number").toString();
        userModel.setTo_account(firstCustomerPhoneNumber);
        userModel.setAmount(1500);
        Response res = userController.systemToAgent(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath= res.jsonPath();
        String message=jsonPath.get("message").toString();
        Assert.assertTrue(message.contains("Deposit successful"));
    }
    @Test(priority = 3,description = "Customer successfully able to WITHDRAW from Agent")
    public void firstCustomerToAgentWithdraw () throws IOException, ParseException {
        UserController userController = new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        UserModel userModel = new UserModel();
        String firstCustomerPhoneNumber = Utils.getFirstCustomerFromJsonArray().get("phone_number").toString();
        userModel.setFrom_account(firstCustomerPhoneNumber);
        String agentPhoneNumber = Utils.getAgentFromJsonArray().get("phone_number").toString();
        userModel.setTo_account(agentPhoneNumber);
        userModel.setAmount(500);
        Response res = userController.customerToAgentWithdraw(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath= res.jsonPath();
        String message=jsonPath.get("message").toString();
        Assert.assertTrue(message.contains("Withdraw successful"));
    }

    @Test(priority = 4,description = "A Customer successfully able to SEND MONEY to another Customer")
    public void firstCustomerToSecondCustomer () throws IOException, ParseException {
        UserController userController = new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        UserModel userModel = new UserModel();
        String firstCustomerPhoneNumber = Utils.getFirstCustomerFromJsonArray().get("phone_number").toString();
        userModel.setFrom_account(firstCustomerPhoneNumber);
        String secondCustomerPhoneNumber = Utils.getSecondCustomerFromJsonArray().get("phone_number").toString();
        userModel.setTo_account(secondCustomerPhoneNumber);
        userModel.setAmount(500);
        Response res = userController.customerToSecondCustomer(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath= res.jsonPath();
        String message=jsonPath.get("message").toString();
        Assert.assertTrue(message.contains("Send money successful"));
    }
    @Test(priority = 5,description = "Recipient customer successfully able to Make PAYMENT to merchant")
    public void paymentFromSecondCustomerToMerchant () throws IOException, ParseException {
        UserController userController = new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        UserModel userModel = new UserModel();
        String secondCustomerPhone = Utils.getSecondCustomerFromJsonArray().get("phone_number").toString();
        userModel.setFrom_account(secondCustomerPhone);
        userModel.setTo_account("01686606905");
        userModel.setAmount(100);
        Response res = userController.merchantToSecondCustomerPayment(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message").toString();
        Assert.assertTrue(message.contains("Payment successful"));
    }
    @Test(priority = 6,description = "Checking Recipient customer's Updated Balance")
    public void secondCustomersUpdatedBalance () throws IOException, ParseException {
        UserController userController = new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
        Response res = userController.secondCustomerBalance(Utils.getSecondCustomerFromJsonArray().get("phone_number").toString());
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String message = jsonPath.get("message").toString();
        Assert.assertTrue(message.contains("User balance"));
    }
}
