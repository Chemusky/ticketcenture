package es.ticketcenture.utilities;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageManager {

    private static MessageSource messageSource;

    public void setMessageSource(MessageSource source) {
        MessageManager.messageSource = source;
    }

    public static String get(String code) {
        try {
            return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return  code;
        }
    }
}
