package org.whh.bz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.whh.bz.interceptor.WsInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private WsInterceptor myInterceptor;

    private WebSocketHttpAuthHandler httpAuthHandler;

    @Autowired
    public WebSocketConfig(WebSocketHttpAuthHandler httpAuthHandler, WsInterceptor myInterceptor) {
        this.httpAuthHandler = httpAuthHandler;
        this.myInterceptor = myInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "/wsCode")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}
