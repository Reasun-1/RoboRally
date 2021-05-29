package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

// JsonTypeInfo IMPORTANT!! for extends relation between two classes
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class FeldObject {
}
