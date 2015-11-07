package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;

import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.writers.CollectedClassWriter;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public interface CollectedClass {

    Element getClassElement();

    List<CollectedField> getFields();

    void addChild(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements, @NonNull Element child);

    CollectedClassWriter getWriter();

}
