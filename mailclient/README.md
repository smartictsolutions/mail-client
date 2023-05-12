# Mail Client

***Mail Client*** is a mail sender application that can easily configure and use. After the container image pulled and configure it, *Abracadabra* you can start sending mails right away.


### Project Info

It is a Gradle-Spring Framework project that has Rest endpoint.  


## Usage

### Configuration

##### Docker-Compose Configuration 

This service has own configurations that based on Gmail. Also another mail configurations accepted. For the adjustments *Environment Variables* can be set for your container that like below on the docker-compose file. 

```yaml
version: '3.9'
services:
  mail-client:
    container_name: mail-client-container
    image: smartictsolutions/mailclient:latest
    ports:
      - "7002:7002"
    environment:
      MAIL_HOST: smtp.gmail.com
      MAIL_PORT: 587
      MAIL_USERNAME: [your_mail]
      MAIL_PASSWORD: [your_password]
      MAIL_PROTOCOL: smtp
      MAIL_IS_AUTH: "true"
      MAIL_START_TLS_ENABLE: "true"
      MAIL_SSL_TRUST: smtp.gmail.com
      MAIL_FROM: [your_mail]
    volumes:
      - ./exposed_config/mail-client-config-base.yml:/config/mail-client-config.yml
    networks:
      mail-client-net:
        ipv4_address: 152.24.0.2 

networks:
  mail-client-net:
    name: mail-client-net
    ipam:
      driver: default
      config:
        - subnet: 152.24.0.0/16
          gateway: 152.24.0.1
```

Our service mainly considers *Environment Variables* for mail configurations. If these entries does not set, 

-   *MAIL_HOST:* SMTP host 
-   *MAIL_PORT:* SMTP port 
-   *MAIL_USERNAME:* SMTP mail username with full address (ex -> your_username@gmail.com)
-   *MAIL_PASSWORD:* SMTP mail password
-   *MAIL_PROTOCOL:* Mail protocol
-   *MAIL_IS_AUTH:* SMTP authentication parameter
-   *MAIL_START_TLS_ENABLE:* Start TLS enable parameter  
-   *MAIL_SSL_TRUST:* SMTP SSL trusted address 
-   *MAIL_FROM:* Mail from  

If you want use Gmail services , you must set only **MAIL_USERNAME** and **MAIL_PASSWORD**. Other mail service properties already set like below on application properties. 

> Note: If you use the gmail account, you must set **ON** *Enabling Less Secure App Access*. Otherwise there will be malfunction for our service. For further info : [Less secure apps & your Google Account](https://support.google.com/accounts/answer/6010255?hl=en#zippy=%2Cif-less-secure-app-access-is-on-for-your-account)
>
>*Update*:  According to google support (https://support.google.com/accounts/answer/6010255?hl=en) Less Secure App Permissions cannot be enabled after 30th May 22. So if you want use gmail anyway, you must use Application Passwords on Google Account settings and get an app password. After providing an app passsword, you must set MAIL_PASSWORD variable with this new password.  For further info : [Related StackOverflow Topic](https://stackoverflow.com/questions/70606793/nodemailer-with-gmail-there-was-an-error-error-invalid-login-535-5-7-8-user)


---



##### Application YAML Based Configuration

The other way of the setup mail configuration is editing application's own configuration file. This file must defined in the docker compose file as  a volume that mentioned at the previous section. As you can see below, **mail-client-config-base.yml** file must placed the right place.  

```yaml
version: '3.9'
services:
  mail-client:
    container_name: mail-client-container
    image: smartsolutions/mail-client-image:latest
    ports:
          - "7002:7002"
    volumes:
      - ./exposed_config/mail-client-config-base.yml:/config/mail-client-config.yml
```

 For this compose file there must be a **mail-client-config-base.yml** under **exposed_config** folder.

    <left><img src="C:\Users\ceyhun.yilmaz\AppData\Roaming\Typora\typora-user-images\image-20210902152349286.png" alt="image-20210902152349286" style="zoom:120%;" /></left>


*Exposed config folder:*  


    <left><img src="C:\Users\ceyhun.yilmaz\Desktop\image-20210902152511236.png" alt="image-20210902152511236" style="zoom:120%;" /></left>

Sample application's yaml file :

```yaml
server:
  port: 7002

spring:
  servlet:
    multipart:
      max-file-size: 100GB
      max-request-size: 100GB
  mail:
    host: smtp.gmail.com
    port: 587
    username: [your_mail]
    password: [your_mail_password]
    protocol: smtp
    tls: true
    from: [your_mail]
    properties.mail.smtp:
      auth: true
      starttls.enable: true
      ssl.trust: smtp.gmail.com
mail:
  shutdown-after-load: false
  is-test-env: true
  security:
    cors:
      allowed-hosts:
        - /**
```

- **server** 

- **port:** Application's serving port.   

    >   Note: This port must be exact same value on the docker-compose ***<ports>*** 's value.


- **spring\mail**

- **host:** SMTP host 

- **port:** SMTP port 

- **username:** SMTP mail username with full address (ex -> your_username@gmail.com)

- **password:** SMTP mail password

- **protocol:** Mail protocol

- **from:** Mail from property

- **is_auth:** SMTP authentication parameter

- **starttls.enable:** Start TLS enable parameter  

- **ssl.trust:** SMTP SSL trusted address 

 

### Docker

```shell
  docker run --name mail-client-container -p 7002:7002 -e MAIL_USERNAME=[your_mail] -e MAIL_PASSWORD=[your_password] -d smartsolutions/mail-client-image:latest
```

- ***Install with docker-compose***
First of all, you must create a docker-compose file. Depends on the configuration choice   

## API Endpoints 

**POST  mail/sendMail**

This endpoint includes 2 part (@RequestPart Json ,@RequestPart MultipartFile):

First part is mail body :

```
{
    "to":"dev@smartict.com.tr",
    "cc":"dev2@smartict.com.tr",
    "bcc":"dev3@smartict.com.tr",
    "subject":"Hello There!",
	"body":"This is Smart Mail Client Application :)"
}
```
Also CC and BCC addresses can be set.

Second part is optional (Nullable):

```
if you want to add attachment to your mail you can use "MultiPart File Data (multipart/form-data)" request .

Supported file formats: .pdf , .xlsx , .xls , .png , .jgp , .txt 
```

**GET /test** 

This endpoint placed for test to service is running.   


## Quick Reference

    - [Github repository]( https://github.com/smartictsolutions/mailclient)
    - [Docker Repository](https://hub.docker.com/r/smartictsolutions/mailclient)
    - [About us](https://www.smartict.com.tr/)