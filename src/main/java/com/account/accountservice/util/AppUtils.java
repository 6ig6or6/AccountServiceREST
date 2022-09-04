package com.account.accountservice.util;

import com.account.accountservice.model.user.AccessOperation;
import com.account.accountservice.model.user.Role;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AppUtils {
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        try {
            return Enum.valueOf(enumType, name);
        } catch (Exception e) {
            if (enumType.equals(Role.class)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
            } else if (enumType.equals(AccessOperation.class)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Operation");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
