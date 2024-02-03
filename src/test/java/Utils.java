import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Utils {
    public static void setEnvVariable(String key,String value) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }
    public static int generateRandomNumber(int min, int max){
        double rand = Math.random() * (max - min) + min;
        return (int) rand;
    }

    public static String savingAgentInfo(UserModel userModel) throws IOException, ParseException {

        String fileLocation= "./src/test/resources/agent.json";
        JSONParser parser= new JSONParser();
        JSONArray userArray = (JSONArray) parser.parse(new FileReader(fileLocation));
        JSONObject userObj = new JSONObject();
        userObj.put("id", userModel.getId());
        userObj.put("role", userModel.getRole());
        userObj.put("phone_number",userModel.getPhone_number());
        userArray.add(userObj);
        FileWriter writer=new FileWriter(fileLocation);
        writer.write(userArray.toJSONString());
        writer.flush();
        writer.close();
        return fileLocation;
    }
    public static String savingUserInfo(UserModel userModel) throws IOException, ParseException {

        String fileLocation= "./src/test/resources/user.json";
        JSONParser parser= new JSONParser();
        JSONArray userArray = (JSONArray) parser.parse(new FileReader(fileLocation));
        JSONObject userObj = new JSONObject();
        userObj.put("id", userModel.getId());
        userObj.put("role", userModel.getRole());
        userObj.put("phone_number",userModel.getPhone_number());
        userArray.add(userObj);
        FileWriter writer=new FileWriter(fileLocation);
        writer.write(userArray.toJSONString());
        writer.flush();
        writer.close();
        return fileLocation;
    }

    public static JSONObject getAgentFromJsonArray() throws IOException, ParseException {
        String fileLocation= "./src/test/resources/agent.json";
        JSONParser parser= new JSONParser();
        JSONArray userArray= (JSONArray) parser.parse(new FileReader(fileLocation));

        return (JSONObject) userArray.get(userArray.size()-1);
    }
    public static JSONObject getFirstCustomerFromJsonArray() throws IOException, ParseException {
        String fileLocation= "./src/test/resources/user.json";
        JSONParser parser= new JSONParser();
        JSONArray userArray= (JSONArray) parser.parse(new FileReader(fileLocation));

        return (JSONObject) userArray.get(userArray.size()-2);
    }

    public static JSONObject getSecondCustomerFromJsonArray() throws IOException, ParseException {
        String fileLocation= "./src/test/resources/user.json";
        JSONParser parser= new JSONParser();
        JSONArray userArray= (JSONArray) parser.parse(new FileReader(fileLocation));

        return (JSONObject) userArray.get(userArray.size()-1);
    }
}
