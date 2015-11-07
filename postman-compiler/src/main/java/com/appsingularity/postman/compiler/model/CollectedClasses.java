package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.compiler.Logger;
import com.appsingularity.postman.compiler.model.classes.PostmenEnabledCollectedClass;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class CollectedClasses {

    private CollectedClasses() {
    }

    @Nullable
    public static CollectedClass obtain(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements, @NonNull Element classElement) {
        CollectedClass collectedClass = null;
        if (PostmenEnabledCollectedClass.canProcessElement(classElement)) {
            collectedClass = new PostmenEnabledCollectedClass(classElement);
        }
        if (collectedClass != null) {
            List<? extends Element> children = classElement.getEnclosedElements();
            for (Element child : children) {
                collectedClass.addChild(logger, types, elements, child);
            }
        } else {
            logger.warn(classElement, "Class file annotated with @PostmanEnabled but it could not be processed.");
        }
        return collectedClass;
    }

}
