package com.serenebond.persistent;

import java.io.DataOutput;
import java.io.IOException;

public interface Savable {

    void save(DataOutput dataOutput) throws IOException;
}
