package birintsev.artplace;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import java.util.Locale;

@SpringBootApplication
@Configuration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
            = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        return messageSource;
    }

    @Bean(name = "LocaleDefault")
    Locale localeDefault(
        @Value("${ap.application.locale}") String localeDefault
    ) {
        return new Locale(localeDefault);
    }
}
