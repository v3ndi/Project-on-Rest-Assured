import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class UserTestRunner extends Setup {
    @Test(priority = 1,description = "login successfully")
    public void login() throws ConfigurationException{
        UserController userController = new UserController(props.getProperty("baseUrl"));
        String token =userController.login("salman@roadtocareer.net","1234");
        System.out.println(token);
    }
    @Test(priority = 2,description = "Agent created successfully")
    public void createAgent () throws  IOException, ParseException {
        UserController userController = new UserController(props.getProperty("baseUrl"),props.getProperty("token"));
        Faker faker=new Faker();
        UserModel userModel=new UserModel();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress());
        userModel.setPassword("1234");
        userModel.setPhone_number("0119"+Utils.generateRandomNumber(1000000,9999999));
        userModel.setNid("1234567890");
        userModel.setRole("Agent");
        Response res=userController.createUser(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath= res.jsonPath();
        String message=jsonPath.get("message").toString();
        Assert.assertTrue(message.contains("User created"));

        String id=jsonPath.get("user.id").toString();
        userModel.setId(id);
        String role=jsonPath.get("user.role").toString();
        userModel.setRole(role);
        String phone_number=jsonPath.get("user.phone_number").toString();
        userModel.setPhone_number(phone_number);
        Utils.savingAgentInfo(userModel);

    }

    @Test(priority = 3,description = "Customer created successfully")
    public void createUser () throws  IOException, ParseException {
        for (int i = 0; i < 2; i++) {
            UserController userController = new UserController(props.getProperty("baseUrl"), props.getProperty("token"));
            Faker faker = new Faker();
            UserModel userModel = new UserModel();
            userModel.setName(faker.name().fullName());
            userModel.setEmail(faker.internet().emailAddress());
            userModel.setPassword("1234");
            userModel.setPhone_number("0119" + Utils.generateRandomNumber(1000000, 9999999));
            userModel.setNid("1234567890");
            userModel.setRole("Customer");
            Response res = userController.createUser(userModel);
            System.out.println(res.asString());

            JsonPath jsonPath = res.jsonPath();
            String message = jsonPath.get("message").toString();
            Assert.assertTrue(message.contains("User created"));

            String id = jsonPath.get("user.id").toString();
            userModel.setId(id);

            String phone_number = jsonPath.get("user.phone_number").toString();
            userModel.setPhone_number(phone_number);
            Utils.savingUserInfo(userModel);         //saving to json
        }
    }
}
