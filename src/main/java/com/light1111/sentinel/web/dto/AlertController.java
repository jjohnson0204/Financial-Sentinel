package com.light1111.sentinel.web.dto;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class AlertController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/api/alerts/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamAlerts() {
        // High timeout so the connection stays open during your demo
        SseEmitter emitter = new SseEmitter(3600_000L);
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
        emitter.onError((ex) -> this.emitters.remove(emitter));

        return emitter;
    }

    public void sendAlert(LiveAlert alert) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(alert);
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }

    // Inner record for easy JSON conversion
    public record LiveAlert(
            String description,
            BigDecimal amount,
            String riskLevel,
            String recommendation,
            String categoryFocus
    ) {}
}