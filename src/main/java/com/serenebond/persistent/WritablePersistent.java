package com.serenebond.persistent;

import java.io.DataOutput;
import java.io.IOException;

@FunctionalInterface
public interface WritablePersistent {

    void write(DataOutput dataOutput) throws IOException;
}
