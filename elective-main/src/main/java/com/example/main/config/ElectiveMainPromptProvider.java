package com.example.main.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Component
public class ElectiveMainPromptProvider implements PromptProvider {
    @Value("${elective-main.config.name}")
    private String name;

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(name + " >",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }
}
