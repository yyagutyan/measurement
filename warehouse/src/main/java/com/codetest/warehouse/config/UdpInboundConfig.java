package com.codetest.warehouse.config;

import com.codetest.warehouse.model.SensorType;
import com.codetest.warehouse.service.UdpMessageHandlerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class UdpInboundConfig {

    @Value("${sensor.humidity.udp.port}")
    private int humidyUdpPort;

    @Value("${sensor.humidity.topic.name}")
    private String humidityTopic;

    @Value("${sensor.temperature.udp.port}")
    private int temperatureUdpPort;

    @Value("${sensor.humidity.topic.name}")
    private String temperatureTopic;

    private final UdpMessageHandlerService udpMessageHandlerService;

    public UdpInboundConfig(UdpMessageHandlerService udpMessageHandlerService) {
        this.udpMessageHandlerService = udpMessageHandlerService;
    }

    @Bean
    public UnicastReceivingChannelAdapter temperatureUnicastReceivingChannelAdapter() {
        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(temperatureUdpPort);
        adapter.setOutputChannel(temperatureReceivedDataChannel());
        return adapter;
    }

    @Bean
    public MessageChannel temperatureReceivedDataChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "temperatureReceivedDataChannel")
    public MessageHandler handleTemperatureData() {
        return message -> {
            byte[] payload = (byte[]) message.getPayload();
            udpMessageHandlerService.handleReceivedData(payload, SensorType.Temperature, temperatureTopic);
        };
    }

    @Bean
    public UnicastReceivingChannelAdapter humidityUnicastReceivingChannelAdapter() {
        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(humidyUdpPort);
        adapter.setOutputChannel(humidityReceivedDataChannel());
        return adapter;
    }

    @Bean
    public MessageChannel humidityReceivedDataChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "humidityReceivedDataChannel")
    public MessageHandler handleHumidityData() {
        return message -> {
            byte[] payload = (byte[]) message.getPayload();
            udpMessageHandlerService.handleReceivedData(payload, SensorType.Humidity, humidityTopic);
        };
    }
}
