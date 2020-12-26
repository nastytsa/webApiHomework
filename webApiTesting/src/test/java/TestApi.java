import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.io.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestApi {

    private static final String ACCESS_TOKEN = "k22xIkOEk4UAAAAAAAAAAQGnV7_UHGw6QPS6c49018sc7SeM3fSdgjytRrq0HVff";

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    @Test
    public void A_uploadTest() throws IOException, InterruptedException {

        disableWarning();

        JSONObject json = new JSONObject();
        json.put("mode","add");
        json.put("autorename", true);
        json.put("path","/text.txt");

        File file = new File("text.txt");

        RestAssured.given()
                .headers("Dropbox-API-Arg",json.toJSONString(),
                        "Content-Type","text/plain; charset=dropbox-cors-hack",
                        "Authorization", "Bearer " + ACCESS_TOKEN)
                .body(FileUtils.readFileToByteArray(file))
                .when().post("https://content.dropboxapi.com/2/files/upload")
                .then().statusCode(200);
    }

    @Test
    public void B_getMetadataTest(){

        disableWarning();

        JSONObject json = new JSONObject();
        json.put("path","/text.txt");

        RestAssured.given()
                .headers("Authorization", "Bearer " + ACCESS_TOKEN,
                        "Content-Type","application/json")
                .body(json.toJSONString())
                .when().post("https://api.dropboxapi.com/2/files/get_metadata")
                .then().statusCode(200);
    }

    @Test
    public void C_deleteTest(){

        disableWarning();

        JSONObject json = new JSONObject();
        json.put("path","/text.txt");

        RestAssured.given()
                .headers("Authorization", "Bearer " + ACCESS_TOKEN,
                        "Content-Type","application/json")
                .body(json.toJSONString())
                .when().post("https://api.dropboxapi.com/2/files/delete_v2")
                .then().statusCode(200);
    }
}
