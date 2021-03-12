package com.kaka.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author Kaka
 */
public class UUIDutils {
    public static final Logger logger = LoggerFactory.getLogger(UUIDutils.class);

    public static String getUUID() {
        logger.info("創建UUID...");
        return UUID.randomUUID().toString().replace("-", "");
    }
}