package com.luislr.zerif.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="storage")
public class StorageProperties {

  private final String location = "upload-dir";

}
