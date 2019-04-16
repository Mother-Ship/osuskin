package com.osuskin.cloud.util;

import com.osuskin.cloud.security.ScUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserUtil {
    public static ScUserDetails getUserDetails() {
        ScUserDetails userDetails = (ScUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }
}
