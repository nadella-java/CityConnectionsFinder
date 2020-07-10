package com.javapropjects.ccf.util;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
    
    public boolean isEmpty(String val) {
        if(val != null && val != "")
            return false;
        return true;
    }

    public boolean isObjectEmpty(Object object) {
        if(object != null)
            return false;
        return true;
    }

    public String lrtrim(String str) {
	    String trimVal = "";
	    trimVal = str.replaceAll("^\\s+", "");
	    trimVal = trimVal.replaceAll("\\s+$", "");
	    
	    return trimVal;
		

    }
}