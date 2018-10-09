package com.eposi.fms.rest.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.eposi.common.util.AbstractBean;
import org.apache.log4j.Logger;

import java.util.Iterator;

public class RWsClient extends AbstractBean {
	private static final long serialVersionUID = 2008863338652648227L;
	private static Logger log = Logger.getLogger(RWsClient.class.getName());
	private String rootURI;
	private int    port;
	private String username;
	private String password;
	private String authorization;

	private CloseableHttpClient httpClient;
	private PoolingHttpClientConnectionManager mgr;


	public String buildFullURI(String path){
		return rootURI+path;
	}

	public String getObject(String path) {
		String response = null;
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(buildFullURI(path));
			httpGet.addHeader(HttpHeaders.AUTHORIZATION, getAuthorization());//"Basic c2NvdHQ6dGlnZXI="
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = httpClient.execute(httpGet, responseHandler);
		}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("RWsClient.getObject: " + e.getMessage()+":"+path);
		} finally {
			httpGet.reset();
		}

		return response;
	}

	public String postObject(String path, String jsonObject) {
		String response = null;
		HttpPost httpPost = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			httpPost = new HttpPost(buildFullURI(path));
			httpPost.addHeader(HttpHeaders.CONTENT_TYPE,"application/json;charset=UTF-8");
			httpPost.addHeader(HttpHeaders.AUTHORIZATION, getAuthorization());

			StringEntity stringEntity = new StringEntity(jsonObject, "UTF-8");
			stringEntity.setContentType("application/json;charset=UTF-8");
			httpPost.setEntity(stringEntity);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = httpClient.execute(httpPost, responseHandler);
		} catch(Exception e) {
			System.out.println("RWsClient.postObject: " + e.getMessage()+":"+path+"->"+jsonObject);
		} finally {
			httpPost.reset();
		}

		return response;
	}

	public String putObject(String path, String jsonObject) {
		String response = null;
		HttpPut httpPut = null;
		try {
			httpPut = new HttpPut(buildFullURI(path));
			httpPut.addHeader("Content-Type", "application/json;charset=UTF-8");
			httpPut.addHeader("Authorization", getAuthorization());
			StringEntity stringEntity = new StringEntity(jsonObject,"UTF-8");
			stringEntity.setContentType("application/json");
			httpPut.setEntity(stringEntity);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = httpClient.execute(httpPut, responseHandler);
		}  catch(Exception e) {
			e.printStackTrace();
			System.out.println("RWsClient.putObject: " + e.getMessage()+":"+path);
		} finally {
			httpPut.reset();
		}

		return response;
	}

	public String deleteObject(String path) {
		String response = null;
		HttpDelete httpDelete = null;
		try {
			httpDelete = new HttpDelete(buildFullURI(path));
			httpDelete.addHeader("Content-Type", "application/json;charset=UTF-8");
			httpDelete.addHeader("Authorization", getAuthorization());

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = httpClient.execute(httpDelete, responseHandler);
		}catch(Exception e) {
			System.out.println("RWsClient.deleteObject: " + e.getMessage()+":"+path);
		} finally {
			httpDelete.reset();
		}

		return response;
	}


	private void builAuthorization(){
		String strAut = username+":"+password;
		byte[]   bytesEncoded = Base64.encodeBase64(strAut.trim().getBytes());
		setAuthorization("Basic " + new String(bytesEncoded));
	}

	public void start() {
		builAuthorization();
		RequestConfig config = RequestConfig.custom().setConnectTimeout(120000).setConnectionRequestTimeout(180000).setSocketTimeout(300000).build();
		this.httpClient = HttpClients.custom().setDefaultRequestConfig(config).setConnectionManager(mgr).build();
	}

	public void restart() {
		try {
			shutdown();
		} catch (Exception e) {
		}

		try {
			start();
		} catch (Exception e) {
		}
	}

	public void shutdown() {
		this.mgr.shutdown();
	}

	public PoolingHttpClientConnectionManager getMgr() {
		return mgr;
	}

	public void setMgr(PoolingHttpClientConnectionManager mgr) {
		this.mgr = mgr;
	}

	public String getRootURI() {
		return rootURI;
	}

	public void setRootURI(String rootURI) {
		this.rootURI = rootURI;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public static void main(String[] args) {
		ObjectMapper objectMapper = new ObjectMapper();
		RWsClient client = new RWsClient();
		client.setRootURI("http://103.52.92.8/rest/api");
		client.setPort(80);
		client.setUsername("tienmanh");//tuannq
		client.setPassword("tieutruc");//eposi123@123a
		client.start();
		int unixTime=(int)(System.currentTimeMillis()/1000L);
		String path="/log/F49JZnir/range?from="+unixTime+"&to="+unixTime;
		String respone = client.getObject(path);
		if(respone!=null) {
			try {
				JsonNode node = objectMapper.readValue(respone, JsonNode.class);
				//Json paser
				JsonNode statusNode = node.get("status");
				String status = statusNode.asText();
				if (status.equals("success")) {
					JsonNode dataNode = node.get("data");
					Iterator<JsonNode> iterator = dataNode.iterator();
					if(!iterator.hasNext()){
						System.out.println("validate");
					}
				}
			}catch (Exception e){

			}
		}

	}

}
