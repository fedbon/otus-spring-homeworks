package ru.fedbon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fedbon.domain.Butterfly;
import ru.fedbon.domain.Caterpillar;

@Service
@Slf4j
public class ButterflyTransformerImpl implements ButterflyTransformer {
    @Override
    public Butterfly transform(Caterpillar caterpillar) {
        log.info(caterpillar + " transform to butterfly");
        return new Butterfly(caterpillar.getName());
    }
}
