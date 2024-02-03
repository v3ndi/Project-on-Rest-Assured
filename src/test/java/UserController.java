import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UserController {
    private String baseUrl;
    private String token;
    public UserController(String baseUrl,String token){
        this.baseUrl=baseUrl;
        this.token=token;
    }
    public UserController(String baseUrl){
        this.baseUrl=baseUrl;
    }

    public String login(String email,String password) throws ConfigurationException{
        RestAssured.baseURI=baseUrl;
        UserModel userModel=new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);
        Response res=given().contentType("application/json")
                .body(userModel)
                .when()
                .post("/user/login");
//        System.out.println(res.asString());
        JsonPath jsonPath= res.jsonPath();
        String token=jsonPath.get("token").toString();
        Utils.setEnvVariable("token",token);
        return token;
    }
    public Response createUser(UserModel userModel) {
        RestAssured.baseURI=baseUrl;
        Response res=given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .body(userModel)
                .when()
                .post("/user/create");
        return res;
    }
    public Response systemToAgent(UserModel userModel){
        RestAssured.baseURI=baseUrl;
        Response res=given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .body(userModel)
                .when()
                .post("/transaction/deposit");
        return res;
    }
    public Response customerToAgentWithdraw(UserModel userModel){
        RestAssured.baseURI=baseUrl;
        Response res=given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .body(userModel)
                .when()
                .post("/transaction/withdraw");
        return res;
    }
    public Response customerToSecondCustomer(UserModel userModel){
        RestAssured.baseURI=baseUrl;
        Response res=given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .body(userModel)
                .when()
                .post("/transaction/sendmoney");
        return res;
    }
    public Response merchantToSecondCustomerPayment(UserModel userModel){
        RestAssured.baseURI=baseUrl;
        Response res=given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .body(userModel)
                .when()
                .post("/transaction/payment");
        return res;
    }

    public Response secondCustomerBalance (String phone_number) throws IOException, ParseException {
        RestAssured.baseURI=baseUrl;
        Response res=given().contentType("application/json")
                .header("Authorization",token)
                .header("X-AUTH-SECRET-KEY","ROADTOSDET")
                .when()
                .get("/transaction/balance/"+phone_number);
        return res;
    }

}
