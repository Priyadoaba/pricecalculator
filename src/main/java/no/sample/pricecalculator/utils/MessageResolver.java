package no.sample.pricecalculator.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {

    private final MessageSource messageSource;

    public MessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String resolve(String errorMessage) {
        if (errorMessage != null && errorMessage.startsWith("{") && errorMessage.endsWith("}")) {
            String messageKey = errorMessage.substring(1, errorMessage.length() - 1);
            try {
                return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
            } catch (NoSuchMessageException noSuchMessageException) {
                return errorMessage;
            }
        }
        return errorMessage;
    }
}
