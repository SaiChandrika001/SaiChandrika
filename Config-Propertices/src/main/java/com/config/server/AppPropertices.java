package com.config.server;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "app")
public class AppPropertices {
	
	  @NotBlank
	    private String name;

	    @Min(1)
	    private int version;

	    @Valid
	    private List<ServerConfig> servers;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public List<ServerConfig> getServers() {
			return servers;
		}

		public void setServers(List<ServerConfig> servers) {
			this.servers = servers;
		}

	    @Override
	    public String toString() {
	        return "AppProperties{" +
	                "name='" + name + '\'' +
	                ", version=" + version +
	                ", servers=" + servers +
	                '}';
	    }
	    
	    
}
