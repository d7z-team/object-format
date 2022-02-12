import org.d7z.objects.format.api.IFormat;
import org.d7z.objects.format.ext.time.DateTimeFormat;

module org.d7z.objects.format.ext.time {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive org.d7z.objects.format.core;
    opens org.d7z.objects.format.ext.time;
    exports org.d7z.objects.format.ext.time;
    provides IFormat with DateTimeFormat;
}
