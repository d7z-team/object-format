import org.d7z.objects.format.api.IFormat;
import org.d7z.objects.format.rules.BasicDataFormat;
import org.d7z.objects.format.rules.DefaultDataFormat;

module org.d7z.objects.format.core {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    exports org.d7z.objects.format;
    exports org.d7z.objects.format.api;
    opens org.d7z.objects.format.rules;
    exports org.d7z.objects.format.rules;
    provides IFormat with BasicDataFormat, DefaultDataFormat;
}
