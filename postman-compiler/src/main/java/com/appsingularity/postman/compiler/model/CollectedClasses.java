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
    public static CollectedClass obtain(@NonNull Logger logger, @NonNull Types types, @NonNull Elements elements,
                                        @NonNull Element classElement) {
        CollectedClass collectedClass = null;
        if (PostmenEnabledCollectedClass.canProcessElement(logger, classElement)) {
            collectedClass = new PostmenEnabledCollectedClass(classElement);
        }
        if (collectedClass != null) {
            boolean hasError = false;
            List<? extends Element> children = classElement.getEnclosedElements();
            for (Element child : children) {
                if (!collectedClass.addChild(logger, types, elements, child)) {
                    hasError = true;
                }
            }
            // Check for errors and log it
            if (hasError) {
                logger.other(classElement,
                        "Not all attributes in '%s' were processed, please check the generated source for details.",
                        classElement.getSimpleName());
            }
        }
        return collectedClass;
    }

}
