package com.config.server;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ServerConfig {
	   @NotBlank
	    private String host;

	    @Min(1024)
	    private int port;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		@Override
		public String toString() {
			return "ServerConfig [host=" + host + ", port=" + port + "]";
		}
	    
	    

}
