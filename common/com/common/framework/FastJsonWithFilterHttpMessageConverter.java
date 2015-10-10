package com.common.framework;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 可配置filter
 * 
 * @author 25019
 * 
 */
public class FastJsonWithFilterHttpMessageConverter extends
		FastJsonHttpMessageConverter {
	private SerializeFilter filter = null;

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		OutputStream out = outputMessage.getBody();
		String text = null;

		if (getFilter() != null)
			text = JSON.toJSONString(obj, filter, getFeatures());
		else
			text = JSON.toJSONString(obj, getFeatures());

		byte[] bytes = text.getBytes(getCharset());
		out.write(bytes);
	}

	public SerializeFilter getFilter() {
		return filter;
	}

	public void setFilter(SerializeFilter filter) {
		this.filter = filter;
	}
}
