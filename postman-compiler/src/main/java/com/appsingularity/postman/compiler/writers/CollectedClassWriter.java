package com.appsingularity.postman.compiler.writers;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.util.Elements;


public interface CollectedClassWriter {


    void writeToFile(@NonNull Elements elements, @NonNull Filer filer)
            throws IOException;

}
