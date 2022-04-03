import org.d7z.objects.format.api.IFormat;
import org.d7z.objects.format.ext.json.JsonDataFormat;

module org.d7z.objects.format.ext.jackson {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive org.d7z.objects.format.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.kotlin;
    opens org.d7z.objects.format.ext.json;
    exports org.d7z.objects.format.ext.json;
    provides IFormat with JsonDataFormat;
}
