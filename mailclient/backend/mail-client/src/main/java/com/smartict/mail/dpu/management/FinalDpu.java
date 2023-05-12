/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.mail.dpu.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class FinalDpu implements ApplicationListener<DpuStarterEvent> {

    private final ApplicationContext appContext;

    /**
     * Bu değer dbLoad gradle taskında veritabanına başlangıç verisini yükledikten sonra uygulama kendisini otomatik olarak kapatsın diye kullanılıyor
     */
    @Value("${mail.shutdown-after-load}")
    private Boolean shutDownAfterLoad;

    public FinalDpu(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public void onApplicationEvent(@NonNull DpuStarterEvent event) {
        try {
            // SecurityContextHolder.getContext().setAuthentication(null);

            if (this.shutDownAfterLoad) {
                SpringApplication.exit(this.appContext, () -> 0);
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            SpringApplication.exit(this.appContext, () -> 1);
            System.exit(1);
        }
    }
}
