package com.serenebond.persistent;

import java.io.DataInput;
import java.io.IOException;

@FunctionalInterface
public interface ReadablePersistent {

    void read(DataInput dataInput) throws IOException;
}
