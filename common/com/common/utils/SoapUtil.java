package com.common.utils;

import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * webservice请求
 * 
 * @author Administrator zwc
 * 
 */
public class SoapUtil {

	/**
	 * 获取webservice信息
	 * 
	 * @param url
	 *            webservice地址
	 * @param namespace
	 *            命名空间
	 * @param methodName
	 *            方法名
	 * @param requestParams
	 *            请求参数
	 * @return 返回webservice信息
	 */
	public static Object CallWebService(String url, String namespace,
			String methodName, Map<String, Object> requestParams) {
		String SOAP_ACTION = namespace + methodName;

		SoapObject request = new SoapObject(namespace, methodName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		if (requestParams != null) {
			for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
				request.addProperty(entry.getKey(), entry.getValue());
			}
		}

		envelope.bodyOut = request;
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(url, 5000);// 5s超时

		Object result = null;

		try {
			transport.call(SOAP_ACTION, envelope);
			result = envelope.getResponse();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(result.toString());

		return result;
	}

	/**
	 * 获取webservice信息
	 * 
	 * @param url
	 *            webservice地址
	 * @param namespace
	 *            命名空间
	 * @param methodName
	 *            方法名
	 * @param requestParams
	 *            请求参数
	 * @return 返回webservice信息
	 */
	public static String CallWebServiceForString(String url, String namespace,
			String methodName, Map<String, Object> requestParams) {
		try {
			return CallWebService(url, namespace, methodName, requestParams)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
