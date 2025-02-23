package com.example.text.exception;

import com.example.text.utils.CollectionUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

public class AssertUtils {

    public static void isNotEmpty(List list, String message) {
        if (CollectionUtils.isEmpty(list)) {
            throw new TextException(message);
        }
    }

    public static void isOneItem(List list, String message) {
        if (CollectionUtils.isEmpty(list) || list.size() != 1) {
            throw new TextException(message);
        }
    }

    public static void notNull(String str, String message) {
        if (str != null && !Strings.EMPTY.equals(str)) {
            return;
        }
        throw new TextException(message);

    }
}
