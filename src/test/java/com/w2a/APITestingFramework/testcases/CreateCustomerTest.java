package com.w2a.APITestingFramework.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.APIs.CreateCustomerAPI;
import com.w2a.APITestingFramework.listeners.ExtentListeners;
import com.w2a.APITestingFramework.setUp.BaseTest;
import com.w2a.APITestingFramework.utilities.DataUtil;

import io.restassured.response.Response;
//import static io.restassured.RestAssured.*;

import java.util.Hashtable;

public class CreateCustomerTest extends BaseTest{

	//Valid key
	@Test(dataProviderClass=DataUtil.class,dataProvider="dp")
	public void validateCreateCustomerAPIWithValidSecretKey(Hashtable<String,String> data) {
		
		Response response = CreateCustomerAPI.sendPostRequestToCreateCustomerAPIWithValidAuthKey(data);
		ExtentListeners.testReport.get().info(data.toString());
		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	//Invalid key
	@Test(dataProviderClass=DataUtil.class,dataProvider="dp")
	public void validateCreateCustomerAPIWithInValidSecretKey(Hashtable<String,String> data) {
		
		Response response = CreateCustomerAPI.sendPostRequestToCreateCustomerAPIWithInValidAuthKey(data);
		ExtentListeners.testReport.get().info(data.toString());
		response.prettyPrint();
		System.out.println(response.statusCode());
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	
}