package com.cqmi.controller.orderi.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cqmi.db.util.BeanUtil;

import md5.JavaMD5;

public class HttpOrder {

	public static void main(String[] args) {
		HttpOrder d = new HttpOrder();
		try {
			// String bider = "重庆立言科技有限公司1";
			// String budget = "45600.09";
			// String contacts = "余婷婷";
			// String demandId = "250629511096250368";
			// String employer = "小莹";
			// String endDate = "Sat Aug 22 14:32:12 CST 2020";
			// String intermediateDate = "";
			// String phone = "18696753096";
			// String startDate = "Fri May 22 14:32:12 CST 2020";
			// String status = "03";
			// String title = "子平台分包对接";
			String token = d.getTokenByUrlpost();
			Map m = d.sendInserCheckUrlpost("250629511096250368", "中验收测试", "01", "", token);
			System.out.println(m.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map test_insertOrUpdate(String demandId, String title, String bider, String employer, String budget,
			String startDate, String intermediateDate, String endDate, String status, String contacts, String phone)
					throws ClientProtocolException, IOException {
		Map m = new HashMap();
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzeXN0ZW0iOiJ0cyIsImV4cCI6MTU5MDAzODY3MiwiY2xpZW50X2lkIjoiT3Z5aktrclAifQ.FQcgl0F0QD1HKFFHN77NLVYJZHzUGTxlzOc1lo2UOgWA-iIhIRMWznqlEHmKbsHNwO9PizElJMjC8ILTVaQomHWN5yt4KoXkoKFeTJlIhS8UBqRfUDx5L44kjGhHV46iBSV5oysnoQj0MKs6qjPBDb5r-e_Y3ysZRJ1aSfnAUsASedpgHXuiITOyCwxJw5-ySFdLyWalxEUvnNKeZwWZgpaaDKS6lFRR1Fifn6BBbS-bj9CPONAvt52yJG98C0qhdSgmTm8vjFLLbC2av1XqqT1MZmqB3_ICvIhbP6ngfcBoK__a9vKK_F68zJM4c5auti4J9IrkWGjFO4KVHB7Jaw";
		// 创建HttpClient实例     
		HttpClient httpclient = HttpClients.createDefault();
		// http://192.168.66.156:8080/calf/data?p=fun&m=synDataFromPlatform&ip=192.168.1.234&machineName=test&type=3&role=8&isEmail=1&mStatus=1&isBusiness=1&region=0
		String params = "http://121.43.157.157/SaiBao/dispatch/insert";
		// System.out.println(params);
		// 创建Get方法实例     
		HttpPost httppost = new HttpPost(params);
		httppost.setHeader("access-token", token.trim());
		// httppost.setHeader("Content-Type", "application/json");
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("demandId", demandId));
		parameters.add(new BasicNameValuePair("title", title));
		parameters.add(new BasicNameValuePair("bider", bider));
		parameters.add(new BasicNameValuePair("employer", employer));
		parameters.add(new BasicNameValuePair("budget", budget));
		parameters.add(new BasicNameValuePair("startDate", startDate));
		parameters.add(new BasicNameValuePair("intermediateDate", intermediateDate));
		parameters.add(new BasicNameValuePair("endDate", endDate));
		parameters.add(new BasicNameValuePair("status", status));
		parameters.add(new BasicNameValuePair("contacts", contacts));
		parameters.add(new BasicNameValuePair("phone", phone));
		httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

		String Key = "OvyjKkrP";
		String secretKey = "3ac74ea09e9f4593991d5962ca490a50";
		httppost.setHeader("KEY", Key);
		String paras = "/dispatch/insert?bider=" + bider + "&budget=" + budget + "&contacts=" + contacts + "&demandId="
				+ demandId + "&employer=" + employer + "&endDate=" + endDate + "&intermediateDate=" + intermediateDate
				+ "&phone=" + phone + "&startDate=" + startDate + "&status=" + status + "&title=" + title + secretKey;
		String urlencode = URLEncoder.encode(paras, "UTF-8");
		JavaMD5 str2 = new md5.JavaMD5();
		String _SN = "";
		try {
			_SN = str2.getMD5ofStr(urlencode.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httppost.setHeader("SN", _SN);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instreams = entity.getContent();
			String str = convertStreamToString(instreams);
			System.out.println(str);
			BeanUtil bul = new BeanUtil();
			try {
				m.putAll(bul.jsontoMap(str));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Do not need the rest    
			httppost.abort();
		}
		return m;
	}

	/**
	 * 使用HttpClient4.5 post提交multipart/form-data数据实现多文件上传
	 * 
	 * @param url
	 *            请求地址
	 * @param multipartFiles
	 *            post提交的文件列表
	 * @param fileParName
	 *            fileKey
	 * @param params
	 *            附带的文本参数
	 * @param timeout
	 *            请求超时时间(毫秒)
	 * @return
	 * @author alexli
	 * @date 2018年5月8日 上午10:26:15
	 */
	public static Map<String, String> uploadFile(String token, File file, String fileParName, int timeout) {
		Map<String, String> resultMap = new HashMap<String, String>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		try {
			String urli = Configs.getPatameters().getProperty("fileuploadUri");
			// String url = "http://fs.liyantech.cn/file/upload";
			// System.out.println(params);
			// 创建Get方法实例     
			HttpPost httpPost = new HttpPost(urli);

			httpPost.setHeader("access-token", token.trim());
			// httpPost.setHeader("Content-Type", "multipart/form-data");

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			if (file != null) {
				String fileName = file.getName();
				builder.addBinaryBody(fileParName, file, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
				System.out.println(fileName);
			}
			Map<String, Object> params = new HashMap();
			params.put("system", "ts");
			// 决中文乱码
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() == null) {
					continue;
				}
				// 类似浏览器表单提交，对应input的name和value
				builder.addTextBody(entry.getKey(), entry.getValue().toString());
			}
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);// 执行提交

			// 设置连接超时时间
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
					.setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
			httpPost.setConfig(requestConfig);

			HttpEntity responseEntity = response.getEntity();
			resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
			resultMap.put("data", "");
			if (responseEntity != null) {
				// 将响应内容转换为字符串
				result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
				BeanUtil bul = new BeanUtil();
				try {
					resultMap.putAll(bul.jsontoMap(result));
					System.out.println("result=" + result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				httpPost.abort();
			}
		} catch (Exception e) {
			resultMap.put("scode", "error");
			resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());

			Writer w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			System.out.println("HTTP请求出现异常: " + w.toString());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	public Map test_uploadFile(MultipartFile[] multipartFiles, String token)
			throws ClientProtocolException, IOException {
		Map m = new HashMap();
		try {
			// 创建HttpClient实例     
			HttpClient httpclient = HttpClients.createDefault();
			// http://192.168.66.156:8080/calf/data?p=fun&m=synDataFromPlatform&ip=192.168.1.234&machineName=test&type=3&role=8&isEmail=1&mStatus=1&isBusiness=1&region=0
			// String urli = "http://fs.liyantech.cn/file/upload";
			String urli = Configs.getPatameters().getProperty("fileuploadUri");

			// System.out.println(params);
			// 创建Get方法实例     
			HttpPost httppost = new HttpPost(urli);
			httppost.setHeader("access-token", token.trim());
			// httppost.setHeader("Content-Type", "multipart/form-data");
			// HttpEntity reqEntity =
			// MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
			// .addPart("file", bin)// 相当于<input type="file" name="media"/>
			// .addPart("system", ts) // 相当于<input type="text" name="name"
			// value=name>
			// .build();
			// httppost.setEntity(reqEntity);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			String fileName = null;
			MultipartFile multipartFile = null;
			for (int i = 0; i < multipartFiles.length; i++) {
				multipartFile = multipartFiles[i];
				fileName = multipartFile.getOriginalFilename();
				builder.addBinaryBody("file", multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA,
						fileName);// 文件流
			}
			builder.addTextBody("system", "ts");
			HttpEntity reqEntity = builder.build();
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				System.out.println(str);
				BeanUtil bul = new BeanUtil();
				try {
					m.putAll(bul.jsontoMap(str));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Do not need the rest    
				httppost.abort();
			}
		} catch (ClientProtocolException e1) {

		} catch (IOException e2) {

		} catch (Exception e2) {

		}
		return m;
	}

	public Map sendInserCheckUrlpost(String ordercode, String inspectedCase, String status, String fileIds,
			String token) throws ClientProtocolException, IOException {
		Map m = new HashMap();
		try {
			String urli = Configs.getPatameters().getProperty("insertCheckUri");
			// token="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzeXN0ZW0iOiJ0cyIsImV4cCI6MTU5MDQ2ODEyOCwiY2xpZW50X2lkIjoiT3Z5aktrclAifQ.jAjFKRIP6nzbQw0hI36XYwZ6jwI8MgBPjjzfjQ92vf3ockGbvItIMWjcN6K-l4ueJDINapt5ezXLnDwkbOCElYOCLvaAk-ZJYJZh6VJblKs1jWDKXvocpNobsImY3CiV5kJVmfEqweygN8APp0cskp2Li2GEoWDA1OFM8yJoh11YpN9jqmCYspOv-Xx8nh78KCTAU_W-15S2pQ-h55phc5k-yNdKxfmn2GQNQ9uZkir7IaYKMUlRnTielbf3mCsXl2Dmu_9C0kuwQwwuSlLEZAOsy6kHWKOIyiDFFP6--sLlb38Bcwq4MvWbIX5yfPtqPKFtP5Dw9tXy6uh78gkW5w";
			// 创建HttpClient实例     
			HttpClient httpclient = HttpClients.createDefault();
			// http://192.168.66.156:8080/calf/data?p=fun&m=synDataFromPlatform&ip=192.168.1.234&machineName=test&type=3&role=8&isEmail=1&mStatus=1&isBusiness=1&region=0
			String params = urli + "/tsAdmin/dispatch/insertCheck";
			// System.out.println(params);
			// 创建Get方法实例     
			HttpPost httppost = new HttpPost(params);
			httppost.setHeader("access-token", token.trim());
			httppost.setHeader("Content-Type", "application/json");
			// List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// parameters.add(new BasicNameValuePair("demandId", ordercode));
			// parameters.add(new BasicNameValuePair("inspectedCase",
			// inspectedCase));
			// parameters.add(new BasicNameValuePair("status", status));
			// parameters.add(new BasicNameValuePair("fileIds", fileIds));
			// httppost.setEntity(new UrlEncodedFormEntity(parameters,
			// "UTF-8"));

			String billList = "{\"demandId\":" + ordercode + ",\"inspectedCase\":\"" + inspectedCase
					+ "\",\"status\":\"" + status + "\",\"fileIds\":[" + fileIds + "]}";
			System.out.println(billList);
			httppost.setEntity(new StringEntity(billList, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				System.out.println(str);
				BeanUtil bul = new BeanUtil();
				try {
					m.putAll(bul.jsontoMap(str));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				httppost.abort();
			}
		} catch (ClientProtocolException e1) {

		} catch (IOException e2) {

		} catch (Exception e2) {

		}
		return m;
	}

	public String getTokenByUrlpost() {
		String token = "";
		try {

			String urli = Configs.getPatameters().getProperty("tokenUri");
			// 创建HttpClient实例     
			HttpClient httpclient = HttpClients.createDefault();
			// http://192.168.66.156:8080/calf/data?p=fun&m=synDataFromPlatform&ip=192.168.1.234&machineName=test&type=3&role=8&isEmail=1&mStatus=1&isBusiness=1&region=0
			String params = urli + "/tsOauth2/oauth/token";
			// System.out.println(params);
			// 创建Get方法实例     
			HttpPost httppost = new HttpPost(params);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
			parameters.add(new BasicNameValuePair("client_id", "OvyjKkrP"));
			parameters.add(new BasicNameValuePair("client_secret", "3ac74ea09e9f4593991d5962ca490a50"));
			httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				System.out.println(str);
				BeanUtil bul = new BeanUtil();
				try {
					Map m = bul.jsontoMap(str);
					if (m != null) {
						Map tokenMap = (Map) m.get("result");
						if (tokenMap != null) {
							token = tokenMap.get("access_token") + "";
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Do not need the rest    
				httppost.abort();
			}
		} catch (ClientProtocolException e1) {

		} catch (IOException e2) {

		} catch (Exception e2) {

		}
		return token;
	}

	private String convertStreamToString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}
}
