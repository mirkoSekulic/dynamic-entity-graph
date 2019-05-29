package com.dynamic.graph.modules.shared.util;

public class FormattingUtil {
    private FormattingUtil() {
    }

    public static String likeFormat(String stringForLikeFormat) {
        return String.format("%%%s%%", stringForLikeFormat);
    }
}
