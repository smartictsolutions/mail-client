/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.mail.dpu.system;

import com.smartict.mail.dpu.management.DpuStarterEvent;
import com.smartict.mail.service.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SystemDefaultDataDpu implements ApplicationListener<DpuStarterEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemDefaultDataDpu.class);
    private final MailService mailService;

    private final ApplicationContext appContext;

    public SystemDefaultDataDpu(MailService mailService, ApplicationContext appContext) {
        this.mailService = mailService;
        this.appContext = appContext;
    }

    @Override
    public void onApplicationEvent(@NonNull DpuStarterEvent event) {
        try {
            // Email kullanıcı adı ve şifre doğru olmasına rağmen hata verdiği için kapatıldı.
            /* LOGGER.info("Bağlantı testi " + System.getenv("MAIL_" + "username".toUpperCase()) + " isimli mail hesabı için yapılıyor .\n");
             * LOGGER.info("Bağlantı protokolü: " + System.getenv("MAIL_" + "protocol".toUpperCase()) + "\n"); LOGGER.info("Bağlanlanılan host: " +
             * System.getenv("MAIL_" + "host".toUpperCase()) + "\n"); LOGGER.info("10 saniyelik bağlantı testi yapılıyor..." + "\n");
             * mailService.testMailService(); LOGGER.info("Test Tamamlandı!"); } catch (Exception e) {
             * LOGGER.info("Bağlantı testi başarısız oldu !\nHesap ve konfigürasyon bilgilerinin doğruluğundan emin olunuz.\n Sistem kapatılıyor...");
             * SpringApplication.exit(this.appContext, () -> 1); System.exit(1); } */
        } catch (Exception e) {
            e.printStackTrace();
            SpringApplication.exit(this.appContext, () -> 1);
            System.exit(1);
        }
    }
}
