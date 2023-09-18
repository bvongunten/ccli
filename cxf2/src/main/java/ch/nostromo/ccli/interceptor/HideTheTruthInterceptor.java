package ch.nostromo.ccli.interceptor;


import org.apache.cxf.interceptor.LoggingOutInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HideTheTruthInterceptor extends LoggingOutInterceptor {


    public class LogFilter {
        private static final String REPLACEMENT = "XXXX";

        private static final String ELEMENT_REGEX = "<%s>(.+?)</%s>";

        private static final String HEADER_REGEXP = "%s=(.+?)]";

        private final List<Pattern> elementPatterns = new ArrayList<>();
        private final List<Pattern> headerPatterns = new ArrayList<>();

        public LogFilter(final List<String> elementsToFilter, final List<String> headersToFilter) {

            if (elementsToFilter != null) {
                for (final String elementToFilter : elementsToFilter) {
                    elementPatterns.add(Pattern.compile(String.format(ELEMENT_REGEX, elementToFilter, elementToFilter), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE));
                }
            }

            if (headersToFilter != null) {
                for (final String headerToFilter : headersToFilter) {
                    headerPatterns.add(Pattern.compile(String.format(HEADER_REGEXP, headerToFilter, headerToFilter), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE));
                }
            }
        }

        public String transform(final String originalLogString) {
            String result = originalLogString;

            for (Pattern pattern : elementPatterns) {
                Matcher matcher = pattern.matcher(originalLogString);
                while (matcher.find()) {
                    result = result.substring(0, matcher.start(1)) + REPLACEMENT + result.substring(matcher.end(1));
                }
            }

            for (Pattern pattern : headerPatterns) {
                Matcher matcher = pattern.matcher(originalLogString);
                while (matcher.find()) {
                    result = result.substring(0, matcher.start(1)) + REPLACEMENT + result.substring(matcher.end(1));
                }
            }

            return result;
        }

    }

    private final LogFilter loggingTransform;

    public HideTheTruthInterceptor(final List<String> tagsToFilter, final List<String> headersToFilter) {
        loggingTransform = new LogFilter(tagsToFilter, headersToFilter);
    }

    public HideTheTruthInterceptor() {
        loggingTransform = new LogFilter(new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public String transform(final String originalLogString) {
        try {
            return loggingTransform.transform(originalLogString);
        } catch (Exception ignore) {
            return originalLogString;
        }
    }


}
