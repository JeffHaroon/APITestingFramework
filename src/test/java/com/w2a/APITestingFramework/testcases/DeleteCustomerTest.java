package com.w2a.APITestingFramework.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.APIs.DeleteCustomerAPI;
import com.w2a.APITestingFramework.listeners.ExtentListeners;
import com.w2a.APITestingFramework.setUp.BaseTest;
import com.w2a.APITestingFramework.utilities.DataUtil;
import com.w2a.APITestingFramework.utilities.TestUtil;

import io.restassured.response.Response;
//import static io.restassured.RestAssured.*;

import java.util.Hashtable;

public class DeleteCustomerTest extends BaseTest{

	//Valid key
	@Test(dataProviderClass=DataUtil.class,dataProvider="dp")
	public void deleteCustomer(Hashtable<String,String> data) {
		
		Response response = DeleteCustomerAPI.sendDeleteRequestToDeleteCustomerAPIWithValidID(data);
		response.prettyPrint();
		ExtentListeners.testReport.get().info(data.toString());	
		System.out.println(response.statusCode());
		
		/* One way of checking if the deleted ID matches with ID in input excel file
		String actual_id = response.jsonPath().get("id").toString();
		System.out.println("Getting id from Json Path: " + actual_id);
		Assert.assertEquals(actual_id, data.get("id"), "ID not matching");
		*/
		
		//Second way of checking
		/*JSONObject jsonObj = new JSONObject(response.asString());
		System.out.println(jsonObj.has("id"));
		Assert.assertTrue(jsonObj.has("id"), "ID key is not present in Json response");
		String actual_id = jsonObj.get("id").toString();
		System.out.println(actual_id);
		Assert.assertEquals(actual_id, data.get("id"), "ID not matching");*/
		
		
		
		//Third way of checking
		System.out.println("Presence check for Object Key: " + TestUtil.jsonHasKey(response.asString(),"object"));
		System.out.println("Presence check for Deleted Key: " +TestUtil.jsonHasKey(response.asString(), "deleted"));
		Assert.assertTrue(TestUtil.jsonHasKey(response.asString(),"id"), "ID key is not present in Json response");
		
		String actual_id = TestUtil.getJsonKeyValue(response.asString(),"id");
		System.out.println(actual_id);
		Assert.assertEquals(actual_id, data.get("id"), "ID not matching");
		
		System.out.println("Object key value is:  "+TestUtil.getJsonKeyValue(response.asString(), "object"));
		System.out.println("Deleted key value is: "+TestUtil.getJsonKeyValue(response.asString(), "deleted"));
		
		
		Assert.assertEquals(response.statusCode(), 200);
	}	
}
