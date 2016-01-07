package com.common.utils.jsonparser;

public interface JSONParser<E> {
	public E parse(String str) throws ParseErrorException;
}
