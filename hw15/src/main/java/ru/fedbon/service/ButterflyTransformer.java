package ru.fedbon.service;

import ru.fedbon.domain.Butterfly;
import ru.fedbon.domain.Caterpillar;

public interface ButterflyTransformer {
    Butterfly transform(Caterpillar caterpillar);
}
