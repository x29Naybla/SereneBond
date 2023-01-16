package com.serenebond.persistent;

import java.io.DataInput;
import java.io.IOException;

public interface Loadable {

    void load(DataInput dataInput) throws IOException;
}
