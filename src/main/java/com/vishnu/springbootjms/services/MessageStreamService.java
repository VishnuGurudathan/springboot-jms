package com.vishnu.springbootjms.services;

import com.vishnu.springbootjms.model.QueueMessage;
import com.vishnu.springbootjms.model.TempStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;

/**
 * Class description
 *
 * @author : vishnu.g
 * created on : 28/Aug/2020
 */
@Slf4j
@Service
public class MessageStreamService {

    /**
     * Send messages stored in the temp store as streams
     * @return {@link Flux<QueueMessage>}
     */
    public Flux<QueueMessage> messageStreams() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        interval.subscribe((i) -> log.info("collecting messages as stream"));

        Flux<QueueMessage> stockTransactionFlux = Flux.fromStream(TempStore.messageStore.stream());
        return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
    }
}
