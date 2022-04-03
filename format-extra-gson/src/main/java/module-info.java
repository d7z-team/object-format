import org.d7z.objects.format.api.IFormat;
import org.d7z.objects.format.ext.json.GsonDataFormat;

module org.d7z.objects.format.ext.json {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive org.d7z.objects.format.core;
    requires com.google.gson;
    opens org.d7z.objects.format.ext.json;
    exports org.d7z.objects.format.ext.json;
    provides IFormat with GsonDataFormat;
}
