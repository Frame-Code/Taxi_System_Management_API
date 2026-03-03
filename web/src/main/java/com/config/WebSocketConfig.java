package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*Se registra endpoint http que acepta upgrade a WebSocket, se define el punto de entrada al websocket
    * Permite que cualquier dominio pueda acceder al endpoint
    * SocketJs para soportar entornos sin web sockets
    * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ride-ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();

    }

    /*Se habilita un broker simple en memoria que empieza en /room, es la sala o canal para comunicar del servidor a clientes suscritos
    * Prefijo que el cliente usa para mandar un mensaje al servidor (destinos que entran a los @MessageMappingque)
    * */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/room");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
