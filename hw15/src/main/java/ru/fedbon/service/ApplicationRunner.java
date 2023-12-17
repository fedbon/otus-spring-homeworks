package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fedbon.domain.Caterpillar;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationRunner {

    private final ButterflyTransformGateway butterflyTransformGateway;


    public void run() {
        var butterflies = butterflyTransformGateway.process(
                List.of(new Caterpillar("RED"),
                        new Caterpillar("BLUE"),
                        new Caterpillar("GREEN"),
                        new Caterpillar("ORANGE"),
                        new Caterpillar("PURPLE"))
        );
        log.info(butterflies.toString());
    }

}
