package com.jeancabral.ToolsChalenge.util;

import java.util.List;
import java.util.function.Function;

public class CollectionUtil {
    
    
    public static <IN, OUT> List<OUT> mapTo(final List<IN> list, final Function<IN, OUT> mapper) {
        
        if (list == null) {
            return null;
        }
        
        return list.stream()
                .map(mapper)
                .toList();
    }
    
}
