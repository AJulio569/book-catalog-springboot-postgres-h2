package com.book.utils;

public interface IConvertData {
    <T> T fromJson(String json, Class<T> tClass);
}
