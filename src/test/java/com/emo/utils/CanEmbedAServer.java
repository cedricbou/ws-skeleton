package com.emo.utils;

import com.caucho.resin.HttpEmbed;
import com.caucho.resin.ResinEmbed;
import com.caucho.resin.WebAppEmbed;

public abstract class CanEmbedAServer {

	protected ResinEmbed server = null;

	protected void startServer() {
		if (server != null) {
			throw new IllegalStateException("Server is already started");
		}

		server = new ResinEmbed();

		HttpEmbed http = new HttpEmbed(8080);
		server.addPort(http);

		WebAppEmbed webApp = new WebAppEmbed("/", "src/main/web");
		server.addWebApp(webApp);

		server.start();
	}
	
	protected void stopServer() {
		server.stop();
	}


}
