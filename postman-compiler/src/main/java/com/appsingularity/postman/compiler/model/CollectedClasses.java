package com.appsingularity.postman.compiler.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appsingularity.postman.compiler.model.classes.PostmenEnabledCollectedClass;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class CollectedClasses {

    private CollectedClasses() {
    }

    @Nullable
    public static CollectedClass obtain(@NonNull Types types, @NonNull Elements elements, @NonNull Element classElement) {
        CollectedClass collectedClass = null;
        if (PostmenEnabledCollectedClass.canProcessElement(classElement)) {
            collectedClass = new PostmenEnabledCollectedClass(classElement);
        }
        if (collectedClass != null) {
            List<? extends Element> children = classElement.getEnclosedElements();
            for (Element child : children) {
                collectedClass.addChild(types, elements, child);
            }
        }
        return collectedClass;
    }

}
