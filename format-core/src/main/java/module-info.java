import org.d7z.objects.format.api.IFormat;
import org.d7z.objects.format.api.SpiFormatContext;
import org.d7z.objects.format.rules.*;

module org.d7z.objects.format.core {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    exports org.d7z.objects.format;
    exports org.d7z.objects.format.api;
    exports org.d7z.objects.format.utils;
    opens org.d7z.objects.format.rules;
    exports org.d7z.objects.format.rules;
    uses IFormat;
    uses SpiFormatContext;
    provides IFormat with DateFormat, DateTimeFormat, BasicDataFormat, DefaultDataFormat, StringDataFormat;
}
